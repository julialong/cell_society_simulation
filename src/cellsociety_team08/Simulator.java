package cellsociety_team08;

import java.io.File;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.util.Duration;

public class Simulator {

    private final String CONWAYS = "life";
    private final String SPREADINGFIRE = "fire";
    private final String SEGREGATION = "segregation";
    private final String WATOR = "wator";

    private static final int FRAMES_PER_SECOND = 40;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private int[] dimensions;
    private Map<String, int[][]> cellTypes;
    private String simulationType;
    private Map<String, Double> parameters;

    private int gridWidth;
    private int gridHeight;
    private CellController control;

    private Boolean simulationState = false;
    private int stepTime;

    private List<Rectangle> currentGrid = new ArrayList<>();

    private Timeline animation;
    private KeyFrame frame;

    /**
     * Sets up the beginning configuration for the simulation
     *
     * @param stage the JavaFX stage in GUI
     * @param root  the JavaFX Group root in GUI
     */
    public void startSimulation(Stage stage, Group root) {
        stepTime = 10;
        setupCellController();
        setupGrid(root);
        stage.show();
        startAnimation(root);
    }

    /**
     * Steps through the simulation
     *
     * @param root the JavaFX Group root in GUI
     */
    private void step(Group root) {
        if (!simulationState) return;
        updateCells(root);
    }

    /**
     * Updates cells to the next state and fills in the grid
     * @param root the JavaFX Group root in GUI
     */
    private void updateCells(Group root) {
        control.setNextStates();
        control.updateCells();
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
        animation.setDelay(Duration.seconds(stepTime));
        animation.play();
    }

    /**
     * Sets up the cell controller based on the simulation type from the XML file
     */
    private void setupCellController() {
        if (simulationType.equals(CONWAYS)) control = new LifeCellController(dimensions, cellTypes);
        else if (simulationType.equals(SPREADINGFIRE)) control = new FireController(dimensions, cellTypes, parameters);
        else if (simulationType.equals(SEGREGATION)) control = new SegregationController(dimensions, cellTypes, parameters);
        else throw new IllegalArgumentException();
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
        Color[][] newColors = control.getColors();
        for (int i = 0; i < dimensions[0]; i++) {
            for (int j = 0; j < dimensions[1]; j++) {
                Rectangle currentCell = new Rectangle(GUI.XBAR + i * gridWidth, j * gridHeight, gridWidth, gridHeight);
                currentCell.setFill(newColors[i][j]);
                currentCell.setStroke(Color.DARKGREY);
                currentCell.setStrokeType(StrokeType.INSIDE);
                currentGrid.add(currentCell);
                root.getChildren().add(currentCell);
            }
        }
    }

    /**
     * Removes current cells from the GUI root
     * @param root the JavaFX Group root in GUI
     */
    private void clearGrid(Group root) {
        for (Rectangle cell : currentGrid) root.getChildren().remove(cell);
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
        if (this.stepTime > 0) this.stepTime--;
    }

    /**
     * Increases the delay time for the simulation so it moves more slowly
     */
    public void speedDown() {
        this.stepTime++;
    }
}