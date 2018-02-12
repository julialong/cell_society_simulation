package front_end;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A cell simulator that can handle a variety of different simulations
 * @author julialong, edwardzhuang, jeffreyli
 */
public class Main extends Application{

    /**
     * Launches the UI
     * @param primaryStage the stage to open the simulation
     */
    @Override
    public void start(Stage primaryStage) {
        GUI gui = new GUI();
        gui.start(primaryStage);
    }

    /**
     * Open the program and start the simulation
     */
    public static void main(String[] args) {
        launch(args);
    }
}

