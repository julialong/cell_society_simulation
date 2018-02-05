package cellsociety_team08;

import javafx.scene.paint.Color;

public class FireController extends CellController {

	float catchProbability;

	public FireController(int[] dimensions, int[][] cellsOnFire, float probability) {
		super(dimensions);
		catchProbability = probability;
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell tempCell = new Cell("tree");
				cellGrid[x][y] = tempCell;
				tempCell.setState(Color.GREEN);
			}
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
		for (int x = 0; x < xSize; x++) {
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

	public boolean catchResult() {
		return Math.random() < catchProbability;
	}

	public boolean fireBeside(Cell cell) {
		for (String state : cell.getNeighborStateNames()) {
			if (state.equals("fire")) {
				return true;
			}
		}
		return false;
	}

}
