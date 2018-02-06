package cellsociety_team08;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{


    @Override
    public void start(Stage primaryStage) {
        GUI gui = new GUI();
        gui.start(primaryStage);
    }

    /**
     * Open the program and start the simulation
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

