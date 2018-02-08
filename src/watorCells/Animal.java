package watorCells;

public abstract class Animal {
	protected int time;
	private final int TIME_TO_MULTIPLY = 10;

	public int getHealth() {
		return 1;
	}

	public void incrementTime() {
		time++;
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
