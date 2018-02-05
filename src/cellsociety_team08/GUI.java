package cellsociety_team08;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class GUI extends Application{

    public static final String NAME = "Cell Society";
    private Scene startScene;
    private Stage myStage;
    private final int XSIZE = 600;
    private final int YSIZE = 600;
    private final int YBAR = 200;

    private final String DEFAULT_RESOURCE_PATH = "resources/";

    private ToggleButton playButton;
    private ToggleButton pauseButton;
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

    public void start(Stage stage) {
        setStage(stage);

        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PATH + language);
        sim = new Simulator();

        Rectangle settingsBar = new Rectangle(0,YSIZE,XSIZE,YBAR);
        settingsBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(settingsBar);

        openStartFile();

        sim.startSimulation(stage, root);

        addButtons(stage);
    }

    private void setStage(Stage stage) {
        myStage = stage;
        stage.setTitle(NAME);
        startScene = new Scene(root, XSIZE, YSIZE + YBAR);
        stage.setScene(startScene);
    }

    private void openStartFile() {
        File start = new File("startfile.xml");
        sim.setFile(start);
    }

    /**
     * Adds necessary buttons to user interface
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
        playButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(playButton);
        playButton.setOnAction((ActionEvent event) ->  sim.turnOn());
    }

    /**
     * Adds button that allows user to pause simulation
     */
    private void addPauseButton() {
        pauseButton = new ToggleButton(myResources.getString("Pause"));
        pauseButton.setLayoutX(playButton.getLayoutX() + 50);
        pauseButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(pauseButton);
        pauseButton.setOnAction((ActionEvent event) ->  sim.turnOff());
    }

    /**
     * Adds button that allows user to manually step the simulation
     */
    private void addStepButton() {
        stepButton = new Button(myResources.getString("Step"));
        stepButton.setLayoutX(pauseButton.getLayoutX() + 70);
        stepButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(stepButton);
        stepButton.setOnAction((ActionEvent event) ->  sim.manualStep(root));
    }

    /**
     * Adds button to speed up simulation
     */
    private void addFasterButton() {
        fasterButton = new Button(myResources.getString("Faster"));
        fasterButton.setLayoutX(stepButton.getLayoutX() + 60);
        fasterButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(fasterButton);
        fasterButton.setOnAction((ActionEvent event) ->  sim.speedUp());
    }

    /**
     * Adds button to slow down simulation
     */
    private void addSlowerButton() {
        slowerButton = new Button(myResources.getString("Slower"));
        slowerButton.setLayoutX(fasterButton.getLayoutX() + 120);
        slowerButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(slowerButton);
        slowerButton.setOnAction((ActionEvent event) ->  sim.speedDown());
    }

    /**
     * Adds button to upload file to user interface
     * @param stage is the window currently showing the simulation
     */
    private void addFileButton(Stage stage) {
        fileButton = new Button(myResources.getString("UploadFile"));
        fileButton.setLayoutX(slowerButton.getLayoutX() + 140);
        fileButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(fileButton);
        fileButton.setOnAction((ActionEvent event) -> changeFile(stage));
    }

    /**
     * Opens the file chooser for the user to choose a new file and starts the new
     * simulation
     * @param stage is the window currently showing the simulation
     */
    private void changeFile(Stage stage) {
        openFileChooser(stage);
        sim.startSimulation(stage, root);
    }

    /**
     * Opens a file chooser window for the user to select their configuration
     * XML file
     * @param stage is the window currently showing the simulation
     */
    private void openFileChooser(Stage stage) {
        configFile = fileChooser.showOpenDialog(stage);
        if (configFile != null) {
            sim.setFile(configFile);
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
