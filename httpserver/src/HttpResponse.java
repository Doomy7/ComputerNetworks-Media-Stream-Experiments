import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpResponse {

	HttpRequest req;
	
	
	//this is the final response generated
	String response;
	
	//root path of server
	String root = "C:/Users/DoomyX2/workspace/httpserver/src/Files";
	
	
	HttpResponse(HttpRequest request,Socket socket){
		req = request;
		//now we have to open the file mentioned in request
		File f = new File(root + req.filename);
		
		try {
			//to read this file 
			System.out.println(root + req.filename);
			
			
			response = "HTTP/1.1 200 \r\n"; //version of http and 200 for status (ok)		
			response += "Server: Stream Server/1.0 \r\n"; //identity of server
			response += "Content-Type: \"application/force-download\" \r\n"; //response is in html format
			response += "Connection: close \r\n"; //for browser for discontinuing communication
			response += "Content-Length: " + (int)f.length() + " \r\n"; //length of response file
			
			response += "\r\n"; //after here append file data
			
			if(!f.getName().endsWith(".ts")) {
				FileInputStream fis = new FileInputStream(f);
				
				int s;	
				while((s = fis.read()) != -1) { // -1 == eof
					response += (char) s;
				}
				
				fis.close();
			}else {
				  
		          byte [] mybytearray  = new byte [(int)f.length()];
		          FileInputStream fis = new FileInputStream(f);
		          BufferedInputStream bis = new BufferedInputStream(fis);
		          bis.read(mybytearray,0,mybytearray.length);
		          OutputStream os = socket.getOutputStream();
		          System.out.println("Sending " + f.getName() + "(" + mybytearray.length + " bytes)");
		          os.write(mybytearray,0,mybytearray.length);
		          os.flush();
		          System.out.println("Done.");
			}
		}catch(FileNotFoundException e) {
			//if we dont get File Error 404
			response = response.replace("200", "404");
		}catch(Exception e) {
			//if other error 500 internal server error
			response = response.replace("200", "500");
		}
	}
	

	
	
}
