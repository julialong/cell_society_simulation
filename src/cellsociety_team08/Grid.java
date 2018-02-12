package cellsociety_team08;

import cell_controllers.CellController;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private List<Polygon> currentGrid;
    private int[] dimensions;
    private int gridWidth;
    private int gridHeight;

    public Grid(int[] dim, int width, int height) {
        dimensions = new int[2];
        currentGrid = new ArrayList<>();
        dimensions[0] = dim[0];
        dimensions[1] = dim[1];
        gridWidth = width;
        gridHeight = height;
    }

    /**
     * Gets the current cell colors from the cell controller and updates the displayed grid
     * @param root the JavaFX Group root in GUI
     */
    public void updateGridColors(Group root, CellController control) {
        clearGrid(root);
        currentGrid = new ArrayList<>();
        Color[][] newColors = control.getColors();
        for (int i = 0; i < dimensions[0]; i++) {
            for (int j = 0; j < dimensions[1]; j++) {
                Polygon currentCell = createNewCell(GUI.SIDE_BAR + i * gridWidth, GUI.TOP_BAR + j * gridHeight, newColors[i][j]);
                currentGrid.add(currentCell);
                root.getChildren().add(currentCell);
            }
        }
    }

    /**
     * Creates a new cell with the given parameters
     * @param x x position
     * @param y y position
     * @param color color of cell
     * @return
     */
    private Polygon createNewCell(int x, int y, Color color) {
        Polygon newCell = createTriangle(x, y);
        newCell.setFill(color);
        newCell.setStroke(Color.DARKGREY);
        newCell.setStrokeType(StrokeType.INSIDE);
        return newCell;
    }

    private Polygon createSquare(int x, int y) {
        Polygon square = new Polygon();
        double i = x;
        double j = y;
        square.getPoints().addAll(i, j, i, j + gridHeight, i + gridWidth, j + gridHeight, i + gridWidth, j);
        return square;
    }

    private Polygon createTriangle(int x, int y) {
        Polygon triangle = new Polygon();
        double i = x;
        double j = y;
        if ((x + y) % 2 == 0) triangle.getPoints().addAll(i, j, i + gridWidth /2, j + gridHeight, i + gridWidth, j);
        else triangle.getPoints().addAll( i , j, i - gridWidth /2, j + gridHeight, i + gridWidth /2, j + gridHeight);
        return triangle;
    }

    /**
     * Removes current cells from the GUI root
     * @param root the JavaFX Group root in GUI
     */
    private void clearGrid(Group root) {
        for (Polygon cell : currentGrid) {
            root.getChildren().remove(cell);
        }
        currentGrid = null;
    }

}
