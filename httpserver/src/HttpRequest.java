
public class HttpRequest {

	//first line contains 3 parts
	//1-request type 2-file name 3-http version
	//for us only the file name is important
	String filename;
	//we have to create a constructor that accepts a string
	HttpRequest(String request){
		//now we have the request
		//only 1st line is important for us
		String lines[] = request.split("\n");//now we have all the lines of request seperated
		
		//this line splits the first line using space and then selects the 2nd item
		//which is our filename
		lines = lines[0].split(" "); 
		filename = lines[1];
	}
	
}
