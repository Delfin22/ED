module com.ed.ed {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.ed.ed to javafx.fxml;
    exports com.ed.ed;
}
