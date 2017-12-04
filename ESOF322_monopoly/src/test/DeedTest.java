package test;

import org.junit.Test;
import src.Deed;
import src.Player;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


/**
 * calculates the rent for 0-4 houses and with a hotel and compares them to the expected values for a test property
 */
public class DeedTest {
    @Test
    public void isHasHotel() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        assertTrue(testDeed.isHasHotel() == false);
    }

    @Test
    public void setHasHotel() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        testDeed.setHasHotel(true);
        assertTrue(testDeed.isHasHotel() == true);
    }

    @Test
    public void isMortgaged() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        assertTrue(testDeed.isMortgaged() == false);
    }

    @Test
    public void setMortgaged() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        testDeed.setMortgaged(true);
        assertTrue(testDeed.isMortgaged() == true);
    }

    @Test
    public void getCurrentHouses() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        assertTrue(testDeed.getCurrentHouses() == 0);
    }

    @Test
    public void getMortgageValue() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        assertTrue(testDeed.getMortgageValue() == 50);
    }

    @Test
    public void getRent() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        assertTrue(testDeed.getRent() == 10);
    }

    @Test
    public void setCurrentHouses() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        testDeed.setCurrentHouses(2);
        assertTrue(testDeed.getCurrentHouses() == 2);
    }


    @Test
    public void getOwner() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        Player testPlayer = new Player("test", "player", 1);
        testDeed.setOwner(testPlayer);
        assertTrue(testDeed.getOwner().equals(testPlayer));
    }

    @Test
    public void setOwner() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        Player testPlayer = new Player("test", "player", 1);
        testDeed.setOwner(testPlayer);
        assertTrue(testDeed.getOwner().equals(testPlayer));
    }


    @Test
    public void calculateRent() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        int[] expectedRent = {10, 15, 17, 19, 21, 30};
        int[] calculatedRent = new int[6];

        calculatedRent[0] = testDeed.calculateRent();
        testDeed.setCurrentHouses(1);
        calculatedRent[1] = testDeed.calculateRent();
        testDeed.setCurrentHouses(2);
        calculatedRent[2] = testDeed.calculateRent();
        testDeed.setCurrentHouses(3);
        calculatedRent[3] = testDeed.calculateRent();
        testDeed.setCurrentHouses(4);
        calculatedRent[4] = testDeed.calculateRent();
        testDeed.setHasHotel(true);
        calculatedRent[5] = testDeed.calculateRent();


        assertArrayEquals(expectedRent, calculatedRent);

    }

    @Test
    public void calculateMortgage() throws Exception {
        Deed testDeed = new Deed(5, "testDeed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        int[] expectedRent = {50, 65, 80, 95, 110, 125};
        int[] calculatedRent = new int[6];

        calculatedRent[0] = testDeed.calculateMortgage();
        testDeed.setCurrentHouses(1);
        calculatedRent[1] = testDeed.calculateMortgage();
        testDeed.setCurrentHouses(2);
        calculatedRent[2] = testDeed.calculateMortgage();
        testDeed.setCurrentHouses(3);
        calculatedRent[3] = testDeed.calculateMortgage();
        testDeed.setCurrentHouses(4);
        calculatedRent[4] = testDeed.calculateMortgage();
        testDeed.setHasHotel(true);
        calculatedRent[5] = testDeed.calculateMortgage();


        assertArrayEquals(expectedRent, calculatedRent);
    }


}
