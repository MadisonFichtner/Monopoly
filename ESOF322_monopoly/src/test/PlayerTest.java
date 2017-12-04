package test;

import org.junit.Test;
import src.Deed;
import src.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    /*
     * Add deed directly to a player, then use the bought_property on another
     * player using the same deed and compare results
     */
    @Test
    public void test_bought_property() {
        Player test_player = new Player("test", "player", 1);
        boolean valid = false;
        Deed test_deed = new Deed(5, "test", 5, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
        Player testPlayer2 = new Player("test2", "player2", 2);
        test_deed.setOwner(testPlayer2);
        test_player.setPropertyGroups(new int[6]);
        test_player.boughtProperty(test_deed);
        valid = test_player.getMoney() == 1400 && test_deed.getOwner() == test_player && test_player.getDeeds().get(0) == test_deed;

        assertTrue(valid);
    }

    /*
     * Roll dice x number of times, if its between 1-12 each time, return true
     */
    @Test
    public void test_roll_dice() {
        boolean valid = false;
        Player test_player = new Player("test", "player", 1);
        for (int i = 0; i < 50; i++) {
            test_player.rollDice();
            valid = test_player.getDiceSum() >= 2 && test_player.getDiceSum() <= 12;
        }
        assertTrue(valid);
    }

    /*
     * Set dice_sum to some number, and set expected space to be whatever that number is
     * move the player that number, and compare the expected space to actual space
     */
    @Test
    public void test_move() {
        Player test_player = new Player("test", "player", 1);
        test_player.setDiceSum(6);
        int expected_position = 6;
        test_player.move();
        boolean valid = false;
        if (test_player.getPosition() == expected_position) {
            valid = true;
        }
        assertTrue(valid);
        assertEquals(test_player.getPosition(), expected_position);
    }

    /*
     * Mortgage a deed, and test that the mortgage flag is set
     */
    @Test
    public void testMortgageDeed() {
        Player testPlayer = new Player("test", "player", 1);
        boolean valid = false;
        Deed testDeed = new Deed(0, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
        testPlayer.mortgageDeed(testDeed);
        if (testPlayer.getMortgageOwed() == testDeed.getMortgageValue() && testPlayer.getMoney() == 1500 + testDeed.getMortgageValue() && testDeed.isMortgaged() == true)
            valid = true;

        assertTrue(valid);
    }

    /*
     * Incremently add properties to a deed, and calculate rent each time, then check
     * if all the outcomes are as predicted
     */
    @Test
    public void testPayRent() {
        Player testPlayer = new Player("test", "player", 1);
        Player testPlayer2 = new Player("test2", "player2", 2);
        Deed testDeed = new Deed(5, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "street");
        testDeed.setOwner(testPlayer2);

        testPlayer.setMoney(1000);
        testPlayer.payRent(testDeed);

        assertEquals((long) 1000 - testDeed.getRent(), (long) testPlayer.getMoney());
    }

    /*
     * Just add properties/money/anything with value towards net worth and check
     * to see that it matches the expected net_worth
     */
    @Test
    public void test_calculate_net_worth() {
        Player testPlayer = new Player("test", "player", 1);
		testPlayer.setMoney(testPlayer.getMoney() + 500);
        int expected_net_worth = 2000;
        testPlayer.calculateNetWorth();
        assertEquals(testPlayer.getNetWorth(), expected_net_worth);
    }


}
