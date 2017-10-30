import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
	public ArrayList<Deed> deeds; //Assuming one person can own all positions on board
	public int money;
	public String token;
	public String name;
	public int position;
	public int[] dice;
	public int dice_sum;
	public int railroad_count;
	public int building_value;
	
	
	/*
	 * Creates new player
	 * 
	 * @param name -> name of player entered by user
	 * @param toke -> name of token entered by user (make picture on board)
	 */
	public Player(String name, String token) {
		this.deeds = new ArrayList<Deed>();
		this.money = 1500;
		this.position = 0;
		this.dice = new int[]{0,0};
		this.name = name;
		this.token = token;
		this.railroad_count = 0; //if 2 RR, rent = 50, 3-rent=100, 4-rent=200
		this.building_value = 0; //initially owns 0 buildings
	}
	
	/*
	 * randomly generates two numbers between 1-6 to represent dice, 
	 * sets the sum of the dice, and returns images of the two dices sides that were rolled
	 */
	public int[] roll_dice() {
		//randomize dice throw and display image of side of each dice that was rolled
		Random rand = new Random();
		dice[0] = rand.nextInt(6) + 1;
		dice[1] = rand.nextInt(6) + 1;
		dice_sum = dice[0] + dice[1];
		System.out.println(dice[0] + " " + dice[1]);
		return dice;
	}
	
	//is this supposed to be move_to_space
	public void take_turn() {
		
	}
	
	public boolean buy_property(Deed deed) {
		boolean bought = false;
		Scanner in = new Scanner(System.in);
		boolean valid;
		int answer = 0;
		do {
			valid = true;
			try {
				System.out.println("Do you want to buy: " + deed.name + " for $" + deed.purchase_price + "? (1. yes / 2. no)");
				answer = in.nextInt();
			}
			catch (Exception e) {
				System.out.println("That was an incorrect input, enter an integer");
				valid = false;
				in.nextLine();
			}
		}while(valid == false);
		
		if(answer == 1) {
			money -= deed.purchase_price;
			deeds.add(deed);
			if(deed.deed_type.equals("Railroad"))
			railroad_count++;
			bought = true;
		}
		else
			bought = false;
		
		return bought;
	}
	
	/*
	 * Moves players token, and returns $200 if they passed go
	 * 
	 * @param spaces -> dice_sum
	 */
	public void move() {
		boolean passed_go = false;
		if(position + dice_sum > 40) {
			int overflow = (position + dice_sum) - 40;
			position = overflow;
			passed_go = true;
		}
		else
			position = position += dice_sum;
		//if position 0 get passed
		//passed_go = true;
		
		if(passed_go == true) {
			money += 200;
		}
	}
	public void move_to_jail() {
		position = 10;
	}
	
	
	/*
	 * Trades deed between two players
	 * 
	 * @param deed -> deed to be traded
	 * @param recipient -> player to receive deed
	 */
	public void trade_deed(Deed deed, Player recipient) {
		
	}
	
	/*
	 * Mortgages a deed. Player receives specified money from mortgage, and the deed's mortgage flag is flipped
	 * 
	 * @param deed -> deed to be mortgaged
	 */
	//In design we have this returning an int but i dont see a point in that
	public void mortgage_deed(Deed deed) {
		money += deed.get_mortgage();
		deed.mortgaged = true;
	}
	
	
	/*
	 * Pays rent to owner of property landed on. Nothing happens if the player whose token lands on the deed
	 * is the same player who owns the property
	 * 
	 * @param deed -> deed landed on/rent being paid for
	 * @param receiving_player -> player who owns deed
	 * 
	 * 
	 */
	public void pay_rent(Deed deed) {
		Player receiving_player = deed.get_owner();
		
		if (receiving_player == this)
		{
		}
		else {
			money -= deed.calculate_rent();
			receiving_player.money += deed.calculate_rent();
		}
		if(deed.deed_type.equals("company")) {
			//if(receiving_player owns 1 company, receiving_player.money += 4 * dice_sum
			//else if(receiving_player owns 2 company, receiving_player.money += 10 * dice_sum
		}
		
	}
	
	public int calculate_net_worth() {
		int net_worth = 0; //= all of items values and money added up
		for(int i = 0; i < deeds.size(); i++) {
			net_worth += money;
			net_worth += (deeds.get(i).purchase_price + (deeds.get(i).build_cost * deeds.get(i).current_houses));
			if(deeds.get(i).has_hotel == true)
				net_worth += deeds.get(i).build_cost;
		}
		return net_worth;
	}
	
	public void pay_tax(int response) {
		if(response == 1)
			money -= calculate_net_worth();
		else
			money -= 200;
	}
}
