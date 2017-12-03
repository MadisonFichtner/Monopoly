package src;

public class Card {
	public String name;
	public int type;
	public boolean isUsable;
	
	public Card(String name, int type) {
		this.name = name;
		this.type = type;
		if(name.equals("Get Out of Jail Free"))
			isUsable = true;
	}
}
