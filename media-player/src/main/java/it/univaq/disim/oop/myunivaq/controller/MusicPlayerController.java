package it.univaq.disim.oop.myunivaq.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicPlayerController implements Initializable {

	@FXML
	private ImageView songImage;

	@FXML
	private Label songTitleLabel;

	@FXML
	private ImageView previousSongImage;

	@FXML
	private ImageView playSongImage;

	@FXML
	private ImageView nextSongImage;

	@FXML
	private ProgressBar songProgressBar;

	private Media media;
	private MediaPlayer mediaPlayer;

	private File directory;
	private File[] files;

	private ArrayList<File> songs;

	private int songNumber;

	private Timer timer;
	private TimerTask task;
	private boolean running;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		songTitleLabel.setAlignment(Pos.CENTER_LEFT);

		songs = new ArrayList<File>();

		directory = new File("music");

		files = directory.listFiles();

		if (files != null) {

			for (File file : files) {

				songs.add(file);
				System.out.println(file);
			}
		}

		createMedia();

		/*
		 * volumeSlider.valueProperty().addListener(new ChangeListener<Number>(){
		 * 
		 * @Override public void changed(ObservableValue<? extends Number> arg0, Number
		 * arg1, Number arg2) {
		 * 
		 * mediaPlayer.setVolume(volumeSlider.getValue() * 0.01); }
		 * 
		 * });
		 */

		songProgressBar.setStyle("-fx-accent: #00FF00;");

	}

	// initialises the label and starts playing the song
	public void createMedia() {
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);

		songTitleLabel.setText(songs.get(songNumber).getName());
	}

	@FXML
	public void playSong() {
		beginTimer();
		mediaPlayer.play();
		
		//mediaPlayer.pause();
	}

	@FXML
	public void previousSong() {

		if (songNumber > 0) {
			songNumber--;
		} else {
			songNumber = songs.size() - 1;
		}

		mediaPlayer.stop();

		if (running) {
			cancelTimer();
		}

		createMedia();
		playSong();
	}

	@FXML
	public void nextSong() {

		if (songNumber < songs.size() - 1) {
			songNumber++;
		} else {
			songNumber = 0;
		}

		mediaPlayer.stop();

		if (running) {
			cancelTimer();
		}

		createMedia();
		playSong();
	}

	// used for progressBar
	public void beginTimer() {

		timer = new Timer();

		task = new TimerTask() {

			public void run() {

				running = true;
				double current = mediaPlayer.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				songProgressBar.setProgress(current / end);

				if (current / end == 1) {
					cancelTimer();
				}
			}

		};

		timer.scheduleAtFixedRate(task, 1000, 1000);
	}

	// when song is finished resets the progressBar advancement
	public void cancelTimer() {

		running = false;
		timer.cancel();
	}
	
	public void pauseTimer() {
		running = false;
	}

}
