package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public abstract class Automaton1Dim extends Automaton {
    private int size;

    protected boolean hasNextCoordinates(CellCoordinates coords) {
        return (coords.getX()+1 < size);
    }

    protected CellCoordinates initialCoordinates() {
        return new Coords1D(0);
    }

    protected CellCoordinates nextCoordinates(CellCoordinates cellCoords){
        return new Coords1D(cellCoords.getX() + 1);
    }

    protected void setSize(int size){
        this.size = size;
    }

    protected int getSize(){
        return size;
    }
}
