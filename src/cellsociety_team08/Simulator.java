package cellsociety_team08;

import java.io.File;
import java.util.*;

import cell_controllers.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.util.Duration;

public class Simulator {


    private static final String CONWAYS = "life";
    private static final String SPREADINGFIRE = "fire";
    private static final String SEGREGATION = "segregation";
    private static final String WATOR = "wator";
    private static final String RPS = "rps";

    private static final int FRAMES_PER_SECOND = 30;
    private static final int MILLISECOND_DELAY = 6000 / FRAMES_PER_SECOND;
    private static final int START_STEP = 5;

    private int[] dimensions;
    private Map<String, int[][]> cellTypes;
    private String simulationType;
    private Map<String, Double> parameters;
    private Boolean isRandom;

    private int gridWidth;
    private int gridHeight;
    private CellController control;

    private Boolean simulationState = false;
    private int stepTime;
    private int stepNum = 0;

    private List<Rectangle> currentGrid = new ArrayList<>();

    private Graph myGraph;

    private Timeline animation;
    private KeyFrame frame;

    /**
     * Sets up the beginning configuration for the simulation
     *
     * @param stage the JavaFX stage in GUI
     * @param root  the JavaFX Group root in GUI
     */
    public void startSimulation(Stage stage, Group root) {
        stepTime = START_STEP;
        setupCellController();
        myGraph = new Graph();
        myGraph.updateGraph(root);
        setupGrid(root);
        stage.show();
        startAnimation(root);
        stepNum = 0;
    }

    /**
     * @return current dimensions of the grid
     */
    public int getDimensions() {
    	return dimensions[0];
    }

    /**
     *
     * @param newDimensions is the new dimension of the gris
     * @param root is the JavaFX Group
     */
    public void resize(int newDimensions, Group root) {
    	control.resize(newDimensions);
    	dimensions[0] = newDimensions;
    	dimensions[1] = newDimensions;
    	setupGrid(root);
    }
    /**
     * Steps through the simulation
     *
     * @param root the JavaFX Group root in GUI
     */
    private void step(Group root) {
        if (!simulationState) {
            return;
        }
        updateCells(root);
    }

    /**
     * Updates cells to the next state and fills in the grid
     * @param root the JavaFX Group root in GUI
     */
    private void updateCells(Group root) {
        stepNum++;
        control.setNextStates();
        control.updateCells();
        updateGraph(root);
        updateGridColors(root);
    }

    /**
     * Sets up the simulation
     *
     * @param root the JavaFX Group root in GUI
     */
    private void startAnimation(Group root) {
        frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(root));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * Sets up the cell controller based on the simulation type from the XML file
     */
    private void setupCellController() {
    		
        if (simulationType.equals(CONWAYS)) {

            control = new LifeCellController(dimensions, cellTypes, parameters, isRandom);
        }
        else if (simulationType.equals(SPREADINGFIRE)) {
            control = new FireController(dimensions, cellTypes, parameters, isRandom);
        }
        else if (simulationType.equals(SEGREGATION)) {
            control = new SegregationController(dimensions, cellTypes, parameters, isRandom);
        }
        else if (simulationType.equals(WATOR)) {
            control = new WatorController(dimensions, cellTypes, parameters, isRandom);
        }
        else if(simulationType.equals(RPS)) {
            control = new RPSController(dimensions, cellTypes, parameters, isRandom);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Sets up the grid based on the number of cells so that the size of the grid itself will
     * be constant
     *
     * @param root the JavaFX Group root in GUI
     */
    private void setupGrid(Group root) {
        gridWidth = GUI.XSIZE / dimensions[0];
        gridHeight = GUI.YSIZE / dimensions[1];
        updateGridColors(root);
    }

    /**
     * Gets the current cell colors from the cell controller and updates the displayed grid
     * @param root the JavaFX Group root in GUI
     */
    private void updateGridColors(Group root) {
        clearGrid(root);
		currentGrid = new ArrayList<>();
        Color[][] newColors = control.getColors();
        for (int i = 0; i < dimensions[0]; i++) {
            for (int j = 0; j < dimensions[1]; j++) {
                Rectangle currentCell = createNewCell(GUI.SIDE_BAR + i * gridWidth, GUI.TOP_BAR + j * gridHeight, gridWidth, gridHeight, newColors[i][j]);
                currentGrid.add(currentCell);
                root.getChildren().add(currentCell);
            }
        }
    }

    /**
     * Updates the graph with the correct colors
     * @param root the JavaFX Group root in GUI
     */
    public void updateGraph(Group root) {
        Map<String, Map<Color, Integer>> data = control.getData();
        for (String thisType : data.keySet()) {
            for (Color thisColor : data.get(thisType).keySet()) {
                myGraph.addPoint(thisType, stepNum, data.get(thisType).get(thisColor), thisColor);
            }
        }
        myGraph.updateGraph(root);
    }

	/**
	 * Creates a new cell with the given parameters
	 * @param x x position
	 * @param y y position
	 * @param width width of cell
	 * @param height height of cell
	 * @param color color of cell
	 * @return
	 */
    private Rectangle createNewCell(int x, int y, int width, int height, Color color) {
    	Rectangle newCell = new Rectangle(x, y, width, height);
		newCell.setFill(color);
		newCell.setStroke(Color.DARKGREY);
		newCell.setStrokeType(StrokeType.INSIDE);
		return newCell;
	}

    /**
     * Removes current cells from the GUI root
     * @param root the JavaFX Group root in GUI
     */
    private void clearGrid(Group root) {
        for (Rectangle cell : currentGrid) {
            root.getChildren().remove(cell);
        }
        currentGrid = null;
    }

    /**
     * Steps the simulation manually
     * @param root the JavaFX Group root in GUI
     */
    public void manualStep(Group root) {
        this.updateCells(root);
    }

    /**
     * Calls the XML parser to read the file from the GUI to get information for the simulaton
     * including simulation type, dimensions of grid, and types of cells
     * @param file is the configuration file to be read
     */
    public void setFile(File file) {
        ParserXML parser = new ParserXML(file);
        simulationType = parser.getSimulationType();
        dimensions = parser.getDimensions();
        cellTypes = parser.getAllCells();
        parameters = parser.getParameters();
        isRandom = parser.isRandom();
    }

    /**
     * Turns on the simulation so that it changes with each step
     */
    public void turnOn() {
        this.simulationState = true;
    }

    /**
     * Turns off the simulation so that it does not change with each step
     */
    public void turnOff() {
        this.simulationState = false;
    }

    /**
     * Decreases the delay time for the simulation so it moves more quickly
     */
    public void speedUp() {
        if (this.stepTime > 0) {
            this.stepTime--;
        }
    }

    /**
     * Increases the delay time for the simulation so it moves more slowly
     */
    public void speedDown() {
        this.stepTime++;
    }

    /**
     * @return array with cell type strings
     */
    public String[] getCellTypes() {
        String[] answer = new String[cellTypes.keySet().size()];
        int i = 0;
        for (String type : cellTypes.keySet()){
            answer[i] = type;
            i++;
        }
        return answer;
    }

    /**
     * @return the maximum number of cells in the grid
     */
    public int getMaxCells() {
        return dimensions[0]*dimensions[1];
    }

    /**
     * Creates a new file with the current configuration
     * @param filename the filename given by the user
     */
    public void toXML(String filename) {
        System.out.println(filename);
        //TODO: Change empty map to map from Jeffrey OR we can change this method to be called in CellController
        control.writeToXML(filename);
    }

}