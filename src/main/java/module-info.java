module org.example {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports org.example.nauczycieldesktopapp to javafx.graphics;
    opens org.example.nauczycieldesktopapp to javafx.fxml;

    opens org.example.nauczycieldesktopapp.controller to javafx.fxml;

    opens org.example.nauczycieldesktopapp.model to com.fasterxml.jackson.databind;
    exports org.example.nauczycieldesktopapp.model;
}