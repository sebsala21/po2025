module com.example.samochodgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.samochodgui to javafx.fxml;
    exports com.example.samochodgui;
}