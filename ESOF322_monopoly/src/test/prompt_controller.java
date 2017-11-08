package test;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class prompt_controller implements Initializable{
	public Button hotel_button;
	public Button mort_button;
	public Button sell_button;
	public Button house_button;
	public Button end_button;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		hotel_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
		});
		
		mort_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            }
		});
		
		sell_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            }
		});
		
		house_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            }
		});
		
		end_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            }
		});
	}

}
