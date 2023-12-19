package com.example.sappergame;

public class GameMode {
    private final int numberOfMines;
    private final int boardWidth;
    private final int boardLength;
    private final int buttonWidth;
    private final int buttonLength;

    public GameMode(int numberOfMines, int boardWidth, int boardLength, int buttonWidth, int buttonLength) {
        this.numberOfMines = numberOfMines;
        this.boardWidth = boardWidth;
        this.boardLength = boardLength;
        this.buttonWidth = buttonWidth;
        this.buttonLength = buttonLength;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardLength() {
        return boardLength;
    }

    public int getButtonWidth() {
        return buttonWidth;
    }

    public int getButtonLength() {
        return buttonLength;
    }
}
