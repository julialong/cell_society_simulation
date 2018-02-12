package cell_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import cellsociety_team08.WriterXML;
import javafx.scene.paint.Color;

public class FireController extends CellController {

	private double catchProbability;
	private static final String TREE = "tree";
	private static final String FIRE = "fire";
	private static final String DEFAULT = "default";
	private Map<String, Double> param;
	
	public FireController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap, boolean random) {
		super(dimensions, random);
		
		param = paramMap;
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
	}

	public void setUpSpecific(Map<String, int[][]> map) {
		int[][] cellsOnFire = map.get("burning");
		for (int x = 0; x < cellsOnFire.length; x++) {
			int xCoord = cellsOnFire[x][0];
			int yCoord = cellsOnFire[x][1];
			cellGrid[xCoord][yCoord] = new Cell(FIRE);
			cellGrid[xCoord][yCoord].setState(Color.RED);
		}
		int[][] cellsBurnt = map.get("default");
		for (int x = 0; x < cellsBurnt.length; x++) {
			int xCoord = cellsBurnt[x][0];
			int yCoord = cellsBurnt[x][1];
			cellGrid[xCoord][yCoord] = new Cell(DEFAULT);
			cellGrid[xCoord][yCoord].setState(Color.WHITE);
		}
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
	
	@Override
	public Map<String, int[][]> makeCellMap() {
		Map<String, int[][]> map = new HashMap<String, int[][]>();
		List<int[]> cellListFire = new ArrayList<int[]>();
		List<int[]> cellListBurnt = new ArrayList<int[]>();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				if (cellGrid[x][y].getState() == DEFAULT) {
					int[] temp = {x,y};
					cellListBurnt.add(temp);
				}
				if (cellGrid[x][y].getState() == FIRE) {
					int[] temp = {x,y};
					cellListFire.add(temp);
				}
				
			}
		}
		
		map.put("burning", cellListFire.toArray(new int[cellListFire.size()][]));
		map.put("default", cellListBurnt.toArray(new int[cellListBurnt.size()][]));
		
		
		
		return map;
	}
	
	@Override
	public void writeToXML(String filename) {
		// TODO Auto-generated method stub
		WriterXML writer = new WriterXML(filename, "fire", param, makeCellMap(), xSize, ySize);
		writer.convert();
	}
	

}
