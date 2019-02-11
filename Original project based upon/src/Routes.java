
public class Routes {

	private String filename,server;
	private int filesize,port;
	
	public Routes(String filename, int filesize, String server, int port){
		this.filename = filename;
		this.filesize = filesize;
		this.server = server;
		this.port = port;
	}
	
	public String getFileName(){
		return filename;
	}
	
	public int getFileSize(){
		return filesize;
	}
	
	public String getServer(){
		return server;
	}
	
	public int getPort(){
		return port;
	}
	
	public String print(){
		return "\nFILENAME: " + filename + "\nFILESIZE: " + filesize + "\nSERVER: " + server + "\nSERVER PORT: " + port;
	}
}
