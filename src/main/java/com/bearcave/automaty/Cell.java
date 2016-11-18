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
        coords = new Coords1D();
        coords.setHeight(size);
    }

    public Cell(CellState cellState, int x, int y){
        state = cellState;
        coords = new Coords2d(x, y);
    }
}
