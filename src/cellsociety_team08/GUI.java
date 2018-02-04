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

public class GUI extends Application{

    public static final String NAME = "Cell Society";
    private Scene startScene;
    private Stage myStage;
    private final int XSIZE = 800;
    private final int YSIZE = 800;
    private final int YBAR = 300;

    private ToggleButton playButton;
    private ToggleButton pauseButton;
    private Button fasterButton;
    private Button slowerButton;
    private Button fileButton;

    private FileChooser fileChooser = new FileChooser();
    private File configFile;

    private Group root = new Group();
    private Simulator sim;

    public void start(Stage stage) {
        myStage = stage;
        stage.setTitle(NAME);
        startScene = new Scene(root, XSIZE, YSIZE + YBAR);
        stage.setScene(startScene);

        sim = new Simulator();

        // I am trying to place a rectangle on the screen to improve the visuals with the
        // buttons, but this causes nothing to show up
        Rectangle settingsBar = new Rectangle(0,YSIZE - YBAR,XSIZE,YBAR);
        settingsBar.setFill(Color.LIGHTGRAY);
        root.getChildren().add(settingsBar);

        // I am having trouble with this -- The steps of the simulation are overriding
        // the buttons for the GUI once the simulation begins
        sim.startSimulation(stage, root);

        addButtons(stage);


    }

    private void addButtons(Stage stage) {
        addPlayButton();
        addPauseButton();
        addFasterButton();
        addSlowerButton();
        addFileButton(stage);
    }

    private void addPlayButton() {
        playButton = new ToggleButton("Play");
        playButton.setLayoutX(20);
        playButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(playButton);
        playButton.setOnAction((ActionEvent event) ->  sim.turnOn());
    }

    private void addPauseButton() {
        pauseButton = new ToggleButton("Pause");
        pauseButton.setLayoutX(playButton.getLayoutX() + 50);
        pauseButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(pauseButton);
        pauseButton.setOnAction((ActionEvent event) ->  sim.turnOff());
    }

    private void addFasterButton() {
        fasterButton = new Button("Increase speed");
        fasterButton.setLayoutX(pauseButton.getLayoutX() + 60);
        fasterButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(fasterButton);
        fasterButton.setOnAction((ActionEvent event) ->  sim.speedUp());
    }

    private void addSlowerButton() {
        slowerButton = new Button("Decrease speed");
        slowerButton.setLayoutX(fasterButton.getLayoutX() + 120);
        slowerButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(slowerButton);
        slowerButton.setOnAction((ActionEvent event) ->  sim.speedDown());
    }

    private void addFileButton(Stage stage) {
        fileButton = new Button("Upload configuration");
        fileButton.setLayoutX(slowerButton.getLayoutX() + 140);
        fileButton.setLayoutY(YSIZE + 10);
        root.getChildren().add(fileButton);
        fileButton.setOnAction((ActionEvent event) ->  openFileChooser(stage));
    }

    private void openFileChooser(Stage stage) {
        configFile = fileChooser.showOpenDialog(stage);
        if (configFile != null) {
            // TODO: read file
        }
    }

    public static void main (String[] args) {
        launch(args);
    }
}
