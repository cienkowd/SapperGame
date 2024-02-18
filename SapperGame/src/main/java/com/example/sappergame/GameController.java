package com.example.sappergame;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameController{

    @FXML
    private GridPane gridPane;

    List<MyButton> neighbours = new ArrayList<>();

    private boolean isFirstClick;

    private final GameMode mode = GuideController.getMode();

    private final Board board = new Board(mode);

    @FXML
    private void initialize() {
        generateBoard();
    }
    private void generateBoard() {
        isFirstClick = true;
        board.generateBoard();
        int a = board.getBoardWidth();
        int b = board.getBoardLength();
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
        button.setMinSize(mode.getButtonWidth(), mode.getButtonLength());
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
        if(isFirstClick) {
            if (event.getButton() == MouseButton.PRIMARY) {
                isFirstClick = false;
                findNeighboursWhenFirstClick(clickedButton);
                clickedButton.setValue(0);
                board.setValueOfIndex(clickedButton.getXCoordinate(), clickedButton.getYCoordinate(), 0);
                revealNeighbours(neighbours);
                board.refreshBoard();
                rewriteMinesAroundAgain();
                handleLeftClick(clickedButton);
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                isFirstClick = false;
                handleRightClick(clickedButton);
            }
        }
        else {
            if (event.getButton() == MouseButton.PRIMARY && whenYouCannotClickPrimary(clickedButton)) {
                handleLeftClick(clickedButton);
            }
            else if (event.getButton() == MouseButton.SECONDARY && whenYouCannotClickOrWantCancelFlag(clickedButton)) {
                if (!(clickedButton.getValue() == -2))
                    handleRightClick(clickedButton);
            }
            checkGameStatus();
        }
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
            if(mode == MediumGameMode.INSTANCE) {
                imageView.setFitWidth(19);
                imageView.setFitHeight(19);
            }
            if(mode == EasyGameMode.INSTANCE) {
                imageView.setFitWidth(39);
                imageView.setFitHeight(39);
            }

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
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button1_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button1_2");
        }
        else if (button.getValue() == 2) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button2");
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button2_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button2_2");
        }
        else if (button.getValue() == 3) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button3");
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button3_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button3_2");
        }
        else if (button.getValue() == 4) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button4");
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button4_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button4_2");
        }
        else if (button.getValue() == 5) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button5");
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button5_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button5_2");
        }
        else if (button.getValue() == 6) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button6");
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button6_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button6_2");
        }
        else if (button.getValue() == 7) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button7");
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button7_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button7_2");
        }
        else if (button.getValue() == 8) {
            button.setText(button.getValueAsString());
            button.setValue(-3);
            button.getStyleClass().add("button8");
            if(mode == MediumGameMode.INSTANCE)
                button.getStyleClass().add("button8_1");
            if(mode == EasyGameMode.INSTANCE)
                button.getStyleClass().add("button8_2");
        }
    }
    private void handleRightClick(MyButton button) {
        String imagePath = "/flaga.png";
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(13);
        imageView.setFitHeight(13);
        if(mode == MediumGameMode.INSTANCE) {
            imageView.setFitWidth(19);
            imageView.setFitHeight(19);
        }
        if(mode == EasyGameMode.INSTANCE) {
            imageView.setFitWidth(39);
            imageView.setFitHeight(39);
        }

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
        Game.resetGame();
    }

    private void showAroundWhenZero(MyButton button) throws IOException {
        int x = button.getXCoordinate();
        int y = button.getYCoordinate();
        for(int  i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (j >= 0 && j < board.getBoardLength() && i >= 0 && i < board.getBoardWidth()) {
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

    private void findNeighboursWhenFirstClick(MyButton button) {
        int x = button.getXCoordinate();
        int y = button.getYCoordinate();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < board.getBoardWidth() && j >= 0 && j < board.getBoardLength() && !(i == x && j == y)) {
                    MyButton neighbourButton = MyButton.getButton(i, j);
                    if (neighbourButton != null) {
                        neighbours.add(neighbourButton);
                    }
                }
            }
        }
    }

    private void revealNeighbours(List<MyButton> neighbours) {
        for (MyButton neighbourButton : neighbours) {
            if (neighbourButton.getValue() == -1) {
                int x = neighbourButton.getXCoordinate();
                int y = neighbourButton.getYCoordinate();
                board.setValueOfIndex(x,y,0);
            }
        }
    }

    private void rewriteMinesAroundAgain() {
        for (int j = 0; j < board.getBoardWidth(); j++) {
            for (int k = 0; k < board.getBoardLength(); k++) {
                int newValue = board.getValueOfIndexFromBoard(j,k);
                MyButton button = MyButton.getButton(j,k);
                button.setValue(newValue);
            }
        }
    }

    private void checkGameStatus() throws IOException {
        int counterFlagOnMines = 0;
        int counterClickedButtons = 0;
        int fieldsInsteadOfMines = board.getBoardLength()* board.getBoardWidth() - board.getNumberOfMines();

        for (int i = 0; i < board.getBoardWidth(); i++) {
            for (int j = 0; j < board.getBoardLength(); j++) {
                MyButton button = MyButton.getButton(i, j);
                if (button != null) {
                    int buttonValue = button.getValue();

                    if (buttonValue == -1 && button.getGraphic() != null) {
                        counterFlagOnMines++;
                    }
                    if (buttonValue == 0 && button.getGraphic() == null) {
                        return;
                    }
                    if (counterFlagOnMines == board.getNumberOfMines()) {
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