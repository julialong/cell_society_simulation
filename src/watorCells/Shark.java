package watorCells;

public class Shark extends Animal {
	private int health;
	private final int SHARKHEALTH = 10;
	private final int HEALTHGAIN = 5;

	@Override
	public int getHealth() {
		return health;
	}

	public Shark() {
		health = SHARKHEALTH;
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
		health += HEALTHGAIN;
	};
}
