package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GUIHelper {
	private static MonopolyController monopoly;
	private static final String COMMA_DELIMITER = ",";
	private static int users = 4;
	private static Player[] players = new Player[users];
	private static Board board;
	private static int playerTurn = 0;

	public static void setMessage(String string) {
		monopoly.message.setText(string);
	}

	public static void setMonopoly(MonopolyController monopolyCont) {
		monopoly = monopolyCont;
	}

	public static void enableRollGUI() {
		monopoly.disable_buttons();
	}

	public static void enableTurnGUI() {
		monopoly.enable_buttons();
	}

	public static void moveTokenImg(int player_num, int spot) {
		monopoly.move_token(player_num, spot);
	}
	
	public static int getNumPlayers() {
		return players.length;
	}
	
	public static Player getPlayer(int index) {
		return players[index];
	}
	
	public static Player[] getPlayers() {
		return players;
	}

	public static void nextTurn() {
		enableRollGUI();
		playerTurn++;
		if (playerTurn == users) {
			playerTurn = 0;
		}
	}
	
    public static void takeTurn() {
        board.start_turn(players[playerTurn]);
    }
    
    //MAKE RECEIVE MONOPOLY BOARD OR MSU BOARD
    //USE APPROPRIATE BOARD.JPG, DEEDS.CSV AND CARDS.CSV
    //is called by users.fxml
    public static void setPlayers(CharSequence[] users) {
        Deed[] deeds = new Deed[40];
        ArrayList<Card> communityChest = new ArrayList<Card>();
        ArrayList<Card> chance = new ArrayList<Card>();
        
        //if(users choice == monopoly)
        	//String deedsFileName = "monopolyDeeds.csv";
        //else
        String deedsFileName = "MSUdeeds.csv"; // just a sample file name
        File csvDeedsFile = new File(deedsFileName);
        try {
            deeds = parse_deeds_CSV(csvDeedsFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //if(users choice == monopoly)
        	//String communityFileName = "monopolyChestCards.csv";
        //else
        String communityFileName = "MSUChestCards.csv";
        File csvCommunityFile = new File(communityFileName);
        try {
        	communityChest = parse_community_CSV(csvCommunityFile);
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
        
        //if(users choice == monopoly)
        	//String chanceFileName = "monopolyChanceCards.csv";
        //else
        String chanceFileName = "MSUChanceCards.csv";
        File csvChanceFile = new File(chanceFileName);
        try {
        	chance = parse_chance_CSV(csvChanceFile);
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        }

        for (int i = 0; i < users.length; i++) {
            players[i] = new Player(users[i].toString(), "token" + 1, i);
        }
        
        //Randomizing cards
        Collections.shuffle(communityChest);
        Collections.shuffle(chance);
        
        //Board now takes in the communityChest and chance cards
        board = new Board(players, deeds, communityChest, chance);
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
    
    public static ArrayList<Card> parse_community_CSV(File csv_file) throws FileNotFoundException {
    	//Card[] community = new Card[16];
        ArrayList<Card> community = new ArrayList<Card>();
    	Scanner in = new Scanner(csv_file);
    	in.useDelimiter(COMMA_DELIMITER);
    	for(int i = 0; i < 16; i++) {
    		String name = in.next();
    		int type = in.nextInt();
    		in.next();
    		
    		Card card = new Card(name, type);	
    		community.add(card);
    	}
    	return community;
    }
    
    public static ArrayList<Card> parse_chance_CSV(File csv_file) throws FileNotFoundException {
    	//Card[] chance = new Card[15];
        ArrayList<Card> chance = new ArrayList<Card>();
    	Scanner in = new Scanner(csv_file);
    	in.useDelimiter(COMMA_DELIMITER);
    	for(int i = 0; i < 15; i++) {
    		String name = in.next();
    		int type = in.nextInt();
    		in.next();
    		
    		Card card = new Card(name, type);
    		chance.add(card);
    	}
    	return chance;
    }
}
