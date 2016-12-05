package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords2d implements CellCoordinates {
    private Integer x;
    private Integer y;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coords2d)) return false;

        Coords2d coords2d = (Coords2d) o;

        if (!x.equals(coords2d.x)) return false;
        return y.equals(coords2d.y);

    }

    @Override
    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }
}
