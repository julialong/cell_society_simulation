package cell_controllers;

import java.util.HashMap;
import java.util.Map;

import cells.Cell;
import javafx.scene.paint.Color;
import shapes.NeighborFinder;
import shapes.SquareNeighborFinder;

public abstract class CellController {

	protected Cell[][] cellGrid;
	protected int xSize;
	protected int ySize;
	protected Map<String, Map<Color, Integer>> data;
	protected boolean isRandom;
	protected boolean torroidal;

	/**
	 * 
	 * @param dimensions
	 *            the dimensions of the cellGrid
	 */
	public CellController(int[] dimensions, boolean random) {
		xSize = dimensions[0];
		ySize = dimensions[1];
		isRandom = random;
		torroidal = false;

		data = new HashMap<>();

		cellGrid = new Cell[xSize][ySize];
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell tempCell = new Cell("default");
				cellGrid[x][y] = tempCell;
				tempCell.setState(Color.WHITE);
			}
		}
	}
	
	public Map getData() {
		updateData();
		return data;
	}
	
	// this wouldn't be necessary after refactoring, as seen in the firecontroller class
	protected void updateData() {
		Color colour;
		String type;
		data = new HashMap<>();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell toGet = cellGrid[x][y];
				colour = toGet.getColor();
				type = toGet.getState();
				if (!data.containsKey(type)) {
					data.put(type, new HashMap<>());
					data.get(type).put(colour, 0);

				}

				data.get(type).put(colour, data.get(type).get(colour) + 1);
			}
		}
	}
	
	protected void initialValues() {
		Color colour;
		String type;
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell toGet = cellGrid[x][y];
				colour = toGet.getColor();
				type = toGet.getState();
				data.get(type).put(colour, data.get(type).get(colour) + 1);
			}
		}
	}
	
	public void increaseData(String type, Color colour) {
		data.get(type).put(colour, data.get(type).get(colour) + 1);
	}
	public void decreaseData(String type, Color colour) {
		data.get(type).put(colour, data.get(type).get(colour) -1);
	}

	public abstract void setUpSpecific(Map<String, int[][]> map);

	public abstract void setUpRandom(Map<String, Double> paramMap);

	// changes the size of cell grid

	public void resize(int dimensions) {
		if (dimensions < xSize)
			truncate(dimensions);
		if (dimensions > xSize)
			enlarge(dimensions);
	}

	// changes the size of the cell grid, used if desired dimensions are smaller
	// than current

	public void truncate(int dimensions) {
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

	public abstract Cell getDefaultCell();
	
	public void switchTorroidal() {
		torroidal = !torroidal;
		initializeNeighbors();
	}

	public void enlarge(int dimensions) {
		Cell[][] cellGrid2 = new Cell[dimensions][dimensions];
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				if (i < xSize && j < ySize) {
					cellGrid2[i][j] = cellGrid[i][j];
				} else {
					cellGrid2[i][j] = getDefaultCell();
				}
			}
		}

		cellGrid = cellGrid2;

		xSize = dimensions;
		ySize = dimensions;

		initializeNeighbors();
	}

	/**
	 * Goes through the cellGrid, and sets up the neighbors of all the cells. Cells
	 * have neighbors around them stored in an array, with this configuration: 0 1 2
	 * 3 x 4 5 6 7
	 */
	public void initializeNeighbors() {
		
		// create if statements to figure out with neighborfinder
		NeighborFinder finder = new SquareNeighborFinder(cellGrid, torroidal);
		finder.initializeNeighbors();
		cellGrid = finder.getCellGrid();
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
				colors[x][y] = cellGrid[x][y].getColor();
			}
		}
		return colors;
	}
	
	public abstract Map<String, int[][]> makeCellMap();
	public abstract void writeToXML(String filename); 

}
