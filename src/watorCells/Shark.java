package watorCells;

public class Shark extends Animal{
	int health;
	
	public Shark() {
		health = 10;
	}
	
	public String getType() {
		return "shark";
	}
	@Override
	public void decrementHealth() {
		health--;
	}
	@Override
	public void replenishHealth() {
		health+=5;
	};
}
