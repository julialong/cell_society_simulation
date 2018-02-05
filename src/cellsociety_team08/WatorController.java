package cellsociety_team08;

import java.util.ArrayList;

import cell_controllers.CellController;
import javafx.scene.paint.Color;

public class WatorController extends CellController{
	public Animal[][] animals;
	public ArrayList<int[]> shark;
	public ArrayList<int[]> fish;
	

	public WatorController(int[] dimensions, double fishPercent, double sharkPercent) {
		super(dimensions);
		animals = new Animal[xSize][ySize];
		for (int x = 0; x < xSize; x++) {
			for (int y = 0; y < ySize; y++) {
				Animal animal = randomAnimalGenerator(fishPercent, sharkPercent, x, y);
				animals[x][y] = animal;
				updateColor(x,y);
			}
		}
	}
	
	private void updateColor(int x, int y) {
		if (animals[x][y].getType().equals("Shark")) {
			cellGrid[x][y].setState(Color.RED);
		}
		else if (animals[x][y].getType().equals("Fish")) {
			cellGrid[x][y].setState(Color.GREENYELLOW);
		}
		else if (animals[x][y].getType() == null) {
			cellGrid[x][y].setState(Color.WHITE);
		}
	}
	
	private Animal randomAnimalGenerator(double fishPercent,double sharkPercent, int x, int y) {
		double rand = Math.random();
		if (rand < fishPercent) {
			return new Fish(x, y);
		}
		else if (rand < fishPercent + sharkPercent) {
			return new Shark(x, y);
		}
		else return null;
	}

	@Override
	public void setNextStates() {
		// TODO Auto-generated method stub
		
	}
}
