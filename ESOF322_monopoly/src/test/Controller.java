package test;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import java.net.URL;


import java.util.ResourceBundle;

public class Controller  implements Initializable{
    @FXML
    public Button roll_button;
    public ImageView dice_a;
    public ImageView dice_b;
    public ImageView test_token;
    public ImageView board;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        roll_button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
//                ROLL DICE METHOD
            }
        });    }
}
