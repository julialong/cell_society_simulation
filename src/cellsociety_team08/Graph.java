package cellsociety_team08;

import javafx.scene.Group;
import javafx.scene.chart.CategoryAxis;
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

    private Rectangle myGraph;

    private Map<String, Color> plotColors;
    private Map<String, ArrayList<Double>> steps;
    private Map<String, ArrayList<Integer>> numCells;
    private Map<String, XYChart.Series> series;

    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<CategoryAxis, NumberAxis> lineChart;
    private VBox vbox;


    public Graph() {
        myGraph = new Rectangle(GRAPH_X_SIZE, GRAPH_Y_SIZE);
        plotColors = new HashMap<>();
        steps = new HashMap<>();
        numCells = new HashMap<>();
        series = new HashMap<>();
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart(xAxis, yAxis);
    }

    public void addPoint(String cellType, int step, Integer number, Color color) {
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

    public void updateGraph(Group root) {
        vbox = new VBox();
        for (String type : series.keySet()) {
            lineChart.getData().add(series.get(type));
        }
        vbox.getChildren().add(lineChart);
        root.getChildren().add(vbox);
    }
}
