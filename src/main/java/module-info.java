module org.example {
    requires javafx.fxml;
    requires javafx.controls;

    opens  org.example.listaobecnosci.AppViews to javafx.fxml;
    exports org.example.listaobecnosci.AppViews;
}