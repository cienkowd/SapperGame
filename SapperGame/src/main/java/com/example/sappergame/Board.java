package com.example.sappergame;

import java.util.Random;

public class Board {
    private int[][] board;
    private final int numberOfMines;
    private final int boardWidth;
    private final int boardLength;
    private final GameMode gameMode;

    public Board(GameMode mode) {
        this.gameMode = mode;
        this.numberOfMines = mode.getNumberOfMines();
        this.boardWidth = mode.getBoardWidth();
        this.boardLength = mode.getBoardLength();
    }

    public void generateBoard() {
        if (gameMode == null) {
            throw new IllegalStateException("GameMode must be set before generating the board.");
        }

        board = new int[gameMode.getBoardWidth()][gameMode.getBoardLength()];

        int i = gameMode.getNumberOfMines();
        while (i > 0) {
            int x = placeMineRandom(gameMode.getBoardWidth());
            int y = placeMineRandom(gameMode.getBoardLength());
            if (canPutTheMine(x, y)) {
                board[x][y] = -1;
                i--;
            }
        }

        for (int j = 0; j < gameMode.getBoardWidth(); j++) {
            for (int k = 0; k < gameMode.getBoardLength(); k++) {
                if (board[j][k] == 0)
                    board[j][k] = minesAround(j, k);
            }
        }
    }

    public void refreshBoard() {
        int k = numberOfMines - countMines();
        while (k > 0) {
            int x = placeMineRandom(boardWidth);
            int y = placeMineRandom(boardLength);
            if (canPutTheMine(x, y)) {
                board[x][y] = -1;
                k--;
            }
        }

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (board[i][j] != -1) {
                    board[i][j] = minesAround(i, j);
                }
            }
        }
    }

    private boolean canPutTheMine(int x, int y) {
        if (board[x][y] == -1) {
            return false;
        }
        if(minesAround(x,y) == 8) {
            return false;
        }
        return true;
    }

    private int minesAround(int x, int y) {
         int mines = 0;
         for(int  i = x-1; i <= x+1; i++) {
             if(i >= 0 && i < boardWidth)
                 for (int j = y-1; j <= y+1; j++)
                     if (j >= 0 && j < boardLength)
                         if (i != x || j != y)
                             if (board[i][j] == -1)
                                 mines++;
         }
         return mines;
    }

    public int countMines() {
        int mineCount = 0;
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (board[i][j] == -1) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    private int placeMineRandom(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public int getValueOfIndexFromBoard(int x, int y) {
        return board[x][y];
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardLength() {
        return boardLength;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setValueOfIndex(int x, int y, int value) {
        board[x][y] = value;
    }

}
