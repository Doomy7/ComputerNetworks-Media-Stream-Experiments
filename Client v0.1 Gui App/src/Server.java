import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private static ServerSocket server;
	private static Socket connection;
	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	
	
	public static void setUpServer(Boolean IsOnline){
		
		
		try{
			server = new ServerSocket(80,5);
			while(IsOnline){
				try{
					
					waitForClients();
					setUpStreams();
					sendDirectories();
					//getRequests();
				
				}catch(EOFException eofException){
					System.out.println("\nServer ended connection\n");
				}finally{
					close();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	
	}
	
	private static void waitForClients() throws IOException{
		System.out.println("Waiting for connection...\n");
		connection = server.accept();
		System.out.println("Accepted " + connection.getInetAddress().getHostName() + "connection.");
	}
	
	private static void setUpStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream()); //server ==> client
		output.flush();
		input = new ObjectInputStream(connection.getInputStream()); //client ==> server
		System.out.println("Streams with" + connection.getInetAddress().getHostAddress() + " are online.");
	}
	
	private static void sendDirectories(){
		File srcDir = new File("Files/");
		File[] subDirs = srcDir.listFiles();
		try{
			for(File file : subDirs){
				output.writeObject(file);
			}
			output.flush();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
			
	}
	
	private static void getRequest() throws IOException{
		System.out.println("Receiving request from" + connection.getInetAddress().getHostAddress());
		//try{
			
		//}
	}
	
	private static void close(){
		System.out.println("Closing Connection");
		try{
			output.close();
			input.close();
			connection.close();			
		}catch (IOException ioException){
			ioException.printStackTrace();
		}
	}
}
