package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("monopoly.fxml"));
    	Parent root = loader.load();
    	GUIHelper.setMonopoly(loader.getController());
        primaryStage.setTitle("Monopoly");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.toBack();
    }
}
