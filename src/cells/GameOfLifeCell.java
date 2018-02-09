package cells;

import javafx.scene.paint.Color;

public class GameOfLifeCell extends Cell {

	private static final String ON = "on";
	private static final String OFF = "default";
	private static final int MIN_NEIGHBOUR = 2;
	private static final int MAX_NEIGHBOUR = 3;

	public GameOfLifeCell(String onOrOff) {
		super(onOrOff);
		currentState = onOrOff;
		if (onOrOff == ON) {
			setOn();
		}
		else {
			setOff();
		}
	}

	public void setNextState() {
		String[] stateNameList = getNeighborStateNames();
		int numberOn = calcNumbersOn(stateNameList);

		if (getState().equals(ON)) {
			if (numberOn < MIN_NEIGHBOUR || numberOn > MAX_NEIGHBOUR) {
				setOff();
			} else {
				setOn();
			}
		} else {
			if (numberOn == MAX_NEIGHBOUR) {
				setOn();
			} else {
				setOff();
			}
		}
	}
	
	private void setOn() {
		colour = Color.WHITE;
		nextState = ON;
	}
	
	private void setOff() {
		colour = Color.BLACK;
		nextState = OFF;
	}

	private int calcNumbersOn(String[] neighbourStates) {
		int numberOn = 0;

		for (int z = 0; z < neighbourStates.length; z++) {
			if (neighbourStates[z] != null) {

				if (neighbourStates[z].equals(ON)) {
					numberOn++;
				}
			}
		}
		return numberOn;
	}

}
