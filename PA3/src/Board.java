import java.io.File;
import java.util.Scanner;

public class Board {
	
	public int numHotelsRemaining;
	public int numHousesRemaining;
	public Player[] players;
	public Deed[] board = new Deed[40];
	
	public Board(Player[] users, Deed[] new_board) {
		this.numHotelsRemaining = 12;
		this.numHousesRemaining = 32;
		this.players = users;
		this.board = new_board;
	}
	
	public void populate_board() {
		
	}
	
	//The dice roll logic needs to be in the player class
	public void take_turn(Player player) {
		Scanner in = new Scanner(System.in);
		boolean in_jail = false;		//Is true if player is in jail	
		boolean is_free_parking = false;//Is true of player lands on GO, Jail, or Free Parking
		int double_roll_counter = 0;	//Keeps track of how many times player has rolled doubles
		int roll_dice = 0;
		
		System.out.println(player.name + ", what would you like to do? (1. Roll Dice / 2. Sell Property / 3. Buy House / 4. Buy Hotel / 5. Mortgage) > ");
		roll_dice = in.nextInt();
		switch(roll_dice) {
		case 1: 
			player.roll_dice();				//Player rolls dice, dice values are stored in player class
			player.move();					//Player moves the number of spaces specified in the roll
		case 2: //sell property
		case 3: //buy houses
		case 4: //buy hotels
		case 5: //prompt for what property to mortgage	player.mortgage_deed(deed);
		}
		
		//player.roll_dice();				//Player rolls dice, dice values are stored in player class
		//player.move();					//Player moves the number of spaces specified in the roll

		int position = player.position;	//Players current position used to check deed in that position
		
		//If the player is jail
		if(in_jail == true) {
			//give option to roll dice for doubles or pay $50
		}
		
		//If player lands on free parking, GO, or Jail set is_free_parking to true, and there is no property to buy
		if(board[position].name.equals("Free Parking") || board[position].name.equals("Jail") || board[position].name.equals("GO")) {
			System.out.println("No property to buy. Landed on space is " + board[position].name  + "\n");
			is_free_parking = true;
		}
		else if(board[position].name.equals("Income Tax")) {
			System.out.println("You landed on Income Tax, would you like to pay 10% of your net worth, or $200? (1. 10% / 2. $200");
			int answer = in.nextInt();
			player.pay_tax(answer);
		}
		//If there is no owner of the deed landed on and it's not free parking, allow user to buy the property, or it will be auctioned
		else if(board[player.position].owner == null && is_free_parking == false) {
			Deed deed = board[player.position];
			boolean bought = false;
			bought = player.buy_property(deed);
			if(bought == true) {
				System.out.println(player.name + " bought " + deed.name + " for $" + deed.purchase_price + "\n");
			}
			else {
				System.out.println(player.name + " did not buy " + deed.name + " so " + deed.name + " will be auctioned.");
			}
		}
		
		//Doubles were rolled, does the same thing as above, just repeats if doubles are rolled
		while(player.dice[0] == player.dice[1]) {
			if(double_roll_counter == 2) { //if doubles have been rolled twice, go to jail
				player.move_to_jail();
				in_jail = true;
			}
			else {							//if doubles haven't been rolled twice, keep being offered to buy property or auction it off
				double_roll_counter++;
				System.out.println("Double rolled, rolling dice again");
				player.roll_dice();
				player.move();
				
				if(board[position].name.equals("Free Parking") || board[position].name.equals("Jail") || board[position].name.equals("GO")) {
					System.out.println("No property to buy. Landed on space is " + board[position].name  + "\n");
					is_free_parking = true;
				}
				else if(board[position].name.equals("Income Tax")) {
					System.out.println("You landed on Income Tax, would you like to pay 10% of your net worth, or $200? (1. 10% / 2. $200");
					int answer = in.nextInt();
					player.pay_tax(answer);
				}
				else if(board[position].owner == null && is_free_parking == false) {
					Deed deed = board[position];
					boolean bought = false;
					bought = player.buy_property(deed);
					if(bought == true) {
						System.out.println(player.name + " bought " + deed.name + " for $" + deed.purchase_price + "\n");
					}
					else {
						System.out.println(player.name + " did not buy " + deed.name + " so " + deed.name + " will be auctioned.");
					}
				}
			}
		}
	}
	
	//Auctions deed between all players
	public void hold_auction(Deed auctionedDeed) {
		
	}

}
