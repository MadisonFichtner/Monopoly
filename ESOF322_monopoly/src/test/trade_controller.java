package test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

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
            	
                Window this_window = accept_button.getScene().getWindow();
                this_window.hide();
//                ACCEPT BUTTON IS PRESSED
            }
        });
    }
}
