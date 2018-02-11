package cell_controllers;

import java.util.Map;

import cells.Cell;
import cells.GameOfLifeCell;

public class LifeCellController extends CellController {

	private static final String ON = "on";
	private static final String OFF = "default";

	// 2 maps and bololean

	public LifeCellController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap,
			boolean random) {

		super(dimensions, random);
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y] = new GameOfLifeCell(OFF);
			}
		}
		System.out.println(random);
		if (isRandom) {
			setUpRandom(paramMap);
		} else {
			setUpSpecific(map);
		}
		initializeNeighbors();
	}

	public void setUpRandom(Map<String, Double> paramMap) {

		double onrate = paramMap.get("onrate");
	
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				double rand = Math.random();
				if (rand < onrate) {
					cellGrid[x][y] = new GameOfLifeCell(ON);
				}
			}
		}
	}

	public void setUpSpecific(Map<String, int[][]> map) {

		int[][] cellsOn = map.get(ON);
		for (int z = 0; z < cellsOn.length; z++) {
			int xCoord = cellsOn[z][0];
			int yCoord = cellsOn[z][1];
			cellGrid[xCoord][yCoord] = new GameOfLifeCell(ON);
		}
	}

	public Cell getDefaultCell() {
		return new GameOfLifeCell(OFF);

	}

	public void setNextStates() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				GameOfLifeCell toSet = (GameOfLifeCell) retrieveCell(x, y);
				toSet.setNextState();
			}
		}
	}
}
