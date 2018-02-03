package cellsociety_team08;

import java.util.ArrayList;

public class Cell {
	State currentState;
	State nextState;
//	Cell left;
//	Cell right;
//	Cell top;
//	Cell bottom;
//	Cell topLeft;
//	Cell topRight;
//	Cell bottomLeft;
//	Cell bottomRight;
	
	Cell[] neighbors; 
	 
    //  array of neighbor cells with specific order
	//  0 1 2
	//  3 x 4
	//  5 6 7
	

	public Cell(State state) {
		currentState = state;
	}

	public void updateState() {
		currentState = nextState; 
	}
	
	public void setNextState(State state) {
		nextState = state;
	}
	
	public State getState() {
		return currentState;
	}
	
	//  passes array neighbor cells with specific order
	//  0 1 2
	//  3 x 4
	//  5 6 7

	
	public void addNeighbors(Cell[] inputNeighbors) {		
			neighbors = inputNeighbors;
	}

	public String[] getNeighborStateNames() {
		String[] neighborStateNames = new String[8];
		for (int x = 0; x < neighborStateNames.length; x++) {
			if (neighbors[x] != null) neighborStateNames[x] = neighbors[x].getState().getStateName();
		}
		return neighborStateNames;
	}
	
//	public ArrayList<String> getNeighbourStates() {
//		ArrayList<String> neighbours = new ArrayList<String>();
//		neighbours.add(left.currentState.getState());
//		neighbours.add(top.currentState.getState());
//		neighbours.add(bottom.currentState.getState());
//		neighbours.add(right.currentState.getState());
//		return neighbours;
//	}

}
