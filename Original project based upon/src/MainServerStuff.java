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

public class MainServerStuff{
	
	private ServerSocket server;
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String sevandpor[] = {"192.168.1.83","6001","192.168.1.83","6002"};
	private static ExecutorService threadExecutor = Executors.newCachedThreadPool();
	
	public void startRunning(){
		
		try{
			server = new ServerSocket(6792,100);
			while(true){
				try{
					
					waitForConnection();
					setupStreams();
					getRequest();
					
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
	
	private void waitForConnection() throws IOException{
		System.out.println("Waiting for connection...\n");
		connection = server.accept();
		System.out.println("Connected to "+ connection.getInetAddress().getHostName());	
	}

	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream()); //server => client
		output.flush();
		input = new ObjectInputStream(connection.getInputStream()); //client => server
		System.out.println("Streams are online\n");
	}
	
	private void getRequest() throws IOException{
		System.out.println("Receiving request from client");
		try{
			String filename = (String) input.readObject();
			File file = new File("../MainServerFiles/"+filename);
			if(file.exists() && !file.isDirectory()){
				splitFile(file);
			}
		}catch(ClassNotFoundException classNotFoundException){
			System.err.println("Something weird was received\n");
		}
	}
	
	private void close(){
		System.out.println("Closing Main Server Connection\n");
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private void splitFile(File f) throws IOException {
		System.out.println("Spliting File...\n");
	        int partCounter = 1;//I like to name parts from 001, 002, 003, ...
	                            //you can change it to 0 if you want 000, 001, ...
	        int sizeOfFiles;
	        
	        if(((int)(f.length() % 2) == 0)){
	        	sizeOfFiles = (int)(f.length()/2);
	        }else{
	        	sizeOfFiles = (int)(f.length()/2 + 1);
	        }
	        
	        byte[] buffer = new byte[sizeOfFiles];

	        try (BufferedInputStream buffInStream = new BufferedInputStream(
	                new FileInputStream(f))) {//try-with-resources to ensure closing stream
	            String name = f.getName();
	            int i = 0;
	            int tmp = 0;
	            String info = "";
	            while ((tmp = buffInStream.read(buffer)) > 0) {
	                //write each chunk of data into separate file with different number in name
	            	i++;
	            	String filename = name + "." + String.format("%03d", partCounter);
	                File newFile = new File(f.getParent()+"/temptransfer/", name + "."
	                        + String.format("%03d", partCounter++));
	                try (FileOutputStream out = new FileOutputStream(newFile)) {
	                    out.write(buffer, 0, tmp);//tmp is chunk size
	                    
	                    int filesize = (int)newFile.length();
	                    
	                    if(i==1){
	                    	info += filename +"|"+ filesize + "|" + sevandpor[0] + "|" + sevandpor[1];
	                    	StartServer1(filename,Integer.parseInt(sevandpor[1]),filesize);
	                    	
	                    }else{
	                    	info += "|" + filename +"|"+ filesize + "|" + sevandpor[2] + "|" + sevandpor[3];
	                    	StartServer2(filename,Integer.parseInt(sevandpor[3]),filesize);
	                    	notifyClient(info);
	                    }
	                    
	                }
	               
	            }
	        }
	}
	
	private void notifyClient(String notification) {
		System.out.println("Notifying client..\n");
		try{
			output.writeObject(notification);
			output.flush();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private static void StartServer1(String file_to_send,int port,int file_size) throws IOException{
		Runnable st1 = new Runnable(){
					public void run(){
						Server1 server1 = new Server1();	
						try {
							server1.startServer1(file_to_send, port,file_size);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				Thread serverThread = new Thread(st1);
				serverThread.start();
	}
			
	private static void StartServer2(String file_to_send, int port, int file_size) throws IOException{
		Runnable st2 = new Runnable(){
			public void run(){
				Server2 server2 = new Server2();
				try {
					server2.startServer2(file_to_send, port,file_size);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		Thread serverThread = new Thread(st2);
		serverThread.start();
	}

}
