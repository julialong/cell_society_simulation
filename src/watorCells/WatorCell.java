package watorCells;

import javafx.scene.paint.Color;

import cellsociety_team08.Cell;

public class WatorCell extends Cell{
	private Animal animal;

	public WatorCell(String state, Animal object) {
		super(state);
		animal = object;
		if (animal.getType().equals("shark")){
			colour = Color.RED;
		}
		else if (animal.getType().equals("fish")){
			colour = Color.GREENYELLOW;
		}
		else if (animal.getType().equals("water")){
			colour = Color.CORNFLOWERBLUE;
		}
	}
	
	public String getAnimalType() {
		return animal.getType();
	}
	
	public Animal getAnimal() {
		return animal;
	}
	
	public void setToWater() {
		animal = new Water();
		colour = Color.CORNFLOWERBLUE;
	}
	
	public void setNewAnimal(Animal newAnimal) {
		animal = newAnimal;
	}
	
}
