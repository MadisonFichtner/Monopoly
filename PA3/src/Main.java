import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main{
	static int interval;
	static Timer timer;
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
		
		String fileName = "test.csv"; //just a sample file name
		File csvFile = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(csvFile));
		new_board = parse_CSV(csvFile);
		
		Board board = new Board(players, new_board);
		
		//Timer to kill program
		Timer timer = new Timer();
		interval = 180;
		int delay = 1000;
		int period = 1000;
		timer.scheduleAtFixedRate(new TimerTask() {

        	int i = 0;
	        public void run() {
	        	//put program code in here, will stop when timer hits 0 starting at interval (300)
	        	board.take_turn(players[i]);
	        	i++;
	        	if(i == players.length)
	        		i = 0;
	        }
	    }, delay, period);
	    board.game_over();
	}
	
	/*
	 * Parses the inputted .csv file to populate the game board with deeds
	 */
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
			in.next();
			String deed_type = in.next();
			
			deeds[i] = new Deed(position, name, property_group, color, price, mortgage_value, rent,
					rent1, rent2, rent3, rent4, rent_hotel, build_cost, deed_type);
		}
		//populate deeds giving csv_file
		return deeds;
	}
	
	/**
	 * Part of timer.
	 */
	private static final int setInterval() {
	    if (interval == 1)
	        timer.cancel();
	    return --interval;
	}
}
