package test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
//import javafx.stage.Window;

public class jail_controller implements Initializable {
	public Button roll_button;
	public Button pay_button;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		roll_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("test");
				Board.current.roll_dice();
				Board.moveToSpace(true);
				System.out.println("test");
				Stage stage = (Stage) roll_button.getScene().getWindow();
            	stage.close();
			}
		});

		pay_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Board.current.in_jail = false;
				Main.monopoly.enable_buttons();
				Stage stage = (Stage) roll_button.getScene().getWindow();
            	stage.close();
			}
		});
	}

}
