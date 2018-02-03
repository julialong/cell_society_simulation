package cellsociety_team08;
import java.awt.*;
import java.util.Arrays;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

public class Simulator extends Application {
	public static final String NAME = "Cell Society";
	private Scene startScene;
	private final int XSIZE = 1000;
	private final int YSIZE = 1000;
	private static final int FRAMES_PER_SECOND = 40;
	private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
	private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
	private int[] dimensions;
	private int gridWidth;
	private int gridHeight;
	private int[][] cellTypes;
	private CellController control;

	private Group root = new Group();
	private Timeline animation;
	private KeyFrame frame;

	@Override
	public void start(Stage stage) {
		stage.setTitle(NAME);
		startScene = new Scene(root, XSIZE, YSIZE);
		stage.setScene(startScene);
		ParserXML parser = new ParserXML("life.xml");
		dimensions = parser.getDimensions();
		cellTypes = parser.getCellList();

		GridPane gridPane = new GridPane();
		for (int x = 0; x < dimensions[0]; x++) {
			RowConstraints row = new RowConstraints(30);
			gridPane.getRowConstraints().add(row);
		}

		setupCellController();
		setupGrid();

		root.getChildren().add(gridPane);

		stage.show();

		startAnimation();
	}

	private void step() {
		control.setNextStates();
		control.updateCells();
		updateGridColors();
	}

	private void startAnimation() {
		frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
		animation = new Timeline();
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.setDelay(Duration.seconds(2));
		animation.play();
	}

	private void setupCellController() {
		control = new LifeCellController(dimensions,cellTypes);
	}

	private void setupGrid() {
		System.out.println(dimensions[0]);
		System.out.println(XSIZE);
		System.out.println(XSIZE/dimensions[0]);
		gridWidth = XSIZE/dimensions[0];
		gridHeight = YSIZE/dimensions[1];
		updateGridColors();
	}

	private void updateGridColors() {
		Color[][] newColors = control.getColors();
		for (int i = 0; i < dimensions[0]; i++){
			for (int j = 0; j < dimensions[1]; j++){
				Rectangle currentCell = new Rectangle(i * gridWidth, j * gridHeight, gridWidth, gridHeight);
				currentCell.setFill(newColors[i][j]);
				currentCell.setStroke(Color.DARKGREY);
				currentCell.setStrokeType(StrokeType.INSIDE);
				root.getChildren().add(currentCell);
			}
		}
	}

	public static void main (String[] args) {
		launch(args);
		ParserXML parser = new ParserXML("life.xml");
		System.out.println(Arrays.toString(parser.getDimensions()));
	}
}