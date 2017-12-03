package src;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class JailController implements Initializable {
	public Button rollButton;
	public Button payButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Board board = GUIHelper.getBoard();

		rollButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("test");
				board.current.rollDice();
				board.moveToSpace(true);
				System.out.println("test");
				Stage stage = (Stage) rollButton.getScene().getWindow();
            	stage.close();
			}
		});

		payButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				board.current.inJail = false;
                GUIHelper.enableTurnGUI();
				Stage stage = (Stage) rollButton.getScene().getWindow();
            	stage.close();
			}
		});
	}

}
