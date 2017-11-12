package test;

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class auction_controller implements Initializable {
    @FXML
    public Label current_price;
    public ImageView auction_deed;
    public ChoiceBox<Player> bid_player;
    public TextField bid_amount;
    public Button bid_button;
    public Button done_button;
    

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
    	int[] bids = Board.bids;
    	ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(monopoly_controller.players));
		bid_player.setItems(FXCollections.observableArrayList(players));
		current_price.setText("The highest bid is $" + Board.highest_bid);
        
		// Changes the way the players are displayed
     		bid_player.setConverter(new StringConverter<Player>() {
     			@Override
     			public String toString(Player person) {
     				return new StringBuilder(person.name + " - $" + person.money).toString();
     			}

     			@Override
     			public Player fromString(String string) {
     				throw new UnsupportedOperationException("Not supported.");
     			}
     		});
     		
     		bid_button.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
    				Player bidder = bid_player.getValue();
    				for(int i =0; i < monopoly_controller.players.length; i++) {
    					if(monopoly_controller.players[i].name.compareTo(bidder.name) == 0) {
    						if(monopoly_controller.players[i].money > Integer.parseInt(bid_amount.getText())) {
        						bids[i] = Integer.parseInt(bid_amount.getText());
    						} else {
    							Main.monopoly.set_message("You don't have that much money!");
    						}
    					}
    				}
    			}
    		});
     		
     		done_button.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
    				Board.gui_auction(bids, Board.highest_bid, Board.board[Board.position]);
    				Stage stage = (Stage) done_button.getScene().getWindow();
                	stage.close();
    			}
    		});
     		
     		// This listener deletes any characters that get added to the amount
    		bid_amount.lengthProperty().addListener(new ChangeListener<Number>() {
    			@Override
    			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
    				if (newValue.intValue() > oldValue.intValue()) {
    					char ch = bid_amount.getText().charAt(oldValue.intValue());
    					// Check if the new character is the number or other's
    					if (!(ch >= '0' && ch <= '9')) {
    						// if it's not number then just setText to previous one
    						bid_amount.setText(bid_amount.getText().substring(0, bid_amount.getText().length() - 1));
    					}
    				}
    			}
    		});
        
    }

}
