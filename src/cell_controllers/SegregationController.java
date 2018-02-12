package cell_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import javafx.scene.paint.Color;
import xml.WriterXML;

public class SegregationController extends CellController {
	private double threshold;
	private static final String DEFAULT = "default";
	private double xrate;
	private double orate;
	private Map<String, Double> param;

	public SegregationController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap, 
			boolean random) {
		
		super(dimensions, random);
		param = paramMap;
		xrate = paramMap.get("xrate");
		orate = paramMap.get("orate");

		threshold = paramMap.get("threshold");
		if (isRandom) {
			setUpRandom(paramMap);
		} else {
			setUpSpecific(map);
		}
		initializeNeighbors();
		initializeData();
	}
	
	@Override
	public void setUpSpecific(Map<String, int[][]> map) {
		int[][] cellsX = map.get("x");
		for (int z = 0; z < cellsX.length; z++) {
			int xCoord = cellsX[z][0];
			int yCoord = cellsX[z][1];
			cellGrid[xCoord][yCoord] = new Cell("X");
			cellGrid[xCoord][yCoord].setState(Color.RED);
		}
		int[][] cellsO = map.get("o");
		for (int z = 0; z < cellsO.length; z++) {
			int xCoord = cellsO[z][0];
			int yCoord = cellsO[z][1];
			cellGrid[xCoord][yCoord] = new Cell("O");
			cellGrid[xCoord][yCoord].setState(Color.BLUE);
		}
	}
	
	@Override
	protected void updateData() {
		//method unnecessary for this class now that the graph is refactored in this simulation
	}
	
	public void initializeData() {

		data = new HashMap<>();
		data.put(DEFAULT, new HashMap<>());
		data.get(DEFAULT).put(Color.WHITE, 0);
		data.put("X", new HashMap<>());
		data.get("X").put(Color.RED, 0);
		data.put("O", new HashMap<>());
		data.get("O").put(Color.BLUE, 0);
		initialValues();
	}
	
	@Override
	public void setUpRandom(Map<String, Double> paramMap) {
		// TODO Auto-generated method stub
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y] = generateCell();
			}
		}
	}
	

	
	private Cell generateCell() {
		Cell tempCell;
		double rand = Math.random();
		if (rand < xrate) {
			tempCell = new Cell("X");
			tempCell.setState(Color.RED);
		} else if (rand < xrate + orate) {
			tempCell = new Cell("O");
			tempCell.setState(Color.BLUE);
		} else {
			tempCell = new Cell("default");
			tempCell.setState(Color.WHITE);
		}
		return tempCell;
	}

	@Override
	public void setNextStates() {
		ArrayList<String> movers = new ArrayList<>();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				
				Cell toSet = cellGrid[x][y];
				String toSetType = toSet.getState();
				
				// deal with x and o
				if (!toSetType.equals(DEFAULT)) {
					double same = 0;
					double diff = 0;
					String[] neighbours = toSet.getNeighborStateNames();
					for (String type:neighbours) {
						
						if (type != null && !type.equals(DEFAULT)) {

							if (type.equals(toSetType)) {
								same++;
							}
							else if (!type.equals(toSetType) && !type.equals(DEFAULT)){
								diff++;
							}
						}
					}
					

					if (diff > 0) {
					double myRatio = same/diff;
					if (myRatio < threshold) {
						toSet.setNextStateDefault();
						movers.add(toSetType);
					}
					else if (myRatio >= threshold) {
						toSet.setNextState(toSetType);
					}
					}
					else {
						toSet.setNextState(toSetType);
					}
				}
				
				// deal with default
				else if (toSetType.equals(DEFAULT)) {
					if (!movers.isEmpty()) {
						String race = movers.remove(0);
						toSet.setNextState(race);
						if (race.equals("X")) {
							toSet.setState(Color.RED);
						}
						else {
							toSet.setState(Color.BLUE);
						}
					}
				}
			}
		}
	}

	@Override
	public Cell getDefaultCell() {
		Cell temp = generateCell();
		String type = temp.getState();
		Color colour = temp.getColor();
		data.get(type).put(colour, data.get(type).get(colour) + 1);
		return temp;
	}

	@Override
	public Map<String, int[][]> makeCellMap() {
		Map<String, int[][]> map = new HashMap<String, int[][]>();
		List<int[]> cellListX = new ArrayList<int[]>();
		List<int[]> cellListO = new ArrayList<int[]>();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				if (cellGrid[x][y].getState() == "X") {
					int[] temp = {x,y};
					cellListX.add(temp);
				}
				if (cellGrid[x][y].getState() == "O") {
					int[] temp = {x,y};
					cellListO.add(temp);
				}
				
			}
		}
		
		map.put("x", cellListX.toArray(new int[cellListX.size()][]));
		map.put("o", cellListO.toArray(new int[cellListO.size()][]));
		
		
		return map;
	}
	
	@Override
	public void writeToXML(String filename) {
		// TODO Auto-generated method stub
		WriterXML writer = new WriterXML(filename, "segregation", param, makeCellMap(), xSize, ySize);
		writer.convert();
	}


}
