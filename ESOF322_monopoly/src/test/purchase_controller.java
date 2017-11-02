package test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class purchase_controller implements Initializable {
    @FXML
    public Button yes_button;
    public Button no_button;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        yes_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                PLAYER HAS CHOSEN TO BUY THE PROPERTY
            }
        });

        no_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                PLAYER HAS CHOSEN NOT TO BUY THE PROPERTY
            }
        });

    }
}
