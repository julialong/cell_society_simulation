package watorCells;

public class Shark extends Animal{
	int health;
	
	@Override
	public int getHealth() {
		return health;
	}
	
	public Shark() {
		health = 10;
	}
	
	public String getType() {
		return "shark";
	}
	@Override
	public void decrementHealth() {
		System.out.println(health);
		health--;
	}
	@Override
	public void replenishHealth() {
		health+=5;
	};
}
