package watorCells;

public abstract class Animal {
	public int health = 10;
	protected int time = 0;
	
	public int getHealth() {
		return health;
	}
	
	public void incrementTime() {
		time++;
	}
	
	public int getTime() {
		return time;
	}
	
	public abstract String getType();
	public void decrementHealth() {
	};
	public void replenishHealth() {
	};
}
