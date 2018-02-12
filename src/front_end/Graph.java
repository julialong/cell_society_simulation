package front_end;

import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Creates and displays the graph that shows tha number of cells of each type,
 * excluding any "default" cells
 * @author julialong
 */
public class Graph {

    private static final int GRAPH_Y = 550;
    private static final int GRAPH_HEIGHT = 200;
    private static final int GRAPH_WIDTH = 600;

    private Map<String, ArrayList<Double>> steps;
    private Map<String, ArrayList<Integer>> numCells;
    private Map<String, XYChart.Series> series;

    private NumberAxis xAxis;
    private NumberAxis yAxis;

    private ResourceBundle myResources = ResourceBundle.getBundle(GUI.DEFAULT_RESOURCE_PATH + GUI.LANGUAGE);

    /**
     * Creates a new Graph object
     */
    public Graph() {
        steps = new HashMap<>();
        numCells = new HashMap<>();
        series = new HashMap<>();

        xAxis = new NumberAxis();
        xAxis.setLabel(myResources.getString("Steps"));
        yAxis = new NumberAxis();
        yAxis.setLabel(myResources.getString("CellNumber"));
    }

    /**
     * Adds point to the graph
     * @param cellType type of cell
     * @param step step number
     * @param number number of cells
     * @param color color of cells
     */
    public void addPoint(String cellType, int step, Integer number, Color color) {
        if (cellType.equals("default")) return;
        if (!steps.containsKey(cellType)) {
            steps.put(cellType, new ArrayList<>());
            numCells.put(cellType, new ArrayList<>());
            series.put(cellType, new XYChart.Series());
            series.get(cellType).setName(cellType);
        }
        numCells.get(cellType).add(number);
        series.get(cellType).getData().add(new XYChart.Data(step, number));
    }

    /**
     * Updates the graph and adds it to screen
     * @param root The JavaFX Group in GUI
     */
    public void updateGraph(Group root) {
        VBox vbox = new VBox();
        LineChart lineChart = new LineChart(xAxis, yAxis);
        for (String type : series.keySet()) {
            lineChart.getData().add(series.get(type));
        }
        vbox.getChildren().add(lineChart);
        vbox.setLayoutX(GUI.BUTTON_X);
        vbox.setLayoutY(GRAPH_Y);
        vbox.setMaxHeight(GRAPH_HEIGHT);
        vbox.setMaxWidth(GRAPH_WIDTH);
        root.getChildren().add(vbox);
    }
}
