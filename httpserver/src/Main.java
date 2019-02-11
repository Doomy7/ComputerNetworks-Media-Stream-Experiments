import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	ServerSocket serverSocket;
	Socket s;
	
	//entry point for our program
	public static void main(String args[]) throws Exception {
		new Main().runServer(); //to avoid any problems with static fields
	}
	
	
	public void runServer() throws Exception {
		System.out.println("Stream server is online");
		serverSocket = new ServerSocket(7000); //port number at which server is running
		
		//for accepting requests
		acceptRequests();
	}

	private void acceptRequests() throws Exception {
		while(true) { //we have to accept all the requests
			//connection to client is in the form of socket which contains the stream for input and output
			Socket s = serverSocket.accept();
			ConnectionHandler ch = new ConnectionHandler(s);
			
			//ch is the thread so we have to start out thread using
			ch.start(); //this will call the run method automatically
		}
	}
	
}

