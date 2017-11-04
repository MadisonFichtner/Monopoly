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
	public int turns_in_jail;
	public boolean is_interested = true;
	
	/*
	 * Creates new player object. Starts with $1500
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
		this.turns_in_jail = 0;
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
	
	/*
	 * Allows user to purchase property that is not already owned by another player.
	 * 
	 * @param deed
	 * @return boolean based on whether the property was bought or not
	 */
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

			deeds.get(deed_num).owner = players[recipient_num];		//makes owner of deed the new owner
			players[recipient_num].deeds.add(deeds.get(deed_num));	//adds deed to recipients deeds
			deeds.remove(deed_num);									//removes deed from current players deeds
			money += price;
			players[recipient_num].money -= price;
		}
		else if(deeds.size() == 0)
			System.out.println("No properties owned, so no properties to sell.");
	}	
	
	/*
	 * If player bought auction, this is used
	 * 
	 * @param deed -> the deed that was purchased
	 * @param price -> price at which they purchased the deed
	 */
	public void buy_auction(Deed deed, int price) {
		deeds.add(deed);
		money -= price;
		deed.owner = this;
		if(deed.deed_type.equals("railroad"))
            railroad_count++;
        if(deed.deed_type.equals("utility"))
            utilities_count++;
	}
	
	/*
	 * Moves players token based on dice roll and current position, and returns $200 if they passed go
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
	
	/**
	 * Moves player directly to jail, and sets a boolean for whether they are in jail to true
	 */
	public void move_to_jail() {
		position = 10;
		in_jail = true;
	}
	
	/*
	 * Logic to get out of jail
	 */
	public void get_out_of_jail() {
		//give option to roll dice for doubles or pay $50
		Scanner in = new Scanner(System.in);
		if(turns_in_jail == 3) {	//If they've rolled for doubles the max number of times
			System.out.println("Turn 3 in jail. You must pay $50 and roll forward.");
			money -= 50;
			in_jail = false;
			turns_in_jail = 0;
			roll_dice();
			move();
		}
		else {	// give option to either pay or roll for doubles
			System.out.println("Turn: " + turns_in_jail + " spent in jail. " + (3-turns_in_jail) + " more before you must pay $50 dollars.");
			System.out.println("Roll for doubles, or pay $50? (1. Roll / 2. $50)");
			int choice = in.nextInt();
			switch(choice){
				case 1:	//If they roll for doubles
					int dice[] = roll_dice();
					if(dice[0] == dice [1])	//If doubles are rolled
					{
						System.out.println("You rolled doubles. Gratz.");
						in_jail = false;
						turns_in_jail = 0;
						move();
						break;
					}
					else	//If doubles are not rolled
					{
						System.out.println("You did not roll doubles...");
						break;
					}
				case 2:	//If they pay $50
					System.out.println("You paid $50 and are now out of jail.");
					roll_dice();
					move();
					break;
			}
		}
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
	
	/*
	 * Allows user to pay off all mortgages or a single mortgage of their choosing
	 * 
	 * @param choice -> user's choice as to whether they are paying all mortgages or a single mortgage
	 */
	public void pay_mortgage(int choice) {
		Scanner in = new Scanner(System.in);
		if(mortgage_owed > 0) {
			if(choice == 1) {//Paying all mortgages
				System.out.println("Remaining Money: " + money);
				System.out.println("Mortgage Owed: " + mortgage_owed);
				if(mortgage_owed > money) {
					System.out.println("You do not have the funds to pay of your mortgages all at once.");
				}
				else if(mortgage_owed <= money) {
					for(int i = 0; i < deeds.size(); i++) {
						if(deeds.get(i).mortgaged == true) {
							deeds.get(i).mortgaged = false;				//set mortgaged flag to false
							money -= deeds.get(i).mortgage_owed;			//subtract mortgage_owed for specific property from money
							mortgage_owed -= deeds.get(i).mortgage_owed;	//subtract mortgage_owed for specific property from overall mortgage_owed
							deeds.get(i).mortgage_owed = 0;
						}
					}
					System.out.println("Remaining Money: " + money);
					System.out.println("Mortgage Owed: " + mortgage_owed);
				}
			}
				
			else if(choice == 2){//Paying single mortgage
				print_mortgaged_deeds();
				System.out.println("Which mortgage would you like to pay off?");
				int response = in.nextInt();
				deeds.get(response).mortgaged = false;				//set mortgaged flag to false
				money -= deeds.get(response).mortgage_owed;			//subtract mortgage_owed for specific property from money
				mortgage_owed -= deeds.get(response).mortgage_owed;	//subtract mortgage_owed for specific property from overall mortgage_owed
				deeds.get(response).mortgage_owed = 0;
				System.out.println("You successfully paid off the mortgage on: " + deeds.get(response).name + "\nRemaining Money: " + money);
			}
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
	
	/*
	 * Calculates a user's net worth based on current money, and properties owned
	 */
	public int calculate_net_worth() {
		int net_worth = 0; //= all of items values and money added up
		net_worth += money;
		for(int i = 0; i < deeds.size(); i++) {
			net_worth += (deeds.get(i).purchase_price + (deeds.get(i).build_cost * deeds.get(i).current_houses));
			if(deeds.get(i).has_hotel == true)
				net_worth += deeds.get(i).build_cost;
		}
		return net_worth;
	}
	
	/*
	 * Allows user to pay tax
	 */
	public void pay_tax(int response) {
		if(response == 1)
			money -= calculate_net_worth();
		else
			money -= 200;
	}
	
	/*
	 * Prints out deeds owned by player
	 */
	public void print_deeds() {
		for(int i = 0; i < deeds.size(); i++) {
			System.out.println(i + ") Name: " + deeds.get(i).name + "| Rent Cost: " + deeds.get(i).calculate_rent() + 
					"| Houses: " + deeds.get(i).current_houses + "| Hotel: " + deeds.get(i).has_hotel + "| Mortgage Value:" 
					+ deeds.get(i).calculate_mortgage()); //add sell info, and houses/hotels
		}
	}
	
	/*
	 * Prints ownly mortgaged deeds
	 */
	public void print_mortgaged_deeds() {
		for(int i = 0; i < deeds.size(); i++) {
			if(deeds.get(i).mortgaged == true) {
				System.out.println(i + ") Name: " + deeds.get(i).name + "| Mortgage Owed: " + deeds.get(i).mortgage_owed);
			}
		}
	}
	
	/*
	 * Prints only street type deeds
	 */
	public void print_streets() {
		for(int i = 0; i < deeds.size(); i++) {
			if(deeds.get(i).deed_type.equals("street")) {
				System.out.println(i + ") Name: " + deeds.get(i).name + "| Build Cost:" + deeds.get(i).build_cost + 
						"| Current Houses: " + deeds.get(i).current_houses + "| Has a Hotel: " + deeds.get(i).has_hotel);
			}
		}
	}
	
	/*
	 * Allows a user to purchase a house and sets necessary information in deed class
	 */
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
	
	/*
	 * Allows user to purchase a hotel on a property
	 */
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
		int property = 0;
		if(has_one_eligible == true) {
			System.out.println("Which property would you like to build a hotel on?");
			property = in.nextInt();
			deeds.get(property).has_hotel = true;
			deeds.get(property).current_houses = 0;
			deeds.get(property).max_houses = false;
			money -= deeds.get(property).build_cost;
			System.out.println("You purchased a hotel on " + deeds.get(property).name + " for $" + deeds.get(property).build_cost);
			System.out.println("Remaining money = " + money);
		}
		else if(has_one_eligible == false) {
			System.out.println("No properties are eligible to have a hotel developed. (No properties owned, or no properties with 4 houses) \nWould you like to build a house instead? (1. Yes / 2. No)");
			int answer = in.nextInt();
			if(answer == 1)
				buy_house();
		}	
	}
}
