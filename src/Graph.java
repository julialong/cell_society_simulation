import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    public final int GRAPH_X_SIZE = 600;
    public final int GRAPH_Y_SIZE = 200;

    private Rectangle myGraph;
    private Color[] plotColors;

    private Map<String, ArrayList<Double>> xPositions;
    private Map<String, ArrayList<Double>> yPositions;

    public Graph(int numTypes) {
        myGraph = new Rectangle(GRAPH_X_SIZE, GRAPH_Y_SIZE);
        plotColors = new Color[numTypes];
        xPositions = new HashMap<String, ArrayList<Double>>();
        yPositions = new HashMap<String, ArrayList<Double>>();
    }

    public void addPoint(String cellType, Double x, Double y) {
        if (!xPositions.keySet().contains(cellType)) {
            xPositions.put(cellType, new ArrayList<>());
            yPositions.put(cellType, new ArrayList<>());
        }
        else {
            xPositions.get(cellType).add(x);
            yPositions.get(cellType).add(y);
        }
    }

}
