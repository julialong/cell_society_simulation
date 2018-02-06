package watorCells;

public abstract class Animal {
	protected int time;
	
	public int getHealth() {
		return 1;
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
