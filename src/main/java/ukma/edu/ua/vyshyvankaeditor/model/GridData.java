package ukma.edu.ua.vyshyvankaeditor.model;

import javafx.scene.paint.Color;

public class GridData {
    private final int width = 41;
    private final int height = 41;

    private final Color[][] gridColors;
    private final GridCellType[][] gridTypes;

    public GridData() {
        gridColors = new Color[width][height];
        gridTypes = new GridCellType[width][height];
        clearGrid();
    }

    public void clearGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gridColors[x][y] = Color.WHITE;
                gridTypes[x][y] = GridCellType.CROSS_STITCH;
            }
        }
    }

    public void setCell(int x, int y, Color color, GridCellType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            gridColors[x][y] = color;
            gridTypes[x][y] = type;
        }
    }


    public GridCellType getCellType(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return gridTypes[x][y];
        }
        return GridCellType.CROSS_STITCH;
    }

    public Color getCellColor(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return gridColors[x][y];
        }
        return Color.WHITE;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}