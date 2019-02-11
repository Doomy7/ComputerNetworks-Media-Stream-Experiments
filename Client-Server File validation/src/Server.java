import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static ServerSocket server;
	private static Socket connection;
	private static ObjectInputStream input;
	private static ObjectOutputStream output;
	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	
	public static void startRunning(){
		
		try{
			server = new ServerSocket(6795,100);
			while(true){
				try{
					
					waitForConnection();
					setupStreams();
					getRequest();
					
				}catch(EOFException eofException){
					System.out.println("Client ended connection\n");
				}finally{
					close();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private static void waitForConnection() throws IOException{
		System.out.println("Waiting for connection...\n");
		connection = server.accept();
		System.out.println("Connected to "+ connection.getInetAddress().getHostName());	
	}

	private static void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream()); //server => client
		output.flush();
		input = new ObjectInputStream(connection.getInputStream()); //client => server
		System.out.println("Streams are online\n");
	}
	
	private static void getRequest() throws IOException{
		System.out.println("Receiving request from client");
		try{
			String filename = (String) input.readObject();
			File file = new File("../Files/Videos/"+filename);
			if(file.exists() && !file.isDirectory()){
				output.writeObject(filename + " is streaming.");
				output.flush();
			}else{
				output.writeObject("Nope");
				output.flush();
			}
		}catch(ClassNotFoundException classNotFoundException){
			System.err.println("Something weird was received\n");
		}
	}
	
	private static void close(){
		System.out.println("Closing Main Server Connection\n");
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	
	public static void main(String args[]){
		startRunning();
	}

	
	
}
