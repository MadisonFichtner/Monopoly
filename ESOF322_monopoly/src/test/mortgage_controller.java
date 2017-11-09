package test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Window;
import javafx.util.StringConverter;

public class mortgage_controller  implements Initializable {
	public Button done_button;
	public ChoiceBox<Deed> deed_box;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		deed_box.setItems(FXCollections.observableArrayList(Board.current.deeds));
		
		done_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Window this_window = done_button.getScene().getWindow();
            	this_window.hide();
				if(deed_box.getValue().mortgaged && Board.current.money > deed_box.getValue().mortgage_owed) {
					Board.current.pay_mortgage_single(deed_box.getValue());
				} else if (!deed_box.getValue().mortgaged) {
					Board.current.mortgage_deed(deed_box.getValue());
				}
			}
		});

		// Changes the way the deed are displayed
		deed_box.setConverter(new StringConverter<Deed>() {
			@Override
			public String toString(Deed deed) {
				return new StringBuilder(deed.name +" - houses: " + deed.current_houses + " - Hotel: "+ deed.has_hotel + " - Mortgaged: " + deed.mortgaged).toString();
			}

			@Override
			public Deed fromString(String string) {
				throw new UnsupportedOperationException("Not supported.");
			}
		});
	}
}
