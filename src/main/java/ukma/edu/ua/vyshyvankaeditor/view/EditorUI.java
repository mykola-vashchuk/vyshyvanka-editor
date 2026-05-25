package ukma.edu.ua.vyshyvankaeditor.view;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ukma.edu.ua.vyshyvankaeditor.model.GridCellType;

public class EditorUI {
    private final BorderPane root;

    private Canvas canvas;
    private Group canvasGroup;
    private GraphicsContext gc;
    private CheckBox horSymetry;
    private CheckBox verSymetry;
    private Button clearButton;
    private Button saveButton;
    private Button importButton;
    private Button applySizeButton;
    private Button duplicateButton;
    private Button zoomInButton;
    private Button zoomOutButton;
    private Button panUpButton;
    private Button panDownButton;
    private Button panLeftButton;
    private Button panRightButton;
    private Button resetViewButton;
    private Button toggleSidebarButton;
    private ComboBox<GridCellType> cellTypePicker;
    private ColorPicker colorPicker;
    private Spinner<Integer> gridWidthSpinner;
    private Spinner<Integer> gridHeightSpinner;
    private Spinner<Integer> patternWidthSpinner;
    private Spinner<Integer> patternHeightSpinner;
    private ScrollPane toolbarScroll;

    private final int cellSize = 15;
    private int gridWidth = 41;
    private int gridHeight = 41;

    private double zoom = 1.0;
    private double panX = 0.0;
    private double panY = 0.0;

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

        toolbar.getChildren().add(new Label("Масштаб і панорама:"));
        zoomInButton = new Button("Збільшити +");
        zoomOutButton = new Button("Зменшити -");
        panUpButton = new Button("Вгору");
        panDownButton = new Button("Вниз");
        panLeftButton = new Button("Вліво");
        panRightButton = new Button("Вправо");
        resetViewButton = new Button("Скинути вигляд");
        zoomInButton.setMaxWidth(Double.MAX_VALUE);
        zoomOutButton.setMaxWidth(Double.MAX_VALUE);
        panUpButton.setMaxWidth(Double.MAX_VALUE);
        panDownButton.setMaxWidth(Double.MAX_VALUE);
        panLeftButton.setMaxWidth(Double.MAX_VALUE);
        panRightButton.setMaxWidth(Double.MAX_VALUE);
        resetViewButton.setMaxWidth(Double.MAX_VALUE);
        toolbar.getChildren().addAll(
                zoomInButton,
                zoomOutButton,
                panUpButton,
                panDownButton,
                panLeftButton,
                panRightButton,
                resetViewButton
        );

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
        canvasGroup = new Group(canvas);

        StackPane canvasWrapper = new StackPane(canvasGroup);
        canvasWrapper.setPadding(new Insets(25));
        canvasWrapper.setStyle("-fx-background-color: #e2e2e2;");

        toolbarScroll = new ScrollPane(toolbar);
        toolbarScroll.setFitToWidth(true);
        toolbarScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        toolbarScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        toggleSidebarButton = new Button("Сховати панель");
        HBox topBar = new HBox(toggleSidebarButton);
        topBar.setPadding(new Insets(6, 10, 6, 10));

        root.setTop(topBar);
        root.setLeft(toolbarScroll);
        root.setCenter(canvasWrapper);
    }

    public void setGridSize(int width, int height) {
        gridWidth = Math.max(1, width);
        gridHeight = Math.max(1, height);
        canvas.setWidth(gridWidth * cellSize);
        canvas.setHeight(gridHeight * cellSize);
        drawGrid();
    }

    public void setSidebarVisible(boolean visible) {
        toolbarScroll.setVisible(visible);
        toolbarScroll.setManaged(visible);
    }

    public void panBy(double dx, double dy) {
        panX += dx;
        panY += dy;
        applyViewTransform();
    }

    public void zoomBy(double factor) {
        zoom = Math.max(0.5, Math.min(3.0, zoom * factor));
        applyViewTransform();
    }

    public void resetView() {
        zoom = 1.0;
        panX = 0.0;
        panY = 0.0;
        applyViewTransform();
    }

    private void applyViewTransform() {
        canvasGroup.setScaleX(zoom);
        canvasGroup.setScaleY(zoom);
        canvasGroup.setTranslateX(panX);
        canvasGroup.setTranslateY(panY);
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
    public Button getZoomInButton() { return zoomInButton; }
    public Button getZoomOutButton() { return zoomOutButton; }
    public Button getPanUpButton() { return panUpButton; }
    public Button getPanDownButton() { return panDownButton; }
    public Button getPanLeftButton() { return panLeftButton; }
    public Button getPanRightButton() { return panRightButton; }
    public Button getResetViewButton() { return resetViewButton; }
    public Button getToggleSidebarButton() { return toggleSidebarButton; }
}