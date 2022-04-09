module pl.polsl.polybiussquare2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens pl.polsl.polybiussquare2 to javafx.fxml;
    exports pl.polsl.polybiussquare2;
    exports pl.polsl.math;
}
