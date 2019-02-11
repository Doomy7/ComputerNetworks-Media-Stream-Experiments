import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private static Socket connection;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	
	
	public static void Start() throws ClassNotFoundException,IOException{
		newPrompt();
		
		/*try{
			
			//connectToServer();
			//setUpStreams();
			//sendRequest();
			//getDirectories();
		}catch(EOFException eofException){
			System.out.println("Server terminated connection");
		}catch(IOException ioException){
			ioException.getStackTrace();
		}finally{
			//close();
		}*/
	}

	private static void connectToServer() throws UnknownHostException, IOException{
		
		System.out.println("Attempting connection to server");
		//connection = new Socket(InetAddress.getByName(ClientGUI.getServerIP()),ClientGUI.getServerPORT());
		System.out.println(ClientGUI.getServerIP() + " : " + ClientGUI.getServerPORT());
		System.out.println("Successfully connected to " + connection.getInetAddress().getHostName()+"\n");
	}
	
	private void setUpStreams(){
		
	}
	
	private static void newPrompt(){
		ClientGUI.connectionPrompt();
	}
}
