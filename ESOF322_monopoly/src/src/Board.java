package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    int numHotelsRemaining;
    int numHousesRemaining;
    int playerTurn = 0;

    Deed[] deedBoard = new Deed[40];
    ArrayList<Card> communityChest = new ArrayList<Card>();
    ArrayList<Card> chance = new ArrayList<Card>();
    Player current;
    int position;
    int[] bids = {0, 0, 0, 0};
    int highestBid = 50;
    int doublesRolled; // Keeps track of how many times player has rolled doubles
    Player[] users;

    public Board(Player[] users, Deed[] new_board, ArrayList<Card> communityChest, ArrayList<Card> chance) {
        this.numHotelsRemaining = 12;
        this.numHousesRemaining = 32;
        deedBoard = new_board;
        this.users = users;
        this.communityChest = communityChest;
        this.chance = chance;
    }

    // Reset all the variables for a players turn and gives them the option to roll
    // if they are not in jail
    // The ui for escaping jail still needs to be created and connected to
    // moveToSpace()
    public void beginTurn(Player player) {
        current = player;
        GUIHelper.setMessage("It is " + current.name + "'s turn.");
        position = current.position;
        doublesRolled = 0; // Keeps track of how many times player has rolled doubles
        if (player.inJail == true) {
            // give option to roll dice for doubles or pay $50
            player.turnsInJail++;
            if (player.turnsInJail >= 3) {
                player.payBail();
            } else {
                try {
                    Parent root = FXMLLoader.load(Board.class.getResource("jail.fxml"));
                    Stage trade_stage = new Stage();
                    trade_stage.setTitle("jail");
                    trade_stage.setScene(new Scene(root));
                    trade_stage.show();
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }
            }
        } else {
            moveToSpace(false);
        }
    }

    // Move the player and deals with what happens at that space. If the player
    // started the turn in jail, don't roll.
    // The buy Property ui hasn't been created or connected to auctionProperty()
    public void moveToSpace(boolean fromJail) {
        if (fromJail && current.inJail == true) {
            current.rollDice();

            if (current.dice[0] == current.dice[1]) // If doubles are rolled
            {
                GUIHelper.setMessage("You rolled doubles. Gratz.");
                current.inJail = false;
                current.turnsInJail = 0;
                current.move();
            } else // If doubles are not rolled
            {
                GUIHelper.setMessage("You did not roll doubles...");
                GUIHelper.enableTurnGUI();
                return;
            }
        } else {
            current.rollDice(); // Player rolls dice, dice values are stored in player class
            current.move();
        }

        position = current.position;

        // If player lands on free parking, GO, or Jail set is_free_parking to true, and
        // there is no property to buy
        if (deedBoard[position].name.equals("Free Parking") || deedBoard[position].name.equals("Jail")
                || deedBoard[position].name.equals("GO")) {
            if (current.inJail == false) {
                System.out.println("No property to buy. Landed on space is " + deedBoard[position].name + "\n");
                GUIHelper.setMessage("No property to buy. " + current.name + " landed on " + deedBoard[position].name);
            }
        }

        // If player lands on "Go to Jail" send the player to jail
        else if (deedBoard[position].name.equals("Go to Jail")) {
            current.moveToJail();
            GUIHelper.setMessage("You're going to jail!");
        }

        // If player lands on "Income Tax", give them the option to pay $200 or %10 of
        // their net worth (without letting them calculate net worth prior to
        else if (deedBoard[position].name.equals("Income Tax")) {
            System.out.println("You landed on Income Tax, you have to pay 10% of your net worth, or $200. (1. 10% / 2. $200)");
            GUIHelper.setMessage("You have to pay income tax, and now have $" + current.money);
            // int answer = in.nextInt(); Fix this with a a prompt later
            current.payTax(1);
        } else if (deedBoard[position].name.equals("Luxury Tax")) {
            System.out.println("You landed on Luxury Tax, you have to pay $100");
            GUIHelper.setMessage("You have to pay Luxury tax, and now have $" + current.money);
            // int answer = in.nextInt(); Fix this with a a prompt later
            current.payTax(2);
        }
        
        //Handles if they land on community chest or chance
        else if(deedBoard[current.position].name.equals("Community Chest")) {
        	GUIHelper.setMessage("You landed on the Community Chest! Drawing and playing a card.");
        	current.communityChest(chance.get(0), users, deedBoard);
        }
        else if(deedBoard[current.position].name.equals("Chance")) {
        	GUIHelper.setMessage("You landed on Chance! Drawing and playing a card.");
        	int case18case19 = 0;
        	case18case19 = current.chance(chance.get(0), users, deedBoard);
        	if(case18case19 != 0) {
        		twoCases(case18case19);
        	}
        }
        // If player lands on property owned by somebody else, they pay rent
        else if (deedBoard[current.position].owner != null) {
            Deed deed = deedBoard[current.position];
            current.payRent(deed);
        }
        // If there is no owner of the deed landed on and it's not free parking, allow
        // user to buy the property, or it will be auctioned
        else if (deedBoard[current.position].owner == null) {// && is_free_parking == false)
            if (current.money > deedBoard[current.position].price) {
                try {
                    Parent root = FXMLLoader.load(Board.class.getResource("purchase.fxml"));
                    Stage trade_stage = new Stage();
                    trade_stage.setTitle("Purchase");
                    trade_stage.setScene(new Scene(root));
                    trade_stage.show();
                } catch (Exception e) {
                    System.out.println("Something went wrong");
                }
            } else {
                auctionProperty();
            }

            // boolean bought = false;
            // bought = current.buy_property(deed);

        }
        
        checkForDoubles(fromJail);
    }

    // This determines if an auction should be held after a player has had the
    // chance to buy it
    // The ui for auction has been started but the code has not.
    // After the auction is done Main.test.enableButtons() need to be called.
    public void auctionProperty() {
        if (current.money < deedBoard[current.position].price) {
            GUIHelper.setMessage(current.name + " did not have enough money to buy " + deedBoard[position].name + " so "
                    + deedBoard[position].name + " will be auctioned.");
        } else {
            GUIHelper.setMessage(current.name + " did not buy " + deedBoard[position].name + " so " + deedBoard[position].name
                    + " will be auctioned.");
        }
        try {
            Parent root = FXMLLoader.load(Board.class.getResource("auction.fxml"));
            Stage trade_stage = new Stage();
            trade_stage.setTitle("auction");
            trade_stage.setScene(new Scene(root));
            trade_stage.show();
        } catch (Exception e) {
            System.out.println("Something went wrong with auctioning");
        }
    }

    public void checkForDoubles(boolean fromJail) {
        // Doubles were rolled, does the same thing as above, just repeats if doubles
        // are rolled
        if (fromJail == false && current.dice[0] == current.dice[1]) {
        	GUIHelper.setMessage("You rolled doubles. Rolling again.");
            doublesRolled++;
            if (doublesRolled == 2) { // if doubles have been rolled twice, go to jail
            	GUIHelper.setMessage("You rolled doubles. Twice. Go to jail. Now.");
                current.moveToJail();
            } else {
                moveToSpace(false);
            }
        }
        GUIHelper.enableTurnGUI();
    }

    public void auction(int[] bids, int highest_bid, Deed deed) {
        int displayed_highest = highest_bid;
        int players_interested = 0;
        for (int i = 0; i < bids.length; i++) {
            if (bids[i] >= highest_bid) {
                highest_bid = bids[i];
                players_interested++;
            } else if (bids[i] > displayed_highest) {
                players_interested++;
            }
        }
        if (players_interested == 1) {
            for (int i = 0; i < bids.length; i++) {
                if (highest_bid == bids[i]) {
                    Arrays.fill(bids, 0);
                    GUIHelper.setMessage(users[i].name + " won " + deed.name + " for $" + highest_bid);
                    users[i].buyAuction(deed, highest_bid);
                    highest_bid = 50;
                }
            }
        } else if (players_interested > 1) {
            this.highestBid = highest_bid;
            this.bids = bids;
            try {
                Parent root = FXMLLoader.load(Board.class.getResource("auction.fxml"));
                Stage trade_stage = new Stage();
                trade_stage.setTitle("Auction");
                trade_stage.setScene(new Scene(root));
                trade_stage.show();
            } catch (Exception e) {
                System.out.println("Something went wrong");
            }
        }
    }

    public Player gameOver() {
        int current_high = -1;
        int high_player = -1;
        for (int i = 0; i < users.length; i++) {
            users[i].calculate_net_worth();
            if (users[i].netWorth > current_high) {
                high_player = i;
                current_high = users[i].netWorth;
            }
        }
        System.out.println(users[high_player].name + " is the winner with a net worth of: " + current_high);
        return users[high_player];
    }

    public void twoCases(int whichCase) {
    	if(whichCase == 18) {
	    	if (deedBoard[current.position].owner == null) {// && is_free_parking == false)
	            if (current.money > deedBoard[current.position].price) {
	                try {
	                    Parent root = FXMLLoader.load(Board.class.getResource("purchase.fxml"));
	                    Stage trade_stage = new Stage();
	                    trade_stage.setTitle("Purchase");
	                    trade_stage.setScene(new Scene(root));
	                    trade_stage.show();
	                } catch (Exception e) {
	                    System.out.println("Something went wrong");
	                }
	            } else {
	                auctionProperty();
	            }
	    	}
	    	else if (deedBoard[current.position].owner != null) {
	            Deed deed = deedBoard[current.position];
	            current.payRent(deed);
	        }
    	}
    	
    	else if(whichCase == 19) {
	    	if (deedBoard[current.position].owner == null) {// && is_free_parking == false)
	            if (current.money > deedBoard[current.position].price) {
	                try {
	                    Parent root = FXMLLoader.load(Board.class.getResource("purchase.fxml"));
	                    Stage trade_stage = new Stage();
	                    trade_stage.setTitle("Purchase");
	                    trade_stage.setScene(new Scene(root));
	                    trade_stage.show();
	                } catch (Exception e) {
	                    System.out.println("Something went wrong");
	                }
	            } else {
	                auctionProperty();
	            }
	    	}
	    	else if (deedBoard[current.position].owner != null) {
	            Deed deed = deedBoard[current.position];
	            current.payRent(deed);
	        }
    	}
    }
    
    public Deed[] getDeedBoard() {
    	return deedBoard;
    }
}
