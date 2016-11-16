package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords1D implements CellCoordinates {
    public int size;

    public int getWidth() {
        return size;
    }

    public int getHeight() {
        return this.getWidth();
    }

    public void setHeight(int height) {
        size = height;
    }

    public void setWidth(int width) {
        this.setHeight(width);
    }

}
