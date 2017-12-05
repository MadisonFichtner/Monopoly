package src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MonopolyController implements Initializable {
	@FXML
	public Button hotelButton;
	public Button mortButton;
	public Button sellButton;
	public Button houseButton;
	public Button endButton;
	public Button rollButton;
	public Label message;
	public ImageView tokenHat;
	public ImageView tokenDog;
	public ImageView tokenShip;
	public ImageView tokenBoot;
	public ImageView board;

	ArrayList<ImageView> tokenList = new ArrayList<ImageView>();
	int[][] coords = { { 320, 315 }, { 250, 315 }, { 190, 315 }, { 130, 315 }, { 70, 315 }, { 10, 315 }, { -70, 315 },
			{ -130, 315 }, { -200, 315 }, { -260, 315 }, { -375, 380 }, { -320, 250 }, { -320, 190 }, { -320, 130 },
			{ -320, 70 }, { -320, 10 }, { -320, -70 }, { -320, -130 }, { -320, -200 }, { -320, -260 }, { -320, -320 },
			{ -260, -320 }, { -200, -320 }, { -130, -320 }, { -70, -320 }, { 0, -320 }, { 70, -320 }, { 130, -320 },
			{ 200, -320 }, { 260, -320 }, { 330, -320 }, { 330, -260 }, { 330, -200 }, { 330, -130 }, { 330, -70 },
			{ 330, 0 }, { 330, 70 }, { 330, 130 }, { 330, 200 }, { 330, 260 } };

	public void moveToken(int player_num, int spot) {
		tokenList.get(player_num).setTranslateX(coords[spot][0]);
		tokenList.get(player_num).setTranslateY(coords[spot][1]);
	}

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		tokenList.add(tokenHat);
		tokenList.add(tokenDog);
		tokenList.add(tokenShip);
		tokenList.add(tokenBoot);

		getPlayers();
		disableButtons();

		hotelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUIHelper.openWindow("hotel");
			}
		});

		mortButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUIHelper.openWindow("mortgage");
			}
		});

		// Handles trading deed. There is a ui template for this but no code
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUIHelper.openWindow("trade");
			}
		});

		houseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUIHelper.openWindow("house");
			}
		});

		endButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GUIHelper.nextTurn();
			}
		});

		rollButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				disableButtons();
				GUIHelper.takeTurn();
			}
		});
	}

	// Opens the UI for inputing player names
	public void getPlayers() {
		GUIHelper.openWindow("users");
	}

	public void disableButtons() {
		rollButton.setDisable(false);
		hotelButton.setDisable(true);
		mortButton.setDisable(true);
		sellButton.setDisable(true);
		houseButton.setDisable(true);
		endButton.setDisable(true);
	}

	public void setBoardImage(String input) {
		try {
			Image boardImage = new Image("file:" + input);
			board.setImage(boardImage);
		} catch (Exception e) {
			System.out.println("Provided board image not found");
			e.printStackTrace();
		}
	}

	public void enableButtons() {
		rollButton.setDisable(true);
		hotelButton.setDisable(false);
		mortButton.setDisable(false);
		sellButton.setDisable(false);
		houseButton.setDisable(false);
		endButton.setDisable(false);
	}
}
