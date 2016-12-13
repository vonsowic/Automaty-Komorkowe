package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords2d implements CellCoordinates {
    private Integer x;
    private Integer y;

    public Coords2d(int width, int height){
        setX(width);
        setY(height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
