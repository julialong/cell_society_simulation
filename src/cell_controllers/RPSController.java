package cell_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cells.Cell;
import cells.RPSCell;
import cellsociety_team08.WriterXML;
import javafx.scene.paint.Color;

public class RPSController extends CellController {
	private double greenRate;
	private double blueRate;
	private double redRate;
	private static final String GREEN = "green";
	private static final String BLUE = "blue";
	private static final String RED = "red";
	private static final String WHITE = "default";
	private Map<String, Double> param;

	public RPSController(int[] dimensions,Map<String, int[][]> map, Map<String, Double> paramMap, 
			boolean random) {
		super(dimensions, random);
		param = paramMap;
		
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
		
		int[][] cellsWhite = map.get(WHITE);
		for (int z = 0; z < cellsWhite.length; z++) {
			int xCoord = cellsWhite[z][0];
			int yCoord = cellsWhite[z][1];
			cellGrid[xCoord][yCoord] = new RPSCell();
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
			tempCell = new RPSCell(Color.WHITE, WHITE);
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
				temp = (RPSCell) retrieveCell(x, y);
				temp.infect(temp.retrieveRandomNeighbour());
			}
		}
	}
	
	@Override
	public Map<String, int[][]> makeCellMap() {
		Map<String, int[][]> map = new HashMap<String, int[][]>();
		List<int[]> cellListRed = new ArrayList<int[]>();
		List<int[]> cellListBlue = new ArrayList<int[]>();
		List<int[]> cellListGreen = new ArrayList<int[]>();
		List<int[]> cellListWhite = new ArrayList<int[]>();
		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				if (cellGrid[x][y].getState() == RED) {
					int[] temp = {x,y};
					cellListRed.add(temp);
				}
				if (cellGrid[x][y].getState() == BLUE) {
					int[] temp = {x,y};
					cellListBlue.add(temp);
				}
				if (cellGrid[x][y].getState() == GREEN) {
					int[] temp = {x,y};
					cellListGreen.add(temp);
				}
				if (cellGrid[x][y].getState() == WHITE) {
					int[] temp = {x,y};
					cellListWhite.add(temp);
				}
			}
		}
		
		map.put("red", cellListRed.toArray(new int[cellListRed.size()][]));
		map.put("blue", cellListBlue.toArray(new int[cellListBlue.size()][]));
		map.put("green", cellListGreen.toArray(new int[cellListGreen.size()][]));
		map.put("default", cellListWhite.toArray(new int[cellListWhite.size()][]));
		
		return map;
	}
	
	@Override
	public void writeToXML(String filename) {
		// TODO Auto-generated method stub
		WriterXML writer = new WriterXML(filename, "rps", param, makeCellMap(), xSize, ySize);
		writer.convert();
	}
	
}
