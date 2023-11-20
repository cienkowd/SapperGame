package com.example.sappergame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;

public class GameController extends Board{

    @FXML
    private GridPane gridPane;
    @FXML
    private Label gameOver;
    public void initialize() {
        Board board = new Board();
        board.generateBoard();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Button button = createButton();
                button.setId(board.getValueOfIndexFromBoard(x,y));
                gridPane.add(button, x, y);
            }
        }
    }

    private Button createButton() {
        Button button = new Button();
        button.setMinSize(50, 50);
        button.setText("");
        button.setOnAction(this::handleButtonClick);
        return button;
    }

    private void handleButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        if(clickedButton.getId().equals("-1")) {
            clickedButton.setStyle("-fx-background-color: #CC0000; -fx-text-fill: white;");
            clickedButton.setText(clickedButton.getId());
            showAlert("BOOOOMMMMM", "Game Over!");
        }
        else {
            clickedButton.setText(clickedButton.getId());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
    }
}