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
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class trade_controller implements Initializable {
	@FXML
	public ChoiceBox<Deed> trade_deed;
	public ChoiceBox<Player> trade_player;
	public TextField trade_amount;
	public Button accept_button;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(Board.players));
		trade_player.setItems(FXCollections.observableArrayList(players));

		trade_deed.setItems(FXCollections.observableArrayList(Board.current.deeds));

		accept_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (trade_player.getValue().money >= Integer.parseInt(trade_amount.getText())) {
					Window this_window = accept_button.getScene().getWindow();
					this_window.hide();
					Board.current.traded_deed(trade_deed.getValue(), trade_player.getValue(), Integer.parseInt(trade_amount.getText()));
				} else {
					Main.monopoly.set_message(trade_player.getValue().name + " does not have $" + Integer.parseInt(trade_amount.getText()) + "!");
				}
			}
		});

		// Changes the way the deed are displayed
		trade_deed.setConverter(new StringConverter<Deed>() {
			@Override
			public String toString(Deed deed) {
				return new StringBuilder(deed.name).toString();
			}

			@Override
			public Deed fromString(String string) {
				throw new UnsupportedOperationException("Not supported.");
			}
		});

		// Changes the way the players are displayed
		trade_player.setConverter(new StringConverter<Player>() {
			@Override
			public String toString(Player person) {
				return new StringBuilder(person.name + " - $" + person.money).toString();
			}

			@Override
			public Player fromString(String string) {
				throw new UnsupportedOperationException("Not supported.");
			}
		});

		// This listener deletes any characters that get addedn to the amount
		trade_amount.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					char ch = trade_amount.getText().charAt(oldValue.intValue());
					// Check if the new character is the number or other's
					if (!(ch >= '0' && ch <= '9')) {
						// if it's not number then just setText to previous one
						trade_amount.setText(trade_amount.getText().substring(0, trade_amount.getText().length() - 1));
					}
				}
			}
		});
	}
}
