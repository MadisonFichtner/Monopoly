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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TradeController implements Initializable {
    @FXML
    public ChoiceBox<Deed> tradeDeed;
    public ChoiceBox<Player> tradePlayer;
    public TextField tradeAmount;
    public Button acceptButton;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Board board = GUIHelper.getBoard();
        ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(GUIHelper.getPlayers()));

        tradePlayer.setItems(FXCollections.observableArrayList(players));
        tradeDeed.setItems(FXCollections.observableArrayList(board.current.deeds));

        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tradePlayer.getValue().money >= Integer.parseInt(tradeAmount.getText())) {
                    Stage stage = (Stage) acceptButton.getScene().getWindow();
                    stage.close();
                    board.current.tradedDeed(tradeDeed.getValue(), tradePlayer.getValue(), Integer.parseInt(tradeAmount.getText()));
                } else {
                    GUIHelper.setMessage(tradePlayer.getValue().name + " does not have $" + Integer.parseInt(tradeAmount.getText()) + "!");
                }
            }
        });

        // Changes the way the deed are displayed
        tradeDeed.setConverter(new StringConverter<Deed>() {
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
        tradePlayer.setConverter(new StringConverter<Player>() {
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
        tradeAmount.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    char ch = tradeAmount.getText().charAt(oldValue.intValue());
                    // Check if the new character is the number or other's
                    if (!(ch >= '0' && ch <= '9')) {
                        // if it's not number then just setText to previous one
                        tradeAmount.setText(tradeAmount.getText().substring(0, tradeAmount.getText().length() - 1));
                    }
                }
            }
        });
    }
}
