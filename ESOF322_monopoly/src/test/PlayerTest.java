package test;

import static org.junit.Assert.*;

import org.junit.Test;

import src.Deed;
import src.Player;

public class PlayerTest {

	/*
	 * Add deed directly to a player, then use the bought_property on another
	 * player using the same deed and compare results
	 */
	@Test
	public void test_bought_property() {
		Player test_player = new Player("test", "player", 1);
		boolean valid = false;
		Deed test_deed = new Deed(0, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
		
		test_player.boughtProperty(test_deed);
		if(test_player.getMoney() == 1400 && test_deed.getOwner() == test_player && test_player.getDeeds().get(0) == test_deed)
			valid = true;
		else 
			valid = false;
		
		assertTrue(valid);
	}

	/*
	 * Roll dice x number of times, if its between 1-12 each time, return true
	 */
	@Test
	public void test_roll_dice() {
		boolean valid = false;
		Player test_player = new Player("test", "player", 1);
		for(int i = 0; i < 50; i++) {
			test_player.rollDice();
			if(test_player.getDiceSum() >= 2 && test_player.getDiceSum() <= 12)
			{
				valid = true;
			}
			else
				valid = false;
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
//		test_player.getDiceSum() = 6;
		int expected_position = 6;
		test_player.move();
		boolean valid = false;
		if(test_player.getPosition() == expected_position)
			valid = true;
		assertTrue(valid);
		//assertEquals(test_player.position, expected_position);
	}
	
	/*
	 * Mortgage a deed, and test that the mortgage flag is set
	 */
	@Test
	public void test_mortgage_deed() {
		Player test_player = new Player("test", "player", 1);
		boolean valid = false;
		Deed test_deed = new Deed(0, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "test");
		test_player.mortgageDeed(test_deed);
		if(test_player.getMortgageOwed() == test_deed.getMortgageValue() && test_player.getMoney() == 1500 + test_deed.getMortgageValue() && test_deed.isMortgaged() == true)
			valid = true;
		
		assertTrue(valid);
	}
	
	/*
	 * Incremently add properties to a deed, and calculate rent each time, then check
	 * if all the outcomes are as predicted
	 */
	@Test
	public void test_pay_rent() {
		Player test_player = new Player("test", "player", 1);
		boolean valid = false;
		Deed test_deed1 = new Deed(0, "test", 0, "blue", 100, 100, 10, 15, 20, 25, 30, 35, 50, "street");
		
		//testing if rent is correctly calculated for no houses/hotels
		test_player.payRent(test_deed1);
		if(test_deed1.getCurrentRent() == test_deed1.getRent())
			valid = true;
		assertTrue(valid);
	}
	
	/*
	 * Just add properties/money/anything with value towards net worth and check
	 * to see that it matches the expected net_worth
	 */
	@Test
	public void test_calculate_net_worth() {
		Player test_player = new Player("test", "player", 1);
//		test_player.getMoney() += 500;
		int expected_net_worth = 2000;
		test_player.calculateNetWorth();
		assertEquals(test_player.getNetWorth(), expected_net_worth);
	}
	

}
