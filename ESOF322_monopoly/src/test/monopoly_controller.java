package test;

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
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class monopoly_controller implements Initializable {
    @FXML
    public Button hotel_button;
    public Button mort_button;
    public Button sell_button;
    public Button house_button;
    public Button end_button;
    public Button roll_button;
    public Label message;


    public ImageView boardImg;
    public ImageView token_hat;
    public ImageView token_dog;
    public ImageView token_ship;
    public ImageView token_boot;

    public ArrayList<ImageView> token_list = new ArrayList<ImageView>();

    public int[][] coords = {{320,315}, {250,315}, {190,315}, {130,315}, {70,315}, {10,315}, {-70,315}, {-130,315}, {-200,315}, {-260,315}, {-375,380}, {-320,250}, {-320,190}, {-320,130}, {-320,70}, {-320,10}, {-320,-70}, {-320,-130}, {-320,-200}, {-320,-260}, {-320,-320}, {-260,-320}, {-200,-320}, {-130,-320}, {-70,-320}, {0,-320}, {70,-320}, {130,-320}, {200,-320}, {260,-320}, {330,-320}, {330,-260}, {330,-200}, {330,-130}, {330,-70}, {330,0}, {330,70}, {330,130}, {330,200}, {330,260}};

    private static final String COMMA_DELIMITER = ",";
    private static int users = 4;
    public static Player[] players = new Player[users];
    public static Board board;
    private static int playerTurn = 0;


    public void move_token(int player_num, int spot) {
        token_list.get(player_num).setTranslateX(coords[spot][0]);
        token_list.get(player_num).setTranslateY(coords[spot][1]);
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        token_list.add(token_hat);
        token_list.add(token_dog);
        token_list.add(token_ship);
        token_list.add(token_boot);

        getPlayers();
        disable_buttons();


        hotel_button.setOnAction(new EventHandler<ActionEvent>() {
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
                    set_message("No eligible properties, buy houses first");
                }
            }
        });

        mort_button.setOnAction(new EventHandler<ActionEvent>() {
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
        sell_button.setOnAction(new EventHandler<ActionEvent>() {
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

        house_button.setOnAction(new EventHandler<ActionEvent>() {
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

        end_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nextTurn();
            }
        });

        roll_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	disable_buttons();
                takeTurn();
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

    //MAKE RECEIVE MONOPOLY BOARD OR MSU BOARD
    //USE APPROPRIATE BOARD.JPG, DEEDS.CSV AND CARDS.CSV
    //is called by users.fxml
    public static void setPlayers(CharSequence[] users) {
        Deed[] deeds = new Deed[40];
        Card[] communityChest = new Card[16];
        Card[] chance = new Card[15];

        String deedsFileName = "monopolyDeeds.csv"; // just a sample file name
        File csvDeedsFile = new File(deedsFileName);
        try {
            deeds = parse_deeds_CSV(csvDeedsFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String communityFileName = "monopolyChestCards.csv";
        File csvCommunityFile = new File(communityFileName);
        
        try {
        	communityChest = parse_community_CSV(csvCommunityFile);
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
        
        String chanceFileName = "monopolyChanceCards.csv";
        File csvChanceFile = new File(chanceFileName);
        try {
        	chance = parse_chance_CSV(csvChanceFile);
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }

        for (int i = 0; i < users.length; i++) {
            players[i] = new Player(users[i].toString(), "token" + 1, i);
        }
        
        //board = new Board(players, deeds, communityChest, chance)
        board = new Board(players, deeds);
    }

    public static void takeTurn() {
        board.start_turn(players[playerTurn]);
    }

    public void nextTurn() {
        disable_buttons();
        playerTurn++;
        if(playerTurn == users) {
        	playerTurn = 0;
        }
    }

    public void disable_buttons() {
        roll_button.setDisable(false);
        hotel_button.setDisable(true);
        mort_button.setDisable(true);
        sell_button.setDisable(true);
        house_button.setDisable(true);
        end_button.setDisable(true);
    }

    public void enable_buttons() {
        roll_button.setDisable(true);
        hotel_button.setDisable(false);
        mort_button.setDisable(false);
        sell_button.setDisable(false);
        house_button.setDisable(false);
        end_button.setDisable(false);
    }

    /*
     * Parses the inputted .csv file to populate the game board with deeds
     */
    public static Deed[] parse_deeds_CSV(File csv_file) throws FileNotFoundException {
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
    
    public static Card[] parse_community_CSV(File csv_file) throws FileNotFoundException {
    	Card[] community = new Card[16];
    	Scanner in = new Scanner(csv_file);
    	in.useDelimiter(COMMA_DELIMITER);
    	for(int i = 0; i < 16; i++) {
    		String name = in.next();
    		int type = in.nextInt();
    		boolean isUsable = in.nextBoolean();
    		System.out.println("");
    		community[i] = new Card(name, type, isUsable);	
    	}
    	return community;
    }
    
    public static Card[] parse_chance_CSV(File csv_file) throws FileNotFoundException {
    	Card[] chance = new Card[15];
    	Scanner in = new Scanner(csv_file);
    	in.useDelimiter(COMMA_DELIMITER);
    	for(int i = 0; i < 15; i++) {
    		String name = in.next();
    		int type = in.nextInt();
    		boolean isUsable = in.nextBoolean();
    		
    		chance[i] = new Card(name, type, isUsable);
    	}
    	return chance;
    }
    
    public void set_message(String string) {
        message.setText(string);
    }
}
