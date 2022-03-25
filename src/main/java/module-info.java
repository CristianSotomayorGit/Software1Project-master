module com.example.software1project {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.context;
    requires spring.beans;
    requires java.logging;


    opens com.example.software1project to javafx.fxml;
    exports com.example.software1project;
}