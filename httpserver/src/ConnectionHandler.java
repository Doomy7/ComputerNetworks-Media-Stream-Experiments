import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//this class basically handles all the connections which contains the requests
public class ConnectionHandler extends Thread{ //by extending to Thread, this class becomes a thread

	public Socket s;
	
	//for sending the output to client
	PrintWriter pw;
	//for getting the input from client
	BufferedReader br;
	
	//constructor 
	//which accepts s socket
	public ConnectionHandler(Socket s) throws Exception {
		this.s = s;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		pw = new PrintWriter(s.getOutputStream());
	}
	
	//thread class contains a method run which is called automatically when we start the thread
	//in this method we have to read the request and give the response
	public void run() {
		try {
			//here we get the request string and give this string to HttpRequest class
			
			String reqS = "";
			
			//from br we have to read our request 
			// read until request is not get or br is ready
			while(br.ready() || reqS.length() == 0) {
				reqS += (char) br.read();
			}
			System.out.print(reqS); // for diplay
			HttpRequest req = new HttpRequest(reqS);
			
			//now we pass the HttpRequest object to HttpResponse class so as to get the Response
			HttpResponse res = new HttpResponse(req,s);
			
			//write the final output to pw
			pw.write(res.response.toCharArray());
			
			pw.close();
			br.close();
			s.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return s;
	}
	
}
