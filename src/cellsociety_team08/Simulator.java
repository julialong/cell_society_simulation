package cellsociety_team08;
import java.util.Arrays;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Simulator extends Application {
	public static final String NAME = "Cell Society";
	private Scene startScene;
	private final int XSIZE = 1000;
	private final int YSIZE = 1000;
	private Group root = new Group();
	
	@Override
	public void start(Stage stage) {
		stage.setTitle(NAME);
		startScene = new Scene(root, XSIZE, YSIZE);
		stage.setScene(startScene);
		ParserXML parser = new ParserXML("life.xml");
		int[] dimensions = parser.getDimensions();
						
		GridPane gridPane = new GridPane();
		for (int x = 0; x < dimensions[0]; x++) {
			RowConstraints row = new RowConstraints(30);
			gridPane.getRowConstraints().add(row);
		}
		
		root.getChildren().add(gridPane);
		
		stage.show();
	}
	
	public static void main (String[] args) {
		launch(args);
		ParserXML parser = new ParserXML("life.xml");
		System.out.println(Arrays.toString(parser.getDimensions()));
	}
}
