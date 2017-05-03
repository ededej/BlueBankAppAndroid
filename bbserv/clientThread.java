import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class clientThread extends Thread{
	private BufferedReader is = null;
	private PrintStream os = null;
	private Socket clientSocket = null;
	private HashMap<String,Dude> users;
	private clientThread[] room;
	private Object lock;
	private Dude res;
	private boolean error = false;
	private String errMsg = "Something went wrong!";
	
	// Constructor.  Takes refs to all relevant fields from main server thread.
	public clientThread(Socket socket, HashMap<String,Dude> users, Object lock, clientThread[] room) {
		this.clientSocket = socket;
		this.users = users;
		this.lock = lock;
		this.room = room;
	}
	
	// Thread process - Runs when we start a new thread.
	public void run() {
		try{
			// Create IO streams for the client.  Each socket has an input and an output.
			// Think of these like System in and System out, but via network.
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
			
			// Unpack request, get lock, and do stuff.
			doLogic(is.readLine().split(Dude.DELIM));
			
			synchronized(lock){
				writeTable();
			}
			
			// Release common server resources.
			roomRelease(room);
			
			// Return user state.
			os.println(error ? "Error"+Dude.DELIM+errMsg : res.toString());
			
			// Close sockets and streams.
			is.close();
			os.close();
			clientSocket.close();
			
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	// Authenticate users based on a username and password.  Query into the main HashTable of users.
	public boolean authUser(String u, String p){
        String hashp = hashPass(p);
		//System.out.println(hashp);
		if (!users.containsKey(u) || !users.get(u).password.equals(hashp)){
			error = true;
			errMsg = "Authentication failed. Invalid username or password";
			//System.out.println(hashp);
			return false;
		}
		return true;
	}
	
	// Frees the resources for the current connection.
	public void roomRelease(clientThread[] room){
		for (int i = 0; i < room.length; i++){
			if (room[i] == this){
				room[i] = null;
				System.out.println("Client has been released.");
			}				
		}
	}
	
	// Main request handler function.
	public void doLogic(String[] args){
		synchronized(lock) {
			// Check for CREATE ACCOUNT REQUEST
			if (args[0].equals("c")){
				// Check if user already exists.
				if (users.containsKey(args[1])){
					error = true;
					errMsg = "Create failed: User already exists";
				}
				// If not, make a new one, add it to HashTable.
				else {
					res = new Dude();
					res.username = args[1];
					//res.password = args[2];
                    res.password = hashPass(args[2]);
					//System.out.println(res.password);
					res.fullname = args[3];
					res.email = args[4];
					res.ssn = args[5];				
					res.dob = args[6];
					res.balance = Double.parseDouble(args[7]);
					users.put(args[1], res);
				}				
				return;
			}
			
			// Check for ADMIN CMDS:  ppp = print user table to server log.
			if (args[0].equals("ppp")){
				System.out.println("DUMPING TABLE\n_____________");
				dumpTable();
				error = true;
				errMsg = "admin cmd";
				return;
			}
		
			// If not create or admin, authenticate the request.
			if (!authUser(args[1],args[2])){ return; }
			
			// Check for WITHDRAW REQUEST
			if (args[0].equals("w")){
				res = users.get(args[1]);
				Double amt = Double.parseDouble(args[3]);
				Double fee_amt = amt * Server.wfee;
				if (res.balance < (amt + fee_amt)){
					error = true;
					errMsg = "Withdraw failed: Not enough cash in the balance";
				} else {
					res.balance = (res.balance - (amt + fee_amt));
				}
				
				users.put(args[1], res);
				// Put Transaction logging here.
				// Make a new Transaction.
				// Say that user $args[1] withdrew $args[3] from their account.
				// Save to transaction log, etc.

				//log the withdraw transaction for this account
				new Transaction().writeLog(Transaction.Type.withdraw,args[1],null,amt,fee_amt,false);

			}	
			
			// Check for DEPOSIT REQUEST
			else if (args[0].equals("d")){
				res = users.get(args[1]);
				Double amt = Double.parseDouble(args[3]);
				Double fee_amt = amt * Server.dfee;
				res.balance = (res.balance + (amt - fee_amt ));
				
				users.put(args[1], res);
				// Put Transaction logging here.

				//log the deposit transaction for this account
				new Transaction().writeLog(Transaction.Type.deposit,args[1],null,amt,fee_amt,false);
			}
			
			// Check for TRANSFER REQUEST
			else if (args[0].equals("t")){
				res = users.get(args[1]);
				if (!users.containsKey(args[3])){
					error = true;
					errMsg = "Transfer failed: Receiving user does not exist.";
				} else {
					Dude dest = users.get(args[3]);
					Double amt = Double.parseDouble(args[4]);
					Double fee_amt = amt * Server.tfee;
					if (res.balance < (amt + fee_amt)){
						error = true;
						errMsg = "Withdraw failed: Not enough cash in the balance";
					} else {
						res.balance = (res.balance - (amt + fee_amt));
						dest.balance = (dest.balance + amt);
						users.put(args[1], res);
						users.put(args[3], dest);
						// Put Transaction logging here.

						//log the transfer transaction for this account
						new Transaction().writeLog(Transaction.Type.transfer,args[1],dest,amt,fee_amt,false);
					}
				}
			}
			
			// Check for LOGIN REQUEST
			else if (args[0].equals("l")){
				res = users.get(args[1]);
			}	
			
			// Check for REFRESH REQUEST
			else if (args[0].equals("r")){
				res = users.get(args[1]);
			}	
			
			// Check for UPDATE ACCOUNT REQUEST
			else if (args[0].equals("u")){
				res = users.get(args[1]);
				//res.password = args[2];
                res.password = hashPass(args[2]);
				res.fullname = args[4];
				res.email = args[5];
				res.ssn = args[6];				
				res.dob = args[7];
				users.put(args[1], res);
			}
			
			// Check for BAD REQUEST
			else {
				error = true;
				errMsg = "Invalid Operation Type: "+args[0]+" is not a valid operation code";
			}
			
			// Commit the current user change back to the db.
			//if (!error) {
			//	users.put(args[1], res);
			//}
		}
	}
	
	// Prints the user table to the server console.
	public void dumpTable(){
		for (String uname : users.keySet()){
			String s = uname +", "+ users.get(uname).username +":"+ users.get(uname).password +" $"+ users.get(uname).balance;
			System.out.println(s);
		}
	}
	
	// Prints the user table to a file (SAVES PROGRESS!)
	public void writeTable(){
		try {
			FileWriter fw = new FileWriter(Server.FILENAME);
			for (Dude d : users.values()) {
				fw.write(d.toString()+"\n");
			}
			fw.close();
		} catch (Exception e){
			System.out.println("ERROR WHILE WRITING USER TABLE");
		}
	}
    
    public String hashPass(String pass) {
        String resPass = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] mdMD5 = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte bytes : mdMD5) {
                sb.append(String.format("%02x", bytes & 0xff));
            }
            resPass = sb.toString();
            return resPass;
        } catch (NoSuchAlgorithmException exception) {
            System.out.println("Error with Hashing Password!!!");
            return resPass;
        }
        
    }
}

