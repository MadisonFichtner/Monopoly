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
	public int utilities_count;
	public int building_value;
	public int mortgage_owed;
	public boolean has_street;
	public boolean in_jail;
	
	
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
		this.has_street = false;
		this.utilities_count = 0;
		this.railroad_count = 0;
		this.in_jail = false;
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
		System.out.println("Dice 1 result: " + dice[0] + "\nDice 2 result: " + dice[1]);
		System.out.println("Current money: "+ money);
		return dice;
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
		
		if(answer == 1) 
        {
            if(money>=deed.purchase_price)
            {
                deed.owner = this;
                money -= deed.purchase_price;
                deeds.add(deed);
                if(deed.deed_type.equals("railroad"))
                    railroad_count++;
                if(deed.deed_type.equals("utility"))
                    utilities_count++;
                bought = true;}

            else 
                bought = false;
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
			System.out.println("You passed go, have $200 on me!");
		}
	}
	public void move_to_jail() {
		position = 10;
		in_jail = true;
	}
	
	
	/*
	 * Trades deed between two players
	 * 
	 * @param deed -> deed to be traded
	 * @param recipient -> player to receive deed
	 */
	public void trade_deed(Player[] players) {
		Scanner in = new Scanner(System.in);
		int deed_num = 0;
		if(deeds.size() != 0) {
			print_deeds();
			System.out.println("Which deed would you like to sell?");
			deed_num = in.nextInt();
			System.out.println("Who is buying it?");
			
			for(int i = 0; i < players.length; i++) {
				System.out.println(i + ". " + players[i].name);
			}
			
			int recipient_num = in.nextInt();
			System.out.println("How much is it being sold for?");
			int price = in.nextInt();
			
			players[recipient_num].deeds.add(deeds.get(deed_num));	//adds deed to recipients deeds
			deeds.remove(deed_num);									//removes deed from current players deeds
			money += price;
			players[recipient_num].money -= price;
		}
		else if(deeds.size() == 0)
			System.out.println("No properties owned, so no properties to sell.");
	}
	
	/*
	 * Mortgages a deed. Player receives specified money from mortgage, and the deed's mortgage flag is flipped
	 * 
	 * @param deed -> deed to be mortgaged
	 */
	//In design we have this returning an int but i dont see a point in that
	public void mortgage_deed(Deed deed) {
		money += deed.calculate_mortgage();
		mortgage_owed += deed.mortgage_value;
		System.out.println(deed.name + " was mortgaged for " + deed.mortgage_value + "\nRemaining Money: " + money);
		deed.mortgaged = true;
	}
	
	public void pay_mortgage(int choice) {
		if(choice == 0) {//Paying all mortgages
			
		}
			
		else if(choice == 1){//Paying single mortgage
		
		}
			
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
		Player receiving_player = deed.owner;
		
		if (receiving_player == this)
		{
		}
		else {
			money -= deed.calculate_rent();
			receiving_player.money += deed.calculate_rent();
			System.out.println(receiving_player.name + " recieved $" + deed.calculate_rent() + " in rent.");
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
	
	public void print_deeds() {
		for(int i = 0; i < deeds.size(); i++) {
			System.out.println(i + ") Name: " + deeds.get(i).name + "| Rent Cost: " + deeds.get(i).calculate_rent() + 
					"| Houses: " + deeds.get(i).current_houses + "| Hotel: " + deeds.get(i).has_hotel + "| Mortgage Value:" 
					+ deeds.get(i).calculate_mortgage()); //add sell info, and houses/hotels
		}
	}
	
	public void print_streets() {
		for(int i = 0; i < deeds.size(); i++) {
			if(deeds.get(i).deed_type.equals("street")) {
				System.out.println(i + ") Name: " + deeds.get(i).name + "| Build Cost:" + deeds.get(i).build_cost + 
						"| Current Houses: " + deeds.get(i).current_houses + "| Has a Hotel: " + deeds.get(i).has_hotel);
			}
		}
	}
	
	public void buy_house() {
		Scanner in = new Scanner(System.in);
		int property = 0;
		for(int i = 0; i < deeds.size(); i++) {
			if(deeds.get(i).deed_type.equals("street"))
				has_street = true; break;
		}
		if(has_street == true) {
			System.out.println("Which property would you like to build on?");
			print_streets();
			property = in.nextInt();
		}
		if(deeds.get(property).current_houses == 4) {
			System.out.println("You already have 4 houses on this property, would you like to build a hotel?");
			buy_hotel();
		}
		else if (deeds.get(property).max_houses == false && deeds.get(property).deed_type.equals("street")) {
			System.out.println("How many houses would you like to build? (up to: " + (4 - deeds.get(property).current_houses) + ")");
			int houses = in.nextInt();
			deeds.get(property).current_houses += houses;
			money -= (deeds.get(property).build_cost * houses);
			if(deeds.get(property).current_houses == 4)
				deeds.get(property).max_houses = true;
			
			System.out.println("You purchased " + houses + " houses on " + deeds.get(property).name + " for $" + (houses * deeds.get(property).build_cost));
			System.out.println("Remaining money = " + money);
		}
		else
			System.out.println("You have no eligible properties to develop a house on. (No streets owned, or no streets with room for any houses)");
	}
	
	public void buy_hotel() {
		Scanner in = new Scanner(System.in);
		boolean has_one_eligible = false;
		for(int i = 0; i < deeds.size(); i++) {
			if(deeds.get(i).max_houses == true)
			{
				System.out.println("Properties eligible to have a hotel built: ");
				System.out.println(i + ") Name: " + deeds.get(i).name + "| Build Cost:" + deeds.get(i).build_cost);
				has_one_eligible = true;
			}
		}
		if(has_one_eligible == false) {
			System.out.print("No properties are eligible to have a hotel developed. (No properties owned, or no properties with 4 houses)");
		}
		System.out.println("Which property would you like to build a hotel on?");
		int property = in.nextInt();
		deeds.get(property).has_hotel = true;
		deeds.get(property).current_houses = 0;
		deeds.get(property).max_houses = false;
		money -= deeds.get(property).build_cost;	
		System.out.println("You purchased a hotel on " + deeds.get(property).name + " for $" + deeds.get(property).build_cost);
		System.out.println("Remaining money = " + money);
	}
}
