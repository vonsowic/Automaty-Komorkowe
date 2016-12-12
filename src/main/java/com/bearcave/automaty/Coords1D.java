package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords1D implements CellCoordinates{
    public int size;

    public Coords1D(int size){
        this.size = size;
    }

    public int getX() {
        return size;
    }

    public int getY() {
        return 0;
    }

    public void setY(int height) {
        size = height;
    }

    public void setX(int width) {
        size = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coords1D)) return false;

        Coords1D coords1D = (Coords1D) o;

        return size == coords1D.size;

    }

    @Override
    public int hashCode() {
        return size;
    }
}
