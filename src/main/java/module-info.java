module com.example.assignmentapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires layout;
    requires kernel;

    opens com.example.assignmentapp to javafx.fxml;
    exports com.example.assignmentapp;
}