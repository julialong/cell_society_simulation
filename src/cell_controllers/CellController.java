package cell_controllers;

import cellsociety_team08.Cell;
import javafx.scene.paint.Color;

public abstract class CellController {

	public Cell[][] cellGrid;
	protected int xSize;
	protected int ySize;

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

	/**
	 * Goes through the cellGrid, and sets up the neighbors of all the cells. Cells
	 * have neighbors around them stored in an array, with this configuration: 0 1 2
	 * 3 4 5 6 7
	 */
	public void initializeNeighbors() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				Cell[] tempArray = new Cell[8];
				tempArray[0] = retrieveCell(x - 1, y - 1);
				tempArray[1] = retrieveCell(x - 1, y);
				tempArray[2] = retrieveCell(x - 1, y + 1);
				tempArray[3] = retrieveCell(x, y - 1);
				tempArray[4] = retrieveCell(x, y + 1);
				tempArray[5] = retrieveCell(x + 1, y - 1);
				tempArray[6] = retrieveCell(x + 1, y);
				tempArray[7] = retrieveCell(x + 1, y + 1);
				cellGrid[x][y].addNeighbors(tempArray);
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

	public void printCells() {
		for (int x = 0; x < cellGrid.length; x++) {
			System.out.println("");
			for (int y = 0; y < cellGrid[x].length; y++) {
				System.out.print(cellGrid[x][y].getState() + " ");
			}
		}
		System.out.println("");
	}
}
