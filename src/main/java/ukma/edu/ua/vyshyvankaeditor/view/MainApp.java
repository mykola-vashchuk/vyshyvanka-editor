package ukma.edu.ua.vyshyvankaeditor.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        // Головний контейнер нашого інтерфейсу
        BorderPane root = new BorderPane();

        // Створюємо вікно розміром 900х600 пікселів
        Scene scene = new Scene(root, 900, 600);

        // Встановлюємо обов'язковий заголовок програми з назвою та автором
        stage.setTitle("Піксельна вишивка. Редактор орнаменту — Микола Ващук");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}