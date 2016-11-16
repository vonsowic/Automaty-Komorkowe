package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class Cell {
    public CellState state;
    public CellCoordinates coords;

    public Cell() {

    }

    public Cell(CellState cellState, int size){
        state = cellState;
        coords.setHeight(size);
    }

    public Cell(CellState cellState, int x, int y){
        state = cellState;
        coords.setWidth(x);
        coords.setHeight(y);
    }
}
