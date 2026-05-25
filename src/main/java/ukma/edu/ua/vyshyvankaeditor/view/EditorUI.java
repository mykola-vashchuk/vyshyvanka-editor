package ukma.edu.ua.vyshyvankaeditor.view;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ukma.edu.ua.vyshyvankaeditor.model.GridCellType;

public class EditorUI {
    private final BorderPane root;

    private Canvas canvas;
    private GraphicsContext gc;
    private CheckBox horSymetry;
    private CheckBox verSymetry;
    private Button clearButton;
    private Button saveButton;
    private Button importButton;
    private Button applySizeButton;
    private Button duplicateButton;
    private ComboBox<GridCellType> cellTypePicker;
    private ColorPicker colorPicker;
    private Spinner<Integer> gridWidthSpinner;
    private Spinner<Integer> gridHeightSpinner;
    private Spinner<Integer> patternWidthSpinner;
    private Spinner<Integer> patternHeightSpinner;

    private final int cellSize = 15;
    private int gridWidth = 41;
    private int gridHeight = 41;

    public EditorUI() {
        root = new BorderPane();
        buildUI();
        drawGrid();
    }

    public void drawGrid() {
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
        colorPicker = new ColorPicker(Color.RED);
        toolbar.getChildren().add(colorPicker);

        toolbar.getChildren().add(new Label("Тип стібка:"));
        cellTypePicker = new ComboBox<>();
        cellTypePicker.getItems().addAll(GridCellType.values());
        cellTypePicker.setValue(GridCellType.CROSS_STITCH);
        cellTypePicker.setMaxWidth(Double.MAX_VALUE);
        toolbar.getChildren().add(cellTypePicker);

        toolbar.getChildren().add(new Label("Симетрія:"));
        horSymetry = new CheckBox("Горизонтальна");
        verSymetry = new CheckBox("Вертикальна");
        toolbar.getChildren().addAll(horSymetry, verSymetry);

        toolbar.getChildren().add(new Label("Розмір сітки (W × H):"));
        gridWidthSpinner = new Spinner<>();
        gridWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 101, gridWidth));
        gridWidthSpinner.setEditable(true);
        gridHeightSpinner = new Spinner<>();
        gridHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 101, gridHeight));
        gridHeightSpinner.setEditable(true);
        applySizeButton = new Button("Змінити розмір");
        applySizeButton.setMaxWidth(Double.MAX_VALUE);
        toolbar.getChildren().addAll(gridWidthSpinner, gridHeightSpinner, applySizeButton);

        toolbar.getChildren().add(new Label("Дублювання фрагмента (W × H):"));
        patternWidthSpinner = new Spinner<>();
        patternWidthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, gridWidth, 7));
        patternWidthSpinner.setEditable(true);
        patternHeightSpinner = new Spinner<>();
        patternHeightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, gridHeight, 7));
        patternHeightSpinner.setEditable(true);
        duplicateButton = new Button("Дублювати фрагмент");
        duplicateButton.setMaxWidth(Double.MAX_VALUE);
        toolbar.getChildren().addAll(patternWidthSpinner, patternHeightSpinner, duplicateButton);

        toolbar.getChildren().add(new Label("Управління:"));
        clearButton = new Button("Очистити");
        saveButton = new Button("Зберегти PNG");
        importButton = new Button("Імпорт PNG");
        clearButton.setMaxWidth(Double.MAX_VALUE);
        saveButton.setMaxWidth(Double.MAX_VALUE);
        importButton.setMaxWidth(Double.MAX_VALUE);
        toolbar.getChildren().addAll(clearButton, saveButton, importButton);

        double canvasWidth = gridWidth * cellSize;
        double canvasHeight = gridHeight * cellSize;
        canvas = new Canvas(canvasWidth, canvasHeight);
        gc = canvas.getGraphicsContext2D();

        StackPane canvasWrapper = new StackPane(canvas);
        canvasWrapper.setPadding(new Insets(25));
        canvasWrapper.setStyle("-fx-background-color: #e2e2e2;");

        root.setLeft(toolbar);
        root.setCenter(canvasWrapper);
    }

    public void setGridSize(int width, int height) {
        gridWidth = Math.max(1, width);
        gridHeight = Math.max(1, height);
        canvas.setWidth(gridWidth * cellSize);
        canvas.setHeight(gridHeight * cellSize);
        drawGrid();
    }

    public BorderPane getRoot() { return root; }
    public Canvas getCanvas() { return canvas; }
    public ColorPicker getColorPicker() { return colorPicker; }
    public Button getClearButton() { return clearButton; }
    public Button getSaveButton() { return saveButton; }
    public CheckBox getHorSymetry() { return horSymetry; }
    public CheckBox getVerSymetry() { return verSymetry; }
    public ComboBox<GridCellType> getCellTypePicker() { return cellTypePicker; }
    public int getCellSize() { return cellSize; }
    public Button getImportButton() { return importButton; }
    public Spinner<Integer> getGridWidthSpinner() { return gridWidthSpinner; }
    public Spinner<Integer> getGridHeightSpinner() { return gridHeightSpinner; }
    public Spinner<Integer> getPatternWidthSpinner() { return patternWidthSpinner; }
    public Spinner<Integer> getPatternHeightSpinner() { return patternHeightSpinner; }
    public Button getApplySizeButton() { return applySizeButton; }
    public Button getDuplicateButton() { return duplicateButton; }
}