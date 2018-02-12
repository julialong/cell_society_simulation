package front_end;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The basic user interface of the simulator, including buttons
 * and sliders
 * Websites used: http://code.makery.ch/blog/javafx-dialogs-official/
 * @author julialong, edwardzhuang
 */
public class GUI {

    private static final String NAME = "Cell Society";
    public static final int XSIZE = 600;
    public static final int YSIZE = 600;
    public static final int SIDE_BAR = 600;
    public static final int TOP_BAR = 50;
    private static final int RIGHT_SIDE_BAR = 50;
    private static final int BOTTOM_BAR = 200;
    public static final int BUTTON_X = 20;
    private static final int BUTTON_SPACING = 50;
    private static final int LARGE_BUTTON_SPACING = 150;
    private static final int JUMBO_BUTTON_SPACING = 250;

    public static final String DEFAULT_RESOURCE_PATH = "resources/";
    public static final String LANGUAGE = "English";
    private static final String START_FILE = "./data/startfile.xml";

    private ToggleButton playButton;
    private ToggleButton pauseButton;
    private ToggleGroup playStatus = new ToggleGroup();
    private Button stepButton;
    private Button fasterButton;
    private Button slowerButton;
    private Button fileButton;
    private ToggleButton toroidalButton;
    private Slider slider;

    private ResourceBundle myResources;

    private FileChooser fileChooser = new FileChooser();

    private Group root = new Group();
    private Simulator mySimulator;

    /**
     * Starts the GUI and initializes a blank simulation grid
     *
     * @param stage is the window currently showing the simulation
     */
    public void start(Stage stage) {
        setStage(stage);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + LANGUAGE);
        mySimulator = new Simulator();
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
        Scene startScene = new Scene(root, XSIZE + SIDE_BAR + RIGHT_SIDE_BAR, YSIZE + BOTTOM_BAR);
        stage.setScene(startScene);
    }

    /**
     * Adds setting bar and buttons
     * @param stage current JavaFX stage
     */
    private void addUserBar(Stage stage) {
        addSettingsBar();
        addBottomBar();
        addTopBar();
        addRightBar();
        addBlockBar();
        addTitle();
        addButtons(stage);
    }

    /**
     * Opens the blank "start" file to display when the program opens
     */
    private void openStartFile() {
        File start = new File(START_FILE);
        mySimulator.setFile(start);
    }

    /**
     * Adds bar to the left of the simulator
     */
    private void addSettingsBar() {
        Rectangle settingsBar = new Rectangle(0, 0, SIDE_BAR, YSIZE);
        settingsBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(settingsBar);
    }

    private void addBlockBar() {
        Rectangle blockBar = new Rectangle(SIDE_BAR, 0, XSIZE + RIGHT_SIDE_BAR, TOP_BAR + YSIZE + BOTTOM_BAR);
        blockBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(blockBar);
    }

    /**
     * Adds bar to the bottom of the simulator
     */
    private void addBottomBar() {
        Rectangle bottomBar = new Rectangle (0,YSIZE, XSIZE + SIDE_BAR + RIGHT_SIDE_BAR, BOTTOM_BAR);
        bottomBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(bottomBar);
    }

    /**
     * Adds bar to the top of the simulator
     */
    private void addTopBar() {
        Rectangle topBar = new Rectangle(0, 0, XSIZE + SIDE_BAR, TOP_BAR);
        topBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(topBar);
    }

    /**
     * Adds bar to the right of the simulator
     */
    private void addRightBar() {
        Rectangle rightBar = new Rectangle(SIDE_BAR + XSIZE, 0, RIGHT_SIDE_BAR, YSIZE);
        rightBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(rightBar);
    }

    /**
     * Adds necessary buttons to user interface
     *
     * @param stage current stage
     */
    private void addButtons(Stage stage) {
        addFileButton(stage);
        addPlayButton();
        addPauseButton();
        addStepButton();
        addFasterButton();
        addSlowerButton();
        addToroidalButton();
        addXMLWriterButton();
    }

    /**
     * Adds slider to change dimensions of simulation
     */
    private void addDimensionSlider() {
        Text sliderText = new Text(BUTTON_X, fasterButton.getLayoutY() + 10 + BUTTON_SPACING, myResources.getString("SetDimensions"));
        root.getChildren().add(sliderText);

        slider = new Slider();
        slider.setMin(10);
        slider.setMax(50);
        slider.setValue(mySimulator.getDimensions());
        slider.setMajorTickUnit(10);

        slider.setLayoutX(BUTTON_X);
        slider.setLayoutY(fasterButton.getLayoutY() + 20 + BUTTON_SPACING);
    	  
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMinWidth(300);
        slider.setSnapToTicks(true);
        root.getChildren().add(slider);

        addSliderButton();
    }

    /**
     * Resizes the simulator grid
     */
    private void resize() {
        mySimulator.resize((int) slider.getValue(), root);
    }

    /**
     * Adds Cell Society title to window
     */
    private void addTitle() {
        Text titleText = new Text(SIDE_BAR/2 - LARGE_BUTTON_SPACING, TOP_BAR, "Cell Society");
        titleText.setFill(Color.DARKRED);
        titleText.setFont(Font.font(40));
        root.getChildren().add(titleText);
    }

    /**
     * Adds button to upload file to user interface
     *
     * @param stage is the window currently showing the simulation
     */
    private void addFileButton(Stage stage) {
        fileButton = new Button(myResources.getString("UploadFile"));
        fileButton.setLayoutX(BUTTON_X);
        fileButton.setLayoutY(TOP_BAR + BUTTON_SPACING);
        root.getChildren().add(fileButton);
        fileButton.setOnAction((ActionEvent event) -> changeFile(stage));
    }

    /**
     * Adds button that allows user to play simulation
     */
    private void addPlayButton() {
        playButton = new ToggleButton(myResources.getString("Play"));
        playButton.setLayoutX(BUTTON_X);
        playButton.setLayoutY(fileButton.getLayoutY() + BUTTON_SPACING);
        playButton.setToggleGroup(playStatus);
        root.getChildren().add(playButton);
        playButton.setOnAction((ActionEvent event) -> turnOn());
    }

    /**
     * Adds button that allows user to pause simulation
     */
    private void addPauseButton() {
        pauseButton = new ToggleButton(myResources.getString("Pause"));
        pauseButton.setLayoutX(playButton.getLayoutX() + BUTTON_SPACING + BUTTON_SPACING);
        pauseButton.setLayoutY(playButton.getLayoutY());
        pauseButton.setToggleGroup(playStatus);
        root.getChildren().add(pauseButton);
        pauseButton.setOnAction((ActionEvent event) -> turnOff());
    }

    /**
     * Adds button that allows user to manually step the simulation
     */
    private void addStepButton() {
        stepButton = new Button(myResources.getString("Step"));
        stepButton.setLayoutX(pauseButton.getLayoutX() + BUTTON_SPACING + BUTTON_SPACING);
        stepButton.setLayoutY(pauseButton.getLayoutY());
        root.getChildren().add(stepButton);
        stepButton.setOnAction((ActionEvent event) -> mySimulator.manualStep(root));
    }

    /**
     * Adds button to speed up simulation
     */
    private void addFasterButton() {
        fasterButton = new Button(myResources.getString("Faster"));
        fasterButton.setLayoutX(BUTTON_X);
        fasterButton.setLayoutY(stepButton.getLayoutY() + BUTTON_SPACING);
        root.getChildren().add(fasterButton);
        fasterButton.setOnAction((ActionEvent event) -> mySimulator.speedUp());
    }

    /**
     * Adds button to slow down simulation
     */
    private void addSlowerButton() {
        slowerButton = new Button(myResources.getString("Slower"));
        slowerButton.setLayoutX(fasterButton.getLayoutX() + LARGE_BUTTON_SPACING);
        slowerButton.setLayoutY(fasterButton.getLayoutY());
        root.getChildren().add(slowerButton);
        slowerButton.setOnAction((ActionEvent event) -> mySimulator.speedDown());
    }

    private void addToroidalButton() {
        toroidalButton = new ToggleButton(myResources.getString("Toroidal"));
        toroidalButton.setLayoutX(BUTTON_X);
        toroidalButton.setLayoutY(slowerButton.getLayoutY() + JUMBO_BUTTON_SPACING);
        root.getChildren().add(toroidalButton);
        toroidalButton.setOnAction((ActionEvent event) -> mySimulator.switchToroidal());
    }

    /**
     * Adds button to write new XML file
     */
    private void addXMLWriterButton() {
        Button toXMLButton = new Button(myResources.getString("Write"));
        toXMLButton.setLayoutX(BUTTON_X);
        toXMLButton.setLayoutY(toroidalButton.getLayoutY() + BUTTON_SPACING);
        root.getChildren().add(toXMLButton);
        toXMLButton.setOnAction((ActionEvent event) -> promptForFilename());
    }

    /**
     * Adds button to set slider dimensions
     */
    private void addSliderButton() {
        Button sliderButton = new Button(myResources.getString("Set"));
        sliderButton.setLayoutX(BUTTON_X + slider.getMinWidth() + BUTTON_SPACING);
        sliderButton.setLayoutY(slider.getLayoutY());
        root.getChildren().add(sliderButton);
        sliderButton.setOnAction((ActionEvent event) -> {
            addBlockBar();
            resize();
        });
    }

    /**
     * Selects the "play" button in the toggle group, and turns on the simulation
     */
    private void turnOn() {
        mySimulator.turnOn();
        playStatus.selectToggle(playButton);
    }

    /**
     * Selects the "pause" button in the toggle group, and turns off the simulation
     */
    private void turnOff() {
        mySimulator.turnOff();
        playStatus.selectToggle(pauseButton);
    }

    /**
     * Opens the file chooser for the user to choose a new file and starts the new
     * simulation
     *
     * @param stage is the window currently showing the simulation
     */
    private void changeFile(Stage stage) {
        mySimulator.turnOff();
        root.getChildren().remove(slider);
        openFileChooser(stage);
        addUserBar(stage);
        startSimulation(stage);
    }

    /**
     * Opens a file chooser window for the user to select their configuration
     * XML file
     *
     * @param stage is the window currently showing the simulation
     */
    private void openFileChooser(Stage stage) {
        File configFile = fileChooser.showOpenDialog(stage);
        if (configFile != null) {
            mySimulator = new Simulator();
            mySimulator.setFile(configFile);
        }
    }

    /**
     * Prompts user for the filename of the XML file they want to write
     */
    private void promptForFilename() {
        TextInputDialog prompt = new TextInputDialog("newfile");
        prompt.setTitle("Write to file");
        prompt.setHeaderText("Please enter the name of your configuration file.");
        Optional<String> result = prompt.showAndWait();
        result.ifPresent(filename -> mySimulator.toXML(filename));
    }

    /**
     * Tries to start the simulation, catches exception where the simulation type is invalid and displays an error
     * @param stage is the window currently showing the simulation
     */
    private void startSimulation(Stage stage){
        try {
            mySimulator.startSimulation(stage, root);
            addDimensionSlider();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(myResources.getString("Error"));
            alert.setHeaderText(myResources.getString("ErrorType"));
            alert.setContentText(myResources.getString("Error"));
            alert.showAndWait();
        }
    }
}