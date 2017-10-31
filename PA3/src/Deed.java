
public class Deed {
	public int position;
	public String name;
	public int property_group;
	public String color;
	public int purchase_price;
	public int mortgage_value;
	public int rent;
	public int rent1house;
	public int rent2house;
	public int rent3house;
	public int rent4house;
	public int rent_hotel;
	public int build_cost; //same for both houses and hotels
	public String deed_type;
	public int current_houses;
	public boolean has_hotel;
	
	public Player owner;
	public boolean whole_color_group_owned;
	public boolean mortgaged;
	
	/*
	 * Creates new deed, initially the owner, whole_group_owned, and mortgaged will be null
	 * 
	 * @param color -> color of space
	 * @param position -> position on the board for the deed
	 * @param purchase_price
	 * @param mortgage_value -> reward for mortgaging the deed 
	 * @param rent -> cost for landing on deed
	 * @param name -> name of deed
	 * @param deed_type -> Street, Railroad, Utility, or other
	 */
	public Deed(int position, String name, int property_group, String color, int purchase_price, int mortgage_value, int rent, int rent1house, int rent2house, int rent3house,
			int rent4house, int rent_hotel, int build_cost, String deed_type) {
		this.position = position;
		this.name = name;
		this.property_group = property_group;
		this.color = color;
		this.purchase_price	= purchase_price;
		this.mortgage_value = mortgage_value;
		this.rent = rent;
		this.rent1house = rent1house;
		this.rent2house = rent2house;
		this.rent3house = rent3house;
		this.rent4house = rent4house;
		this.rent_hotel = rent_hotel;
		this.build_cost = build_cost;
		this.deed_type = deed_type;
		this.current_houses = 0; //initially has no houses/hotel
		this.has_hotel = false;
		
		this.owner = null;
		this.whole_color_group_owned = false;
		this.mortgaged = false;
	}
	
	
	/*
	 * Calculates rent based on deed_type, houses/hotel on deed if it's a 
	 * street and whether the player who owns said deed owns all deeds of that color group
	 */
	public int calculate_rent() {
		int rent = 0;
		if(deed_type.equals("Street")) {
			//search streets[] for deed name and access calculate_rent from the street class
			//if owner owns all of color group, double
		}
		else if(deed_type.equals("Railroad")) {
			//search railroad[] for deed name and access calculate_rent from the street class
			//if owner owns 1 rent = 25; 2 rent = 50; 3 rent = 100; 4 rent = 200
		}
		else if(deed_type.equals("Utility")) {
			//search utilities[] for deed name and access calculate_rent from the street class
			//if owner owns 1 rent = 4*dice; 2 rent= 10 * dice
		}
		else {
			//else its free parking/other spots
			return 0;
		}
		return rent;
	}
	
	public int calculate_mortgage() {
		int mortgage = 0;
		mortgage += mortgage_value;
		if(current_houses != 0) {
			mortgage += current_houses * build_cost;
			current_houses = 0;
		}
		else if(has_hotel == true) {
			mortgage += 5 * build_cost;
			current_houses = 0;
			has_hotel = true;
		}
		mortgage_value = mortgage;
		return mortgage;
	}
	
}
