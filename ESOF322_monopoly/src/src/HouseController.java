package src;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class HouseController implements Initializable {
    public Button doneButton;
    public ChoiceBox<Deed> deedBox;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Board board = GUIHelper.getBoard();
        deedBox.setItems(FXCollections.observableArrayList(board.current.deeds));

        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.current.boughtHouse(deedBox.getValue(), 1);
                Stage stage = (Stage) doneButton.getScene().getWindow();
                stage.close();
            }
        });

        // Changes the way the deed are displayed
        deedBox.setConverter(new StringConverter<Deed>() {
            @Override
            public String toString(Deed deed) {
                return new StringBuilder(deed.name + " - houses: " + deed.currentHouses).toString();
            }

            @Override
            public Deed fromString(String string) {
                throw new UnsupportedOperationException("Not supported.");
            }
        });
    }
}
