package test;

import org.junit.Test;
import src.Deed;
import src.Player;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void boughtProperty() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        boolean valid = false;
        Deed testDeed = new Deed(5, "test", 5, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
        Player testPlayer2 = new Player("test2", "player2", 2);
        testDeed.setOwner(testPlayer2);
        testPlayer.setPropertyGroups(new int[6]);
        testPlayer.boughtProperty(testDeed);
        valid = testPlayer.getMoney() == 1400 && testDeed.getOwner() == testPlayer && testPlayer.getDeeds().get(0) == testDeed;

        assertTrue(valid);
    }

    @Test
    public void move() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        testPlayer.setDiceSum(6);
        int expected_position = 6;
        testPlayer.move();
        boolean valid = false;
        if (testPlayer.getPosition() == expected_position) {
            valid = true;
        }
        assertTrue(valid);
        assertEquals(testPlayer.getPosition(), expected_position);
    }

    @Test
    public void moveToJail() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        testPlayer.moveToJail();
        assertEquals(testPlayer.getPosition(), 10);
    }

    @Test
    public void rollDice() throws Exception {
        boolean valid = false;
        Player testPlayer = new Player("test", "player", 1);
        for (int i = 0; i < 50; i++) {
            testPlayer.rollDice();
            valid = testPlayer.getDiceSum() >= 2 && testPlayer.getDiceSum() <= 12;
        }
        assertTrue(valid);
    }

    @Test
    public void buyAuction() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        Deed testDeed = new Deed(0, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
        testPlayer.buyAuction(testDeed, 50);
        ArrayList<Deed> expected = new ArrayList<>();
        expected.add(testDeed);
        assertTrue(testPlayer.getDeeds().equals(expected));
    }

    @Test
    public void payBail() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        testPlayer.payBail();
        assertTrue(testPlayer.getMoney() == 1450);
    }

    @Test
    public void tradedDeed() throws Exception {
        Deed testDeed = new Deed(5, "test", 5, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
        Deed testDeed2 = new Deed(2, "te2st", 5, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
        Player testPlayer = new Player("test", "player", 1);
        Player testPlayer2 = new Player("test2", "player2", 2);

        testPlayer.boughtProperty(testDeed);
        testPlayer2.boughtProperty(testDeed2);
        testDeed.setOwner(testPlayer);
        testDeed2.setOwner(testPlayer2);

        testPlayer2.tradedDeed(testDeed, testPlayer,  100);
        assertTrue(testPlayer2.getDeeds().contains(testDeed));

    }

    @Test
    public void mortgageDeed() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        boolean valid = false;
        Deed testDeed = new Deed(0, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
        testPlayer.mortgageDeed(testDeed);
        if (testPlayer.getMortgageOwed() == testDeed.getMortgageValue() && testPlayer.getMoney() == 1500 + testDeed.getMortgageValue() && testDeed.isMortgaged() == true)
            valid = true;

        assertTrue(valid);
    }

    @Test
    public void payMortage() throws Exception {
    }

    @Test
    public void payRent() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        Player testPlayer2 = new Player("test2", "player2", 2);
        Deed testDeed = new Deed(5, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "street");
        testDeed.setOwner(testPlayer2);

        testPlayer.setMoney(1000);
        testPlayer.payRent(testDeed);

        assertEquals((long) 1000 - testDeed.getRent(), (long) testPlayer.getMoney());
    }

    @Test
    public void calculateNetWorth() throws Exception {
        Player testPlayer = new Player("test", "player", 1);
        testPlayer.setMoney(testPlayer.getMoney() + 500);
        int expected_net_worth = 2000;
        testPlayer.calculateNetWorth();
        assertEquals(testPlayer.getNetWorth(), expected_net_worth);
    }

    @Test
    public void payTax() throws Exception {
    }

    @Test
    public void boughtHouse() throws Exception {
    }

    @Test
    public void boughtHotel() throws Exception {
    }

    @Test
    public void communityChest() throws Exception {
    }

    @Test
    public void chance() throws Exception {
    }

    @Test
    public void useCard() throws Exception {
    }


}
