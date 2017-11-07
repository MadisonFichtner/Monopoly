package test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class monopoly_controller implements Initializable {
	@FXML
	public Button hotel_button;
	public Button mort_button;
	public Button sell_button;
	public Button house_button;
	public Button end_button;
	
	public ImageView dice_a;
	public ImageView dice_b;
	public ImageView test_token;
	public ImageView boardImg;

	private static final String COMMA_DELIMITER = ",";

	private static int users = 4;
	private static Player[] players = new Player[users];
	private static Board board;
	private static int playerTurn = 0;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		getPlayers();
		disableButtons();
		
		hotel_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Board.current.buy_hotel();
            }
		});
		
		mort_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	//This just mortgages their 0th deed
            	Board.current.print_deeds();
				System.out.println("Which deed would you like to mortgage?");
				Deed deed = Board.current.deeds.get(0);
				Board.current.mortgage_deed(deed);
            }
		});
		
		// Handles trading deed. There is a ui template for this but no code
		sell_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				Board.current.trade_deed(players);
            }
		});
		
		house_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Board.current.buy_house();
            }
		});
		
		end_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	nextTurn();
            }
		});
//
//		trade_button.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				trade_window();
//				// TRADE BUTTON IN PRESSED
//			}
//		});
//
//		mortgage_button.setOnAction(new EventHandler<ActionEvent>() {
//			@Override
//			public void handle(ActionEvent event) {
//				mortgage_window();
//				// MORTGAGE BUTTON IS PRESSED
//			}
//		});
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
	
	//is called by users.fxml
	public static void setPlayers(CharSequence[] users) {
		Deed[] deeds = new Deed[40];

		String fileName = "ESOF322_monopoly/test.csv"; // just a sample file name
		File csvFile = new File(fileName);
		try {
			deeds = parse_CSV(csvFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for ( int i = 0; i < users.length; i++) {
			players[i] = new Player(users[i].toString(), "token" + 1);
		}
		board = new Board(players, deeds);
	}
	
	public static void takeTurn() {
		board.init_turn(players[playerTurn]);
	}
	
	public static void nextTurn() {
		playerTurn++;
		takeTurn();
	}
	public void disableButtons() {
		hotel_button.setDisable(true);
		mort_button.setDisable(true);
		sell_button.setDisable(true);
		house_button.setDisable(true);
		end_button.setDisable(true);
	}
	
	public void enableButtons() {
		hotel_button.setDisable(false);
		mort_button.setDisable(false);
		sell_button.setDisable(false);
		house_button.setDisable(false);
		end_button.setDisable(false);
	}
	
	/*
	 * Parses the inputted .csv file to populate the game board with deeds
	 */
	public static Deed[] parse_CSV(File csv_file) throws FileNotFoundException {
		Deed[] deeds = new Deed[40];
		Scanner in = new Scanner(csv_file);
		in.useDelimiter(COMMA_DELIMITER);
		for (int i = 0; i < 40; i++) {
			int position = in.nextInt();
			String name = in.next();
			int property_group = in.nextInt();
			String color = in.next();
			int price = in.nextInt();
			int mortgage_value = in.nextInt();
			int rent = in.nextInt();
			int rent1 = in.nextInt();
			int rent2 = in.nextInt();
			int rent3 = in.nextInt();
			int rent4 = in.nextInt();
			int rent_hotel = in.nextInt();
			int build_cost = in.nextInt();
			in.next();
			String deed_type = in.next();

			deeds[i] = new Deed(position, name, property_group, color, price, mortgage_value, rent, rent1, rent2, rent3,
					rent4, rent_hotel, build_cost, deed_type);
		}
		// populate deeds giving csv_file
		return deeds;
	}
}
