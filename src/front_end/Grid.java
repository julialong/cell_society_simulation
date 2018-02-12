package front_end;

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
                Polygon currentCell = createNewCell(i , j, GUI.SIDE_BAR + i * gridWidth, GUI.TOP_BAR + j * gridHeight, newColors[i][j]);
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
    private Polygon createNewCell(int xIndex, int yIndex, int x, int y, Color color) {
        //Polygon newCell = createTriangle(xIndex, yIndex, x, y);
        Polygon newCell = createSquare(x, y);
        //Polygon newCell = createHexagon(yIndex,x,y);
        newCell.setFill(color);
        newCell.setStroke(Color.DARKGREY);
        newCell.setStrokeType(StrokeType.INSIDE);
        return newCell;
    }

    /**
     * Creates square shaped cell
     * @param x x position of cell
     * @param y y y position of cell
     * @return new square cell
     */
    private Polygon createSquare(int x, int y) {
        Polygon square = new Polygon();
        double i = x;
        double j = y;
        square.getPoints().addAll(i, j, i, j + gridHeight, i + gridWidth, j + gridHeight, i + gridWidth, j);
        return square;
    }

    /**
     * Creates triangle shaped cell
     * @param xIndex current column of cell
     * @param yIndex current row of cell
     * @param x x position of cell
     * @param y y position of cell
     * @return new triangular cell
     */
    private Polygon createTriangle(int xIndex, int yIndex, double x, double y) {
        Polygon triangle = new Polygon();
        if (yIndex % 2 != 0)  x = x + gridWidth/2;
        if (xIndex != 0) triangle.getPoints().addAll( x , y, x - gridWidth /2, y + gridHeight, x + gridWidth /2, y + gridHeight);
        triangle.getPoints().addAll(x, y, x + gridWidth /2, y + gridHeight, x + gridWidth, y);
        return triangle;
    }

    /**
     * Creates hexagon shaped cell
     * @param yIndex current row of cell
     * @param x x position of cell
     * @param y y position of cells
     * @return new hexagonal cell
     */
    private Polygon createHexagon(int yIndex, int x, int y) {
        Polygon hexagon = new Polygon();
        double i = x;
        double j = y;
        if (yIndex % 2 != 0)  i = i + gridWidth/2;
        hexagon.getPoints().addAll(i, j,
                i + gridWidth/2, j - gridHeight/2,
                i + gridWidth, j,
                i + gridWidth, j + gridHeight/2,
                i + gridWidth/2, j + gridHeight,
                i, j + gridHeight/2);
        return hexagon;
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
