package cellsociety_team08;

import java.io.File;
import java.util.Map;

import javafx.scene.paint.Color;

public class LifeCellController extends CellController {
	public LifeCellController(int[] dimensions, Map<String, int[][]> map) {
		super(dimensions);
		int[][] cellsOn =  map.get("on");
		for (int z = 0; z < cellsOn.length; z++) {
			int xCoord = cellsOn[z][0];
			int yCoord = cellsOn[z][1];
			cellGrid[xCoord][yCoord] = new Cell("on");
			cellGrid[xCoord][yCoord].setState(Color.BLACK);

		}
		initializeNeighbors();
	}

	// for each cell, check how many neighbors are on
	public void setNextStates() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell toSet = retrieveCell(x, y);
				String[] stateNameList = toSet.getNeighborStateNames();
				int numberOn = calcNumbersOn(stateNameList);

				if (toSet.getState().equals("on")) {
					if (numberOn < 2 || numberOn > 3) {
						toSet.setNextStateDefault();
						; // if cell is on and has either <2 or >3
							// neighbors, then turn next state off
					} else {
						toSet.setNextState("on");
					}
				} else {
					if (numberOn == 3) {
						toSet.setNextState("on");
						; // if cell is off, and 3 cells around are on // on,
							// then turn next state on
					} else {
						// otherwise keep next state off
						toSet.setNextStateDefault();
						;
					}
				}
				if (toSet.getNextState().equals("on")) {
					toSet.setState(Color.BLACK);
				}
			}
		}
	}
	public int calcNumbersOn(String[] neighbourStates) {
		int numberOn = 0;
		
		for (int z = 0; z < neighbourStates.length; z++) {
			if (neighbourStates[z] != null) {
			
				if (neighbourStates[z].equals("on")) {
					numberOn++;
				}
			}
		}
		return numberOn;
	}

	
//	public static void main(String[] args) {
//		ParserXML parser = new ParserXML("life.xml");
//		LifeCellController lifeCellController = new LifeCellController(parser.getDimensions(), parser.getCellList());
//		lifeCellController.printCells();
//		lifeCellController.setNextStates();
//		lifeCellController.updateCells();
//		lifeCellController.printCells();
//		lifeCellController.setNextStates();
//		lifeCellController.updateCells();
//		lifeCellController.printCells();
//	}

}
