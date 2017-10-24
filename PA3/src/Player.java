
public class Player {
	public Deed[] deeds = new Deed[40]; //Assuming one person can own all positions on board
	public int money;
	public String token;
	public String name;
	public int position;
	public int[] dice = new int[2];
	
	public int[] roll_dice() {
		//randomize dice throw and display image of side of each dice that was rolled
		return dice;
	}
	
	//is this supposed to be move_to_space
	public void take_turn() {
		
	}
	
	public void move(int spaces) {
		boolean passed_go = false;
		position = position += spaces;
		//if position 0 get passed
		//passed_go = true;
		passed_go = true;
		
		
		if(passed_go == true) {
			money += 200;
		}
	}
	
	public void trade_deed(Deed deed, Player recipient) {
		
	}
	
	//In design we have this returning an int but i dont see a point in that
	public void mortgage_deed(Deed deed) {
		money += deed.get_mortgage();
		deed.mortgaged = true;
	}
	
	public void pay_rent(Deed deed) {
		//money -= deed.getRentPrice
	}
	
	public int calculate_net_worth() {
		int netWorth = 0; //= all of items values and money added up
		return netWorth;
	}
}
