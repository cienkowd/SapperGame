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

import java.io.IOException;
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
        button.setOnMouseClicked(event -> {
            try {
                handleButtonClick(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return button;
    }

    private void handleButtonClick(MouseEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        if (event.getButton() == MouseButton.PRIMARY && whenYouCannotClickPrimary(clickedButton)) {
            handleLeftClick(clickedButton);
        }
        else if (event.getButton() == MouseButton.SECONDARY && whenYouCannotClickOrWantCancelFlag(clickedButton)) {
            if(!(clickedButton.getId().equals("-2")))
                handleRightClick(clickedButton);
        }
    }
    private void handleLeftClick(Button button) throws IOException {
        if (button.getId().equals("0")) {
            showAroundWhenZero(button);
        }
        else {
            setTextForButton(button);
        }
    }

    private void setTextForButton(Button button) throws IOException {
        if (button.getId().equals("-1")) {
            String imagePath = "/mina.png";
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);

            button.setGraphic(imageView);
            showAlert("BOOOOMMMMM", "Game Over!");

        }
        else if(button.getId().equals("0")){
            button.getStyleClass().add("button0");
        }
        else if (button.getId().equals("1")) {
            button.setText(button.getId());
            button.getStyleClass().add("button1");
        }
        else if (button.getId().equals("2")) {
            button.setText(button.getId());
            button.getStyleClass().add("button2");
        }
        else if (button.getId().equals("3")) {
            button.setText(button.getId());
            button.getStyleClass().add("button3");
        }
        else if (button.getId().equals("4")) {
            button.setText(button.getId());
            button.getStyleClass().add("button4");
        }
        else if (button.getId().equals("5")) {
            button.setText(button.getId());
            button.getStyleClass().add("button5");
        }
        else if (button.getId().equals("6")) {
            button.setText(button.getId());
            button.getStyleClass().add("button6");
        }
        else if (button.getId().equals("7")) {
            button.setText(button.getId());
            button.getStyleClass().add("button7");
        }
        else if (button.getId().equals("8")) {
            button.setText(button.getId());
            button.getStyleClass().add("button8");
        }
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

    private void showAlert(String title, String content) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait();
        Game.resetStage();
    }

    private void showAroundWhenZero(Button button) throws IOException {
        int x = GridPane.getRowIndex(button);
        int y = GridPane.getColumnIndex(button);
        for(int  i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (j >= 0 && j < 9 && i >= 0 && i < 9) {
                    Node node = getNodeByRowColumnIndex(gridPane, i, j);
                    if (node != null && !(node.getId().equals("-2"))) {
                        setTextForButton((Button) node);
                        if (node.getId().equals("0")) {
                            node.setId("-2");
                            showAroundWhenZero((Button) node);
                        }
                    }
                }
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