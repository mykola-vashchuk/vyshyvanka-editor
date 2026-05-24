package ukma.edu.ua.vyshyvankaeditor.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ukma.edu.ua.vyshyvankaeditor.controller.EditorController;
import ukma.edu.ua.vyshyvankaeditor.model.GridData;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        GridData model = new GridData();

        EditorUI view = new EditorUI();

        EditorController controller = new EditorController(model, view);

        Scene scene = new Scene(view.getRoot(), 935, 685);

        stage.setTitle("Піксельна вишивка. Редактор орнаменту — Микола Ващук");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}