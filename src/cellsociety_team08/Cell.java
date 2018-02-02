package cellsociety_team08;

import java.util.ArrayList;

public class Cell {
	State currentState;
	State nextState;
	Cell left;
	Cell right;
	Cell top;
	Cell bottom;

	public Cell(State state) {
		currentState = state;
	}

	public Cell(State state, Cell Top, Cell Right, Cell Bottom, Cell Left) {
		this(state);
		left = Left;
		right = Right;
		top = Top;
		bottom = Bottom;
	}

	public void updateState() {
		currentState = nextState; //Merge Test 1232345
	}

	public ArrayList<String> getNeighbourStates() {
		ArrayList<String> neighbours = new ArrayList<String>();
		neighbours.add(left.currentState.getState());
		neighbours.add(top.currentState.getState());
		neighbours.add(bottom.currentState.getState());
		neighbours.add(right.currentState.getState());
		return neighbours;
	}

}
