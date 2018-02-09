package cell_controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import cellsociety_team08.Cell;
import javafx.scene.paint.Color;
import watorCells.Animal;
import watorCells.Fish;
import watorCells.Shark;
import watorCells.Water;
import watorCells.WatorCell;

public class WatorController extends CellController {
	
	private final String SHARK = "shark";
	private final String FISH = "fish";
	private final String WATER = "water";
	private final String FISHRATE = "fishrate";
	private final String SHARKRATE = "sharkrate";
	private final int NUMBER_OF_NEIGHBOURS = 4;
	private final int LEFT = 0;
	private final int BOTTOM = 1;
	private final int TOP = 2;
	private final int RIGHT = 3;
	

	public WatorController(int[] dimensions, Map<String, Double> paramMap) {
		super(dimensions);
		double fishPercent = paramMap.get(FISHRATE);
		double sharkPercent = paramMap.get(SHARKRATE);

		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Cell tempCell = randomAnimalGenerator(fishPercent, sharkPercent);
				cellGrid[x][y] = tempCell;
			}
		}
		initializeNeighbors();
	}

	@Override
	public void initializeNeighbors() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {

				WatorCell[] tempArray = new WatorCell[NUMBER_OF_NEIGHBOURS];
				tempArray[LEFT] = (WatorCell) retrieveCell(x - 1, y);
				tempArray[BOTTOM] = (WatorCell) retrieveCell(x, y - 1);
				tempArray[TOP] = (WatorCell) retrieveCell(x, y + 1);
				tempArray[RIGHT] = (WatorCell) retrieveCell(x + 1, y);
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
				
				WatorCell toSet = (WatorCell) retrieveCell(x, y);
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
		moveHere.setNewAnimal(fishCell.getAnimal());
		if (moveHere != fishCell) {
			if (fishCell.getAnimal().timeToMultiply()) {
				fishCell.setNewAnimal(new Fish());
				fishCell.setState(Color.GREENYELLOW);
			} else {
				fishCell.setToWater();
			}
		}
	}

	public void updateShark(WatorCell sharkCell) {

		sharkCell.decrementAnimalHealth();
		if (sharkCell.getAnimal().getHealth() == 0) {
			sharkCell.setToWater();
		} else {
			WatorCell moveHere = newSpot(sharkCell);
			if (moveHere != sharkCell) {
				moveHere.setNewAnimal(sharkCell.getAnimal());

				if (sharkCell.getAnimal().timeToMultiply()) {
					sharkCell.setNewAnimal(new Shark());
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

		WatorCell[] neighbours = (WatorCell[]) animal.neighbors;
		for (WatorCell wc : neighbours) {
			if (wc!= null) {
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
			if (possibleFish.size() == 0 && possibleWater.size() == 0) {
				return animal; // throw exception because no where to move. returning self for now.
			}
			if (possibleFish.size() != 0) {
				
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
}
