package cellsociety_team08;

public class CellController {

	private Cell[][] cellGrid;
	
	
	/**
	 * 
	 * @param dimensions the dimensions of the cellGrid
	 * @param cellsOn contains a list of cells which should be on (default cell config is off)
	 */
	public CellController(int[] dimensions, int[][] cellsOn) {
		int xCells = dimensions[0];
		int yCells = dimensions[1];
		
		cellGrid = new Cell[xCells][yCells];
		
		for (int x = 0; x < xCells; x++) {
			for (int y = 0; y < yCells; y++) {
			State tempState = new State("off");
			Cell tempCell = new	Cell(tempState);
			cellGrid[x][y] = tempCell;					
			}
		}
		
		for (int z = 0; z < cellsOn.length; z++) {			
			int xCoord = cellsOn[z][0];
			int yCoord = cellsOn[z][1];
			State tempState = new State("on");
			cellGrid[xCoord][yCoord] = new Cell(tempState);
			
		}			
		
	}
	
	/**
	 *  Goes through the cellGrid, and sets up the neighbors of all the cells.
	 *  Cells have neighbors around them stored in an array, with this configuration:
	 *  0 1 2
	 *  3   4 
	 *  5 6 7 
	 */
	public void includeNeighbors() {
		for (int x = 0; x < cellGrid.length; x++) {
			for (int y = 0; y < cellGrid[x].length; y++) {

				Cell[] tempArray = new Cell[8];
				tempArray[0] = retrieveCell(x-1, y-1);
				tempArray[1] = retrieveCell(x-1, y);
				tempArray[2] = retrieveCell(x-1, y+1);
				tempArray[3] = retrieveCell(x, y-1);
				tempArray[4] = retrieveCell(x, y+1);
				tempArray[5] = retrieveCell(x+1, y-1);
				tempArray[6] = retrieveCell(x+1, y);
				tempArray[7] = retrieveCell(x+1, y+1);		
				cellGrid[x][y].addNeighbors(tempArray);
				
			}
		}
	}
	
	
	/**
	 * Checks to see if a suggested neighbor is in bounds
	 * @param x x-coordinate of neighbor cell in grid 
	 * @param y y-coordinate of neighbor cell in grid
	 * @return returns the cell at that specific coordinate (if it's in bounds, null otherwise)
	 */
	public Cell retrieveCell(int x, int y) {
		if (x < 0 || x >= cellGrid.length) return null;
		if (y < 0 || y >= cellGrid[x].length) return null;
		return cellGrid[x][y];
	}
	
	
	
	
}
