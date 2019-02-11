import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.*;

public class ClientStuff {

	public static ObjectOutputStream output;
	public static ObjectInputStream input;
	public static Socket connection;
	String MSERVER = "127.0.0.1";
	int MSPORT = 6792;
	private Routes route;
	private ArrayList<Routes> routes = new ArrayList<Routes>();
	private ArrayList<File> files = new ArrayList<File>();
	
	public void ConnectToMainServer() throws ClassNotFoundException, IOException {
		  
		 try{
			 connectToServer(MSERVER,MSPORT);
		     setupStreams();
		     sendRequest();
		     getNotification();
		     //printArrayList();
		     downloadfiles();
		   	 mergefiles(files, new File("../ClientFiles/"+files.get(0).getName().substring(0, files.get(0).getName().length() - 4)));
		   	
		 }catch(EOFException eofException){
			 System.out.println("Client terminated connection\n");
		 }catch(IOException ioException){
			 ioException.getStackTrace();
		 }finally{
			 close(); 
		 }
		 
	}
	  
	  private void close() throws IOException{
		 output.close();
		 input.close();
		 connection.close();	 
	  }
	  
	  private void connectToServer(String SERVER,int PORT) throws UnknownHostException, IOException{
		  System.out.println("Attempting connection to main server... \n");
		  connection = new Socket(InetAddress.getByName(SERVER),PORT);
		  System.out.println("Connected to main " + connection.getInetAddress().getHostName()+"\n");
	  }
	  
	  private void setupStreams() throws IOException{
			 output = new ObjectOutputStream(connection.getOutputStream()); //client => server
			 output.flush();
		     input = new ObjectInputStream(connection.getInputStream()); //server => client
		     System.out.println("Streams are online\n");	     
	  }
	  
	  private void sendRequest() throws IOException{
		  System.out.println("Sending request to server");
		  String request;
		  request = "codex-steins.gate.iso";
		     
		  try{
		    output.writeObject(request);
		    output.flush();
		  }catch(IOException ioException){
		    ioException.printStackTrace();
		  }
	  }
	  
	  private void getNotification() throws  IOException{
		  
		  try{
			  System.out.println("Receiving notify packet from main server\n");
			  String packet = (String) input.readObject();
			  StringTokenizer st = new StringTokenizer(packet,"|");
			  int i = 0;
			  String temp[] = new String[8];
			  while(st.hasMoreTokens()){
				 temp[i] = st.nextToken();
				 i++;
			  }
			  for(i = 0; i<5;i+=4){
				  String filename = temp[i+0];
				  int filesize = Integer.parseInt(temp[i+1]);
				  String server = temp[i+2];
				  int serverport = Integer.parseInt(temp[i+3]);
				  route = new Routes(filename,filesize,server,serverport);
				  routes.add(route);
			  }
			  
			  
		  }catch(ClassNotFoundException classNotFoundException){
			  System.err.println("Something weird was sent");
		  }
	  }
	  
	  private void printArrayList(){
		  for(int i = 0; i<routes.size();i++){
			  System.out.println(routes.get(i).print());
		  }
		  System.out.println(routes.size());
	  }
	  
	  private void downloadfiles() throws IOException{
		  System.out.println("Downloading files...\n");
		  for(int i = 0; i<routes.size();i++){
			  
			  String file_to_receive = "../ClientFiles/"+routes.get(i).getFileName();
			  int file_size = routes.get(i).getFileSize(); 
			  String server = routes.get(i).getServer();
			  int sport = routes.get(i).getPort();      
			  		  
			    int bytesRead;
			    int current = 0;
			    FileOutputStream fos = null;
			    BufferedOutputStream bos = null;
			    try {
			    	Runnable con = new Runnable(){
						public void run(){
							System.out.println("Connecting...");
								try {
									connection = new Socket(InetAddress.getByName(server), sport);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 System.out.println("Connected to "+ connection.getInetAddress().getHostName());
						}
					};
					Thread serverThread = new Thread(con);
					serverThread.start();
				      
				      
				     
				    		
				      // receive file
				      byte [] mybytearray  = new byte [9999999];
				      InputStream is = connection.getInputStream();
				       
				      fos = new FileOutputStream(file_to_receive);
				      bos = new BufferedOutputStream(fos);
				      bytesRead = is.read(mybytearray,0,mybytearray.length);
				      current = bytesRead;
				      
				      do {
				         bytesRead =
				            is.read(mybytearray, current, (mybytearray.length-current));
				         if(bytesRead >= 0) current += bytesRead;
				      } while(bytesRead > -1);;
				      bos.write(mybytearray, 0 , current);
				      bos.flush();
				      System.out.println("File " + file_to_receive
				          + " downloaded (" + current + " bytes read)");	
				      File f = new File(file_to_receive);
				      files.add(f);
			    }finally {
			      if (fos != null) fos.close();
			      if (bos != null) bos.close();
			      if (connection != null) connection.close();
			    }
			  }
	  }
		  
	  private void mergefiles(List<File> files, File into)throws IOException {
		  try (BufferedOutputStream mergingStream = new BufferedOutputStream(
		            new FileOutputStream(into))) {
		        for (File f : files) {
		            Files.copy(f.toPath(), mergingStream);
		        }
		    }
	  }
}
