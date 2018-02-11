package cellsociety_team08;

import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    public final int GRAPH_X_SIZE = 600;
    public final int GRAPH_Y_SIZE = 200;

    private Map<String, Color> plotColors;
    private Map<String, ArrayList<Double>> steps;
    private Map<String, ArrayList<Integer>> numCells;
    private Map<String, XYChart.Series> series;

    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<NumberAxis, NumberAxis> lineChart;
    private VBox vbox;

    /**
     * Creates a new Graph object
     */
    public Graph() {
        plotColors = new HashMap<>();
        steps = new HashMap<>();
        numCells = new HashMap<>();
        series = new HashMap<>();

        xAxis = new NumberAxis();
        xAxis.setLabel("Step");
        yAxis = new NumberAxis();
        yAxis.setLabel("Number of cells");
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
            plotColors.put(cellType, color);
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
        vbox = new VBox();
        lineChart = new LineChart(xAxis,yAxis);
        for (String type : series.keySet()) {
            lineChart.getData().add(series.get(type));
        }
        vbox.getChildren().add(lineChart);
        vbox.setLayoutX(GUI.BUTTON_X);
        vbox.setLayoutY(500);
        vbox.setMaxHeight(200);
        vbox.setMaxWidth(600);
        root.getChildren().add(vbox);
    }
}
