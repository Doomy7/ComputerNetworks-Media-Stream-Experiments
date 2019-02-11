import java.nio.file.Paths;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Main extends Application {
	

	private static final String MEDIA_URL1 = "Files/Videos/Steins;Gate.Cognitive.Computing.01.mp4";
	private static final String MEDIA_URL2 = "Files/Music/03 Come Cover Me.mp3";
	private static final String MEDIA_URL3 = "Files/Pictures/front.jpg";
	private static final String MEDIA_URL4 = "http://download.oracle.com/otndocs/products/javafx/JavaRap/prog_index.m3u8";
	static String mediaUrl;
	public void start(Stage primaryStage) {
		try {
			
				//String MediaUrl = "192.168.1.83";
				primaryStage.setTitle("Player");
				Group root = new Group();
				//create media player
				
				//Media media = new Media(Paths.get(getMediaURL()).toUri().toString());
				Media media = new Media(getMediaURL());
				MediaPlayer mediaPlayer = new MediaPlayer(media);
				Scene scene = new Scene(root,1280/*media.getWidth()*/,755/*media.getHeight()*/);
				mediaPlayer.setAutoPlay(true);
				MediaControl mediaControl = new MediaControl(mediaPlayer);							
				scene.setRoot(mediaControl);
				
				//MediaView mediaView = new MediaView(mediaPlayer);
				//((Group)scene.getRoot()).getChildren().add(mediaView);
				
				primaryStage.setScene(scene);
			
				primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getMediaURL() {
		// TODO Auto-generated method stub
		return mediaUrl;
	}

	public class MediaControl extends BorderPane{
		
		MediaPlayer mp;
		private MediaView mediaView;
		private final boolean repeat = false;
		private boolean stopRequested = false;
		private boolean atEndOfMedia = false;
		private Duration duration;
		private Slider timeSlider;
		private Label playTime;
		private Slider volumeSlider;
		private HBox mediaBar;
		
		public MediaControl(final MediaPlayer mp) {
			this.mp = mp;
			setStyle("-fx-background-color: #bfc2c7;");
			mediaView = new MediaView(mp);
			Pane mvPane = new Pane() {
				
			};
			
			mvPane.getChildren().add(mediaView);
			mvPane.setStyle("-fx-background-color: black");
			setCenter(mvPane);
			
			mediaBar = new HBox();
			mediaBar.setAlignment(Pos.CENTER);
			mediaBar.setPadding(new Insets(5,200,5,200));
			BorderPane.setAlignment(mediaBar,  Pos.CENTER);
			final Button playButton = new Button(">");
			mediaBar.getChildren().add(playButton);
			
			setBottom(mediaBar);
			
			
			playButton.setOnAction(new EventHandler<ActionEvent>(){
				
				public void handle(ActionEvent e) {
					Status status = mp.getStatus();
					
					if(status == Status.UNKNOWN || status == Status.HALTED) {
						return;
					}
					
					if(status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
						if(atEndOfMedia) {
							mp.seek(mp.getStartTime());
							atEndOfMedia = false;
						}
						mp.play();
						
					}else {
						mp.pause();
					}
				}
				
			});
			
			
			
			
			//Add spacer
			Label spacer = new Label("  ");
			mediaBar.getChildren().add(spacer);
			
			
			//Add timeLabel
			Label timeLabel = new Label("Time: ");
			mediaBar.getChildren().add(timeLabel);
			
			//Add timeSlider
			timeSlider = new Slider();
			HBox.setHgrow(timeSlider,Priority.ALWAYS);
			timeSlider.setMinWidth(50);
			timeSlider.setMaxWidth(Double.MAX_VALUE);
			mp.currentTimeProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable ov) {				
					updateValues();
	
				}					
			});
			mp.setOnPlaying(new Runnable() {
				public void run() {
					if(stopRequested) {
						mp.pause();
						stopRequested = false;
					}else {
						playButton.setText("||");
					}
				}
			});
			
			mp.setOnPaused(new Runnable() {
				public void run() {
					System.out.println("onPaused");
					playButton.setText(">");
				}
			});
			
			mp.setOnReady(new Runnable(){
				public void run() {
					duration = mp.getMedia().getDuration();
					updateValues();
				}
			});
			
			mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE:1);
			
			mp.setOnEndOfMedia(new Runnable() {
				public void run() {
					if(!repeat) {
						playButton.setText(">");
						stopRequested = true;
						atEndOfMedia = true;
					}
				}
			});
			
			timeSlider.valueProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable ov) {
					if(timeSlider.isValueChanging()) {
						mp.seek(duration.multiply((timeSlider.getValue()/100.0)));
					}
				}
			});
			
			timeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							// TODO Auto-generated method stub
							
						}
				
			});
			mediaBar.getChildren().add(timeSlider);
			
			
			//Add Play Label
			playTime = new Label();
			playTime.setPrefWidth(130);
			playTime.setMinWidth(50);
			mediaBar.getChildren().add(playTime);
			
			//Add volume label
			Label volumeLabel = new Label("Vol: ");
			mediaBar.getChildren().add(volumeLabel);
			
			//Add volume Slider
			volumeSlider = new Slider();
			volumeSlider.setPrefWidth(70);
			volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
			volumeSlider.setMinWidth(30);
			volumeSlider.valueProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable ov) {
					if(volumeSlider.isValueChanging()) {
						mp.setVolume(volumeSlider.getValue()/100.0);
					}
				}
			});
			
			mediaBar.getChildren().add(volumeSlider);
			
			
			setBottom(mediaBar);
		}
		
		protected void updateValues() {
			if(playTime != null && timeSlider != null && volumeSlider != null) {
				Platform.runLater(new Runnable() {
					public void run() {
						Duration currentTime = mp.getCurrentTime();
						playTime.setText(formatTime(currentTime,duration));
						timeSlider.setDisable(duration.isUnknown());
						if(!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()){
							timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
						}
						if(!volumeSlider.isValueChanging()) {
							volumeSlider.setValue((int)Math.round(mp.getVolume() * 100.0));
						}
					}
				});
			}
		}
		
	}
	
	

	
	private static String formatTime(Duration elapsed, Duration duration) {
		
		int intElapsed = (int)Math.floor(elapsed.toSeconds());
		int elapsedHours = intElapsed/(60*60);
		if(elapsedHours > 0) {
			intElapsed -= elapsedHours * 60 * 60;
		}
		int elapsedMinutes = intElapsed/60;
		int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
		
		if(duration.greaterThan(Duration.ZERO)) {
			int intDuration = (int)Math.floor(duration.toSeconds());
			int durationHours = intDuration /(60*60);
			if( durationHours > 0) {
				intDuration -= durationHours /(60*60);
			}
			int durationMinutes = intDuration/60;
			int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;
			if (durationHours > 0) {         
				return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, 
						elapsedMinutes, elapsedSeconds, durationHours, durationMinutes, durationSeconds); 
			} else {          
				return String.format("%02d:%02d/%02d:%02d",            
						elapsedMinutes, elapsedSeconds,durationMinutes,         
						durationSeconds);      
			} 
		} else {
			if (elapsedHours > 0) {             
				return String.format("%d:%02d:%02d", elapsedHours,    
						elapsedMinutes, elapsedSeconds);           
			} else {               
				return String.format("%02d:%02d",elapsedMinutes,   
						elapsedSeconds);            
			}        
			
		}
	}
	
	public static void main(String[] args) {
		//mediaUrl = MEDIA_URL1;
		launch(args);
	} 



	
}
