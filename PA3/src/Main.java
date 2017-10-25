import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main{
	private static final String COMMA_DELIMITER = ",";
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(System.in);
		System.out.println("How many players? ");
		int users = in.nextInt();
	
		Player[] players = new Player[users];
		for(int i = 0; i < users; i++) {
			System.out.println("Please enter a name for player " + (i+1) + ": ");
			String name = in.next();
			System.out.println("Please select a token for player " + (i+1) + ": ");
			String token = in.next();

			players[i] = new Player(name, token);
		}
		Deed[] new_board = new Deed[40];
		in.close();
		
		String fileName = "monopolyDeeds.csv"; //just a sample file name
		File csvFile = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(csvFile));
		new_board = parse_CSV(csvFile);
		
		Board board = new Board(players, new_board);
	}
	
	//parse_CSV needs to read in contents of .csv and populate an array of deeds
	public static Deed[] parse_CSV(File csv_file) throws FileNotFoundException {
		Deed[] deeds = new Deed[40];
		Scanner in = new Scanner(csv_file);
		in.useDelimiter(COMMA_DELIMITER);
		for(int i = 0; i < 40; i++) {
			int position = in.nextInt();
			String name = in.next();
			int property_group = in.nextInt();
			String color = in.next();
			int price = in.nextInt();
			int mortgage_value = in.nextInt();
			int rent = in.nextInt();
			int rent1 = in.nextInt();
			int rent2 = in.nextInt();
			int rent3 = in.nextInt();
			int rent4 = in.nextInt();
			int rent_hotel = in.nextInt();
			int build_cost = in.nextInt();
			String deed_type = in.next();
			
			deeds[i] = new Deed(position, name, property_group, color, price, mortgage_value, rent,
					rent1, rent2, rent3, rent4, rent_hotel, build_cost, deed_type);
		}
		in.close();
		//populate deeds giving csv_file
		return deeds;
	}
	

}
