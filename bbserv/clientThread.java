import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.ArrayList;

public class clientThread extends Thread{
	private BufferedReader is = null;
	private PrintStream os = null;
	private Socket clientSocket = null;
	private HashMap<String,Dude> users;
	private LinkedList<Transaction> trans;
	private clientThread[] room;
	private Object lock;
	private Response res;
	private boolean error = false;
	private String errMsg = "Something went wrong!";
	private boolean isDude = true;
	private String transactions;
	
	// Constructor.  Takes refs to all relevant fields from main server thread.
	public clientThread(Socket socket, HashMap<String,Dude> users, LinkedList<Transaction> trans, Object lock, clientThread[] room) {
		this.clientSocket = socket;
		this.users = users;
		this.trans = trans;
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
				writeLog();
			}
			
			// Release common server resources.
			roomRelease(room);
			
			// Return user state.
			if (isDude){
				os.println(error ? "Error"+Response.DELIM+errMsg : ((Dude) res).toString());			
			} else {
				os.println(Double.toString(((Dude)res).balance)+transactions);
			}
			
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
					((Dude) res).username = args[1];
					//res.password = args[2];
                    ((Dude) res).password = hashPass(args[2]);
					//System.out.println(res.password);
					((Dude) res).fullname = args[3];
					((Dude) res).email = args[4];
					((Dude) res).ssn = args[5];				
					((Dude) res).dob = args[6];
					((Dude) res).balance = Double.parseDouble(args[7]);
					users.put(args[1], ((Dude) res));
					trans.add(new Transaction("Create",((Dude) res).username,"",0.0,((Dude) res).balance));
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
				if (((Dude)res).balance < (amt + fee_amt)){
					error = true;
					errMsg = "Withdraw failed: Not enough cash in the balance";
					return;
				} else {
					((Dude) res).balance = (((Dude) res).balance - (amt + fee_amt));
				}
				
				users.put(args[1], ((Dude) res));
				trans.add(new Transaction("Withdraw",args[1],"",fee_amt,amt));
			}	
			
			// Check for DEPOSIT REQUEST
			else if (args[0].equals("d")){
				res = users.get(args[1]);
				Double amt = Double.parseDouble(args[3]);
				Double fee_amt = amt * Server.dfee;
				((Dude) res).balance = (((Dude) res).balance + (amt - fee_amt ));
				
				users.put(args[1], ((Dude) res));
				trans.add(new Transaction("Deposit",args[1],"",fee_amt,amt));
			}
			
			// Check for TRANSFER REQUEST
			else if (args[0].equals("t")){
				res = users.get(args[1]);
				if (!users.containsKey(args[3])){
					error = true;
					errMsg = "Transfer failed: Receiving user does not exist.";
					return;
				} else {
					Dude dest = users.get(args[3]);
					Double amt = Double.parseDouble(args[4]);
					Double fee_amt = amt * Server.tfee;
					if (((Dude) res).balance < (amt + fee_amt)){
						error = true;
						errMsg = "Withdraw failed: Not enough cash in the balance";
						return;
					} else {
						((Dude) res).balance = (((Dude) res).balance - (amt + fee_amt));
						dest.balance = (dest.balance + amt);
						users.put(args[1], ((Dude) res));
						users.put(args[3], dest);
						trans.add(new Transaction("Transfer",args[1],args[3],fee_amt,amt));
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
			}
			
			// Check for TRANSACTION HISTORY REQUEST
			else if (args[0].equals("h")){
				res = users.get(args[1]);
				String user = args[1];
				String output = "";
				for (Transaction t : trans) {
					if (t.acc1.equals(user) || t.acc2.equals(user)){
						output = Response.DELIM + t.toString() + output;
					}
				}
				transactions = output;
				isDude = false;
			}

			else if (args[0].equals("a")){
				res = users.get(args[1]);
				String user = args[1];
				String output = "";
				for (Transaction t : trans) {
					output = Response.DELIM + t.toString() + output;
				}
				transactions = output;
				isDude = false;
			}
			
			// Check for BAD REQUEST
			else {
				error = true;
				errMsg = "Invalid Operation Type: "+args[0]+" is not a valid operation code";
			}
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
			FileWriter fw = new FileWriter(Server.U_FILENAME);
			for (Dude d : users.values()) {
				fw.write(d.toString()+"\n");
			}
			fw.close();
		} catch (Exception e){
			System.out.println("ERROR WHILE WRITING USER TABLE!");
		}
	}
	
	// Prints the transaction log to a file (SAVES PROGRESS!)
	public void writeLog(){
		try {
			FileWriter fw = new FileWriter(Server.T_FILENAME);
			for (Transaction t : trans) {
				fw.write(t.toString()+"\n");
			}
			fw.close();
		} catch (Exception e){
			System.out.println("ERROR WHILE WRITING TRANSACTION TABLE!");
		}
	}
    
	// Password hashing function.
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

