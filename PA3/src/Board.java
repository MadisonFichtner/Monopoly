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

	/*
	 * Player takes turn. They roll dice (if double is rolled, dice are rolled again) and the player moves
	 * x spaces corresponding to the sum of the two dice rolled. The player can buy and sell property,
	 * mortgage property, buy houses and hotels
	 * 
	 * @param player -> The player whose turn it currently is
	 */
	public void take_turn(Player player) {
		System.out.println("It is " + player.name + "'s turn.");
		Scanner in = new Scanner(System.in);
		boolean is_free_parking = false;//Is true of player lands on GO, Jail, or Free Parking
		int double_roll_counter = 0;	//Keeps track of how many times player has rolled doubles
		int response = 0;

		
		
		if(player.in_jail == false) {
			while(response != 1) {	//Take in user input and return an int based on their response
				response = user_prompt(player, 0); //turn 0, meaning they haven't rolled doubles
			}
		}
		
		//If the player is jail
		boolean was_in_jail = false;
		if(player.in_jail == true) {
			//give option to roll dice for doubles or pay $50
			player.turns_in_jail++;
			player.get_out_of_jail();
			was_in_jail = true;
		}
		int position = player.position;
		//If player lands on free parking, GO, or Jail set is_free_parking to true, and there is no property to buy
		if(board[position].name.equals("Free Parking") || board[position].name.equals("Jail") || board[position].name.equals("GO")) {
			if(player.in_jail == true)
				System.out.println("No property to buy. Landed on space is " + board[position].name  + "\n");
		}
		
		//If player lands on "Go to Jail" send the player to jail
		else if(board[position].name.equals("Go to Jail"))
			player.move_to_jail();
		
		//If player lands on "Income Tax", give them the option to pay $200 or %10 of their net worth (without letting them calculate net worth prior to
		else if(board[position].name.equals("Income Tax")) {
			System.out.println("You landed on Income Tax, you piece of shit have to pay 10% of your net worth, or $200. (1. 10% / 2. $200)");
			int answer = in.nextInt();
			player.pay_tax(answer);
		}
		
		//If there is no owner of the deed landed on and it's not free parking, allow user to buy the property, or it will be auctioned
		else if(board[player.position].owner == null) {// && is_free_parking == false)
			Deed deed = board[player.position];
			boolean bought = false;
			bought = player.buy_property(deed);
			if(bought == true && player.money >= board[player.position].purchase_price) {
				System.out.println(player.name + " bought " + deed.name + " for $" + deed.purchase_price + "\n");
			}
			else if(bought == false && player.money < board[player.position].purchase_price) {	//If player does not have fund to buy property, it will automatically be auctioned
				System.out.println(player.name + " did not have enough money to buy " + deed.name + " so " + deed.name + " will be auctioned.");
				auction(deed);
			}
			else {
				System.out.println(player.name + " did not buy " + deed.name + " so " + deed.name + " will be auctioned.");
				auction(deed);
			}
		}
		
		//If player lands on property owned by somebody else, they pay rent
		else if(board[player.position].owner != null) {
			Deed deed = board[player.position];
			player.pay_rent(deed);
		}
		
		
		
		//Doubles were rolled, does the same thing as above, just repeats if doubles are rolled
		if(was_in_jail == false) {
			while(player.dice[0] == player.dice[1]) {
				double_roll_counter++;
				if(double_roll_counter == 2) { //if doubles have been rolled twice, go to jail
					player.move_to_jail();
				}
				
				else {	//if doubles haven't been rolled twice, keep being offered to buy property or auction it off
					response = 0;
					response = user_prompt(player, 1);
					position = player.position;
					
					//If player lands on free parking, GO, or Jail set is_free_parking to true, and there is no property to buy
					if(board[position].name.equals("Free Parking") || board[position].name.equals("Jail") || board[position].name.equals("GO")) {
						System.out.println("No property to buy. Landed on space is " + board[position].name  + "\n");
						is_free_parking = true;
					}
					
					//If player lands on "Income Tax", give them the option to pay $200 or %10 of their net worth (without letting them calculate net worth prior to
					else if(board[position].name.equals("Income Tax")) {
						System.out.println("You landed on Income Tax, you piece of shit have to pay 10% of your net worth, or $200? (1. 10% / 2. $200");
						int answer = in.nextInt();
						player.pay_tax(answer);
					}
					
					//If there is no owner of the deed landed on and it's not free parking, allow user to buy the property, or it will be auctioned
					else if(board[position].owner == null) {
						Deed deed = board[position];
						boolean bought = false;
						bought = player.buy_property(deed);
						if(bought == true && player.money >= board[player.position].purchase_price) {
							System.out.println(player.name + " bought " + deed.name + " for $" + deed.purchase_price + "\n");
						}
						else if(bought == false && player.money < board[player.position].purchase_price) {	//If player does not have fund to buy property, it will automatically be auctioned
							System.out.println(player.name + " did not have enough money to buy " + deed.name + " so " + deed.name + " will be auctioned.");
							auction(deed);
						}
						else
						{
							System.out.println(player.name + " did not buy " + deed.name + " so " + deed.name + " will be auctioned.\n");
							auction(deed);
						}
						
					}
					
					//If player lands on property owned by somebody else, they pay rent
					else if(board[player.position].owner != null) {
						Deed deed = board[player.position];
						player.pay_rent(deed);
					}
				}
			}
		
		}
		
		response = 0;	//Reset response to 0
		
		//Take user input until they choose to end their turn by inputting 6
		while(response != 6) {
			response = user_prompt(player, 2);
		}
		System.out.println(player.name + " has ended their turn. \n");
	}
	
	
	public int user_prompt(Player player, int turn) { //turn 0 means they haven't rolled dice yet, turn 1 means they rolled doubles, turn 2 means they have the option to buy/sell property and develop
		Scanner in = new Scanner(System.in);
		int response = 0;
		if(turn == 0) { //What are players actually allowed to do at the beginning of their turn other than roll? nothing?
			System.out.println(player.name + ", what would you like to do? (1. Roll Dice> )");
			response = in.nextInt();
			switch(response) {
			case 1: //Roll Dice
				player.roll_dice();				//Player rolls dice, dice values are stored in player class
				player.move();					//Player moves the number of spaces specified in the roll
				response = 1;
				break;
			/**case 2: //sell property
				player.trade_deed(players);
				response = 2;
				break;
			case 3: //buy houses
				player.buy_house();
				response = 3;
				break;
			case 4: //buy hotels
				player.buy_hotel();
				response = 4;
				break;
			case 5: //mortgage 
				player.print_deeds();
				System.out.println("Which deed would you like to mortgage?");
				response = in.nextInt();
				Deed deed = player.deeds.get(response);
				player.mortgage_deed(deed);
				response = 5;
				break;**/
			}
		}
		
		else if(turn == 1) { //Doubles were rolled, roll dice and move again
			System.out.println(player.name + ", you rolled doubles, rolling dice again.");
			player.roll_dice();				//Player rolls dice, dice values are stored in player class
			player.move();					//Player moves the number of spaces specified in the roll
			response = 1;
		}
		
		else if(turn == 2) {	//Player lands on spot, and is giving options until they end turn
			System.out.println(player.name + ", what would you like to do? (1. Sell Property / 2. Buy House / 3. Buy Hotel / 4. Mortgage / 5. Pay Mortgage(s) / 6. End Turn) > ");
			response = in.nextInt();
			switch(response) {
			case 1: //sell property
				player.trade_deed(players);
				response = 1;
				break;
			case 2: //buy house
				player.buy_house();
				response = 2;
				break;
			case 3: //buy hotel
				player.buy_hotel();
				response = 3;
				break;
			case 4: //mortgage
				player.print_deeds();
				System.out.println("Which deed would you like to mortgage?");
				response = in.nextInt();
				Deed deed = player.deeds.get(response);
				player.mortgage_deed(deed);
				response = 4;
				break;
			case 5: //pay mortgage(s)
				System.out.println("Would you like to pay off all mortgages, or a single mortgage? (1. All Mortgages / 2. Single Mortgage)");
				response = in.nextInt();
				player.pay_mortgage(response);
				response = 5;
				break;
			case 6: //end turn
				response = 6;
				break;
			}
		}
		return response;
	}
	
	/*
	 * Auctions a deed to the player with the highest bid
	 */
	public void auction(Deed auctionedDeed) {
		
	}

}
