module com.example.sappergame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sappergame to javafx.fxml;
    exports com.example.sappergame;
}