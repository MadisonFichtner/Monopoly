package test;

import org.junit.Test;
import src.Deed;

import static org.junit.Assert.assertArrayEquals;


/**
 * calculates the rent for 0-4 houses and with a hotel and compares them to the expected values for a test property
 */
public class DeedTest {
    @Test
    public void calculateRent() throws Exception {
        Deed test_deed = new Deed(5, "test_deed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        int[] expectedRent = {10, 15, 17, 19, 21, 30};
        int[] calculatedRent = new int[6];

        calculatedRent[0] = test_deed.calculateRent();
        test_deed.setCurrentHouses(1);
        calculatedRent[1] = test_deed.calculateRent();
        test_deed.setCurrentHouses(2);
        calculatedRent[2] = test_deed.calculateRent();
        test_deed.setCurrentHouses(3);
        calculatedRent[3] = test_deed.calculateRent();
        test_deed.setCurrentHouses(4);
        calculatedRent[4] = test_deed.calculateRent();
        test_deed.setHasHotel(true);
        calculatedRent[5] = test_deed.calculateRent();


        assertArrayEquals(expectedRent, calculatedRent);

    }

    @Test
    public void calculateMortgage() throws Exception {
        Deed test_deed = new Deed(5, "test_deed", 0, "red", 50, 50, 10, 15, 17, 19, 21, 30, 15, "street");
        int[] expectedRent = {50, 65, 80, 95, 110, 125};
        int[] calculatedRent = new int[6];

        calculatedRent[0] = test_deed.calculateMortgage();
        test_deed.setCurrentHouses(1);
        calculatedRent[1] = test_deed.calculateMortgage();
        test_deed.setCurrentHouses(2);
        calculatedRent[2] = test_deed.calculateMortgage();
        test_deed.setCurrentHouses(3);
        calculatedRent[3] = test_deed.calculateMortgage();
        test_deed.setCurrentHouses(4);
        calculatedRent[4] = test_deed.calculateMortgage();
        test_deed.setHasHotel(true);
        calculatedRent[5] = test_deed.calculateMortgage();


        assertArrayEquals(expectedRent, calculatedRent);
    }


}
