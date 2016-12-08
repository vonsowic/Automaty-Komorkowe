package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords1D implements CellCoordinates, Comparable<Coords1D> {
    public int size;

    public Coords1D(int size){
        this.size = size;
    }

    public int getX() {
        return size;
    }

    public int getY() {
        return this.getX();
    }

    public void setY(int height) {
        size = height;
    }

    public void setX(int width) {
        this.setX(width);
    }

    @Override
    public int compareTo(Coords1D o) {
        return size-o.size;
    }
}
