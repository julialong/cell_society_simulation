package cell_controllers;

import cellsociety_team08.Cell;
import javafx.scene.paint.Color;

public class FireController extends CellController {

	double catchProbability;

	public FireController(int[] dimensions, int[][] cellsOnFire, int[][] cellsTree, double probability) {
		super(dimensions);
		catchProbability = probability;
		for (int i = 0; i < cellsTree.length; i++) {
			int xCoord = cellsOnFire[i][0];
			int yCoord = cellsOnFire[i][1];
			cellGrid[xCoord][yCoord] = new Cell("tree");
			cellGrid[xCoord][yCoord].setState(Color.GREEN);
		}
		
		for (int x = 0; x < cellsOnFire.length; x++) {
			int xCoord = cellsOnFire[x][0];
			int yCoord = cellsOnFire[x][1];
			cellGrid[xCoord][yCoord] = new Cell("fire");
			cellGrid[xCoord][yCoord].setState(Color.RED);
		}
	}

	@Override
	public void setNextStates() {
		for (int x = 0; x < this.xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell toSet = retrieveCell(x, y);
				String toSetType = toSet.getState();

				if (toSetType.equals("default")) {
					toSet.setNextStateDefault();
				}

				else if (toSetType.equals("fire")) {
					toSet.setNextStateDefault();
				}

				else if (toSetType.equals("tree")) {
					if (fireBeside(toSet) && catchResult()) {
						toSet.setNextState("fire");
						toSet.setState(Color.ORANGERED);
					} else {
						toSet.setNextState("tree");
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
			if (state.equals("fire")) {
				return true;
			}
		}
		return false;
	}

}
