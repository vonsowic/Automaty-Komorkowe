package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords2d implements CellCoordinates {
    public int x;
    public int y;

    public Coords2d(int width, int height){
        setWidth(width);
        setHeight(height);
    }

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }

    public void setWidth(int width) {
        x = width;
    }

    public void setHeight(int height) {
        y = height;
    }

}
