package cellsociety_team08;

import javafx.scene.paint.Color;

public class Cell {
	private String currentState;
	private String nextState;
	protected Color colour;
	private final int NUMBER_OF_NEIGHBOURS = 8;
	private Cell[] neighbors;

	// array of neighbor cells with specific order
	// 0 1 2
	// 3 x 4
	// 5 6 7

	public Cell(String state) {
		currentState = state;
		nextState = state;
	}

	public void updateState() {
		currentState = nextState;
	}

	public void setNextState(String state) {
		nextState = state;
	}

	public void setState(Color newColour) {
		colour = newColour;
	}

	public void setNextStateDefault() {
		colour = Color.WHITE;
		nextState = "default";
	}

	public String getState() {
		return currentState;
	}

	public String getNextState() {
		return nextState;
	}

	public Color getColor() {
		return colour;
	}

	public String[] getNeighborStateNames() {
		String[] neighborStateNames = new String[NUMBER_OF_NEIGHBOURS];
		for (int x = 0; x < neighborStateNames.length; x++) {

			if (getNeighbors()[x] != null)
				neighborStateNames[x] = getNeighbors()[x].currentState;
		}
		return neighborStateNames;
	}

	public Cell[] getNeighbors() {
		return neighbors;
	}
	// passes array neighbor cells with specific order
	// 0 1 2
	// 3 x 4
	// 5 6 7
	public void addNeighbors(Cell[] neighbors) {
		this.neighbors = neighbors;
	}
}