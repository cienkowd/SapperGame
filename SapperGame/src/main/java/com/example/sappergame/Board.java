package com.example.sappergame;

import java.util.Random;

public class Board {
    private final int numberOfMines = 99;
    private final int boardWidth = 24;
    private final int boardLength = 24;
    private int[][] board;

    public void generateBoard() {
        board = new int[boardWidth][boardLength];

        int i = numberOfMines;
        while (i > 0) {
            int x = placeMineRandom(boardWidth);
            int y = placeMineRandom(boardLength);
            if (canPutTheMine(x, y)) {
                board[x][y] = -1;
                i--;
            }
        }

        for (int j = 0; j < boardWidth; j++) {
            for (int k = 0; k < boardLength; k++) {
                if(board[j][k] == 0)
                    board[j][k] = minesAround(j,k);
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
                     if (j >= 0 && j < boardWidth)
                         if (i != x || j != y)
                             if (board[i][j] == -1)
                                 mines++;
         }
         return mines;
    }

    private int countMines() {
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

    public String getValueOfIndexFromBoard(int x, int y) {
        return String.valueOf(board[x][y]);
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
}
