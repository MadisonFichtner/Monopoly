package test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
//                ROLL DICE HERE
                System.out.println("rolling dice");

//                MOVING TOKENS WILL BE SOMETHING LIKE THIS
                double tempx = test_token.getTranslateX();
                test_token.setTranslateX(tempx - 50);

//                we shouldn't do relative transforms, we should have translate values associated with each board location so we can just do token.setTranslateX(some_val)

                testWindow();
            }
        });

    }

        public void testWindow() {
            try{
                Parent newWin = FXMLLoader.load(getClass().getResource("auction.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(newWin));
                newStage.show();
            }
            catch (Exception e) {
                System.out.print("something went wrong");
            }

        }
}
