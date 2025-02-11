module com.example.pidev {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.pidev to javafx.fxml;
    exports com.example.pidev;
}