package cellsociety_team08;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class GUI {

    public static final String NAME = "Cell Society";
    private Scene startScene;
    public static final int XSIZE = 600;
    public static final int YSIZE = 600;
    public static final int HALF_X = XSIZE/2;
    public static final int HALF_Y = YSIZE/2;
    public static final int SIDE_BAR = 600;
    public static final int BOTTOM_BAR = 200;
    public static final int POPUP_BOX_WIDTH = 300;
    public static final int POPUP_BOX_HEIGHT = 50;
    public static final int ERROR_X_ADJUSTMENT = 50;
    public static final int ERROR_Y_ADJUSTMENT = 25;
    public static final int BUTTON_X = 20;
    public static final int BUTTON_SPACING = 50;

    private static final String DEFAULT_RESOURCE_PATH = "resources/";
    private static final String START_FILE = "./data/startfile.xml";

    private ToggleButton playButton;
    private ToggleButton pauseButton;
    private ToggleGroup playStatus = new ToggleGroup();
    private Button stepButton;
    private Button fasterButton;
    private Button slowerButton;
    private Button fileButton;

    private ResourceBundle myResources;
    private String language = "English";

    private FileChooser fileChooser = new FileChooser();
    private File configFile;

    private Group root = new Group();
    private Simulator sim;

    /**
     * Starts the GUI and initializes a blank simulation grid
     *
     * @param stage is the window currently showing the simulation
     */
    public void start(Stage stage) {
        setStage(stage);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + language);
        sim = new Simulator();
        addUserBar(stage);
        openStartFile();
        startSimulation(stage);
    }

    /**
     * Sets up the beginning JavaFX stage with the appropriate dimensions
     *
     * @param stage is the window currently showing the simulation
     */
    private void setStage(Stage stage) {
        stage.setTitle(NAME);
        startScene = new Scene(root, XSIZE + SIDE_BAR, YSIZE + BOTTOM_BAR);
        stage.setScene(startScene);
    }

    /**
     * Adds setting bar and buttons
     * @param stage
     */
    private void addUserBar(Stage stage) {
        addSettingsBar();
        addBottomBar();
        addButtons(stage);
    }

    /**
     * Opens the blank "start" file to display when the program opens
     */
    private void openStartFile() {
        File start = new File(START_FILE);
        sim.setFile(start);
    }

    /**
     * Adds bar to the left of the simulator
     */
    private void addSettingsBar() {
        Rectangle settingsBar = new Rectangle(0, 0, SIDE_BAR, YSIZE);
        settingsBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(settingsBar);
    }

    /**
     * Adds bar to the bottom of the simulator
     */
    private void addBottomBar() {
        Rectangle bottomBar = new Rectangle (0,YSIZE, XSIZE + SIDE_BAR, BOTTOM_BAR);
        bottomBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(bottomBar);
    }

    /**
     * Adds necessary buttons to user interface
     *
     * @param stage
     */
    private void addButtons(Stage stage) {
        addPlayButton();
        addPauseButton();
        addStepButton();
        addFasterButton();
        addSlowerButton();
        addFileButton(stage);
    }

    /**
     * Adds button that allows user to play simulation
     */
    private void addPlayButton() {
        playButton = new ToggleButton(myResources.getString("Play"));
        playButton.setLayoutX(BUTTON_X);
        playButton.setLayoutY(BUTTON_SPACING);
        playButton.setToggleGroup(playStatus);
        root.getChildren().add(playButton);
        playButton.setOnAction((ActionEvent event) -> turnOn());
    }

    /**
     * Adds button that allows user to pause simulation
     */
    private void addPauseButton() {
        pauseButton = new ToggleButton(myResources.getString("Pause"));
        pauseButton.setLayoutX(BUTTON_X);
        pauseButton.setLayoutY(playButton.getLayoutY() + BUTTON_SPACING);
        pauseButton.setToggleGroup(playStatus);
        root.getChildren().add(pauseButton);
        pauseButton.setOnAction((ActionEvent event) -> turnOff());
    }

    /**
     * Adds button that allows user to manually step the simulation
     */
    private void addStepButton() {
        stepButton = new Button(myResources.getString("Step"));
        stepButton.setLayoutX(BUTTON_X);
        stepButton.setLayoutY(pauseButton.getLayoutY() + BUTTON_SPACING);
        root.getChildren().add(stepButton);
        stepButton.setOnAction((ActionEvent event) -> sim.manualStep(root));
    }

    /**
     * Adds button to speed up simulation
     */
    private void addFasterButton() {
        fasterButton = new Button(myResources.getString("Faster"));
        fasterButton.setLayoutX(BUTTON_X);
        fasterButton.setLayoutY(stepButton.getLayoutY() + BUTTON_SPACING);
        root.getChildren().add(fasterButton);
        fasterButton.setOnAction((ActionEvent event) -> sim.speedUp());
    }

    /**
     * Adds button to slow down simulation
     */
    private void addSlowerButton() {
        slowerButton = new Button(myResources.getString("Slower"));
        slowerButton.setLayoutX(BUTTON_X);
        slowerButton.setLayoutY(fasterButton.getLayoutY() + BUTTON_SPACING);
        root.getChildren().add(slowerButton);
        slowerButton.setOnAction((ActionEvent event) -> sim.speedDown());
    }

    /**
     * Adds button to upload file to user interface
     *
     * @param stage is the window currently showing the simulation
     */
    private void addFileButton(Stage stage) {
        fileButton = new Button(myResources.getString("UploadFile"));
        fileButton.setLayoutX(BUTTON_X);
        fileButton.setLayoutY(slowerButton.getLayoutY() + BUTTON_SPACING);
        root.getChildren().add(fileButton);
        fileButton.setOnAction((ActionEvent event) -> changeFile(stage));
    }

    /**
     * Selects the "play" button in the toggle group, and turns on the simulation
     */
    private void turnOn() {
        sim.turnOn();
        playStatus.selectToggle(playButton);
    }

    /**
     * Selects the "pause" button in the toggle group, and turns off the simulation
     */
    private void turnOff() {
        sim.turnOff();
        playStatus.selectToggle(pauseButton);
    }

    /**
     * Opens the file chooser for the user to choose a new file and starts the new
     * simulation
     *
     * @param stage is the window currently showing the simulation
     */
    private void changeFile(Stage stage) {
        sim.turnOff();
        openFileChooser(stage);
        startSimulation(stage);
    }

    /**
     * Opens a file chooser window for the user to select their configuration
     * XML file
     *
     * @param stage is the window currently showing the simulation
     */
    private void openFileChooser(Stage stage) {
        configFile = fileChooser.showOpenDialog(stage);
        if (configFile != null) {
            sim = new Simulator();
            sim.setFile(configFile);
        }
    }

    /**
     * Tries to start the simulation, catches exception where the simulation type is invalid and displays an error
     * @param stage is the window currently showing the simulation
     */
    private void startSimulation(Stage stage){
        try {
            sim.startSimulation(stage, root);
        }
        catch (Exception e) {
            Rectangle errorBar = new Rectangle(HALF_X - ERROR_X_ADJUSTMENT, HALF_Y - ERROR_Y_ADJUSTMENT, POPUP_BOX_WIDTH, POPUP_BOX_HEIGHT);
            errorBar.setFill(Color.DARKRED);
            Text errorText = new Text(HALF_X, HALF_Y, myResources.getString("Error"));
            errorText.setFill(Color.WHITE);
            root.getChildren().add(errorBar);
            root.getChildren().add(errorText);
        }
    }
}