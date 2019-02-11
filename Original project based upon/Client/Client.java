import java.io.IOException;

public class Client {
 

  public static void main (String [] args ) throws IOException, ClassNotFoundException {
	  ClientStuff client = new ClientStuff();
	  client.ConnectToMainServer();
    }
 }
