package cell_controllers;

import java.util.ArrayList;
import java.util.Map;

import cellsociety_team08.Cell;
import javafx.scene.paint.Color;
import watorCells.Animal;
import watorCells.Fish;
import watorCells.Shark;
import watorCells.Water;
import watorCells.WatorCell;

public class WatorController extends CellController {

	public WatorController(int[] dimensions, Map<String, Double> paramMap) {
		super(dimensions);
		double fishPercent = paramMap.get("fishrate");
		double sharkPercent = paramMap.get("sharkrate");

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

				WatorCell[] tempArray = new WatorCell[4];
				tempArray[0] = (WatorCell) retrieveCell(x - 1, y);
				tempArray[1] = (WatorCell) retrieveCell(x, y - 1);
				tempArray[2] = (WatorCell) retrieveCell(x, y + 1);
				tempArray[3] = (WatorCell) retrieveCell(x + 1, y);
				cellGrid[x][y].addNeighbors(tempArray);
			}
		}
	}

	private Cell randomAnimalGenerator(double fishPercent, double sharkPercent) {
		double rand = Math.random();
		if (rand < fishPercent) {
			return new WatorCell("fish", new Fish());
		} else if (rand < fishPercent + sharkPercent) {
			return new WatorCell("shark", new Shark());
		} else
			return new WatorCell("water", new Water());
	}

	@Override
	public void setNextStates() {
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				WatorCell toSet = (WatorCell) retrieveCell(x, y);
				toSet.getAnimal().incrementTime();
				if (toSet.getAnimalType().equals("shark")) {
					updateShark(toSet);
				} else if (toSet.getAnimalType().equals("fish")) {
					updateFish(toSet);
				}
			}
		}
	}

	public void updateFish(WatorCell fishCell) {
		WatorCell moveHere = newSpot(fishCell);
		if (moveHere != fishCell) {
			if (fishCell.getAnimal().getTime() % 10 == 0) {
				fishCell.setNewAnimal(new Fish());
			} else {
				fishCell.setToWater();
			}
		}
	}

	public void updateShark(WatorCell sharkCell) {
		sharkCell.getAnimal().decrementHealth();
		if (sharkCell.getAnimal().getHealth() == 0) {
			sharkCell.setToWater();
		} else {
			WatorCell moveHere = newSpot(sharkCell);
			if (moveHere != sharkCell) {
				moveHere.setNewAnimal(sharkCell.getAnimal());
				if (sharkCell.getAnimal().getTime() % 10 == 0) {
					sharkCell.setNewAnimal(new Shark());
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
			if (wc.getAnimalType().equals("fish")) {
				possibleFish.add(wc);
			}
			if (wc.getAnimalType().equals("water")) {
				possibleWater.add(wc);
			}
		}

		if (animal.getAnimalType().equals("shark")) {
			if (possibleFish.size() == 0 && possibleWater.size() == 0) {
				return animal; // throw exception because no where to move. returning self for now.
			}
			if (possibleFish.size() != 0) {
				int index = (int) (long) Math.random() * possibleFish.size();
				return possibleFish.get(index);
			} else {
				int index = (int) (long) Math.random() * possibleWater.size();
				return possibleWater.get(index);
			}
		}

		if (animal.getAnimalType().equals("fish")) {
			if (possibleWater.size() == 0) {
				return animal; // throw exception because no where to move. returning self for now.
			}
			int index = (int) (long) Math.random() * possibleWater.size();
			return possibleWater.get(index);
		}

		return animal;
	}
}
