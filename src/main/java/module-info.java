module com.ed.ed {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ed.ed to javafx.fxml;
    exports com.ed.ed;
}