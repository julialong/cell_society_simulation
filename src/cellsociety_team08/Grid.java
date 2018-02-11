package cellsociety_team08;

import cell_controllers.CellController;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private List currentGrid;
    private int[] dimensions;

    public Grid(int[] dim) {
        dimensions[0] = dim[0];
        dimensions[1] = dim[1];


    }

    /**
     * Gets the current cell colors from the cell controller and updates the displayed grid
     * @param root the JavaFX Group root in GUI
     */
    private void updateGridColors(Group root, CellController control) {
        clearGrid(root);
        currentGrid = new ArrayList<>();
        Color[][] newColors = control.getColors();
        for (int i = 0; i < dimensions[0]; i++) {
            for (int j = 0; j < dimensions[1]; j++) {
                Rectangle currentCell = createNewCell(GUI.SIDE_BAR + i * gridWidth, GUI.TOP_BAR + j * gridHeight, gridWidth, gridHeight, newColors[i][j]);
                currentGrid.add(currentCell);
                root.getChildren().add(currentCell);
            }
        }
    }

    /**
     * Creates a new cell with the given parameters
     * @param x x position
     * @param y y position
     * @param width width of cell
     * @param height height of cell
     * @param color color of cell
     * @return
     */
    private Rectangle createNewCell(int x, int y, int width, int height, Color color) {
        Rectangle newCell = new Rectangle(x, y, width, height);
        newCell.setFill(color);
        newCell.setStroke(Color.DARKGREY);
        newCell.setStrokeType(StrokeType.INSIDE);
        return newCell;
    }

    /**
     * Removes current cells from the GUI root
     * @param root the JavaFX Group root in GUI
     */
    private void clearGrid(Group root) {
        for (Rectangle cell : currentGrid) {
            root.getChildren().remove(cell);
        }
        currentGrid = null;
    }

}
