package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public interface CellStateFactory {

    public CellState initialState(CellCoordinates cellCoordinates);
}
