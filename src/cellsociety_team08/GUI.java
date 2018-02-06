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
    public static final int XBAR = 200;
    public final int POPUP_BOX_WIDTH = 300;
    public final int POPUP_BOX_HEIGHT = 50;

    private final String DEFAULT_RESOURCE_PATH = "resources/";
    private final String START_FILE = "./data/randomlife.xml";

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
        startScene = new Scene(root, XSIZE + XBAR, YSIZE);
        stage.setScene(startScene);
    }

    /**
     * Adds setting bar and buttons
     * @param stage
     */
    private void addUserBar(Stage stage) {
        addSettingsBar();
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
        Rectangle settingsBar = new Rectangle(0, 0, XBAR, YSIZE);
        settingsBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(settingsBar);
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
        playButton.setLayoutX(20);
        playButton.setLayoutY(50);
        playButton.setToggleGroup(playStatus);
        root.getChildren().add(playButton);
        playButton.setOnAction((ActionEvent event) -> turnOn());
    }

    /**
     * Adds button that allows user to pause simulation
     */
    private void addPauseButton() {
        pauseButton = new ToggleButton(myResources.getString("Pause"));
        pauseButton.setLayoutX(20);
        pauseButton.setLayoutY(playButton.getLayoutY() + 50);
        pauseButton.setToggleGroup(playStatus);
        root.getChildren().add(pauseButton);
        pauseButton.setOnAction((ActionEvent event) -> turnOff());
    }

    /**
     * Adds button that allows user to manually step the simulation
     */
    private void addStepButton() {
        stepButton = new Button(myResources.getString("Step"));
        stepButton.setLayoutX(20);
        stepButton.setLayoutY(pauseButton.getLayoutY() + 50);
        root.getChildren().add(stepButton);
        stepButton.setOnAction((ActionEvent event) -> sim.manualStep(root));
    }

    /**
     * Adds button to speed up simulation
     */
    private void addFasterButton() {
        fasterButton = new Button(myResources.getString("Faster"));
        fasterButton.setLayoutX(20);
        fasterButton.setLayoutY(stepButton.getLayoutY() + 50);
        root.getChildren().add(fasterButton);
        fasterButton.setOnAction((ActionEvent event) -> sim.speedUp());
    }

    /**
     * Adds button to slow down simulation
     */
    private void addSlowerButton() {
        slowerButton = new Button(myResources.getString("Slower"));
        slowerButton.setLayoutX(20);
        slowerButton.setLayoutY(fasterButton.getLayoutY() + 50);
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
        fileButton.setLayoutX(20);
        fileButton.setLayoutY(slowerButton.getLayoutY() + 50);
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
            Rectangle errorBar = new Rectangle(XSIZE/2 - 50, YSIZE/2 - 25, POPUP_BOX_WIDTH, POPUP_BOX_HEIGHT);
            errorBar.setFill(Color.DARKRED);
            Text errorText = new Text(XSIZE/2, YSIZE/2, myResources.getString("Error"));
            errorText.setFill(Color.WHITE);
            root.getChildren().add(errorBar);
            root.getChildren().add(errorText);
        }
    }
}