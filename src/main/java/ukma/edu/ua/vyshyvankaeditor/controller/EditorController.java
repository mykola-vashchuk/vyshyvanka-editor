package ukma.edu.ua.vyshyvankaeditor.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ukma.edu.ua.vyshyvankaeditor.model.GridData;
import ukma.edu.ua.vyshyvankaeditor.view.EditorUI;

public class EditorController {
    private final GridData model;
    private final EditorUI view;

    public EditorController(GridData model, EditorUI view) {
        this.model = model;
        this.view = view;
        initInteractions();
    }

    private void initInteractions() {
        view.getCanvas().setOnMouseClicked(this::handleCanvasClick);

        view.getCanvas().setOnMouseDragged(this::handleCanvasClick);

        view.getClearButton().setOnAction(event -> {
            model.clearGrid();
            view.drawGrid();
        });
    }

    private void handleCanvasClick(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        int x =  (int) (mouseX / view.getCellSize());
        int y = (int) (mouseY  / view.getCellSize());

        if (x >= 0 && x < model.getWidth() && y >= 0 && y < model.getHeight()) {
            Color selectedColor = view.getColorPicker().getValue();
            model.setCellColor(x, y, selectedColor);
            drawCell(x, y, selectedColor);
        }
    }

    private void drawCell(int x, int y, Color color) {
        GraphicsContext gc = view.getCanvas().getGraphicsContext2D();
        int size = view.getCellSize();

        gc.setFill(Color.WHITE);
        gc.fillRect(x * size, y * size, size, size);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        gc.strokeRect(x * size, y * size, size, size);

        if(!color.equals(Color.WHITE)) {
            gc.setStroke(color);
            gc.setLineWidth(1.75);
            double xLeft = x * size;
            double yTop = y * size;
            double xRight = (x + 1) * size;
            double yBottom = (y + 1) * size;
            gc.strokeLine(xLeft, yTop, xRight, yBottom);
            gc.strokeLine(xRight, yTop, xLeft, yBottom);
        }
    }
}