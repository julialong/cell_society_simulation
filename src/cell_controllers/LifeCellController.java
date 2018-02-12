package cell_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cells.Cell;
import cells.GameOfLifeCell;
import xml.WriterXML;
/**
 * cell for simulation based on inability to survive overcrowding or undercrowding
 * @author jeffreyli, edwardzhuang
 *
 */
public class LifeCellController extends CellController {

	private static final String ON = "on";
	private static final String OFF = "default";
	private Map<String, Double> param;



	public LifeCellController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap,
			boolean random) {

		super(dimensions, random);
		param = paramMap;
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y] = new GameOfLifeCell(OFF);
			}
		}
		if (isRandom) {
			setUpRandom(paramMap);
		} else {
			setUpSpecific(map);
		}
		initializeNeighbors();
	}
	
	@Override
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
	@Override
	public void setUpSpecific(Map<String, int[][]> map) {

		int[][] cellsOn = map.get(ON);
		for (int z = 0; z < cellsOn.length; z++) {
			int xCoord = cellsOn[z][0];
			int yCoord = cellsOn[z][1];
			cellGrid[xCoord][yCoord] = new GameOfLifeCell(ON);
		}
	}
	@Override
	public Cell getDefaultCell() {
		return new GameOfLifeCell(OFF);

	}
	@Override
	public void setNextStates() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				GameOfLifeCell toSet = (GameOfLifeCell) cellGrid[x][y];
				toSet.setNextState();
			}
		}
	}

	@Override
	public Map<String, int[][]> makeCellMap() {
		Map<String, int[][]> map = new HashMap<String, int[][]>();
		List<int[]> cellList = new ArrayList<int[]>();
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				if (cellGrid[x][y].getState() == ON) {
					int[] temp = {x,y};
					cellList.add(temp);
				}
			}
		}	
		map.put("on", cellList.toArray(new int[cellList.size()][]));
		return map;
	}
	
	@Override
	public void writeToXML(String filename) {
		// TODO Auto-generated method stub
		WriterXML writer = new WriterXML(filename, "life", param, makeCellMap(), xSize, ySize);
		writer.convert();
	}
}
