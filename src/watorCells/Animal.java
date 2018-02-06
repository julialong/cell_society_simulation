package watorCells;

public abstract class Animal {
	protected int health;
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
