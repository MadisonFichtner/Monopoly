package src;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController implements Initializable{
	public Button doneButton;
	public TextField player1;
	public TextField player2;
	public TextField player3;
	public TextField player4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	CharSequence[] players = {player1.getCharacters(), player2.getCharacters(), player3.getCharacters(), player4.getCharacters()};
            	GUIHelper.setPlayers(players);
            	GUIHelper.enableRollGUI();
            	Stage stage = (Stage) doneButton.getScene().getWindow();
            	stage.close();
            }
		});
	}

}
