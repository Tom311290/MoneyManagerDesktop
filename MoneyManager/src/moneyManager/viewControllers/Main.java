package moneyManager.viewControllers;
	
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import moneyManager.constants.AppConstants;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public Stage primaryStg = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource(AppConstants.MONEY_MANAGER_LAYOUT));
			Scene scene = new Scene(root, 1200, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Money manager");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
