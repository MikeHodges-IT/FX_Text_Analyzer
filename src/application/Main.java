package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
/**
 * 
 * This is the Text Analyzer Project. This class extends Application for javaFx.
 *
 */
/**
 * 
 * @author Mike Hodges
 * @version 1.0000001
 * 
 */

public class Main extends Application {
	@Override
	/**
	 * This is the start method that calls the Launch method to start the stand alone application.
	 */
	public void start(Stage primaryStage) {
		



	
		
		try {
			
			Pane mainPane =  (Pane) FXMLLoader.load(Main.class.getResource("Sample.fxml"));
			Scene scene = new Scene(mainPane);
			primaryStage.setScene(scene);
			primaryStage.show();

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
