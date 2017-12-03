package src;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

public class HouseController implements Initializable {
	public Button done_button;
	public ChoiceBox<Deed> deed_box;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		deed_box.setItems(FXCollections.observableArrayList(Board.current.deeds));
		
		done_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Board.current.bought_house(deed_box.getValue(), 1);
				Stage stage = (Stage) done_button.getScene().getWindow();
            	stage.close();
			}
		});

		// Changes the way the deed are displayed
		deed_box.setConverter(new StringConverter<Deed>() {
			@Override
			public String toString(Deed deed) {
				return new StringBuilder(deed.name +" - houses: " + deed.current_houses).toString();
			}

			@Override
			public Deed fromString(String string) {
				throw new UnsupportedOperationException("Not supported.");
			}
		});
	}
}
