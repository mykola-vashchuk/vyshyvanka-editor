package ukma.edu.ua.vyshyvankaeditor.model;

import javafx.scene.paint.Color;

public class GridData {
    private final int width = 40;
    private final int height = 40;

    private final Color[][] grid;

    public GridData() {
        grid = new Color[width][height];
        clearGrid();
    }

    public void clearGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = Color.WHITE;
            }
        }
    }

    public void setCellColor(int x, int y, Color color) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[x][y] = color;
        }
    }

    public Color getCellColor(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[x][y];
        }
        return Color.WHITE;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}