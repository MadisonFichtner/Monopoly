package test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class monopoly_controller implements Initializable {
    @FXML
    public Button roll_button;
    public Button trade_button;
    public Button mortgage_button;
    public ImageView dice_a;
    public ImageView dice_b;
    public ImageView test_token;
    public ImageView board;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        roll_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dice_a.setImage(new Image("/resources/dice_2.png"));
//                ROLL BUTTON IS PRESSED
            }
        });

        trade_button.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               trade_window();
//               TRADE BUTTON IN PRESSED
           }
       });

        mortgage_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mortgage_window();
//                MORTGAGE BUTTON IS PRESSED
            }
        });




    }

    private void trade_window() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("trade.fxml"));
            Stage trade_stage = new Stage();
            trade_stage.setTitle("Trade");
            trade_stage.setScene(new Scene(root));
            trade_stage.show();
        } catch (Exception e){
            System.out.println("Something went wrong");
        }

    }

    private void mortgage_window() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mortgage.fxml"));
            Stage trade_stage = new Stage();
            trade_stage.setTitle("Mortgage");
            trade_stage.setScene(new Scene(root));
            trade_stage.show();
        } catch (Exception e){
            System.out.println("Something went wrong");
        }

    }
}
