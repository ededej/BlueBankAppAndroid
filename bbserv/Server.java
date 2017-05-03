import java.io.PrintStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.util.HashMap;

public class Server{
 
 private static ServerSocket serverSocket = null;
 private static Socket clientSocket = null;
 private static final int serverCap = 10;
 private static final clientThread[] threads = new clientThread[serverCap];
 public static String FILENAME = "users.data";
 
 // User table & synch lock.
 private static HashMap<String,Dude> users;
 private static Object lock = new Object();

 //Bank fees (these are % s).
 public static double wfee = 0.05;
 public static double dfee = 0.05;
 public static double tfee = 0.05;
 
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
  users = readTable();
  
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
      (threads[i] = new clientThread(clientSocket, users, lock, threads)).start();
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
 public static HashMap<String,Dude> readTable(){
  HashMap<String,Dude> hash = new HashMap<String,Dude>();
  try {
   FileInputStream fis = new FileInputStream(FILENAME);
   BufferedReader br = new BufferedReader(new InputStreamReader(fis));
   System.out.println("LOADING USER LINES!");
   String line;
   while ((line = br.readLine()) != null)   {
    hash.put(line.split(Dude.DELIM)[0],
     new Dude(line));
   }
   br.close();
  } catch (Exception e){
   System.out.println("No table file found or properly loaded.");
  }
  return hash;
 }
}