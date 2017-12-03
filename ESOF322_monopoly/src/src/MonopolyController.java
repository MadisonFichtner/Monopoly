package src;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
				try {
					Parent root = FXMLLoader.load(getClass().getResource("hotel.fxml"));
					Stage trade_stage = new Stage();
					trade_stage.setTitle("Buy hotel");
					trade_stage.setScene(new Scene(root));
					trade_stage.show();
				} catch (Exception e) {
					System.out.println("Something went wrong");
					GUIHelper.setMessage("No eligible properties, buy houses first");
				}
			}
		});

		mortButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				try {
					Parent root = FXMLLoader.load(getClass().getResource("mortgage.fxml"));
					Stage trade_stage = new Stage();
					trade_stage.setTitle("Choose a deed");
					trade_stage.setScene(new Scene(root));
					trade_stage.show();
				} catch (Exception e) {
					System.out.println("Something went wrong");
				}
			}
		});

		// Handles trading deed. There is a ui template for this but no code
		sellButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("trade.fxml"));
					Stage trade_stage = new Stage();
					trade_stage.setTitle("Trade");
					trade_stage.setScene(new Scene(root));
					trade_stage.show();
				} catch (Exception e) {
					System.out.println("Something went wrong");
				}
			}
		});

		houseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("house.fxml"));
					Stage trade_stage = new Stage();
					trade_stage.setTitle("Buy hotel");
					trade_stage.setScene(new Scene(root));
					trade_stage.show();
				} catch (Exception e) {
					System.out.println("Something went wrong");
				}
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
		try {
			Parent root = FXMLLoader.load(getClass().getResource("users.fxml"));
			Stage trade_stage = new Stage();
			trade_stage.setTitle("Players");
			trade_stage.setScene(new Scene(root));
			trade_stage.show();
		} catch (Exception e) {
			System.out.println("Something went wrong");
		}
	}

	public void disableButtons() {
		rollButton.setDisable(false);
		hotelButton.setDisable(true);
		mortButton.setDisable(true);
		sellButton.setDisable(true);
		houseButton.setDisable(true);
		endButton.setDisable(true);
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
