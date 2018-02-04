package cellsociety_team08;

import javafx.scene.paint.Color;

public class LifeCellController extends CellController {
	public LifeCellController(int[] dimensions, int[][] cellsOn) {
		super(dimensions, cellsOn);
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
			Cell tempCell = new	Cell("off");
			cellGrid[x][y] = tempCell;	
			tempCell.setState(Color.WHITE);
			}
		}
		
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
				Cell toSet = retrieveCell(x,y);
				String[] stateNameList = toSet.getNeighborStateNames();
				int numberOn = 0;
	
				for (int z = 0; z < stateNameList.length; z++) {
					if (stateNameList[z] != null) {
					
						if (stateNameList[z].equals("on")) {
							numberOn++;
						}
					}
				}

						if (toSet.getState().equals("on")) {
							if (numberOn < 2 || numberOn > 3) {
								toSet.nextState = "off"; // if cell is on and has either <2 or >3
																				// neighbors, then turn next state off
							}
							else {
								
								toSet.nextState = "on";
							}
						} else {
							if (numberOn == 3) {
								toSet.nextState = "on"; // if cell is off, and 3 cells around are on																	// on,
																			// then turn next state on
							}
							else {
								    // otherwise keep next state off
								toSet.nextState = "off";
							}
						}
						if (toSet.nextState.equals("on")) {
							toSet.setState(Color.BLACK);
						}
						else {
							toSet.setState((Color.WHITE));
						}
						
					
				}
			}
		}
	

	public void printCells() {
		for (int x = 0; x < cellGrid.length; x++) {
			System.out.println("");
			for (int y = 0; y < cellGrid[x].length; y++) {
				System.out.print(cellGrid[x][y].getState() + " " );
			}
		}
		System.out.println("");
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
