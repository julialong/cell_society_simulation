package cells;

import java.util.Random;

import javafx.scene.paint.Color;

/**
 * simulation based on rock paper scissors hierachy. 
 * all coloured cells can infect lower level cells, but they can only infect other coloured cells if they fall below them in the hierachy
 * @author jeffreyli, edwardzhuang
 * 
 */
public class RPSCell extends Cell {
	private int level;
	private int nextLevel;
	private Color nextColour;
	private static final String WHITE = "default";
	private static final String RED = "red";
	private static final String BLUE = "blue";
	private static final String GREEN = "green";

	public RPSCell() {
		super(WHITE);
		level = 10;
		colour = Color.WHITE;
		nextColour = Color.WHITE;
		nextLevel = 10;
	}

	public RPSCell(Color color, String colorString) {
		super(colorString);
		level = 1;
		nextColour = color;
		colour = color;
		nextLevel = 1;
	}
	
	/**
	 * infects the neighbouring cell if required
	 * @param neighbour to infect
	 */
	public void infect(RPSCell neighbour) {
		if (level < 9) {
			if (edible(neighbour)) {
				if (neighbour.currentState.equals(WHITE)) {
					neighbour.changeTo(colour, currentState, level + 1);
				} else {
					neighbour.changeTo(colour, currentState, 1);
				}
			}
		}
	}
	
	/**
	 * checks if neighbour is infectable given the RPS hierachy
	 * @param neighbour to check
	 * @return 
	 */
	public boolean edible(RPSCell neighbour) {
		if (neighbour.currentState.equals(WHITE)) {
			return true;
		}
		if (currentState.equals(RED)) {
			return (neighbour.currentState.equals(BLUE));
		} else if (currentState.equals(BLUE)) {
			return (neighbour.currentState.equals(GREEN));
		} else if (currentState.equals(GREEN)) {
			return (neighbour.currentState.equals(RED));
		}
		return false;
	}
	
	/**
	 * gets a random neighbour
	 * @return
	 */
	public RPSCell retrieveRandomNeighbour() {
		Random rand = new Random();
		int index = rand.nextInt(8);
		return (RPSCell) neighbors[index];

	}

	public void changeTo(Color color, String colorString, int level) {
		nextColour = color;
		nextState = colorString;
		nextLevel = level + 1;
	}

	@Override
	public void updateState() {
		currentState = nextState;
		level = nextLevel;
		colour = nextColour;
	}

}
