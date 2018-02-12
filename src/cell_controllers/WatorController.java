package cell_controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cells.Cell;
import cellsociety_team08.WriterXML;
import javafx.scene.paint.Color;
import watorCells.Animal;
import watorCells.Fish;
import watorCells.Shark;
import watorCells.Water;
import watorCells.WatorCell;

public class WatorController extends CellController {

	private static final String SHARK = "shark";
	private static final String FISH = "fish";
	private static final String WATER = "water";
	private static final String FISHRATE = "fishrate";
	private static final String SHARKRATE = "sharkrate";
	private static final int NUMBER_OF_NEIGHBOURS = 4;
	private static final int LEFT = 0;
	private static final int BOTTOM = 1;
	private static final int TOP = 2;
	private static final int RIGHT = 3;
	private static double fishPercent;
	private static double sharkPercent;
	private Map<String, Double> param;


	public WatorController(int[] dimensions, Map<String, int[][]> map, Map<String, Double> paramMap, boolean random) {
		super(dimensions, random);
		param = paramMap;
		fishPercent = paramMap.get(FISHRATE);
		sharkPercent = paramMap.get(SHARKRATE);
		if (isRandom) {
			setUpRandom(paramMap);
		}
		initializeNeighbors();
	}

	@Override
	public void initializeNeighbors() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				WatorCell[] tempArray = new WatorCell[NUMBER_OF_NEIGHBOURS];
				tempArray[LEFT] = (WatorCell) cellGrid[x - 1][y];
				tempArray[BOTTOM] = (WatorCell) cellGrid[x][y - 1];
				tempArray[TOP] = (WatorCell) cellGrid[x][y + 1];
				tempArray[RIGHT] = (WatorCell) cellGrid[x + 1][y];
				cellGrid[x][y].addNeighbors(tempArray);
			}
		}
	}

	private Cell randomAnimalGenerator(double fishPercent, double sharkPercent) {
		double rand = Math.random();
		if (rand < fishPercent) {
			return new WatorCell(FISH, new Fish());
		} else if (rand < fishPercent + sharkPercent) {
			return new WatorCell(SHARK, new Shark());
		} else
			return new WatorCell(WATER, new Water());
	}

	@Override
	public void setNextStates() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				WatorCell toSet = (WatorCell) cellGrid[x][y];
				toSet.getAnimal().incrementTime();
				if (toSet.getAnimalType().equals(SHARK)) {
					updateShark(toSet);
				} else if (toSet.getAnimalType().equals(FISH)) {
					updateFish(toSet);
				}
			}
		}
	}

	public void updateFish(WatorCell fishCell) {
		WatorCell moveHere = newSpot(fishCell);
		moveHere.setNewAnimal(fishCell.getAnimal(), fishCell.getState());
		if (moveHere != fishCell) {
			if (fishCell.getAnimal().timeToMultiply()) {
				fishCell.setNewAnimal(new Fish(), FISH);
				fishCell.setState(Color.GREENYELLOW);
			} else {
				fishCell.setToWater();
			}
		}
	}

	public Cell getDefaultCell() {
		Cell tempCell = randomAnimalGenerator(fishPercent, sharkPercent);
		return tempCell;
	}

	public void updateShark(WatorCell sharkCell) {

		sharkCell.decrementAnimalHealth();
		if (sharkCell.getAnimal().isDead()) {
			sharkCell.setToWater();
		} else {
			WatorCell moveHere = newSpot(sharkCell);
			if (moveHere != sharkCell) {
				moveHere.setNewAnimal(sharkCell.getAnimal(), SHARK);

				if (sharkCell.getAnimal().timeToMultiply()) {
					sharkCell.setNewAnimal(new Shark(), SHARK);
					sharkCell.setState(Color.RED);
				} else {
					sharkCell.setToWater();
				}
			}
		}
	}

	public WatorCell newSpot(WatorCell animal) {
		ArrayList<WatorCell> possibleFish = new ArrayList<WatorCell>();
		ArrayList<WatorCell> possibleWater = new ArrayList<WatorCell>();

		WatorCell[] neighbours = (WatorCell[]) animal.getNeighbors();
		for (WatorCell wc : neighbours) {
			if (wc != null) {
				if (wc.getAnimalType().equals(FISH)) {
					possibleFish.add(wc);
				}
				if (wc.getAnimalType().equals(WATER)) {
					possibleWater.add(wc);
				}
			}
		}
		Random rand = new Random();

		if (animal.getAnimalType().equals(SHARK)) {
			if (possibleFish.isEmpty() && possibleWater.isEmpty()) {
				return animal; // throw exception because no where to move. returning self for now.
			}
			if (!possibleFish.isEmpty()) {

				int index = rand.nextInt(possibleFish.size());
				animal.getAnimal().replenishHealth();
				return possibleFish.get(index);
			} else {
				int index = rand.nextInt(possibleWater.size());
				return possibleWater.get(index);
			}
		}

		if (animal.getAnimalType().equals(FISH)) {
			if (possibleWater.size() == 0) {
				return animal; // throw exception because no where to move. returning self for now.
			}
			int index = rand.nextInt(possibleWater.size());
			return possibleWater.get(index);
		}

		return animal;
	}

	@Override
	public void setUpSpecific(Map<String, int[][]> map) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUpRandom(Map<String, Double> paramMap) {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell tempCell = randomAnimalGenerator(fishPercent, sharkPercent);
				cellGrid[x][y] = tempCell;
			}
		}

	}
	
	@Override
	public Map<String, int[][]> makeCellMap() {
		Map<String, int[][]> map = new HashMap<String, int[][]>();
//		List<int[]> cellListFire = new ArrayList<int[]>();
//		List<int[]> cellListBurnt = new ArrayList<int[]>();
//		for (int x = 0; x < xSize; x++) {
//			for (int y = 0; y < ySize; y++) {
//				if (cellGrid[x][y].getState() == DEFAULT) {
//					int[] temp = {x,y};
//					cellListBurnt.add(temp);
//				}
//				if (cellGrid[x][y].getState() == FIRE) {
//					int[] temp = {x,y};
//					cellListFire.add(temp);
//				}
//				
//			}
//		}
//		
//		map.put("burning", cellListFire.toArray(new int[cellListFire.size()][]));
//		map.put("default", cellListBurnt.toArray(new int[cellListBurnt.size()][]));
//		
//		
		
		return map;
	}
	
	@Override
	public void writeToXML(String filename) {
		// TODO Auto-generated method stub
		WriterXML writer = new WriterXML(filename, "wator", param, makeCellMap(), xSize, ySize);
		writer.convert();
	}
}
