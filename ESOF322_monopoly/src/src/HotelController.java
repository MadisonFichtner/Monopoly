package src;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class HotelController {
	public Button doneButton;
	public ChoiceBox<Deed> deedBox;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		ArrayList<Deed> deeds_with_max = new ArrayList<Deed>();
		Board board = GUIHelper.getBoard();
		
		for (int i = 0; i < board.current.deeds.size(); i++) {
			if (board.current.deeds.get(i).maxHouses == true) {
				deeds_with_max.add(board.current.deeds.get(i));
			}
		}

		deedBox.setItems(FXCollections.observableArrayList(deeds_with_max));

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
