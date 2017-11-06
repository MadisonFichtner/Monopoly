package test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class users_controller implements Initializable{
	public Button done_button;
	public TextField player1;
	public TextField player2;
	public TextField player3;
	public TextField player4;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		done_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	CharSequence[] players = {player1.getCharacters(), player2.getCharacters(), player3.getCharacters(), player4.getCharacters()};
            	monopoly_controller.setPlayers(players);
            	
            	Window this_window = done_button.getScene().getWindow();
            	this_window.hide();
            }
		});
	}

}
