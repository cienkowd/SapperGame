package com.example.sappergame;

import javafx.scene.control.Button;

import java.util.HashMap;
import java.util.Map;

public class MyButton extends Button {
    private final int x;
    private final int y;
    private int value;
    private static final Map<String, MyButton> buttonsMap = new HashMap<>();

    public MyButton(int x, int y) {
        this.x = x;
        this.y = y;
        buttonsMap.put(getKey(x, y), this);
    }

    public int getXCoordinate() {
        return x;
    }

    public int getYCoordinate() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public String getValueAsString() {
        return String.valueOf(value);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static MyButton getButton(int x, int y) {
        return buttonsMap.get(getKey(x, y));
    }

    private static String getKey(int x, int y) {
        return x + "," + y;
    }
}