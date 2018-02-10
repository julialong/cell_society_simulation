package cell_controllers;

import java.util.ArrayList;
import java.util.Map;

import cells.Cell;
import javafx.scene.paint.Color;

public class SegregationController extends CellController {
	private double threshold;
	private static final String DEFAULT = "default";
	private double xrate;
	private double orate;


	public SegregationController(int[] dimensions, Map<String, Double> paramMap) {
		
		super(dimensions);
		xrate = paramMap.get("xrate");
		orate = paramMap.get("orate");

		threshold = paramMap.get("threshold");
		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				cellGrid[x][y] = generateCell();
				
//				double rand = Math.random();
//				if (rand < xrate) {
//					Cell tempCell = new Cell("X");
//					tempCell.setState(Color.RED);
//					cellGrid[x][y] = tempCell;
//				} else if (rand < xrate + orate) {
//					Cell tempCell = new Cell("O");
//					tempCell.setState(Color.BLUE);
//					cellGrid[x][y] = tempCell;
//				}
			}
		}
		initializeNeighbors();
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
				
				Cell toSet = retrieveCell(x, y);
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
		return generateCell();
	}
}
