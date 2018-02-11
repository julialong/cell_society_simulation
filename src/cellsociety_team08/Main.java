package cellsociety_team08;

import java.util.Random;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{


    @Override
    public void start(Stage primaryStage) {
        GUI gui = new GUI();
        gui.start(primaryStage);
    }

    public void createNewStage() {
        GUI newgui = new GUI();
        newgui.start(new Stage());
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

