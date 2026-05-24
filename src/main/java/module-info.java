module ukma.edu.ua.vyshyvankaeditor {
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.swing;

    opens ukma.edu.ua.vyshyvankaeditor.view to javafx.graphics;

    exports ukma.edu.ua.vyshyvankaeditor;
}