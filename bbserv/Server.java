import java.io.PrintStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;

public class Server{
 
 private static ServerSocket serverSocket = null;
 private static Socket clientSocket = null;
 private static final int serverCap = 10;
 private static final clientThread[] threads = new clientThread[serverCap];
 public static String U_FILENAME = "users.data";
 public static String T_FILENAME = "transaction.data";
 public static String D_FILENAME = "disputed.data";
 
 // User table & synch lock.
 private static Object lock = new Object();
 private static HashMap<String,Dude> users;
 private static LinkedList<Transaction> trans;
 private static LinkedList<Transaction> disputed;
 
 //Bank fees (these are % s).
 public static double wfee = 0.0;
 public static double dfee = 0.0;
 public static double tfee = 0.0;
 
 public static void main(String[] args) throws Exception{
  int portnum = 1337;
  if (args.length == 0){
   System.out.printf("Running on default port: %d\n", portnum);
   System.out.println("To use a custom port, use 'java Server.java $port'");
  } else {
   portnum = Integer.parseInt(args[1]);
   System.out.printf("Running on port: %d\n", portnum);
  }
  
  // Load in users from file.
  users = readTable(U_FILENAME);
  trans = readLog(T_FILENAME);
  disputed = readLog(D_FILENAME);
  
  // Server logic.
  try{
   serverSocket = new ServerSocket(portnum);
  } catch (IOException e) {
   System.out.println(e);
  }
  
  System.out.println(InetAddress.getLocalHost());
  
  // Main loop:  Accept connections and spawn client threads.
  while(true) {
   try {
    // When new clients connect:
    clientSocket = serverSocket.accept();
    System.out.println("The server has found a client");
    int i = 0;
    // See if there's an open spot for a socket.
    for (i = 0; i < threads.length; i++){
     // If so, make a new client thread an run it.
     if (threads[i] == null){
      (threads[i] = new clientThread(clientSocket, users, trans, disputed, lock, threads)).start();
      break;
     }
    }
    // If not, deny the client.
    if (i == threads.length){
     PrintStream os = new PrintStream(clientSocket.getOutputStream());
     os.println("ERR_SERV_OVERLOAD");
     os.close();
     clientSocket.close();
    }
   } catch (IOException e) {
    System.out.println(e);
   }
  }
 }
 
	// Server persistence function.
	public static HashMap<String,Dude> readTable(String filename){
		HashMap<String,Dude> hash = new HashMap<String,Dude>();
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			System.out.println("Loading user lines.");
			String line;
			while ((line = br.readLine()) != null) {
				hash.put(line.split(Dude.DELIM)[0], new Dude(line));
			}
			br.close();
		} catch (Exception e){
			System.out.println("User file DNE or was improperly loaded.");
		}
		return hash;
	}

	// Transaction persistence function.
	public static LinkedList<Transaction> readLog(String filename){
		LinkedList<Transaction> trans = new LinkedList<Transaction>();
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			System.out.println("Loading transaction lines.");
			String line;
			while ((line = br.readLine()) != null)   {
				trans.add(new Transaction(line));
			}
			br.close();
		} catch (Exception e){
			System.out.println("Transaction file DNE or was improperly loaded.");
			System.out.println(e.toString());
		}
		return trans;
	}
}