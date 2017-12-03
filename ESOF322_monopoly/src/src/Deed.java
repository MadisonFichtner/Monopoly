package src;

public class Deed {
	int position;
	String name;
	int propertyGroup;
	String color;
	int price;
	int mortgageValue;
	int rent;
	int rent1house;
	int rent2house;
	int rent3house;
	int rent4house;
	int rentHotel;
	int buildCost; // same for both houses and hotels
	String type;
	int currentHouses = 0;
	boolean hasHotel = false;
	int mortgageOwed = 0;
	int newRent = rent;
	boolean maxHouses = false;
	Player owner = null;
	boolean grouped = false;
	boolean mortgaged = false;
	int currentRent = 0;

	/*
	 * Creates new deed, initially the owner, whole_group_owned, and mortgaged will
	 * be null
	 * 
	 * @param color -> color of space
	 * 
	 * @param position -> position on the board for the deed
	 * 
	 * @param purchase_price
	 * 
	 * @param mortgage_value -> reward for mortgaging the deed
	 * 
	 * @param rent -> cost for landing on deed
	 * 
	 * @param name -> name of deed
	 * 
	 * @param deed_type -> Street, Railroad, Utility, or other
	 */
	public Deed(int position, String name, int property_group, String color, int purchase_price, int mortgage_value,
			int rent, int rent1house, int rent2house, int rent3house, int rent4house, int rent_hotel, int build_cost,
			String deed_type) {
		this.position = position;
		this.name = name;
		this.propertyGroup = property_group;
		this.color = color;
		this.price = purchase_price;
		this.mortgageValue = mortgage_value;
		this.rent = rent;
		this.rent1house = rent1house;
		this.rent2house = rent2house;
		this.rent3house = rent3house;
		this.rent4house = rent4house;
		this.rentHotel = rent_hotel;
		this.buildCost = build_cost;
		this.type = deed_type;
	}

	/*
	 * Calculates rent based on deed_type, houses/hotel on deed if it's a street and
	 * whether the player who owns said deed owns all deeds of that color group
	 */
	public int calculateRent() {
		if (mortgaged == true)
			System.out.println(name + " is mortgaged and will not collect rent.");
		else if (type.equals("street")) {
			switch (currentHouses) {
			case 0:
				newRent = rent;
				break;
			case 1:
				newRent = rent1house;
				break;
			case 2:
				newRent = rent2house;
				break;
			case 3:
				newRent = rent3house;
				break;
			case 4:
				newRent = rent4house;
				break;
			}
			if (hasHotel == true)
				newRent = rentHotel;
		} else if (type.equals("railroad")) {
			// if owner owns 1 rent = 25; 2 rent = 50; 3 rent = 100; 4 rent = 200
			switch (owner.railroadCount) {
			case 0:
				newRent = rent;
				break;
			case 1:
				newRent = 25;
				break;
			case 2:
				newRent = 50;
				break;
			case 3:
				newRent = 100;
				break;
			case 4:
				newRent = 200;
				break;
			}
		} else if (type.equals("utility")) {
			// if owner owns 1 rent = 4*dice; 2 rent= 10 * dice
			switch (owner.utilitiesCount) {
			case 1:
				newRent = (4 * owner.diceSum);
			case 2:
				newRent = (10 * owner.diceSum);
			}
		} else {
			// else its free parking/other spots
			return 0;
		}
		if (grouped == true)
			newRent = newRent * 2;
		currentRent = newRent;
		return newRent;
	}

	/*
	 * Calculates mortgage based on base mortgage value, and the nubmer of houses
	 * built, or whether a hotel is built
	 * 
	 * returns the int value of the mortgage
	 */
	public int calculateMortgage() {
		int mortgage = 0;
		mortgage = mortgageValue;
		if (currentHouses != 0) {
			System.out.println(currentHouses + " houses were sold to mortgage the property for: $"
					+ (currentHouses + buildCost));
			mortgage += currentHouses * buildCost;
			currentHouses = 0;
		} else if (hasHotel == true) {
			System.out.println("The hotel on the property was sold to mortgage the property for: $" + (5 * buildCost));
			mortgage += 5 * buildCost;
			currentHouses = 0;
			hasHotel = true;
		}
		mortgageOwed = mortgage;
		return mortgage;
	}
}
