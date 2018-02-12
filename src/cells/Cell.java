package cells;

import java.util.Arrays;

import javafx.scene.paint.Color;
/**
 * Core part of the simulation, holds information regarding states and colours to be projected
 * @author jeffreyli, edwardzhuang
 *
 */
public class Cell {
	protected String currentState;
	protected String nextState;
	protected Color colour;
	private static final int NUMBER_OF_NEIGHBOURS = 8;
	protected Cell[] neighbors;

	// array of neighbor cells with specific order
	// 0 1 2
	// 3 x 4
	// 5 6 7
	
	/**
	 * 
	 * @param state = state of cell
	 */
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
	
	/**
	 * sets the next state as default cell
	 */
	public void setNextStateDefault() {
		colour = Color.WHITE;
		nextState = "default";
	}

	public String getState() {
		return currentState;
	}


	public Color getColor() {
		return colour;
	}
	
	
	/**
	 * retrieves neighbours state names
	 * @return String array of neighbour state names
	 */
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