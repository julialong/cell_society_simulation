package shapes;

import cells.Cell;

public class TriangleNeighborFinder extends NeighborFinder {

	private static final int TOPLEFT = 0;
	private static final int TOP = 1;
	private static final int TOPRIGHT = 2;
	private static final int FARLEFT = 3;
	private static final int LEFT = 4;
	private static final int RIGHT = 5;
	private static final int FARRIGHT = 6;
	private static final int BOTTOMLEFT = 7;
	private static final int BOTTOM = 8;
	private static final int BOTTOMRIGHT = 9;
	private static final int EXTRA1 = 10;
	private static final int EXTRA2 = 11;
	private static final int NUMBER_OF_NEIGHBOURS = 12;
	private boolean down = true;
	
	public TriangleNeighborFinder(Cell[][] inputCellGrid) {
		super(inputCellGrid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeNeighbors() {
		for (int x = 0; x < xSize; x++) {
			
			if (x % 2 == 0) down = true;
			
			for (int y = 0; y < ySize; y++) {

				
				Cell[] tempArray = new Cell[NUMBER_OF_NEIGHBOURS];

				tempArray[TOPLEFT] = retrieveCell(x - 1, y - 1);
				tempArray[TOP] = retrieveCell(x - 1, y);
				tempArray[TOPRIGHT] = retrieveCell(x - 1, y + 1);
				tempArray[FARLEFT] = retrieveCell(x, y - 2);
				tempArray[LEFT] = retrieveCell(x, y - 1);
				tempArray[RIGHT] = retrieveCell(x, y + 1);
				tempArray[FARRIGHT] = retrieveCell(x, y + 2);
				tempArray[BOTTOMLEFT] = retrieveCell(x + 1, y - 1);
				tempArray[BOTTOM] = retrieveCell(x + 1, y);
				tempArray[BOTTOMRIGHT] = retrieveCell(x + 1, y + 1);
				
				if (down) { // if downwards triangle, it will have 2 more neighbors from top 2 vertices
					tempArray[EXTRA1] = retrieveCell(x - 1, y - 2);
					tempArray[EXTRA2] = retrieveCell(x - 1, y + 2);
				}
				else {
					tempArray[EXTRA1] = retrieveCell(x + 1, y - 2);
					tempArray[EXTRA2] = retrieveCell(x + 1, y + 2);
				}
				
				retrieveCell(x, y).addNeighbors(tempArray);
				
				down = !down;
				
			}
		}
		
	}

}
