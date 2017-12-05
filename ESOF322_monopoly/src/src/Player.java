package src;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    ArrayList<Deed> deeds = new ArrayList<Deed>(); // Assuming one person can own all positions on board
    int money = 1500;
    String token;
    String name;
    int position = 0;
    int[] dice = new int[]{0, 0};
    int diceSum = 0;
    int railroadCount = 0;
    int utilitiesCount = 0;
    int mortgageOwed = 0;
    boolean inJail = false;
    int turnsInJail = 0;
    int netWorth = money;
    int[] propertyTotals = {0, 0, 0, 1, 1, 1, 1, 1}; // {2, 3, 3, 3, 3, 3, 3, 2};
    int[] propertyGroups = new int[8];
    int playerNum = 0;
    int getOutOfJail = 0;
    int currentHouses = 0;

    public ArrayList<Deed> getDeeds() {
        return deeds;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getDiceSum() {
        return diceSum;
    }

    public void setDiceSum(int diceSum) {
        this.diceSum = diceSum;
    }

    public int getMortgageOwed() {
        return mortgageOwed;
    }

    public void setMortgageOwed(int mortgageOwed) {
        this.mortgageOwed = mortgageOwed;
    }

    public void setNetWorth(int netWorth) {
        this.netWorth = netWorth;
    }

    public int[] getPropertyGroups() {
        return propertyGroups;
    }

    public int getNetWorth() {
        return netWorth;
    }

    public void setPropertyGroups(int[] propertyGroups) {
        this.propertyGroups = propertyGroups;
    }

    int currentHotels = 0;

    /*
     * Creates new player object. Starts with $1500
     *
     * @param name -> name of player entered by user
     *
     * @param toke -> name of token entered by user (make picture on board)
     */
    public Player(String name, String token, int playerNum) {
        this.name = name;
        this.token = token;
        this.playerNum = playerNum;
    }

    // If a player buys a deed through the gui this is called, otherwise auction is
    // called
    public void boughtProperty(Deed deed) {
        deed.owner = this;
        money -= deed.price;
        deeds.add(deed);
        if (deed.type.equals("railroad")) {
            railroadCount++;
        } else {
            if (deed.type.equals("utility")) {
                utilitiesCount++;
            } else {
                propertyGroups[deed.propertyGroup - 1]++;
                if (propertyGroups[deed.propertyGroup - 1] == propertyTotals[deed.propertyGroup - 1]) {
                    for (int i = 0; i < deeds.size(); i++) {
                        if (deeds.get(i).propertyGroup == 1) {
                            deed.grouped = true;
                            deeds.get(i).grouped = true;
                        }
                    }
                }
            }
        }

        try {
            GUIHelper.setMessage(name + " bought " + deed.name + " and has now currently has $" + money);
            GUIHelper.enableTurnGUI();
        } catch (Exception e) {
            System.out.println("You might be testing, otherwise your GUI has crashed");
            e.printStackTrace();
        }
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
        boolean passedGo = false;
        if (position + diceSum >= 40) {
            int overflow = (position + diceSum) - 40;
            position = overflow;
            passedGo = true;
        } else
            position = position += diceSum;

        // if position 0 get passed
        // passed_go = true;

        if (passedGo == true) {
            money += 200;
            System.out.println("You passed go, have $200 on me!");
        }
        try {
            GUIHelper.moveTokenImg(playerNum, position);
        } catch (Exception e) {
            System.out.println("You might be testing, otherwise your GUI has crashed");
            e.printStackTrace();
        }
    }

    /**
     * Moves player directly to jail, and sets a boolean for whether they are in
     * jail to true
     */
    public void moveToJail() {
        position = 10;
        inJail = true;
        try {
            GUIHelper.moveTokenImg(this.playerNum, 10);
        } catch (Exception e) {
            System.out.println("You might be testing, otherwise your GUI has crashed");
            e.printStackTrace();
        }


    }

    /*
     * randomly generates two numbers between 1-6 to represent dice, sets the sum of
     * the dice, and returns images of the two dices sides that were rolled
     */
    public int[] rollDice() {
        // randomize dice throw and display image of side of each dice that was rolled
        Random rand = new Random();
        dice[0] = rand.nextInt(6) + 1;
        dice[1] = rand.nextInt(6) + 1;
        diceSum = dice[0] + dice[1];
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

    public void buyAuction(Deed deed, int price) {
        deeds.add(deed);
        money -= price;
        deed.owner = this;
        if (deed.type.equals("railroad"))
            railroadCount++;
        if (deed.type.equals("utility"))
            utilitiesCount++;
    }

    public void payBail() {
        try {
            GUIHelper.setMessage("Turn 3 in jail. You must pay $50 and roll forward.");
        } catch (Exception e) {
            System.out.println("You might be testing, otherwise your GUI has crashed");
            e.printStackTrace();
        }
        money -= 50;
        inJail = false;
        turnsInJail = 0;
        rollDice();
    }

    public void tradedDeed(Deed deed, Player player, int price) {
        if (deed.type.equals("railroad")) {
            railroadCount++;
            player.railroadCount--;
        } else {
            if (deed.type.equals("utility")) {
                utilitiesCount++;
                player.utilitiesCount--;
            } else {
                propertyGroups[deed.propertyGroup - 1]++;
                player.propertyGroups[deed.propertyGroup - 1]--;
                if (propertyGroups[deed.propertyGroup - 1] == propertyTotals[deed.propertyGroup - 1]) {
                    for (int i = 0; i < deeds.size(); i++) {
                        if (deeds.get(i).propertyGroup == deed.propertyGroup) {
                            deeds.get(i).grouped = true;
                        }
                    }
                } else if (deed.grouped = true) {
                    deed.grouped = false;
                }

            }
        }

        if (deed.type.equals("railroad")) {

        } else {
            if (deed.type.equals("utility")) {

            } else {

                if (player.propertyGroups[deed.propertyGroup - 1] == player.propertyTotals[deed.propertyGroup - 1]) {
                    for (int i = 0; i < deeds.size(); i++) {
                        if (deeds.get(i).propertyGroup == 1) {
                            deeds.get(i).grouped = true;
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
        try {
            GUIHelper.setMessage(player.name + " Bought " + deed.name + " from " + name + " for $" + price);
        } catch (Exception e) {
            System.out.println("You might be testing, otherwise your GUI has crashed");
            e.printStackTrace();
        }
    }

    // In design we have this returning an int but i dont see a point in that
    public void mortgageDeed(Deed deed) {
        money += deed.calculateMortgage();
        mortgageOwed += deed.mortgageValue;
        System.out.println(deed.name + " was mortgaged for " + deed.mortgageValue + "\nRemaining Money: " + money);
        deed.mortgaged = true;
    }

    public void payMortage(Deed deed) {
        deed.mortgaged = false; // set mortgaged flag to false
        money -= deed.mortgageOwed; // subtract mortgage_owed for specific property from money
        mortgageOwed -= deed.mortgageOwed; // subtract mortgage_owed for specific property from
        deed.mortgageOwed = 0;
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
    public void payRent(Deed deed) {
        Player receivingPlayer = deed.getOwner();

        if (receivingPlayer == this) {
        } else {
            this.setMoney(this.getMoney() - deed.calculateRent());
            receivingPlayer.setMoney(receivingPlayer.getMoney() + deed.calculateRent());
            System.out.println(receivingPlayer.name + " recieved $" + deed.calculateRent() + " in rent.");
            try {
                GUIHelper.setMessage(name + " pays $" + deed.calculateRent() + " in rent to " + receivingPlayer.name + " for " + deed.name + " and now has $" + money);
            } catch (Exception e) {
                System.out.println("You might be testing, otherwise your GUI has crashed");
                e.printStackTrace();
            }

        }
    }

    /*
     * Calculates a user's net worth based on current money, and properties owned
     */
    public int calculateNetWorth() {
        int net_worth = 0; // = all of items values and money added up
        net_worth += money;
        for (int i = 0; i < deeds.size(); i++) {
            net_worth += (deeds.get(i).price + (deeds.get(i).buildCost * deeds.get(i).currentHouses));
            if (deeds.get(i).hasHotel == true)
                net_worth += deeds.get(i).buildCost;
        }
        netWorth = net_worth;
        return net_worth;
    }

    /*
     * Allows user to pay tax
     */
    public void payTax(int response) {
        if (response == 1) {
            money -= calculateNetWorth() * .1;
        } else if (response == 2) {
            money -= 100;
        } else {
            money -= 200;
        }
    }

    public void boughtHouse(Deed deed, int houses) {
        if (deed.currentHouses == 4) {
            GUIHelper.setMessage("You already have 4 houses on this property, build a hotel.");
        } else if (deed.maxHouses == false && deed.type.equals("street")
                && deed.grouped == true) {
            currentHouses += houses;
            deed.currentHouses += houses;
            money -= deed.buildCost * houses;
            if (deed.currentHouses == 4)
                deed.maxHouses = true;
            System.out.println("Remaining money = " + money);
        } else {
            GUIHelper.setMessage("That is not an eligible property to build a house on");
        }
    }

    public void boughtHotel(Deed deed) {
        currentHotels++;
        currentHouses -= 4;
        deed.hasHotel = true;
        deed.currentHouses = 0;
        deed.maxHouses = false;
        money -= deed.buildCost;
        GUIHelper.setMessage("You purchased a hotel on " + deed.name + " for $" + deed.buildCost);
        System.out.println("Remaining money = " + money);
    }

    //Handles when a player lands on Community Chest
    public void communityChest(Card card, Player[] users, Deed[] board) {
        if (card.name.equals("Get Out of Jail Free")) {
            getOutOfJail++;
        } else
            useCard(card, users, board);
    }

    //Handles when a player lands on Chance
    public int chance(Card card, Player[] users, Deed[] board) {
        int case18case19 = 0;
        if (card.name.equals("Get Out of Jail Free")) {
            getOutOfJail++;
        } else
            case18case19 = useCard(card, users, board);
        return case18case19;
    }

    //List out all possiblities, check csv to see numbers associated with each card
    public int useCard(Card card, Player[] users, Deed[] board) {
        int case18case19 = 0;
        switch (card.type) {
            case 0: //Advance to go
                position = 0;
                money += 200;
                GUIHelper.moveTokenImg(playerNum, position);
                GUIHelper.setMessage("You drew: Advance to Go, collect $200");
                break;
            case 1: //bank error - collect 200
                GUIHelper.setMessage("You drew: Bank error - collect $200");
                break;
            case 2: //doctor's fee - pay 50
                GUIHelper.setMessage("You drew: Doctor's fee - pay $50");
                money -= 50;
                break;
            case 3: //from sale of stock - get 50
                GUIHelper.setMessage("You drew: From sale of stock you get $50");
                money += 50;
                break;
            case 4: //get out of jail card
                GUIHelper.setMessage("You drew: Get out of Jail, Free");
                break;
            case 5:    //Go to jail - do not collect 200 if pass go
                GUIHelper.setMessage("You drew: Go to Jail - do not collect Go");
                inJail = true;
                position = 10;
                break;
            case 6: //Grand Opera night - collect 50 from all players
                GUIHelper.setMessage("You drew: Grand Opera Night - collect $50 from everyone else");
                for (int i = 0; i < 4; i++) {
                    if (users[i] != this) {
                        users[i].money -= 50;
                        money += 50;
                    }
                }
                break;
            case 7: //Holiday fund - receive 100
                GUIHelper.setMessage("You drew: Holiday fund - receive $100");
                money += 100;
                break;
            case 8: //Income tax refund - collect 20
                GUIHelper.setMessage("You drew: Income tax refund - collect $20");
                money += 20;
                break;
            case 9:    //life insurance - collect 100
                GUIHelper.setMessage("You drew: Life insurance matures - collect $100");
                money += 100;
                break;
            case 10: //pay hospital fees - pay 100
                GUIHelper.setMessage("You drew: Pay hospital fees - pay $100");
                money -= 100;
                break;
            case 11: //pay school fees of 150
                GUIHelper.setMessage("You drew: Pay school fees - pay $150");
                money -= 150;
                break;
            case 12: //Receive consultancy fee - collect 25
                GUIHelper.setMessage("You drew: Receive consultancy fee - collect $25");
                money += 25;
                break;
            case 13: //street repairs - pay 40 per house and 115 per hotel
                GUIHelper.setMessage("You drew: Street repairs - pay $40 per house and $115 per hotel");
                int total = 0;
                int housesTotal = currentHouses * 40;
                int hotelsTotal = currentHotels * 115;
                total = housesTotal + hotelsTotal;
                money -= total;
                GUIHelper.setMessage("You paid: " + total + " for street repairs");
                break;
            case 14: //2nd place in beauty contest - collect 10
                GUIHelper.setMessage("You drew: Won second prize in a beauty contest - collect $10");
                money += 10;
                break;
            case 15: //inherit 100
                GUIHelper.setMessage("You drew: You inherit $100");
                money += 100;
                break;
            case 16: //Advance to Illinois Ave. or Duck Pond (24) - If you pass Go - collect $200
                GUIHelper.setMessage("You drew: Advance to " + board[24].name + " - If you pass Go - collect $200");
                if (position >= 24) {
                    money += 200;
                    GUIHelper.setMessage("You passed Go. Collect $200");
                }
                position = 24;
                GUIHelper.moveTokenImg(playerNum, position);
                break;
            case 17: //Advance to St. Charles Place or Howard (11) - if you pass Go - collect $200
                GUIHelper.setMessage("You drew: Advance to " + board[11].name + " - If you pass Go - collect $200");
                if (position >= 11) {
                    money += 200;
                    GUIHelper.setMessage("You passed Go. Collect $200");
                }
                position = 11;
                GUIHelper.moveTokenImg(playerNum, position);
                break;
            case 18: //Advance token to nearest Utility. If unowned - you may buy it. If owned - throw dice and pay owner a totel ten times the amount thrown.
                GUIHelper.setMessage("You drew: Advance to the next utility. If unowned - you may buy it. If owned - throw dice and pay owner a total ten times the amount thrown");
                case18case19 = 18;
                if (position == 7) {
                    position = 12;    //TODO
                } else {
                    position = 28;    //TODO
                }
                GUIHelper.moveTokenImg(playerNum, position);
                break;
            case 19: //Advance token to nearest Railroad and pay owner twice the rental. If unowned - you may buy it.
                GUIHelper.setMessage("You drew: Advance to the next Railroad or Street. If unowned - you may buy it. If owned - pay owner twice the rental price");
                case18case19 = 19;
                if (position == 7) {
                    position = 15;
                } else {
                    position = 25;
                }
                GUIHelper.moveTokenImg(playerNum, position);
                break;
            case 20: //Bank pays you dividend of $50
                GUIHelper.setMessage("You drew: Bank pays you dividend of $50");
                money += 50;
                break;
            case 21: //Go Back 3 Spaces
                GUIHelper.setMessage("You drew: Go back 3 spaces");
                if (position - 3 < 0) {
                    int overflow = position - 3;
                    overflow = overflow * (-1);
                    position = 40 - overflow;
                } else
                    position -= 3;
                break;
            case 22: //Make general repairs on all your property - For each house pay $25 - For each hotel $100
                GUIHelper.setMessage("You drew: Make general repairs on all your property - for each house pay $25 and for each hotel pay $100");
                total = 0;
                housesTotal = currentHouses * 25;
                hotelsTotal = currentHotels * 100;
                total = housesTotal + hotelsTotal;
                money -= total;
                GUIHelper.setMessage("You paid: " + total + " for general repairs");
                break;
            case 23: //Pay poor tax of $15
                GUIHelper.setMessage("You drew: Pay poor tax of $15");
                money -= 15;
                break;
            case 24: //Take a trip to Reading Railroad or Grant Street (5) - If you pass Go - collect $200
                GUIHelper.setMessage("You drew: Take a trip to " + board[5].name + " - If you pass GO - collect $200");
                if (position >= 5) {
                    money += 200;
                    GUIHelper.setMessage("You passed Go. Collect $200");
                }
                position = 5;
                GUIHelper.moveTokenImg(playerNum, position);
                break;
            case 25: //Take a walk on the Boardwalk or Montana Hall - Advance token to Boardwalk (39)
                GUIHelper.setMessage("You drew: take a walk to " + board[39].name);
                position = 39;
                GUIHelper.moveTokenImg(playerNum, position);
                break;
            case 26: //You have been elected Chairman of the Board - Pay each player $50
                GUIHelper.setMessage("You drew: You have been elected Chairman of the Board - pay each player $50");
                for (int i = 0; i < 4; i++) {
                    if (users[i] != this) {
                        users[i].money += 50;
                        money -= 50;
                    }
                }
                break;

        }
        return case18case19;
    }
}
