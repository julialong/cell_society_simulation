package cell_controllers;

import java.util.Map;

import cells.Cell;
import javafx.scene.paint.Color;

public class FireController extends CellController {

	private double catchProbability;
	private static final String TREE = "tree";
	private static final String FIRE = "fire";
	private static final String DEFAULT = "default";

	public FireController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap) {
		super(dimensions);
		int[][] cellsOnFire =  map.get("burning");
		
		catchProbability = paramMap.get("probability");
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell tempCell = new Cell(TREE);
				cellGrid[x][y] = tempCell;
				tempCell.setState(Color.GREEN);
			}
		}
		
		for (int x = 0; x < cellsOnFire.length; x++) {
			int xCoord = cellsOnFire[x][0];
			int yCoord = cellsOnFire[x][1];
			cellGrid[xCoord][yCoord] = new Cell(FIRE);
			cellGrid[xCoord][yCoord].setState(Color.RED);
		}
		initializeNeighbors();
		
	}

	@Override
	public void setNextStates() {
		for (int x = 0; x < this.xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell toSet = retrieveCell(x, y);
				String toSetType = toSet.getState();

				if (toSetType.equals(DEFAULT)) {
					toSet.setNextStateDefault();
				}

				else if (toSetType.equals(FIRE)) {
					toSet.setNextStateDefault();
				}

				else if (toSetType.equals(TREE)) {
					if (fireBeside(toSet) && catchResult()) {
						toSet.setNextState(FIRE);
						toSet.setState(Color.ORANGERED);
					} else {
						toSet.setNextState(TREE);
					}
				}
			}
		}
	}

	private boolean catchResult() {
		return Math.random() < catchProbability;
	}

	private boolean fireBeside(Cell cell) {
		for (String state : cell.getNeighborStateNames()) {
			if (state != null) {
			if (state.equals(FIRE)) {
				return true;
			}
			}
		}
		return false;
	}

	@Override
	public Cell getDefaultCell() {
		Cell temp = new Cell(TREE);
		temp.setState(Color.GREEN);
		return temp;
	}

}
