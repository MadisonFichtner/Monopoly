
public class Board {
	
	public int numHotelsRemaining;
	public int numHousesRemaining;
	public Player[] players = new Player[4];
	public Deed[] board = new Deed[40];
	
	public void populate_board() {
		//Pass in all the information about each individual deed and store in them in board[].
		//Need to parse .csv file for information about each deed.
		//Need to build a .csv file with all the information we require for each property,
		//I tried to find one online but none of them are exactly what we need
	}
	
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
