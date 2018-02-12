package cell_controllers;

import cells.Cell;

public abstract class NeighborFinder {
	private Cell[][] cellGrid;
	protected int xSize;
	protected int ySize;
	
	
	
	public NeighborFinder(Cell[][] inputCellGrid) {
		cellGrid = inputCellGrid;
		xSize = cellGrid.length;
		ySize = cellGrid[0].length;
	};
	
	public abstract void initializeNeighbors();
	
	public Cell[][] getCellGrid(){
		return cellGrid;
	}
	
	public Cell retrieveCell(int x, int y) {

//		 if (x < 0 || x >= xSize)
//		 return null;
//		 if (y < 0 || y >= ySize)
//		 return null;

		if (x < 0) {
			x = xSize-1;
		}
		if (x >= xSize) {
			x = 0;
		}
		if (y < 0) {
			y = ySize-1;
		}
		if (y >= ySize) {
			y = 0;
		}

		return cellGrid[x][y];
	}
	
}
