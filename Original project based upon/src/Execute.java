import java.io.IOException;

public class Execute {

	public static void main(String[] args) throws IOException {
		System.out.println(System.getProperty("user.dir") + "\\src\\" + "Server.java");
		
		Runtime.getRuntime().exec("javac " + System.getProperty("user.dir") + "\\src\\" + "Server.java");
		Runtime.getRuntime().exec("javac " + System.getProperty("user.dir") + "\\src\\" + "Client.java");
		Runtime.getRuntime().exec("java " + System.getProperty("user.dir") + "\\src\\" + "Server");
		Runtime.getRuntime().exec("java " + System.getProperty("user.dir") + "\\src\\" + "Client");
	}

}
