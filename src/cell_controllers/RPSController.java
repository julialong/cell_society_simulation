package cell_controllers;

import java.util.Map;

import cells.Cell;
import cells.RPSCell;
import javafx.scene.paint.Color;

public class RPSController extends CellController {
	private double greenRate;
	private double blueRate;
	private double redRate;

	public RPSController(int[] dimensions, Map<String, Double> paramMap) {
		super(dimensions);
		greenRate = paramMap.get("greenRate");
		blueRate = paramMap.get("blueRate");
		redRate = paramMap.get("redRate");

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y] = generateRandomCell();
			}
		}
		initializeNeighbors();
	}

	private RPSCell generateRandomCell() {
		RPSCell tempCell;
		double rand = Math.random();
		if (rand < greenRate) {
			tempCell = new RPSCell(Color.GREEN, "green");

		} else if (rand < greenRate + blueRate) {
			tempCell = new RPSCell(Color.BLUE, "blue");

		} else if (rand < greenRate + blueRate + redRate) {
			tempCell = new RPSCell(Color.RED, "red");
		} else {
			tempCell = new RPSCell(Color.WHITE, "default");
		}
		return tempCell;
	}

	@Override
	public Cell getDefaultCell() {
		return generateRandomCell();
	}

	@Override
	public void setNextStates() {
		// TODO Auto-generated method stub
		RPSCell temp;
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				temp = (RPSCell) retrieveCell(x,y);
				temp.infect(temp.retrieveRandomNeighbour());
			}
		}
	}

}
