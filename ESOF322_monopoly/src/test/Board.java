package test;

import java.io.File;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Board {

	public int numHotelsRemaining;
	public int numHousesRemaining;
	
	public static Player[] players;
	public static Deed[] board = new Deed[40];
	public static Player current;
	private static boolean is_free_parking;//Is true of player lands on GO, Jail, or Free Parking
	private static int double_roll_counter;	//Keeps track of how many times player has rolled doubles
	private static int response;
	private static boolean was_in_jail;

	public Board(Player[] users, Deed[] new_board) {
		this.numHotelsRemaining = 12;
		this.numHousesRemaining = 32;
		this.players = users;
		this.board = new_board;
	}

	public void init_turn(Player player) {
		current = player;
		is_free_parking = false;//Is true of player lands on GO, Jail, or Free Parking
		double_roll_counter = 0;	//Keeps track of how many times player has rolled doubles
		response = 0;
		was_in_jail = false;
		if (player.in_jail == false) {
			current = player;
			try {
				Parent root = FXMLLoader.load(getClass().getResource("roll.fxml"));
				Stage trade_stage = new Stage();
				trade_stage.setTitle("Trade");
				trade_stage.setScene(new Scene(root));
				trade_stage.show();
			} catch (Exception e) {
				System.out.println("Something went wrong");
			}
		} else if (player.in_jail == true) {
			// give option to roll dice for doubles or pay $50
			player.turns_in_jail++;
			player.get_out_of_jail();
			was_in_jail = true;
		}
	}
	
	public static void roll_again() {
		try {
			Parent root = FXMLLoader.load(Board.class.getResource("roll.fxml"));
			Stage trade_stage = new Stage();
			trade_stage.setTitle("Trade");
			trade_stage.setScene(new Scene(root));
			trade_stage.show();
		} catch (Exception e) {
			System.out.println("Something went wrong");
		}
	}

	public static void begin_turn(int[] dice) {
		if(dice != null) {
			//Handle a turn from jail
		} else {
			current.roll_dice();				//Player rolls dice, dice values are stored in player class
			current.move();
			response = 1;
		}
		
		int position = current.position;
		
		//If player lands on free parking, GO, or Jail set is_free_parking to true, and there is no property to buy
				if(board[position].name.equals("Free Parking") || board[position].name.equals("Jail") || board[position].name.equals("GO")) {
					if(current.in_jail == false)
						System.out.println("No property to buy. Landed on space is " + board[position].name  + "\n");
				}
				
				//If player lands on "Go to Jail" send the player to jail
				else if(board[position].name.equals("Go to Jail"))
					current.move_to_jail();
				
				//If player lands on "Income Tax", give them the option to pay $200 or %10 of their net worth (without letting them calculate net worth prior to
				else if(board[position].name.equals("Income Tax")) {
					System.out.println("You landed on Income Tax, you piece of shit have to pay 10% of your net worth, or $200. (1. 10% / 2. $200)");
//					int answer = in.nextInt();  Fix this with a a prompt later
					current.pay_tax(1);
				}
				
				//If there is no owner of the deed landed on and it's not free parking, allow user to buy the property, or it will be auctioned
				else if(board[current.position].owner == null) {// && is_free_parking == false)
					Deed deed = board[current.position];
					boolean bought = false;
					bought = current.buy_property(deed);
					if(bought == true && current.money >= board[current.position].purchase_price) {
						System.out.println(current.name + " bought " + deed.name + " for $" + deed.purchase_price + "\n");
					}
					else if(bought == false && current.money < board[current.position].purchase_price) {	//If player does not have fund to buy property, it will automatically be auctioned
						System.out.println(current.name + " did not have enough money to buy " + deed.name + " so " + deed.name + " will be auctioned.");
						auction(deed);
					}
					else {
						System.out.println(current.name + " did not buy " + deed.name + " so " + deed.name + " will be auctioned.");
						auction(deed);
					}
				}
				
				//If player lands on property owned by somebody else, they pay rent
				else if(board[current.position].owner != null) {
					Deed deed = board[current.position];
					current.pay_rent(deed);
				}
				
				
				
				//Doubles were rolled, does the same thing as above, just repeats if doubles are rolled
				if(was_in_jail == false) {
					while(current.dice[0] == current.dice[1]) {
						double_roll_counter++;
						if(double_roll_counter == 2) { //if doubles have been rolled twice, go to jail
							current.move_to_jail();
						}
						
						else {
							roll_again();
							return;
						}
					}
				
				}
				
				response = 0;	//Reset response to 0
	}
	
	public static void auction(Deed auctionedDeed) {
		Scanner in = new Scanner(System.in);
		int highest_bid = 50;	//Higest bid
		boolean is_valid = true;	//while loop conditional
		int players_interested = players.length;	//To check if there's only 1 person interested 
		int i = 0;	//Keeps track of how whose turn it is to bid
		
		System.out.println("Bid is starting at $50 for: " + auctionedDeed.name);
		System.out.println("Enter bid amount, or 0 if not interested");
		
		//Resets whether the players are interested in the property each time a property is auctioned
		for(int j = 0; j < players.length; j++) {
			players[j].is_interested = true;
		}
		
		//While there are more than 1 person still interested
		while(players_interested > 1) {
			is_valid = true;	//Reset to true to enter while loop
			int bid = -1;		//Set bid to a - so that it's not a player response #
			while(is_valid == true && players[i].is_interested == true) {
				System.out.println(players[i].name + " enter a bid or 0 to back out: ");
				bid = in.nextInt();
				if(bid > highest_bid && bid>=50) {	//If the bid is valid
					highest_bid = bid;
					System.out.println(players[i].name + " has the highest bid of: $" + highest_bid);
					i++;
				}
				else if(bid == 0) {		//If player chooses to back out
					System.out.println(players[i].name + " is no longer interested in the auction.");
					players[i].is_interested = false;
					players_interested--;		//Decrement players interested
					i++;						//Increment the interator
					is_valid = false;			//Set is_valid to false to break inner while loop
				}
				else if(bid <= highest_bid) {	//If bid is lower than highest bid, prompt again
					System.out.println("Bid was not higher than the current highest bid, try again. Enter a value larger, or a 0 to back out");
				}

				else if(i == players.length)		//If the iterator is the same as the array length, reset it
					i = 0;
				
				else if(players_interested == 1) {	//If it's the last player interested turn, break
					System.out.println(players[i].name + " wins the bid for the property: " + auctionedDeed.name + " for $" + highest_bid);
					players[i].buy_auction(auctionedDeed, highest_bid);
				}
			}
		}
	}
}
