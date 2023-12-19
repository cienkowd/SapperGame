package com.example.sappergame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
public class GuideController {

    private static GameMode mode;

    @FXML
    public void handleGuideOptions(MouseEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        if (event.getButton() == MouseButton.PRIMARY) {
            if(clickedButton.getText().equals("Łatwy")) {
                mode = EasyGameMode.INSTANCE;
                Game.resetStage();
            }
            else if(clickedButton.getText().equals("Średni")) {
                mode = MediumGameMode.INSTANCE;
                Game.resetStage();
            }
            else if(clickedButton.getText().equals("Trudny")) {
                mode = HardGameMode.INSTANCE;
                Game.resetStage();
            }
        }
    }

    public static GameMode getMode() {
        return mode;
    }
}
