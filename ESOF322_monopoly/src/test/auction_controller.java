package test;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class auction_controller implements Initializable {
    @FXML
    public Label current_price;
    public ImageView auction_deed;
    public ChoiceBox bid_player;
    public TextField bid_amount;
    public Button bid_button;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        ArrayList<String> testing = new ArrayList<String>();
        testing.add("Trent");
        testing.add("Logan");
        testing.add("Cody");
        testing.add("Madison");
        bid_player.setItems(FXCollections.observableArrayList(testing));
    }

}
