package com.example.sappergame;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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

    private boolean isFirstClick = true;

    public void initialize() {
        Board board = new Board();
        board.generateBoard();
        int a = getBoardWidth();
        int b = getBoardLength();
        for (int x = 0; x < a; x++) {
            for (int y = 0; y < b; y++) {
                MyButton button = createButton(x,y);
                button.setValue(board.getValueOfIndexFromBoard(x,y));
                gridPane.add(button, x, y);
            }
        }

    }

    private MyButton createButton(int x, int y) {
        MyButton button = new MyButton(x,y);
        button.setMinSize(30, 30);
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
        MyButton clickedButton = (MyButton) event.getSource();
        if (event.getButton() == MouseButton.PRIMARY && whenYouCannotClickPrimary(clickedButton)) {
            handleLeftClick(clickedButton);
        }
        else if (event.getButton() == MouseButton.SECONDARY && whenYouCannotClickOrWantCancelFlag(clickedButton)) {
            if(!(clickedButton.getValue() == -2))
                handleRightClick(clickedButton);
        }
        checkGameStatus();
    }
    private void handleLeftClick(MyButton button) throws IOException {
        if (button.getValue() == 0) {
            showAroundWhenZero(button);
        }
        else {
            setTextForButton(button);
        }
    }

    private void setTextForButton(MyButton button) throws IOException {
        if (button.getValue() == -1) {
            String imagePath = "/mina.png";
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(13);
            imageView.setFitHeight(13);

            button.setGraphic(imageView);
            showAlert("BOOOOMMMMM", "Game Over!");

        }
        else if(button.getValue() == 0){
            button.getStyleClass().add("button0");
        }
        else if (button.getValue() == 1) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button1");
        }
        else if (button.getValue() == 2) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button2");
        }
        else if (button.getValue() == 3) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button3");
        }
        else if (button.getValue() == 4) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button4");
        }
        else if (button.getValue() == 5) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button5");
        }
        else if (button.getValue() == 6) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button6");
        }
        else if (button.getValue() == 7) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button7");
        }
        else if (button.getValue() == 8) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button8");
        }
    }
    private void handleRightClick(MyButton button) {
        String imagePath = "/flaga.png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(13);
        imageView.setFitHeight(13);

        button.setGraphic(imageView);
    }

    private boolean whenYouCannotClickPrimary(MyButton button) {
        if(button.getGraphic() != null) {
            return false;
        }
        else if(!(button.getText().isEmpty())) {
            return false;
        }
        return true;
    }

    private boolean whenYouCannotClickOrWantCancelFlag(MyButton button) {
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

    private void showAroundWhenZero(MyButton button) throws IOException {
        int x = button.getXCoordinate();
        int y = button.getYCoordinate();
        for(int  i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (j >= 0 && j < getBoardLength() && i >= 0 && i < getBoardWidth()) {
                    MyButton spectatingButton = MyButton.getButton(i, j);
                    if (spectatingButton != null && !(spectatingButton.getValue() == -2)) {
                        setTextForButton(spectatingButton);
                        if (spectatingButton.getValue() == 0) {
                            spectatingButton.setValue(-2);
                            showAroundWhenZero(spectatingButton);
                        }
                    }
                }
            }
        }
    }

    private void checkGameStatus() throws IOException {
        int counterFlagOnMines = 0;
        int counterClickedButtons = 0;
        int fieldsInsteadOfMines = getBoardLength()*getBoardWidth() - getNumberOfMines();

        for (int i = 0; i < getBoardWidth(); i++) {
            for (int j = 0; j < getBoardLength(); j++) {
                MyButton button = MyButton.getButton(i, j);
                if (button != null) {
                    int buttonValue = button.getValue();

                    if (buttonValue == -1 && button.getGraphic() != null) {
                        counterFlagOnMines++;
                    }
                    if (buttonValue == 0 && button.getGraphic() == null) {
                        return;
                    }
                    if (counterFlagOnMines == getNumberOfMines()) {
                        showAlert("Congratulations!", "You've won!");
                        return;
                    }
                    if ((buttonValue == -3 || buttonValue == -2) && button.getGraphic() == null) {
                        counterClickedButtons++;
                    }
                    if (counterClickedButtons == fieldsInsteadOfMines) {
                        showAlert("Congratulations!", "You've won!");
                        return;
                    }
                }
            }
        }
    }
}