package cellsociety_team08;

import javafx.scene.image.ImageView;

public class State {
	ImageView image;
	String stateName;
	
	public State(String name, ImageView imageview) {
		stateName = name;
		image = imageview;
	}
	
	public void changeState(String name, ImageView imageview) {
		stateName = name;
		image = imageview;
	}
	
	public String getState() {
		return stateName;
	}
}
