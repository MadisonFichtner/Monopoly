package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static monopoly_controller monopoly;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("monopoly.fxml"));
    	Parent root = loader.load();
    	monopoly=loader.<monopoly_controller>getController();
        primaryStage.setTitle("Monopoly");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.toBack();
    }
}
