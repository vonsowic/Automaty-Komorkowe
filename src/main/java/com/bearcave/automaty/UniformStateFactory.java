package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public class UniformStateFactory implements CellStateFactory{

    private CellState state;

    UniformStateFactory(CellState state){
        this.state = state;
    }

    public CellState initialState(CellCoordinates cellCoordinates) {
        return state;
    }
}
