package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import test.Deed;
import test.Player;

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
		
		test_player.bought_property(test_deed);
		if(test_player.money == 1400 && test_deed.owner == test_player && test_player.deeds.get(0) == test_deed)
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
			test_player.roll_dice();
			if(test_player.dice_sum >= 2 && test_player.dice_sum <= 12)
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
		test_player.dice_sum = 6;
		int expected_position = 6;
		test_player.move();
		boolean valid = false;
		if(test_player.position == expected_position)
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
		test_player.mortgage_deed(test_deed);
		if(test_player.mortgage_owed == test_deed.mortgage_value && test_player.money == 1500 + test_deed.mortgage_value && test_deed.mortgaged == true)
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
		test_player.pay_rent(test_deed1);
		if(test_deed1.current_rent == test_deed1.rent)
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
		test_player.money += 500;
		int expected_net_worth = 2000;
		test_player.calculate_net_worth();
		
		assertEquals(test_player.overall_net_worth, expected_net_worth);
	}
	

}
