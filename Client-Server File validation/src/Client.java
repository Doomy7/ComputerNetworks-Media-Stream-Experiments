import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;

public class Client {
	public static ObjectOutputStream output;
	public static ObjectInputStream input;
	public static Socket connection;
	static String MSERVER = "127.0.0.1";
	static int MSPORT = 6794;
	
	
	public static void ConnectToMainServer() throws ClassNotFoundException, IOException {
		  
		 try{
			 connectToServer(MSERVER,MSPORT);
		     setupStreams();
		     sendRequest();
		     getNotification();
		     
		   	
		 }catch(EOFException eofException){
			 System.out.println("Server terminated connection\n");
		 }catch(IOException ioException){
			 ioException.getStackTrace();
		 }finally{
			 close(); 
		 }
		 
	}
	  
	  private static void close() throws IOException{
		 output.close();
		 input.close();
		 connection.close();	 
	  }
	  
	  private static void connectToServer(String SERVER,int PORT) throws UnknownHostException, IOException{
		  System.out.println("Attempting connection to main server... \n");
		  connection = new Socket(InetAddress.getByName(SERVER),PORT);
		  System.out.println("Connected to main " + connection.getInetAddress().getHostName()+"\n");
	  }
	  
	  private static void setupStreams() throws IOException{
			 output = new ObjectOutputStream(connection.getOutputStream()); //client => server
			 output.flush();
		     input = new ObjectInputStream(connection.getInputStream()); //server => client
		     System.out.println("Streams are online\n");	     
	  }
	  
	  private static void sendRequest() throws IOException{
		  System.out.println("Sending request to server");
		  String request;
		  request = "Video1.avi";
		     
		  try{
		    output.writeObject(request);
		    output.flush();
		  }catch(IOException ioException){
		    ioException.printStackTrace();
		  }
	  }
	  
	  private static  void getNotification() throws  IOException{
		  
		  try{
			  
			  System.out.println((String) input.readObject());
			  
		  }catch(ClassNotFoundException classNotFoundException){
			  System.err.println("Something weird was sent");
		  }
	  }
	  

	  public static void main(String args[]) throws ClassNotFoundException, IOException{
		  ConnectToMainServer();
	  }
	  
}
