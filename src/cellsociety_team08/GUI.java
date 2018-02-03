package cellsociety_team08;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GUI extends Application{

    public static final String NAME = "Cell Society";
    private Scene startScene;
    private final int XSIZE = 800;
    private final int YSIZE = 800;
    private final int YBAR = 300;

    private Group root = new Group();

    public void start(Stage stage) {
        stage.setTitle(NAME);
        startScene = new Scene(root, XSIZE, YSIZE + YBAR);
        stage.setScene(startScene);

        Simulator sim = new Simulator();
        Rectangle settingsBar = new Rectangle(0,YSIZE,XSIZE,YBAR);
        settingsBar.setFill(Color.ALICEBLUE);
        root.getChildren().add(settingsBar);
        sim.startSimulation(stage, root);

    }

    public static void main (String[] args) {
        launch(args);
    }

}
