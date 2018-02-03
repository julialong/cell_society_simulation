package cellsociety_team08;

import java.awt.Color;

public class State {
	private Color colour;
	private String stateName;
	
	public State(String name) {
		stateName = name;
	}
	
	public String getStateName() {
		return stateName;
	}
	
	public void changeState(String name) {
		stateName = name;
		if (stateName.equals("on")){
			colour = Color.BLACK;
		}
		else {
			colour = Color.WHITE;
		}
	}
	
	public Color getColor() {
		return colour;
	}
	
	public String getState() {
		return stateName;
	}
}
