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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPropertyGroup() {
		return propertyGroup;
	}

	public void setPropertyGroup(int propertyGroup) {
		this.propertyGroup = propertyGroup;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMortgageValue() {
		return mortgageValue;
	}

	public void setMortgageValue(int mortgageValue) {
		this.mortgageValue = mortgageValue;
	}

	public int getRent() {
		return rent;
	}

	public void setRent(int rent) {
		this.rent = rent;
	}

	public int getRent1house() {
		return rent1house;
	}

	public void setRent1house(int rent1house) {
		this.rent1house = rent1house;
	}

	public int getRent2house() {
		return rent2house;
	}

	public void setRent2house(int rent2house) {
		this.rent2house = rent2house;
	}

	public int getRent3house() {
		return rent3house;
	}

	public void setRent3house(int rent3house) {
		this.rent3house = rent3house;
	}

	public int getRent4house() {
		return rent4house;
	}

	public void setRent4house(int rent4house) {
		this.rent4house = rent4house;
	}

	public int getRentHotel() {
		return rentHotel;
	}

	public void setRentHotel(int rentHotel) {
		this.rentHotel = rentHotel;
	}

	public int getBuildCost() {
		return buildCost;
	}

	public void setBuildCost(int buildCost) {
		this.buildCost = buildCost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCurrentHouses() {
		return currentHouses;
	}

	public void setCurrentHouses(int currentHouses) {
		this.currentHouses = currentHouses;
	}

	public boolean isHasHotel() {
		return hasHotel;
	}

	public void setHasHotel(boolean hasHotel) {
		this.hasHotel = hasHotel;
	}

	public int getMortgageOwed() {
		return mortgageOwed;
	}

	public void setMortgageOwed(int mortgageOwed) {
		this.mortgageOwed = mortgageOwed;
	}

	public int getNewRent() {
		return newRent;
	}

	public void setNewRent(int newRent) {
		this.newRent = newRent;
	}

	public boolean isMaxHouses() {
		return maxHouses;
	}

	public void setMaxHouses(boolean maxHouses) {
		this.maxHouses = maxHouses;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public boolean isGrouped() {
		return grouped;
	}

	public void setGrouped(boolean grouped) {
		this.grouped = grouped;
	}

	public boolean isMortgaged() {
		return mortgaged;
	}

	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
	}

	public int getCurrentRent() {
		return currentRent;
	}

	public void setCurrentRent(int currentRent) {
		this.currentRent = currentRent;
	}

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
	public Deed(int position, String name, int propertyGroup, String color, int purchasePrice, int mortgageValue,
			int rent, int rent1house, int rent2house, int rent3house, int rent4house, int rentHotel, int buildCost,
			String deedType) {
		this.position = position;
		this.name = name;
		this.propertyGroup = propertyGroup;
		this.color = color;
		this.price = purchasePrice;
		this.mortgageValue = mortgageValue;
		this.rent = rent;
		this.rent1house = rent1house;
		this.rent2house = rent2house;
		this.rent3house = rent3house;
		this.rent4house = rent4house;
		this.rentHotel = rentHotel;
		this.buildCost = buildCost;
		this.type = deedType;
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
