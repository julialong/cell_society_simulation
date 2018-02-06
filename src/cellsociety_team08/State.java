package cellsociety_team08;

import javafx.scene.paint.Color;

public class State {
	private Color colour;
	private String stateName;
	
	public State(String name) {
		stateName = name;
	}
	
	public String getStateName() {
		return stateName;
	}
	
	public Color getColor() {
		return colour;
	}
	
	public void setColor(Color color) {
		colour = color;
	}
	
}
