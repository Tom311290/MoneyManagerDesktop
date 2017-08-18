package viewControllers;
	
import constants.ConstantsClass;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Parent root = FXMLLoader.load(getClass().getResource(ConstantsClass.MONEY_MANAGER_LAYOUT));
			Parent root = FXMLLoader.load(getClass().getResource(ConstantsClass.MONEY_MANAGER_LAYOUT));
			Scene scene = new Scene(root,800, 500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Money manager");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
