package ukma.edu.ua.vyshyvankaeditor.controller;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.SpinnerValueFactory;
import ukma.edu.ua.vyshyvankaeditor.model.GridCellType;
import ukma.edu.ua.vyshyvankaeditor.model.GridData;
import ukma.edu.ua.vyshyvankaeditor.model.NameGenerator;
import ukma.edu.ua.vyshyvankaeditor.view.EditorUI;

public class EditorController {
    private GridData model;
    private final EditorUI view;
    private boolean sidebarVisible = true;

    public EditorController(GridData model, EditorUI view) {
        this.model = model;
        this.view = view;
        initInteractions();

        NameGenerator.applyNamePattern(this.model);
        redrawWholeGrid();
    }

    private void initInteractions() {
        view.getCanvas().setOnMouseClicked(this::handleCanvasClick);
        view.getCanvas().setOnMouseDragged(this::handleCanvasClick);

        view.getToggleSidebarButton().setOnAction(event -> {
            sidebarVisible = !sidebarVisible;
            view.setSidebarVisible(sidebarVisible);
            view.getToggleSidebarButton().setText(sidebarVisible ? "Сховати панель" : "Показати панель");
        });

        view.getClearButton().setOnAction(event -> {
            model.clearGrid();
            redrawWholeGrid();
        });

        view.getApplySizeButton().setOnAction(event -> {
            int newWidth = view.getGridWidthSpinner().getValue();
            int newHeight = view.getGridHeightSpinner().getValue();
            model = new GridData(newWidth, newHeight);
            view.setGridSize(newWidth, newHeight);
            view.getPatternWidthSpinner().setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, newWidth, Math.min(7, newWidth))
            );
            view.getPatternHeightSpinner().setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, newHeight, Math.min(7, newHeight))
            );
            redrawWholeGrid();
        });

        view.getDuplicateButton().setOnAction(event -> duplicatePattern());

        view.getZoomInButton().setOnAction(event -> view.zoomBy(1.1));
        view.getZoomOutButton().setOnAction(event -> view.zoomBy(1.0 / 1.1));
        view.getPanUpButton().setOnAction(event -> view.panBy(0, -20));
        view.getPanDownButton().setOnAction(event -> view.panBy(0, 20));
        view.getPanLeftButton().setOnAction(event -> view.panBy(-20, 0));
        view.getPanRightButton().setOnAction(event -> view.panBy(20, 0));
        view.getResetViewButton().setOnAction(event -> view.resetView());

        view.getSaveButton().setOnAction(event -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Зберегти вишивку");
            fileChooser.getExtensionFilters().add(
                    new javafx.stage.FileChooser.ExtensionFilter("PNG файли (*.png)", "*.png")
            );
            java.io.File initialDirectory = getInitialDirectory();
            if (initialDirectory != null) {
                fileChooser.setInitialDirectory(initialDirectory);
            }

            java.io.File file = fileChooser.showSaveDialog(view.getCanvas().getScene().getWindow());
            if (file != null) {
                try {
                    javafx.scene.image.WritableImage writableImage = new javafx.scene.image.WritableImage(
                            (int) view.getCanvas().getWidth(),
                            (int) view.getCanvas().getHeight()
                    );
                    view.getCanvas().snapshot(null, writableImage);

                    java.awt.image.BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(writableImage, null);
                    javax.imageio.ImageIO.write(bufferedImage, "png", file);
                    System.out.println("Збережено в: " + file.getAbsolutePath());
                } catch (java.io.IOException e) {
                    System.err.println("Помилка під час збереження PNG: " + e.getMessage());
                }
            }
        });

        view.getImportButton().setOnAction(event -> {
            javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
            fileChooser.setTitle("Відкрити схему вишивки");
            fileChooser.getExtensionFilters().addAll(
                    new javafx.stage.FileChooser.ExtensionFilter("PNG файли (*.png)", "*.png"),
                    new javafx.stage.FileChooser.ExtensionFilter("Усі файли", "*.*")
            );
            java.io.File initialDirectory = getInitialDirectory();
            if (initialDirectory != null) {
                fileChooser.setInitialDirectory(initialDirectory);
            }

            java.io.File file = fileChooser.showOpenDialog(view.getCanvas().getScene().getWindow());
            if (file != null) {
                try {
                    java.awt.image.BufferedImage bufferedImage = javax.imageio.ImageIO.read(file);
                    if (bufferedImage == null) {
                        System.err.println("Файл не є коректним PNG-зображенням: " + file.getAbsolutePath());
                        return;
                    }

                    model.clearGrid();
                    int size = view.getCellSize();
                    GridCellType importType = view.getCellTypePicker().getValue();

                    for (int x = 0; x < model.getWidth(); x++) {
                        for (int y = 0; y < model.getHeight(); y++) {
                            int pixelX = x * size + (size / 2);
                            int pixelY = y * size + (size / 2);

                            if (pixelX < bufferedImage.getWidth() && pixelY < bufferedImage.getHeight()) {
                                int rgb = bufferedImage.getRGB(pixelX, pixelY);
                                int r = (rgb >> 16) & 0xFF;
                                int g = (rgb >> 8) & 0xFF;
                                int b = rgb & 0xFF;

                                Color fxColor = Color.rgb(r, g, b);

                                if (!fxColor.equals(Color.WHITE) && !(r > 200 && g > 200 && b > 200 && Math.abs(r - g) < 5)) {
                                    model.setCell(x, y, fxColor, importType);
                                }
                            }
                        }
                    }
                    redrawWholeGrid();
                    System.out.println("Успішно імпортовано з: " + file.getAbsolutePath());
                } catch (java.io.IOException e) {
                    System.err.println("Помилка під час імпорту PNG: " + e.getMessage());
                }
            }
        });
    }

    private void duplicatePattern() {
        int patternWidth = Math.max(1, view.getPatternWidthSpinner().getValue());
        int patternHeight = Math.max(1, view.getPatternHeightSpinner().getValue());

        patternWidth = Math.min(patternWidth, model.getWidth());
        patternHeight = Math.min(patternHeight, model.getHeight());

        Color[][] baseColors = new Color[patternWidth][patternHeight];
        GridCellType[][] baseTypes = new GridCellType[patternWidth][patternHeight];

        for (int x = 0; x < patternWidth; x++) {
            for (int y = 0; y < patternHeight; y++) {
                baseColors[x][y] = model.getCellColor(x, y);
                baseTypes[x][y] = model.getCellType(x, y);
            }
        }

        boolean mirrorHorizontally = view.getHorSymetry().isSelected();
        boolean mirrorVertically = view.getVerSymetry().isSelected();

        for (int x = 0; x < model.getWidth(); x++) {
            for (int y = 0; y < model.getHeight(); y++) {
                int sourceX = x % patternWidth;
                int sourceY = y % patternHeight;

                if (mirrorHorizontally) {
                    sourceX = patternWidth - 1 - sourceX;
                }
                if (mirrorVertically) {
                    sourceY = patternHeight - 1 - sourceY;
                }

                model.setCell(x, y, baseColors[sourceX][sourceY], baseTypes[sourceX][sourceY]);
            }
        }

        redrawWholeGrid();
    }

    private void handleCanvasClick(MouseEvent event) {
        Point2D localPoint = view.getCanvas().sceneToLocal(event.getSceneX(), event.getSceneY());
        double mouseX = localPoint.getX();
        double mouseY = localPoint.getY();

        int x = (int) (mouseX / view.getCellSize());
        int y = (int) (mouseY / view.getCellSize());

        if (x >= 0 && x < model.getWidth() && y >= 0 && y < model.getHeight()) {
            Color selectedColor = view.getColorPicker().getValue();

            drawAndSaveCell(x, y, selectedColor);

            if (view.getHorSymetry().isSelected()) {
                int mirroredX = model.getWidth() - 1 - x;
                drawAndSaveCell(mirroredX, y, selectedColor);
            }

            if (view.getVerSymetry().isSelected()) {
                int mirroredY = model.getHeight() - 1 - y;
                drawAndSaveCell(x, mirroredY, selectedColor);
            }

            if (view.getHorSymetry().isSelected() && view.getVerSymetry().isSelected()) {
                int mirroredX = model.getWidth() - 1 - x;
                int mirroredY = model.getHeight() - 1 - y;
                drawAndSaveCell(mirroredX, mirroredY, selectedColor);
            }
        }
    }

    private void drawAndSaveCell(int x, int y, Color color) {
        GridCellType selectedType = view.getCellTypePicker().getValue();
        model.setCell(x, y, color, selectedType);
        drawCell(x, y, color);
    }

    private void drawCell(int x, int y, Color color) {
        GraphicsContext gc = view.getCanvas().getGraphicsContext2D();
        int size = view.getCellSize();

        gc.setFill(Color.WHITE);
        gc.fillRect(x * size, y * size, size, size);

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        gc.strokeRect(x * size, y * size, size, size);

        if (!color.equals(Color.WHITE)) {
            if (model.getCellType(x, y) == GridCellType.FILLED_SQUARE) {
                gc.setFill(color);
                gc.fillRect(x * size + 1, y * size + 1, size - 1, size - 1);
            } else {
                gc.setStroke(color);
                gc.setLineWidth(4);

                gc.setLineCap(javafx.scene.shape.StrokeLineCap.ROUND);

                double padding = 2.2;

                double xLeft = x * size + padding;
                double xRight = (x + 1) * size - padding;
                double yTop = y * size + padding;
                double yBottom = (y + 1) * size - padding;

                gc.strokeLine(xLeft, yTop, xRight, yBottom);
                gc.strokeLine(xRight, yTop, xLeft, yBottom);
            }
        }
    }

    private java.io.File getDefaultChooserDirectory() {
        java.io.File userHome = new java.io.File(System.getProperty("user.home"));
        if (!userHome.isDirectory()) {
            return null;
        }
        return userHome;
    }

    private java.io.File getInitialDirectory() {
        java.io.File projectDir = new java.io.File(System.getProperty("user.dir"));
        if (projectDir.isDirectory()) {
            return projectDir;
        }
        return getDefaultChooserDirectory();
    }

    public void redrawWholeGrid() {
        view.drawGrid();
        for (int x = 0; x < model.getWidth(); x++) {
            for (int y = 0; y < model.getHeight(); y++) {
                Color color = model.getCellColor(x, y);
                if (!color.equals(Color.WHITE)) {
                    drawCell(x, y, color);
                }
            }
        }
    }
}