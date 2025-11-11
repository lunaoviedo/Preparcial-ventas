module com.example.taller_5preparcial {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens com.example.taller_5preparcial.Controllers to javafx.fxml;
    opens com.example.taller_5preparcial.App to javafx.fxml;
    opens com.example.taller_5preparcial.Models to javafx.fxml;

    exports com.example.taller_5preparcial.Controllers;
    exports com.example.taller_5preparcial.App;
    exports com.example.taller_5preparcial.Models;
    exports com.example.taller_5preparcial.Repository;
    opens com.example.taller_5preparcial.Repository to javafx.fxml;
}