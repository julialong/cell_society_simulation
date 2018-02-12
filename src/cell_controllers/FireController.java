package cell_controllers;

import java.util.HashMap;
import java.util.Map;

import cells.Cell;
import javafx.scene.paint.Color;

public class FireController extends CellController {

	private double catchProbability;
	private static final String TREE = "tree";
	private static final String FIRE = "fire";
	private static final String DEFAULT = "default";

	public FireController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap, boolean random) {
		super(dimensions, random);
		catchProbability = paramMap.get("probability");
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell tempCell = new Cell(TREE);
				cellGrid[x][y] = tempCell;
				tempCell.setState(Color.GREEN);
			}
		}

		if (isRandom) {
			setUpRandom(paramMap);
		} else {
			setUpSpecific(map);
		}
		initializeNeighbors();
		initializeData();
	}
	
	public void initializeData() {

		data = new HashMap<>();
		data.put(DEFAULT, new HashMap<>());
		data.get(DEFAULT).put(Color.WHITE, 0);
		data.put(TREE, new HashMap<>());
		data.get(TREE).put(Color.GREEN, 0);
		data.put(FIRE, new HashMap<>());
		data.get(FIRE).put(Color.RED, 0);
		initialValues();
	}
	
	public void increaseData(String type, Color colour) {
		data.get(type).put(colour, data.get(type).get(colour) + 1);
	}
	public void decreaseData(String type, Color colour) {
		data.get(type).put(colour, data.get(type).get(colour) -1);
	}

	public void setUpSpecific(Map<String, int[][]> map) {
		int[][] cellsOnFire = map.get("burning");
		for (int x = 0; x < cellsOnFire.length; x++) {
			int xCoord = cellsOnFire[x][0];
			int yCoord = cellsOnFire[x][1];
			cellGrid[xCoord][yCoord] = new Cell(FIRE);
			cellGrid[xCoord][yCoord].setState(Color.RED);
		}
	}
	
	
	@Override
	protected void updateData() {
		//method unnecessary for this class now that the graph is refactored in this simulation
	}
	
	public void setUpRandom(Map<String, Double> paramMap) {
		double onrate = paramMap.get("firerate");

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				double rand = Math.random();
				if (rand < onrate) {
					cellGrid[x][y] = new Cell(FIRE);
					cellGrid[x][y].setState(Color.RED);
				}
			}
		}
	}

	@Override
	public void setNextStates() {
		for (int x = 0; x < this.xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell toSet = cellGrid[x][y];
				String toSetType = toSet.getState();

				if (toSetType.equals(DEFAULT)) {
					toSet.setNextStateDefault();
				}

				else if (toSetType.equals(FIRE)) {
					toSet.setNextStateDefault();
					decreaseData(FIRE, Color.RED);
					increaseData(DEFAULT, Color.WHITE);
				}

				else if (toSetType.equals(TREE)) {
					if (fireBeside(toSet) && catchResult()) {
						toSet.setNextState(FIRE);
						toSet.setState(Color.ORANGERED);
						decreaseData(DEFAULT, Color.WHITE);
						increaseData(FIRE, Color.RED);
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
		increaseData(TREE,Color.GREEN);
		return temp;
	}

}
