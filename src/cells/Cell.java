package cells;

import javafx.scene.paint.Color;

public class Cell {
	protected String currentState;
	protected String nextState;
	protected Color colour;
	private final int NUMBER_OF_NEIGHBOURS = 8;
	
	public Cell[] neighbors; 
	 
    //  array of neighbor cells with specific order
	//  0 1 2
	//  3 x 4
	//  5 6 7
	

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
	
	public Color getColor() {
		return colour;
	}
	
	//  passes array neighbor cells with specific order
	//  0 1 2
	//  3 x 4
	//  5 6 7
	
	public void addNeighbors(Cell[] inputNeighbors) {		
			neighbors = inputNeighbors;
	}

	public String[] getNeighborStateNames() {
		String[] neighborStateNames = new String[NUMBER_OF_NEIGHBOURS];
		for (int x = 0; x < neighborStateNames.length; x++) {
			
			if (neighbors[x] != null) neighborStateNames[x] = neighbors[x].currentState;
		}
		return neighborStateNames;
	}
}