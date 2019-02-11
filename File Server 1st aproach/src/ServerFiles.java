import java.io.File;

public class ServerFiles {

	protected static GenericQueueWithOnePointer<String> videoFiles = new GenericQueueWithOnePointer<String>();
	protected static GenericQueueWithOnePointer<String> musicFiles = new GenericQueueWithOnePointer<String>();
	protected static GenericQueueWithOnePointer<String> pictureFiles = new GenericQueueWithOnePointer<String>();
	private static String videoPath = "Files/Videos";
	private static String musicPath = "Files/Music";
	private static String picturePath = "Files/Pictures";
	
	/**
	 * Load Methods
	 */
	static void init() {
		loadVideoFiles();
		loadMusicFiles();
		loadPictureFiles();
	}
	/**
	* Loads Video files
	**/
	private static void loadVideoFiles() {
		File vfolder = new File(videoPath);
		File[] listOfVFiles = vfolder.listFiles();
		for(int i = 0; i< listOfVFiles.length; i++) {
			videoFiles.put(listOfVFiles[i].getName());
		}
	}
	/**
	* Loads Music files
	**/
	private static void loadMusicFiles() {
		File mfolder = new File(musicPath);
		File[] listOfMFiles = mfolder.listFiles();
		for(int i = 0; i < listOfMFiles.length; i++) {
			musicFiles.put(listOfMFiles[i].getName());
		}
	}
	/**
	* Loads Picture files
	**/
	private static void loadPictureFiles() {
		File pfolder = new File(picturePath);
		File[] listOfPFiles = pfolder.listFiles();
		for(int i = 0; i < listOfPFiles.length; i++) {
			pictureFiles.put(listOfPFiles[i].getName());
		}
	}
	
}
