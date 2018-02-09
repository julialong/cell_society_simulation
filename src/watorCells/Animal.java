package watorCells;

public abstract class Animal {

	protected int time;
	private final int TIME_TO_MULTIPLY = 10;

	public void incrementTime() {
		time++;
	}
	
	public boolean isDead() {
		return false;
	}

	public boolean timeToMultiply() {

		return ((time % TIME_TO_MULTIPLY) == 0);
	}

	public abstract String getType();

	public void decrementHealth() {
	};

	public void replenishHealth() {
	};
}
