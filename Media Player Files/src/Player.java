import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;



public class Player extends Application{
	
	private static final String MEDIA_URL = "Files/Videos/Steins;Gate.Cognitive.Computing.01.mp4";
	
	public void start (Stage primaryStage) {
		primaryStage.setTitle("Player");
		Group root = new Group();
		Scene scene = new Scene(root,720,480);
		
		//create media player
		Media media = new Media(MEDIA_URL);
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setAutoPlay(true);
		
		MediaView mediaView = new MediaView(mediaPlayer);
		((Group)scene.getRoot()).getChildren().add(mediaView);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	

}
