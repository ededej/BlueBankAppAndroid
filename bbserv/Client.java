
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{
	
	// TESTING SERVER CLIENT
	// ---------------------------------------------------------------------------
	// Use this with the raw request strings from notes.txt to test server calls, 
	// set up server resources, or use the ppp admin utility to print server state.
	
	public static void main(String[] args){
	
		// Communication channels and a socket.
		Socket clientSocket = null;
		BufferedReader is = null;
		PrintStream os = null;
		BufferedReader sysIn = null;
		boolean closed = false;
		
		// Defaults.
		String server = "localhost";
		int port = 1337;
		
		// Check arguments for alternate values.
		System.out.println("Syntax is 'java Client $server $port'");
		if (args.length >= 2){ 
			try {
				port = Integer.parseInt(args[1]);
				server = args[0];
			} catch (Exception e){
				System.out.println("Invalid arguments");
				return;
			}
		}
		System.out.println("Connecting to: "+server+", "+port);
		
		// Open a connection and init all variables.
		try {
			clientSocket = new Socket(server, port);
			os = new PrintStream(clientSocket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sysIn = new BufferedReader(new InputStreamReader(System.in));
		} catch (UnknownHostException e){
			System.out.println("Could not establish connection to host: "+server+", "+port);
		} catch (IOException e) {
			System.out.println("IO Error: The hamsters are on strike.");
		}
		
		// Send the request.  Print the response.
		try {
			os.println(sysIn.readLine().trim());
			System.out.println(is.readLine());
		} catch (IOException e) {
			System.out.println("IO Error: The carrier pigeon was lost.");
		}
	}
}













