package ukma.edu.ua.vyshyvankaeditor.view;

import javafx.scene.layout.BorderPane;

public class EditorUI {
    private final BorderPane root;

    public EditorUI() {
        root = new BorderPane();
        buildUI();
    }

    private void buildUI() {
        // TODO: додати панель інструментів та канвас
    }

    public BorderPane getRoot() {
        return root;
    }
}