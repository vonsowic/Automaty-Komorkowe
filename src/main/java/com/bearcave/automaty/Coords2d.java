package com.bearcave.automaty;

import java.util.Comparator;

/**
 * Created by miwas on 05.11.16.
 */
public class Coords2d implements CellCoordinates, Comparable<Coords2d> {
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


    @Override
    public int compareTo(Coords2d o2) {
        if (this.getWidth() == o2.getWidth()){
            if (this.getHeight() < o2.getHeight()){
                return -1;
            } else if ( this.getHeight() == o2.getHeight() ){
                return 0;
            } else {
                return 1;
            }
        }

        if (this.getWidth() < o2.getWidth()){
            return -1;
        } else {
            return 1;
        }
    }
}
