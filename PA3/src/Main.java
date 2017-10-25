import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println("How many players? ");
		int users = in.nextInt();
	
		Player[] players = new Player[users];
		for(int i = 0; i < users; i++) {
			System.out.println("Please enter a name for player 1: ");
			String name = in.nextLine();
			System.out.println("Please select a token for player 1: ");
			String token = in.nextLine();

			players[i] = new Player(name, token);
		}
		Deed[] new_board = new Deed[40];
		
		
		String fileName = "monopoly.csv"; //just a sample file name
		File csvFile = new File(fileName);
		BufferedReader reader = new BufferedReader(new FileReader(csvFile));
		new_board = parse_CSV(csvFile);
		
		Board board = new Board(players, new_board);
	}
	
	//parse_CSV needs to read in contents of .csv and populate an arry of deeds
	public static Deed[] parse_CSV(File csv_file) {
		Deed[] deeds = new Deed[40];
		//populate deeds giving csv_file
		return deeds;
	}
}
