package shapes;

import cells.Cell;

/**
 * Neighbor Finder class, used to help find neighbors for cells
 * @author Edward Zhuang, Jeffrey Li
 *
 */
public class HexagonNeighborFinder extends NeighborFinder {

	private static final int TOPLEFT = 0;
	private static final int TOPRIGHT = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;
	private static final int BOTTOMLEFT = 4;
	private static final int BOTTOMRIGHT = 5;
	private static final int NUMBER_OF_NEIGHBOURS = 6;

	/**
	 * Constructor for HexagonNeighborFinder
	 * 
	 * @param inputCellGrid
	 *            cellGrid which contains array of array of cells
	 * @param torroidal2
	 *            chooses whether cellGrid should be toroidal
	 */
	public HexagonNeighborFinder(Cell[][] inputCellGrid, boolean torroidal2) {
		super(inputCellGrid, torroidal2);
	}
	/**
	 * initializes neighbors for each cell
	 */
	@Override
	public void initializeNeighbors() {

		boolean longRow = true;

		for (int x = 0; x < xSize; x++) {

			for (int y = 0; y < ySize; y++) {

				Cell[] tempArray = new Cell[NUMBER_OF_NEIGHBOURS];

				tempArray[LEFT] = retrieveCell(x, y - 1);
				tempArray[RIGHT] = retrieveCell(x, y + 1);

				if (longRow) {
					tempArray[TOPLEFT] = retrieveCell(x - 1, y - 1);
					tempArray[TOPRIGHT] = retrieveCell(x - 1, y);
					tempArray[BOTTOMLEFT] = retrieveCell(x + 1, y - 1);
					tempArray[BOTTOMRIGHT] = retrieveCell(x + 1, y);
				} else {
					tempArray[TOPLEFT] = retrieveCell(x - 1, y);
					tempArray[TOPRIGHT] = retrieveCell(x - 1, y + 1);
					tempArray[BOTTOMLEFT] = retrieveCell(x + 1, y);
					tempArray[BOTTOMRIGHT] = retrieveCell(x + 1, y + 1);
				}

				retrieveCell(x, y).addNeighbors(tempArray);

				longRow = !longRow;
			}
		}
	}

}
