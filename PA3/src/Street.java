
public class Street extends Deed{
	public int house_cost;
	public int hotel_cost;
	public int num_houses;
	public boolean hotel;
	public String color;
	
	
	public Street(int position, String color, int purchase_price, int mortgage_value, int rent, String name,
			String deed_type) {
		super(position, color, purchase_price, mortgage_value, rent, name, deed_type);
		// TODO Auto-generated constructor stub
	}


	public int calculate_rent(int num_houses, boolean hotel, Boolean owns_whole_color_group) {
		int rent = 0;
		return rent;
	}
}
