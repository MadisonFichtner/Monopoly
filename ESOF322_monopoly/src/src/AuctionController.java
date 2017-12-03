package src;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {
    @FXML
    public Label currentPrice;
    public ChoiceBox<Player> bidPlayer;
    public TextField bidAmount;
    public Button bidButton;
    public Button doneButton;
    

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	Board board = GUIHelper.getBoard();
    	int[] bids = board.bids;
    	
    	ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(GUIHelper.getPlayers()));
		bidPlayer.setItems(FXCollections.observableArrayList(players));
		currentPrice.setText("The highest bid for " + board.deedBoard[board.position].name + " $" + board.highestBid);
        
		bidButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Player bidder = bidPlayer.getValue();
				for(int i =0; i < GUIHelper.getNumPlayers(); i++) {
					if(GUIHelper.getPlayer(i).name.compareTo(bidder.name) == 0) {
						if(GUIHelper.getPlayer(i).money >= Integer.parseInt(bidAmount.getText())) {
    						bids[i] = Integer.parseInt(bidAmount.getText());
						} else {
							GUIHelper.setMessage("You don't have that much money!");
						}
					}
				}
			}
		});
 		
 		doneButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				board.auction(bids, board.highestBid, board.deedBoard[board.position]);
				Stage stage = (Stage) doneButton.getScene().getWindow();
            	stage.close();
			}
		});
		
		// Changes the way the players are displayed
     		bidPlayer.setConverter(new StringConverter<Player>() {
     			@Override
     			public String toString(Player person) {
     				return new StringBuilder(person.name + " - $" + person.money).toString();
     			}

     			@Override
     			public Player fromString(String string) {
     				throw new UnsupportedOperationException("Not supported.");
     			}
     		});
     		
     		// This listener deletes any characters that get added to the amount
    		bidAmount.lengthProperty().addListener(new ChangeListener<Number>() {
    			@Override
    			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    				if (newValue.intValue() > oldValue.intValue()) {
    					char ch = bidAmount.getText().charAt(oldValue.intValue());
    					// Check if the new character is the number or other's
    					if (!(ch >= '0' && ch <= '9')) {
    						// if it's not number then just setText to previous one
    						bidAmount.setText(bidAmount.getText().substring(0, bidAmount.getText().length() - 1));
    					}
    				}
    			}
    		});
        
    }

}
