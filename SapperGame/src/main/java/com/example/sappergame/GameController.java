package com.example.sappergame;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;


public class GameController extends Board{

    @FXML
    private GridPane gridPane;
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
        button.setMinSize(70, 70);
        button.setOnMouseClicked(this::handleButtonClick);
        return button;
    }

    private void handleButtonClick(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        if (event.getButton() == MouseButton.PRIMARY && whenYouCannotClickPrimary(clickedButton)) {
            handleLeftClick(clickedButton);
        }
        else if (event.getButton() == MouseButton.SECONDARY && whenYouCannotClickOrWantCancelFlag(clickedButton)) {
            handleRightClick(clickedButton);
        }
    }
    private void handleLeftClick(Button button) {
        if (button.getId().equals("0")) {
            showAroundWhenZero(button);
        }
        else {
            setTextForButton(button);
        }
    }

    private void setTextForButton(Button button) {
        if (button.getId().equals("-1")) {
            button.setStyle("-fx-font-size: 30px; -fx-background-color: #CC0000; -fx-text-fill: white;");
            button.setText(button.getId());
            showAlert("BOOOOMMMMM", "Game Over!");

        }
        else if (button.getId().equals("1")) {
            button.setText(button.getId());
            button.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: blue;");
        }
        else if (button.getId().equals("2")) {
            button.setText(button.getId());
            button.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: green;");
        }
        else if (button.getId().equals("3")) {
            button.setText(button.getId());
            button.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: red;");
        }
        else if (button.getId().equals("4")) {
            button.setText(button.getId());
            button.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #660099;");
        }
        else if (button.getId().equals("5")) {
            button.setText(button.getId());
            button.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #660000;");
        }
        else if (button.getId().equals("6")) {
            button.setText(button.getId());
            button.setStyle("-fx-font-weight: bold; -fx-text-fill: #669966;");
        }
        else if (button.getId().equals("7")) {
            button.setText(button.getId());
            button.setStyle("-fx-font-weight: bold; -fx-text-fill: #006699;");
        }
        else
            button.setText(button.getId());
    }
    private void handleRightClick(Button button) {
        String imagePath = "/flaga.png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        button.setGraphic(imageView);
    }

    private boolean whenYouCannotClickPrimary(Button button) {
        if(button.getGraphic() != null) {
            return false;
        }
        else if(!(button.getText().isEmpty())) {
            return false;
        }
        return true;
    }

    private boolean whenYouCannotClickOrWantCancelFlag(Button button) {
        if(button.getGraphic() != null) {
            button.setGraphic(null);
            return false;
        }
        else if(!(button.getText().isEmpty())) {
            return false;
        }
        return true;
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

    private void showAroundWhenZero(Button button) {
        int x = GridPane.getRowIndex(button);
        int y = GridPane.getColumnIndex(button);
        for(int  i = x-1; i <= x+1; i++) {
            if(i >= 0 && i < 9)
                for (int j = y-1; j <= y+1; j++)
                    if (j >= 0 && j < 9) {
                        Node node  = getNodeByRowColumnIndex(gridPane,i,j);
                        if(node != null)
                            setTextForButton((Button) node);
                    }
        }
    }

    private Node getNodeByRowColumnIndex(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }
}