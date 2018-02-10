package cell_controllers;

import java.util.Map;

import cells.Cell;
import cells.GameOfLifeCell;



public class LifeCellController extends CellController {


	private static final String ON = "on";
	private static final String OFF = "default";

	
	public LifeCellController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap) {

		super(dimensions);
	
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y] = new GameOfLifeCell(OFF);
			}
		}
		double onrate = paramMap.get("onrate");

		int[][] cellsOn = map.get(ON);
		for (int z = 0; z < cellsOn.length; z++) {
			int xCoord = cellsOn[z][0];
			int yCoord = cellsOn[z][1];
			cellGrid[xCoord][yCoord] = new GameOfLifeCell(ON);
		}

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				double rand = Math.random();
				if (rand < onrate) {
					cellGrid[x][y] = new GameOfLifeCell(ON);
				}
			}
		}
		initializeNeighbors();
	}
	
	public Cell getDefaultCell() {
		Cell temp = new GameOfLifeCell(OFF);
		return temp;
	}
	

	// for each cell, check how many neighbors are on
	public void setNextStates() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				GameOfLifeCell toSet = (GameOfLifeCell) retrieveCell(x, y);
				toSet.setNextState();
			}
		}
	}
}
