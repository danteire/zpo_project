module org.example {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    exports org.example.listaobecnosci.AppViews to com.fasterxml.jackson.databind;

    opens  org.example.listaobecnosci.AppViews.Login to javafx.fxml;
    exports org.example.listaobecnosci.AppViews.Login;

    opens org.example.listaobecnosci.AppViews.MainMenu to javafx.fxml;
    exports org.example.listaobecnosci.AppViews.MainMenu;

    opens org.example.listaobecnosci.AppViews.UseCases.ZarzadzanieStudentami to javafx.fxml;
    exports org.example.listaobecnosci.AppViews.UseCases.ZarzadzanieStudentami;

    opens org.example.listaobecnosci.AppViews.UseCases.DodajStudenta to javafx.fxml;
    exports org.example.listaobecnosci.AppViews.UseCases.DodajStudenta;
}