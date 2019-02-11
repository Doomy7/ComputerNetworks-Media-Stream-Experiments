import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

	
	private ServerSocket server;
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	FileServer(){
		ServerFiles.init();
		startFileServer();
	}

	private void startFileServer() {
		
		try {
			server = new ServerSocket(7000,5);
			while(true) {
				try {
					
					waitForConnection();
					setUpStreams();
					getRequest();
					
				}catch(EOFException eofException) {
					System.out.println("Server ended connection");
				}finally {
					close();
				}
			}
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	private void waitForConnection() throws IOException{
		System.out.println("Waiting for connection ... ");
		connection = server.accept();
		System.out.println("Connected to "+ connection.getInetAddress().getHostName());
	}
	
	private void setUpStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		System.out.println("Streams are online");
	}
	
	private void getRequest() throws IOException{
		System.out.println("Receiving request from client");
		try {
			String request = (String) input.readObject();
			if(request.equals("bringFileNames")) {
				
				String vf = ServerFiles.videoFiles.returnItems();
				sendVideoNames(vf);
				String mf = ServerFiles.musicFiles.returnItems();
				sendMusicNames(mf);
				String pf = ServerFiles.pictureFiles.returnItems();
				sendPictureNames(pf);
				
			}else if(request.equals("bringFileAddress")){
				output.writeObject("");
			}
		}catch(ClassNotFoundException classNotFoundException){
			System.err.println("Something weird was received");
		}
	}

	//Video Name packet
	private void sendVideoNames(String vf) throws IOException {
		output.writeObject(vf);
		output.flush();
	}
	//Music Name packet
	private void sendMusicNames(String mf) throws IOException {
		output.writeObject(mf);
		output.flush();
	}
	//Picture Name packet
	private void sendPictureNames(String pf) throws IOException {
		output.writeObject(pf);
		output.flush();
	}

	private void close() {
		System.out.println("Closing connection with" + connection.getInetAddress().getHostName());
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		FileServer fServer = new FileServer();	
	}
 	
}
