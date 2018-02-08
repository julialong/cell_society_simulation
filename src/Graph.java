import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Graph {

    public final int GRAPH_X_SIZE = 600;
    public final int GRAPH_Y_SIZE = 200;

    private Rectangle myGraph;
    private Color[] plotColors;

    private int[] currentX;
    private int[] currentY;

    public Graph(int numTypes) {
        myGraph = new Rectangle(GRAPH_X_SIZE, GRAPH_Y_SIZE);
        plotColors = new Color[numTypes];
    }

    public void addPoint() {

    }

}
