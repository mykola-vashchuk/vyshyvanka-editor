package ukma.edu.ua.vyshyvankaeditor.view;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.*;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EditorUI {
    private final BorderPane root;

    private Canvas canvas;
    private GraphicsContext gc;
    private CheckBox horSymetry;
    private CheckBox verSymetry;
    private Button clearButton;
    private Button saveButton;

    private final int cellSize = 15;
    private final int gridWidth = 40;
    private final int gridHeight = 40;

    public EditorUI() {
        root = new BorderPane();
        buildUI();
        drawGrid();
    }

    private void drawGrid() {
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, w, h);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        for (int i = 0; i <= gridWidth; i++) {
            double x = i * cellSize;
            gc.strokeLine(x, 0, x, h);
        }

        for (int i = 0; i <= gridHeight; i++) {
            double y = i * cellSize;
            gc.strokeLine(0, y, w, y);
        }
    }

    private void buildUI() {
        VBox toolbar = new VBox(15);
        toolbar.setPadding(new Insets(15));
        toolbar.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-width: 0 1 0 0;");

        toolbar.getChildren().add(new Label("Колір нитки:"));
        ColorPicker colorPicker = new ColorPicker(Color.RED);
        toolbar.getChildren().add(colorPicker);

        toolbar.getChildren().add(new Label("Симетрія"));
        horSymetry = new CheckBox("Горизонтальна");
        verSymetry = new CheckBox("Вертикальна");
        toolbar.getChildren().addAll(horSymetry,  verSymetry);

        toolbar.getChildren().add(new Label("Управління:"));
        clearButton = new Button("Очмстити");
        saveButton = new Button("Зберегти PNG");
        clearButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        toolbar.getChildren().addAll(clearButton, saveButton);

        double canvasWidth = gridWidth * cellSize;
        double canvasHeight = gridHeight * cellSize;
        canvas = new Canvas(canvasWidth, canvasHeight);
        gc = canvas.getGraphicsContext2D();

        StackPane canvasWrapper = new StackPane(canvas);
        canvasWrapper.setPadding(new Insets(25));
        canvasWrapper.setStyle("-fx-background-color: #e2e2e2;");

        root.setLeft(toolbar);
        root.setCenter(canvas);


    }

    public BorderPane getRoot() {
        return root;
    }
}