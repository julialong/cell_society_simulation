package cellsociety_team08;

public abstract class Animal {
	int xCoordinate;
	int yCoordinate;
	int timer;
	
	public Animal(int x, int y) {
		xCoordinate = x;
		yCoordinate = y;
	}

	
	public abstract String getType();

	
	// divide
	//move
	//die
}
