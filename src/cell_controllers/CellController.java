package cell_controllers;

import java.util.Arrays;

import cells.Cell;
import javafx.scene.paint.Color;

public abstract class CellController {

	protected Cell[][] cellGrid;
	protected int xSize;
	protected int ySize;


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
	 * 
	 * @param dimensions
	 *            the dimensions of the cellGrid
	 * @param cellsOn
	 *            contains a list of cells which should be on (default cell config
	 *            is off)
	 */
	public CellController(int[] dimensions) {
		xSize = dimensions[0];
		ySize = dimensions[1];
		
		cellGrid = new Cell[xSize][ySize];
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell tempCell = new Cell("default");
				cellGrid[x][y] = tempCell;
				tempCell.setState(Color.WHITE);
			}
		}
	}

	// changes the size of cell grid
	
	public void resize(int dimensions) {
		if (dimensions < xSize) truncate(dimensions);
		if (dimensions > xSize) enlarge(dimensions);
	} 
	
	// changes the size of the cell grid, used if desired dimensions are smaller than current
	
	public void truncate(int dimensions){
		Cell[][] cellGrid2 = new Cell[dimensions][dimensions];
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {				
				cellGrid2[i][j] = cellGrid[i][j];
				
			}
		}
		cellGrid = cellGrid2;
		
		xSize = dimensions;
		ySize = dimensions;
	}
	
	public void enlarge(int dimensions) {
		
	}
	
	
	/**
	 * Goes through the cellGrid, and sets up the neighbors of all the cells. Cells
	 * have neighbors around them stored in an array, with this configuration:
	 *  0 1 2
	 *  3 x 4 
	 * 	5 6 7
	 */
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
				
				
				retrieveCell(x,y).addNeighbors(tempArray);

			}
			
		}
	}

	/**
	 * Checks to see if a suggested neighbor is in bounds
	 * 
	 * @param x
	 *            x-coordinate of neighbor cell in grid
	 * @param y
	 *            y-coordinate of neighbor cell in grid
	 * @return returns the cell at that specific coordinate (if it's in bounds, null
	 *         otherwise)
	 */
	public Cell retrieveCell(int x, int y) {
		if (x < 0 || x >= cellGrid.length)
			return null;
		if (y < 0 || y >= cellGrid[x].length)
			return null;
		return cellGrid[x][y];
	}

	public abstract void setNextStates();

	public void updateCells() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y].updateState();
			}
		}
	}

	public Color[][] getColors() {
		Color[][] colors = new Color[xSize][ySize];
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				colors[x][y] = retrieveCell(x, y).getColor();
			}
		}
		return colors;
	}

}
