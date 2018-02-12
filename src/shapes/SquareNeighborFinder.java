package shapes;

import cells.Cell;

/**
 * Neighbor Finder class, used to help find neighbors for cells
 * @author Edward Zhuang, Jeffrey Li
 *
 */
public class SquareNeighborFinder extends NeighborFinder {
	
	private static final int TOPLEFT = 0;
	private static final int TOP = 1;
	private static final int TOPRIGHT = 2;
	private static final int LEFT = 3;
	private static final int RIGHT = 4;
	private static final int BOTTOMLEFT = 5;
	private static final int BOTTOM = 6;
	private static final int BOTTOMRIGHT = 7;
	private static final int NUMBER_OF_NEIGHBOURS = 8;
	
	/**
	 * Constructor for SquareNeighborFinder
	 * @param inputCellGrid cellGrid which contains array of array of cells
	 * @param torroidal2 chooses whether cellGrid should be toroidal 
	 */
	public SquareNeighborFinder(Cell[][] inputCellGrid, Boolean torroidal2) {
		super(inputCellGrid, torroidal2);
	}
	/**
	 * initializes neighbors for each cell
	 */
	@Override
	public void initializeNeighbors() {		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				
				Cell[] tempArray = new Cell[NUMBER_OF_NEIGHBOURS];
				
				tempArray[TOPLEFT] = retrieveCell(x - 1, y - 1);
				tempArray[TOP] = retrieveCell(x - 1, y);
				tempArray[TOPRIGHT] = retrieveCell(x - 1, y + 1);
				tempArray[LEFT] = retrieveCell(x, y - 1);
				tempArray[RIGHT] = retrieveCell(x, y + 1);
				tempArray[BOTTOMLEFT] = retrieveCell(x + 1, y - 1);
				tempArray[BOTTOM] = retrieveCell(x + 1, y);
				tempArray[BOTTOMRIGHT] = retrieveCell(x + 1, y + 1);
				retrieveCell(x, y).addNeighbors(tempArray);
			}
		}
		
	}

}
