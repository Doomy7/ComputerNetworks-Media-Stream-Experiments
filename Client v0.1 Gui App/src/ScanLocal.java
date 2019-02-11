import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.net.*;

public class ScanLocal {
	static ServerSocket newCon;
	public static void main(String args[]) throws SecurityException, IOException,IllegalArgumentException{
		
		setUp();
	}
	
	static InetAddress checkHosts(String subnet) throws UnknownHostException, IOException{
		  
		int timeout=1000;
		   for (int i=1;i<255;i++){
		       InetAddress host= InetAddress.getByName(subnet + "." + i);
		       if (host.isReachable(timeout)){
		           System.out.println(host + " is reachable");
		       }else{
		    	   System.out.println(host + " is not reachable");
		    	   return host;

		       }
		   }
		  return null;
	}
	
	static void setUp() throws IOException{
		newCon = ServerSocket(80,3,checkHosts("192.168.0"));
	}

}
