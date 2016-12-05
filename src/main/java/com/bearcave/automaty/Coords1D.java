package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords1D implements CellCoordinates, Comparable<Coords1D> {
    public int size;

    public Coords1D(int size){
        this.size = size;
    }

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

    @Override
    public int compareTo(Coords1D o) {
        return size-o.size;
    }
}
