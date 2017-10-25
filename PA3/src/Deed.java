
public class Deed {
	public String name;
	public String color;
	public int position;
	public int purchase_price;
	public int mortgage_value;
	public int rent;
	public Player owner;
	public boolean whole_color_group_owned;
	public boolean mortgaged;
	public String deed_type;
	
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
	public Deed(int position, String color, int purchase_price, int mortgage_value, int rent, String name, String deed_type) {
		this.position = position;
		this.color = color;
		this.purchase_price = purchase_price;
		this.mortgage_value = mortgage_value;
		this.rent = rent;
		this.name = name;
		this.owner = null;
		this.whole_color_group_owned = false;
		this.mortgaged = false;
		this.deed_type = deed_type;
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
	
	public Player get_owner() {
		return owner;
	}
	
	public int get_mortgage() {
		return mortgage_value;
	}

}
