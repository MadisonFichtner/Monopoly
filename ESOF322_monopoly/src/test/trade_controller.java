package test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class trade_controller implements Initializable {
    @FXML
    public ChoiceBox trade_deed;
    public ChoiceBox trade_player;
    public TextField trade_amount;
    public Button accept_button;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        accept_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                ACCEPT BUTTON IS PRESSED
            }
        });
    }
}
