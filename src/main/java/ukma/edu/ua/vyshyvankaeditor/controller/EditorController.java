package ukma.edu.ua.vyshyvankaeditor.controller;

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
        // TODO: додати обробку кліків мишки та кнопок
    }
}