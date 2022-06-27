package it.univaq.disim.oop.myunivaq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class MediaPlayerApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/mediaplayer.fxml"));
		Parent musicPlayer = loader.load();
		Scene scene = new Scene(musicPlayer);
		stage.setScene(scene);
		stage.show();

		 
	}

}
