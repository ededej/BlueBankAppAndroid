# Blue Bank Server README

## Index:
* Server files
* Compiling and running the server
* Client connections
* How the Protocol Works
* How the Android Client Works
* Maintaining this code and Adding Features
* Relevant Docs



## Server files:
* Server.java (Server main class)
* clientThread.java (Socket worker thread for server)
* Client.java (Test client to mock Android app functionality with a desktop)

* found at https://github.com/ededej/BlueBankAppAndroid/tree/master/bbserv


## Compiling and running the server:

This server is a java SocketServer that provides a multi-threaded interface that supports up to 10 concurrent connections. It has proper resource locking/access control, so it's completely threadsafe. Data is persisted between server restarts in the users.data file (written after every transaction and read in at server startup). The process for compiling and running the server is as follows:

1. Extract the bbserv directory somewhere and navigate to the location with a cmd line.
2. Run "javac *.java"
3. Find your server IP address with the "ipconfig" cmd  (ifconfig for mac?)
4. Run "java Server"

If running on Windows, you can also run the batch file: ServerSetup.bat. This batch file runs the two commands found above and also prints out the IP address that your Server will be hosted on.

**NOTE: There is a small chance that the server will freeze if left for 10-20 minutes unattended.  Either spam enter a few times to see if it becomes responsive, or just restart the process.  You will know this is happening if the app starts to silently fail and not respond to commands. All successful transactions will be saved in users.data, so a restart will not cause loss of user data.**



## Client connections:

There are two ways of doing this.  For both ways, basic connection information should appear on the server when you connect and when the connection is finished.  Clients connect in using Java Sockets. Links are available at the bottom of this doc for quick reading on these concepts.
1. Use the android app (of course). The android must be on the same network (Local wifi is best. Mobile doesn't work, and mason wifi is sketchy.  It probably won't work at Mason, so we will be using a home router to connect for the demo)
	1. Put in the IP address noted earlier, create an account, and log in.
	2. This app has the features:
		1. Account Creation
		2. Login
		3. Withdraw and Deposit to/from own account
		4. Transfer money to another user (with and without QR)
		5. Transaction log
		6. Admin console
		
	3. If the server is not running, none of the above features will work.
		1. If this is the case, or for just seeing the internal screens, use the dummy credentials "dummy" and "Password1". These will bypass the network login and allow access to the Bank Main screen.
	4. When transferring, withdrawing, or depositing, the balance will update as soon as you return to the main screen.
	
2. Run "java Client" from another cmd line on the same computer as the Server (LEAVE THE SERVER RUNNING!)
	1. This connects on the localhost, and you need to manually form your request strings using the protocol described in notes.txt
	2. "#" is the divider character between fields
	3. ex: to send a login request for user "jess" with password "pass", you would run "java Client" and then type "l#jess#pass"
	4. Run the command "ppp" from Client to dump the user table to the server console. This is extremely helpful for debugging transfers and account creation.
	
	
	
## How the Protocol Works:

This architecture runs off a text-based #-delimited message system. Individual fields of data are concatenated with #'s to be later String.split("#") by the receiver.  

All client-server interactions follow this pattern: 
* Client makes a request string
* Client sends request to server
* Server decodes and processes this message
* Server forms a response string of the new user state which it sends back to the client
* Client receives this string and then decodes the user state to be used in the app


More details on the full protocol as well as string examples of the protocol (explained with the //comment lines) can be found at the top of notes.txt.



## How the Android Client Works:

The app is a thin client for an authoritative server. This means the app doesn't run the banking logic locally, but just sends a request with the users desired action to the server. The authoritative server then decides whether the request is valid, and if it is, it performs the action and sends the user their new state.  The app then updates the locally remembered state and shows the users the results of the process. To accomplish this, the android app uses a mixture of AsyncTasks to perform server requests and SharedPreferences to locally cache things like username, password, server IP, and balance. There are links on those two particular concepts at the bottom for some quick reading.
	

	
## Maintaining this code and Adding Features:

This is a distributed architecture, so changes must be made to
1. Server-side only
2. Client-side only
3. Both Server-side and Client-side

Server side only is for changes that only affect the user data storage. An example of this would be storing passwords as a cryptgraphic hash.  This would be done by going into the "Check for CREATE ACCOUNT REQUEST" section of clientThread and changing the line 

	res.password = args[2]; 
	
to 

	res.password = some_hash_function(args[2]);
	
There is no change done to the Android app, and users will not even be able to detect this change.  

Client-side only changes are for things like adding a QR code scanner to exchange usernames with another app user. This would be done by adding an activity that does the QR scanning and that transitions into the Transfer activity with a pre-populated username. Something like this needs no changes on the server, as it just adds to the process of Transfer that already exists in the backend.  

Client and Server changes, or end-to-end changes are things that must be changed on both the server and the client. One example of this would be end-to-end encryption. This must be placed on the communication logic of both the client and the server so that they can encode and decode messages as a team. This would be done by subclassing the "is" and "os" objects that you see around to encrypt data before writing and decrypt it before reading. 



## RELEVANT DOCS:  

(This is an incomplete list, but all concepts here are easily available online.  The code is commented as thoroughly as possible too)

Server:

* SocketServer - https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html

* Socket - https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html

* synchronized(lock) - https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html


Android:

* SharedPreferences - https://developer.android.com/reference/android/content/SharedPreferences.html

* AsyncTasks - https://developer.android.com/reference/android/os/AsyncTask.html

