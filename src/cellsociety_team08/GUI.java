package cellsociety_team08;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUI extends Application{

    public static final String NAME = "Cell Society";
    private Scene startScene;
    private final int XSIZE = 1000;
    private final int YSIZE = 1000;

    private Group root = new Group();

    public void start(Stage stage) {
        stage.setTitle(NAME);
        startScene = new Scene(root, XSIZE, YSIZE);
        stage.setScene(startScene);

        Simulator sim = new Simulator();
        sim.startSimulation(stage, root);
    }
    


    public static void main (String[] args) {
        launch(args);
    }

}
