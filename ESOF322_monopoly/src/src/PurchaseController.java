package src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class PurchaseController implements Initializable {
    @FXML
    public Button yesButton;
    public Button noButton;
    public Label deedLabel;
    Deed deed;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
//    	System.out.println("test");
    	Board board = GUIHelper.getBoard();
    	deed = board.getDeedBoard()[board.position];
    	deedLabel.setText("Do you want to buy " + deed.name + " for " + deed.price + "?");
        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                PLAYER HAS CHOSEN TO BUY THE PROPERTY
            	board.current.boughtProperty(deed);
            	Stage stage = (Stage) yesButton.getScene().getWindow();
            	stage.close();
            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                PLAYER HAS CHOSEN NOT TO BUY THE PROPERTY
            	board.auctionProperty();
            	Stage stage = (Stage) yesButton.getScene().getWindow();
            	stage.close();
            }
        });
        
    }
}
