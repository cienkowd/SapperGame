package com.example.sappergame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Game.stage = stage;
        stage.setTitle("Sapper");
        stage.setScene(createGuide());
        stage.setMinWidth(750);
        stage.setMinHeight(750);
        stage.setMaxWidth(790);
        stage.setMaxHeight(800);
        stage.setResizable(false);
        stage.show();
    }
    public static Scene createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 750);
        scene.getStylesheets().add(Game.class.getResource("style.css").toString());
        return scene;
    }
    public static Scene createGuide() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("guide.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 750);
        scene.getStylesheets().add(Game.class.getResource("style.css").toString());
        return scene;
    }

    public static Stage stage;

    public static void resetStage() throws IOException {
        Game.stage.setScene(createScene());
    }

    public static void resetGame() throws IOException {
        Game.stage.setScene(createGuide());
    }

    public static void main(String[] args) {
        launch();
    }
}