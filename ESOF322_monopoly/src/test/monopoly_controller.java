package test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.FileReader;

public class monopoly_controller implements Initializable {
	@FXML
	public Button roll_button;
	public Button trade_button;
	public Button mortgage_button;
	public ImageView dice_a;
	public ImageView dice_b;
	public ImageView test_token;
	public ImageView boardImg;

	private static final String COMMA_DELIMITER = ",";

	private static int users = 4;
	private static Player[] players = new Player[users];
	Board board;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		init();

		roll_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(players[0].name);
				dice_a.setImage(new Image("/resources/dice_2.png"));
				// ROLL BUTTON IS PRESSED
			}
		});

		trade_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				trade_window();
				// TRADE BUTTON IN PRESSED
			}
		});

		mortgage_button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mortgage_window();
				// MORTGAGE BUTTON IS PRESSED
			}
		});
	}

	private void trade_window() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("auction.fxml"));
			Stage trade_stage = new Stage();
			trade_stage.setTitle("Trade");
			trade_stage.setScene(new Scene(root));
			trade_stage.show();
		} catch (Exception e) {
			System.out.println("Something went wrong");
		}

	}

	private void mortgage_window() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("mortgage.fxml"));
			Stage trade_stage = new Stage();
			trade_stage.setTitle("Mortgage");
			trade_stage.setScene(new Scene(root));
			trade_stage.show();
		} catch (Exception e) {
			System.out.println("Something went wrong");
		}

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

	public void init() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("users.fxml"));
			Stage trade_stage = new Stage();
			trade_stage.setTitle("Players");
			trade_stage.setScene(new Scene(root));
			trade_stage.show();
		} catch (Exception e) {
			System.out.println("Something went wrong");
		}

		Deed[] new_board = new Deed[40];

		String fileName = "test.csv"; // just a sample file name
		File csvFile = new File(fileName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(csvFile));
			new_board = parse_CSV(csvFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Board board = new Board(players, new_board);
	}
	
	public static void setPlayers(CharSequence[] users) {
		for ( int i = 0; i < users.length; i++) {
			players[i] = new Player(users[i].toString(), "token" + 1);
		}
	}
}
