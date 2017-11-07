package monopoly;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Window;

public class RollController implements Initializable {
		public Button roll_button;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			roll_button.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	Window this_window = roll_button.getScene().getWindow();
	            	this_window.hide();
	            	Board.moveToSpace(false);
	            }
			});
			
		}

}
