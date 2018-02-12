package cell_controllers;

import java.util.Map;


import cells.Cell;
import cells.RPSCell;
import javafx.scene.paint.Color;

public class RPSController extends CellController {
	private double greenRate;
	private double blueRate;
	private double redRate;
	private static final String GREEN = "green";
	private static final String BLUE = "blue";
	private static final String RED = "red";

	public RPSController(int[] dimensions,Map<String, int[][]> map, Map<String, Double> paramMap, 
			boolean random) {
		super(dimensions, random);
		if (isRandom) {
			setUpRandom(paramMap);
		} else {
			setUpSpecific(map);
		}
		initializeNeighbors();
	}

	@Override
	public void setUpRandom(Map<String, Double> paramMap) {
		greenRate = paramMap.get("greenRate");
		blueRate = paramMap.get("blueRate");
		redRate = paramMap.get("redRate");

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y] = generateRandomCell();
			}
		}
	}

	@Override
	public void setUpSpecific(Map<String, int[][]> map) {

		int[][] cellsGreen = map.get(GREEN);
		for (int z = 0; z < cellsGreen.length; z++) {
			int xCoord = cellsGreen[z][0];
			int yCoord = cellsGreen[z][1];
			cellGrid[xCoord][yCoord] = new RPSCell(Color.GREEN, GREEN);
		}

		int[][] cellsBlue = map.get(BLUE);
		for (int z = 0; z < cellsBlue.length; z++) {
			int xCoord = cellsBlue[z][0];
			int yCoord = cellsBlue[z][1];
			cellGrid[xCoord][yCoord] = new RPSCell(Color.BLUE, BLUE);
		}

		int[][] cellsRed = map.get(RED);
		for (int z = 0; z < cellsRed.length; z++) {
			int xCoord = cellsRed[z][0];
			int yCoord = cellsRed[z][1];
			cellGrid[xCoord][yCoord] = new RPSCell(Color.RED, RED);
		}
	}

	private RPSCell generateRandomCell() {
		RPSCell tempCell;
		double rand = Math.random();
		if (rand < greenRate) {
			tempCell = new RPSCell(Color.GREEN, GREEN);

		} else if (rand < greenRate + blueRate) {
			tempCell = new RPSCell(Color.BLUE, BLUE);

		} else if (rand < greenRate + blueRate + redRate) {
			tempCell = new RPSCell(Color.RED, RED);
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
				temp = (RPSCell) cellGrid[x][y];
				temp.infect(temp.retrieveRandomNeighbour());
			}
		}
	}
}
