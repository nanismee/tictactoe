package com.company;

public class Cell {
    private int x;
    private int y;
    private int w;
    private int h;
    private String value;

    //
    public static final String X_VALUE = "x";
    public static final String O_VALUE = "o";
    public static final String EMPTY_VALUE = "";

    public Cell(){
        this.value = EMPTY_VALUE;
    }
    //Constructor
    public Cell(int x, int y, int w, int h, String value) {
        this();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.value = value;
    }
    //getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    //setter
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
