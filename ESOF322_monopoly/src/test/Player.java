package test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static test.Board.current;

public class Player {
    public ArrayList<Deed> deeds = new ArrayList<Deed>(); // Assuming one person can own all positions on board
    public int money = 1500;
    public String token;
    public String name;
    public int position = 0;
    public int[] dice = new int[]{0, 0};
    public int dice_sum = 0;
    public int railroad_count = 0;
    public int utilities_count = 0;
    public int building_value = 0;
    public int mortgage_owed = 0;
    public boolean has_street = false;
    public boolean in_jail = false;
    public int turns_in_jail = 0;
    public boolean is_interested = true;
    public int overall_net_worth = money;
    public int[] property_totals = {2, 3, 3, 3, 3, 3, 3, 2};
    public int[] property_groups = new int[8];
    public int player_num = 0;

    /*
     * Creates new player object. Starts with $1500
     *
     * @param name -> name of player entered by user
     *
     * @param toke -> name of token entered by user (make picture on board)
     */
    public Player(String name, String token, int player_num) {
        this.name = name;
        this.token = token;
        this.player_num = player_num;
    }

    /*
     * randomly generates two numbers between 1-6 to represent dice, sets the sum of
     * the dice, and returns images of the two dices sides that were rolled
     */


    // If a player buys a deed through the gui this is called, otherwise auction is
    // called
    public void bought_property(Deed deed) {
        deed.owner = this;
        money -= deed.purchase_price;
        deeds.add(deed);
        if (deed.deed_type.equals("railroad")) {
            railroad_count++;
        } else {
            if (deed.deed_type.equals("utility")) {
                utilities_count++;
            } else {
                property_groups[deed.property_group - 1]++;
                if (property_groups[deed.property_group - 1] == property_totals[deed.property_group - 1]) {
                    for (int i = 0; i < deeds.size(); i++) {
                        if (deeds.get(i).property_group == 1) {
                            deeds.get(i).whole_color_group_owned = true;
                        }
                    }
                }
            }
        }

        Main.monopoly.set_message(name + " bought " + deed.name);
        Main.monopoly.enable_buttons();
    }

    /*
     * If player bought auction, this is used
     *
     * @param deed -> the deed that was purchased
     *
     * @param price -> price at which they purchased the deed
*/

    /*
     * Moves players token based on dice roll and current position, and returns $200
     * if they passed go
     *
     * @param spaces -> dice_sum
     */
    public void move() {
        boolean passed_go = false;
        if (position + dice_sum >= 40) {
            int overflow = (position + dice_sum) - 40;
            position = overflow;
            passed_go = true;
        } else
            position = position += dice_sum;

        // if position 0 get passed
        // passed_go = true;

        if (passed_go == true) {
            money += 200;
            System.out.println("You passed go, have $200 on me!");
        }
        Main.monopoly.move_token(player_num, position);
    }

    /**
     * Moves player directly to jail, and sets a boolean for whether they are in
     * jail to true
     */
    public void move_to_jail() {
        position = 10;
        in_jail = true;
        Main.monopoly.move_token(this.player_num, 10);


    }


    /*
     * Logic to get out of jail
     */
    public void get_out_of_jail() {
        // give option to roll dice for doubles or pay $50
        Scanner in = new Scanner(System.in);
        if (turns_in_jail == 3) { // If they've rolled for doubles the max number of times
            System.out.println("Turn 3 in jail. You must pay $50 and roll forward.");
            money -= 50;
            in_jail = false;
            turns_in_jail = 0;
            roll_dice();
            move();
        } else { // give option to either pay or roll for doubles
            System.out.println("Turn: " + turns_in_jail + " spent in jail. " + (3 - turns_in_jail)
                    + " more before you must pay $50 dollars.");
            System.out.println("Roll for doubles, or pay $50? (1. Roll / 2. $50)");
            int choice = in.nextInt();
            switch (choice) {
                case 1: // If they roll for doubles
                    int dice[] = roll_dice();
                    if (dice[0] == dice[1]) // If doubles are rolled
                    {
                        System.out.println("You rolled doubles. Gratz.");
                        in_jail = false;
                        turns_in_jail = 0;
                        move();
                        break;
                    } else // If doubles are not rolled
                    {
                        System.out.println("You did not roll doubles...");
                        break;
                    }
                case 2: // If they pay $50
                    System.out.println("You paid $50 and are now out of jail.");
                    roll_dice();
                    move();
                    break;
            }
        }
    }



	/*
     * Creates new player object. Starts with $1500
	 *
	 * @param name -> name of player entered by user
	 *
	 * @param toke -> name of token entered by user (make picture on board)
*/

    /*
     * randomly generates two numbers between 1-6 to represent dice, sets the sum of
     * the dice, and returns images of the two dices sides that were rolled
     */
    public int[] roll_dice() {
        // randomize dice throw and display image of side of each dice that was rolled
        Random rand = new Random();
        dice[0] = rand.nextInt(6) + 1;
        dice[1] = rand.nextInt(6) + 1;
        dice_sum = dice[0] + dice[1];
        System.out.println("Dice 1 result: " + dice[0] + "\nDice 2 result: " + dice[1]);
        System.out.println("Current money: " + money);
        return dice;
    }

    // If a player buys a deed through the gui this is called, otherwise auction is
    // called


    /*
     * If player bought auction, this is used
     *
     * @param deed -> the deed that was purchased
     *
     * @param price -> price at which they purchased the deed
     */
    public void buy_auction(Deed deed, int price) {
        deeds.add(deed);
        money -= price;
        deed.owner = this;
        if (deed.deed_type.equals("railroad"))
            railroad_count++;
        if (deed.deed_type.equals("utility"))
            utilities_count++;
    }

	/*
     * Moves players token based on dice roll and current position, and returns $200
	 * if they passed go
	 *
	 * @param spaces -> dice_sum
	 */


    /**
     * Moves player directly to jail, and sets a boolean for whether they are in
     * jail to true
     */


    public void pay_bail() {
        Main.monopoly.set_message("Turn 3 in jail. You must pay $50 and roll forward.");
        money -= 50;
        in_jail = false;
        turns_in_jail = 0;
        roll_dice();
    }

    public void traded_deed(Deed deed, Player player, int price) {
        if (deed.deed_type.equals("railroad")) {
            railroad_count++;
            player.railroad_count--;
        } else {
            if (deed.deed_type.equals("utility")) {
                utilities_count++;
                player.utilities_count--;
            } else {
                property_groups[deed.property_group - 1]++;
                player.property_groups[deed.property_group - 1]--;
                if (property_groups[deed.property_group - 1] == property_totals[deed.property_group - 1]) {
                    for (int i = 0; i < deeds.size(); i++) {
                        if (deeds.get(i).property_group == deed.property_group) {
                            deeds.get(i).whole_color_group_owned = true;
                        }
                    }
                } else if (deed.whole_color_group_owned = true) {
                    deed.whole_color_group_owned = false;
                }

            }
        }

        if (deed.deed_type.equals("railroad")) {

        } else {
            if (deed.deed_type.equals("utility")) {

            } else {

                if (player.property_groups[deed.property_group - 1] == player.property_totals[deed.property_group - 1]) {
                    for (int i = 0; i < deeds.size(); i++) {
                        if (deeds.get(i).property_group == 1) {
                            deeds.get(i).whole_color_group_owned = true;
                        }
                    }
                }
            }
        }

        player.deeds.add(deed); // adds deed to recipients deeds
        this.deeds.remove(deed); // removes deed from current players deeds
        deed.owner = player;
        money += price;
        player.money -= price;
        System.out.println("Property has been transfered to the buyer.");
        Main.monopoly.set_message(player.name + " Bought " + deed.name + " from " + name + " for $" + price);
    }

    // In design we have this returning an int but i dont see a point in that
    public void mortgage_deed(Deed deed) {
        money += deed.calculate_mortgage();
        mortgage_owed += deed.mortgage_value;
        System.out.println(deed.name + " was mortgaged for " + deed.mortgage_value + "\nRemaining Money: " + money);
        deed.mortgaged = true;
    }

    public void pay_mortgage_single(Deed deed) {
        deed.mortgaged = false; // set mortgaged flag to false
        money -= deed.mortgage_owed; // subtract mortgage_owed for specific property from money
        mortgage_owed -= deed.mortgage_owed; // subtract mortgage_owed for specific property from
        deed.mortgage_owed = 0;
    }

    /*
     * Pays rent to owner of property landed on. Nothing happens if the player whose
     * token lands on the deed is the same player who owns the property
     *
     * @param deed -> deed landed on/rent being paid for
     *
     * @param receiving_player -> player who owns deed
     *
     *
     */
    public void pay_rent(Deed deed) {
        Player receiving_player = deed.owner;

        if (receiving_player == this) {
        } else {
            money -= deed.calculate_rent();
            receiving_player.money += deed.calculate_rent();
            System.out.println(receiving_player.name + " recieved $" + deed.calculate_rent() + " in rent.");
            Main.monopoly.set_message(name + " pays $" + deed.calculate_rent() + " in rent to " + receiving_player.name
                    + " for " + deed.name);
        }
    }

    /*
     * Calculates a user's net worth based on current money, and properties owned
     */
    public int calculate_net_worth() {
        int net_worth = 0; // = all of items values and money added up
        net_worth += money;
        for (int i = 0; i < deeds.size(); i++) {
            net_worth += (deeds.get(i).purchase_price + (deeds.get(i).build_cost * deeds.get(i).current_houses));
            if (deeds.get(i).has_hotel == true)
                net_worth += deeds.get(i).build_cost;
        }
        overall_net_worth = net_worth;
        return net_worth;
    }

    /*
     * Allows user to pay tax
     */
    public void pay_tax(int response) {
        if (response == 1) {
            money -= calculate_net_worth() * .1;
        } else if (response == 2) {
            money -= 100;
        } else {
            money -= 200;
        }
    }

    /*
     * Prints out deeds owned by player
     */
    public void print_deeds() {
        for (int i = 0; i < deeds.size(); i++) {
            System.out.println(i + ") Name: " + deeds.get(i).name + "| Rent Cost: " + deeds.get(i).calculate_rent()
                    + "| Houses: " + deeds.get(i).current_houses + "| Hotel: " + deeds.get(i).has_hotel
                    + "| Mortgage Value:" + deeds.get(i).calculate_mortgage()); // add sell info, and houses/hotels
        }
    }

    /*
     * Prints only mortgaged deeds
     */
    public void print_mortgaged_deeds() {
        for (int i = 0; i < deeds.size(); i++) {
            if (deeds.get(i).mortgaged == true) {
                System.out
                        .println(i + ") Name: " + deeds.get(i).name + "| Mortgage Owed: " + deeds.get(i).mortgage_owed);
            }
        }
    }

    /*
     * Prints only street type deeds
     */
    public void print_streets() {
        for (int i = 0; i < deeds.size(); i++) {
            if (deeds.get(i).deed_type.equals("street")) {
                System.out.println(i + ") Name: " + deeds.get(i).name + "| Build Cost:" + deeds.get(i).build_cost
                        + "| Current Houses: " + deeds.get(i).current_houses + "| Has a Hotel: "
                        + deeds.get(i).has_hotel);
            }
        }
    }

    public void bought_house(Deed deed, int houses) {
        if (deed.current_houses == 4) {
            Main.monopoly.set_message("You already have 4 houses on this property, build a hotel.");
        } else if (deed.max_houses == false && deed.deed_type.equals("street")
                && deed.whole_color_group_owned == true) {
            deed.current_houses += houses;
            money -= deed.build_cost * houses;
            if (deed.current_houses == 4)
                deed.max_houses = true;
            System.out.println("Remaining money = " + money);
        } else {
            Main.monopoly.set_message("That is not an eligible property to build a house on");
        }
    }

    public void bought_hotel(Deed deed) {
        deed.has_hotel = true;
        deed.current_houses = 0;
        deed.max_houses = false;
        money -= deed.build_cost;
        Main.monopoly.set_message("You purchased a hotel on " + deed.name + " for $" + deed.build_cost);
        System.out.println("Remaining money = " + money);
    }

}
