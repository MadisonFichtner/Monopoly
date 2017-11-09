package test;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Window;
import javafx.util.StringConverter;

public class hotel_contoller {
	public Button done_button;
	public ChoiceBox<Deed> deed_box;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		ArrayList<Deed> deeds_with_max = new ArrayList<Deed>();
		boolean eligible = false;
		for (int i = 0; i < Board.current.deeds.size(); i++) {
			if (Board.current.deeds.get(i).max_houses == true) {
				deeds_with_max.add(Board.current.deeds.get(i));
				eligible = true;
			}
		}

		deed_box.setItems(FXCollections.observableArrayList(deeds_with_max));

		done_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Window this_window = done_button.getScene().getWindow();
            	this_window.hide();
				Board.current.bought_house(deed_box.getValue(), 1);
			}
		});

		// Changes the way the deed are displayed
		deed_box.setConverter(new StringConverter<Deed>() {
			@Override
			public String toString(Deed deed) {
				return new StringBuilder(deed.name + " - houses: " + deed.current_houses).toString();
			}

			@Override
			public Deed fromString(String string) {
				throw new UnsupportedOperationException("Not supported.");
			}
		});
	}
}
