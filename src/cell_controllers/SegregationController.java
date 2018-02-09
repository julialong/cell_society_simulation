package cell_controllers;

import java.util.ArrayList;
import java.util.Map;

import cellsociety_team08.Cell;
import javafx.scene.paint.Color;
import watorCells.Fish;
import watorCells.Shark;
import watorCells.Water;
import watorCells.WatorCell;

public class SegregationController extends CellController {
	private double threshold;
	private final String DEFAULT = "default";


	public SegregationController(int[] dimensions, Map<String, Double> paramMap) {
		
		super(dimensions);
		double xrate = paramMap.get("xrate");
		double orate = paramMap.get("orate");

		threshold = paramMap.get("threshold");
		
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				double rand = Math.random();
				if (rand < xrate) {
					Cell tempCell = new Cell("X");
					tempCell.setState(Color.RED);
					cellGrid[x][y] = tempCell;
				} else if (rand < xrate + orate) {
					Cell tempCell = new Cell("O");
					tempCell.setState(Color.BLUE);
					cellGrid[x][y] = tempCell;
				}
			}
		}
		
		
//		for (int x = 0; x < cellsX.length; x++) {
//			int xCoord = cellsX[x][0];
//			int yCoord = cellsX[x][1];
//			cellGrid[xCoord][yCoord] = new Cell("X");
//			cellGrid[xCoord][yCoord].setState(Color.RED);
//		}
//		for (int o = 0; o < cellsO.length; o++) {
//			int xCoord = cellsO[o][0];
//			int yCoord = cellsO[o][1];
//			cellGrid[xCoord][yCoord] = new Cell("O");
//			cellGrid[xCoord][yCoord].setState(Color.BLUE);
//		}
		initializeNeighbors();
	}
	
//	public Cell randomTypeGenerator(double xrate, double orate) {
//		double rand = Math.random();
//		if (rand < xrate) {
//			Cell tempCell = new Cell("X");
//			tempCell.setState(Color.RED);
//			return tempCell;
//		} else if (rand < xrate + orate) {
//			Cell tempCell = new Cell("O");
//			tempCell.setState(Color.BLUE);
//			return tempCell;
//		}
////		} else
////			return new WatorCell("water", new Water());
//	}

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

						if (!type.equals(DEFAULT)) {

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
					if (movers.size()!=0) {
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
}
