import java.io.File;

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
		int double_roll_counter = 0;
		int dice_sum = 0;
		int[] dice = player.roll_dice();
		dice_sum = dice[0] + dice[1];
		player.move(dice_sum);
		//Doubles were rolled
		while(dice[0] == dice[1]) {
			double_roll_counter++;
			player.roll_dice();
			if(double_roll_counter == 2) {
				//send to jail
			}
		}
			
		double_roll_counter++;
		//move to space specified by dice return
		//if double_roll_counter.equals(2) send to jail
		//
	}
	
	public void hold_auction(Deed auctionedDeed) {
		
	}

}
