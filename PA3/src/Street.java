
public class Street extends Deed{
	public int house_cost;
	public int hotel_cost;
	public int num_houses;
	public boolean hotel;
	public String color;
	
	
	public Street(int position, String name, int property_group, String color, int purchase_price, int mortgage_value,
			int rent, int rent1house, int rent2house, int rent3house, int rent4house, int rent_hotel, int build_cost,
			String deed_type) {
		super(position, name, property_group, color, purchase_price, mortgage_value, rent, rent1house, rent2house, rent3house,
				rent4house, rent_hotel, build_cost, deed_type);
		// TODO Auto-generated constructor stub
	}

	public int calculate_rent(int num_houses, boolean hotel, Boolean owns_whole_color_group) {
		int rent = 0;
		return rent;
	}
}
