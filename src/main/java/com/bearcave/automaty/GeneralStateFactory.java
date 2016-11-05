package com.bearcave.automaty;

import java.util.Map;

/**
 * Created by miwas on 05.11.16.
 */
public class GeneralStateFactory implements CellStateFactory {

    private Map<CellCoordinates, CellState> states;

    public CellState initialState(CellCoordinates cellCoordinates) {
        return null;
    }
}
