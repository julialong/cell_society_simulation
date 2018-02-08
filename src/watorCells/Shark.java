package watorCells;

public class Shark extends Animal {
	private int health;
	private final int SHARKHEALTH = 10;
	private final int HEALTHGAIN = 5;

	public Shark() {
		health = SHARKHEALTH;
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
	public boolean isDead() {
		return SHARKHEALTH == 0;
	}
	
	@Override
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
