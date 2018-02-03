package cellsociety_team08;

public class LifeCellController extends CellController {
	public LifeCellController(int[] dimensions, int[][] cellsOn) {
		super(dimensions, cellsOn);
	}

	// for each cell, check how many neighbors are on
	public void setNextStates() {
		for (int x = 0; x < cellGrid.length; x++) {
			for (int y = 0; y < cellGrid[x].length; y++) {
				String[] stateNameList = cellGrid[x][y].getNeighborStateNames();
				int numberOn = 0;
	
				for (int z = 0; z < stateNameList.length; z++) {
					if (stateNameList[z] != null) {
					
						if (stateNameList[z].equals("on")) {
							numberOn++;
						}
					}
				}

						if (cellGrid[x][y].getState().getStateName().equals("on")) {
							if (numberOn < 2 || numberOn > 3) {
								State temp = new State("off");
								cellGrid[x][y].nextState = temp; // if cell is on and has either <2 or >3
																				// neighbors, then turn next state off
							}
							else {
								State temp = new State("on");    // otherwise keep next state on
								cellGrid[x][y].nextState = temp;
							}
						} else {
							if (numberOn == 3) {
								State temp = new State("on");
								cellGrid[x][y].nextState = temp; // if cell is off, and 3 cells around are on																	// on,
																			// then turn next state on
							}
							else {
								State temp = new State("off");    // otherwise keep next state off
								cellGrid[x][y].nextState = temp;
							}
						}
					
				}
			}
		}
	

	public void updateCells() {
		for (int x = 0; x < cellGrid.length; x++) {
			for (int y = 0; y < cellGrid[x].length; y++) {
				cellGrid[x][y].updateState();
				
			}
		}
	}

	public void printCells() {
		for (int x = 0; x < cellGrid.length; x++) {
			System.out.println("");
			for (int y = 0; y < cellGrid[x].length; y++) {
//				System.out.print(x + " " + y);
				System.out.print(cellGrid[x][y].getState().getStateName() + " ");
			}
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		ParserXML parser = new ParserXML("life.xml");
		LifeCellController lifeCellController = new LifeCellController(parser.getDimensions(), parser.getCellList());
		lifeCellController.printCells();
		lifeCellController.setNextStates();
		lifeCellController.updateCells();
		lifeCellController.printCells();
		lifeCellController.setNextStates();
		lifeCellController.updateCells();
		lifeCellController.printCells();
	}

}
