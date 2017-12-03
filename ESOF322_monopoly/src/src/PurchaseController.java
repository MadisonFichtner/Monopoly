package src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseController implements Initializable {
    @FXML
    public Button yes_button;
    public Button no_button;
    public Label deed_label;
    Deed deed;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
//    	System.out.println("test");
    	deed = Board.board[Board.position];
    	deed_label.setText("Do you want to buy " + deed.name + " for " + deed.purchase_price + "?");
        yes_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                PLAYER HAS CHOSEN TO BUY THE PROPERTY
            	Board.current.bought_property(deed);
            	Stage stage = (Stage) yes_button.getScene().getWindow();
            	stage.close();
            }
        });

        no_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                PLAYER HAS CHOSEN NOT TO BUY THE PROPERTY
            	Board.auctionProperty();
            	Stage stage = (Stage) yes_button.getScene().getWindow();
            	stage.close();
            }
        });
        
    }
}
