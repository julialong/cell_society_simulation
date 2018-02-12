package cellsociety_team08;

import cells.Cell;

/**
 * Neighbor Finder class, used to help find neighbors for cells
 * @author Edward Zhuang, Jeffrey Li
 *
 */
public abstract class NeighborFinder {
	private Cell[][] cellGrid;
	protected int xSize;
	protected int ySize;
	boolean torroidal;
	
	
	/**
	 * Constructor for NeighborFinder
	 * @param inputCellGrid cellGrid which contains array of array of cells
	 * @param torroidal2 chooses whether cellGrid should be toroidal 
	 */
	public NeighborFinder(Cell[][] inputCellGrid, Boolean torroidal2) {
		cellGrid = inputCellGrid;
		xSize = cellGrid.length;
		ySize = cellGrid[0].length;
		torroidal = torroidal2;
	};
	
	/**
	 * initializes neighbors for each cell
	 */
	public abstract void initializeNeighbors();
	
	/**
	 *  gets Cell Grid
	 * @return cellGrid
	 */
	public Cell[][] getCellGrid(){
		return cellGrid;
	}
	
	/**
	 * Retrieves a cell from cell grid
	 * @param x x coordinate of cell
	 * @param y y coordinate of cell
	 * @return
	 */
	public Cell retrieveCell(int x, int y) {
	
	if (!torroidal) {
		 if (x < 0 || x >= xSize)
		 return null;
		 if (y < 0 || y >= ySize)
		 return null;	
	}
	if (x < 0) {
		x = xSize - 1;
	}
	if (x >= xSize) {
		x = 0;
	}
	if (y < 0) {
		y = ySize - 1;
	}
	if (y >= ySize) {
		y = 0;
	}

	return cellGrid[x][y];
}
	
}
