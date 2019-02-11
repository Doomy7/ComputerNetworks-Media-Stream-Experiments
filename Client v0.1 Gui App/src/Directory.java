import java.io.File;

public class Directory {

	private static File srcDir = new File("Files/");
	private static File[] subDirs = srcDir.listFiles();
	
	public static void main(String args[]){
		
		
		for(File file : subDirs){
			if(file.isDirectory()){
				System.out.println(file.getName());
			}
		}
		
		
	}
	
	
}
