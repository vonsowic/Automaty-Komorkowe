package com.bearcave.automaty;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by miwas on 05.11.16.
 */
public class GeneralStateFactory implements CellStateFactory {

    private Map<CellCoordinates, CellState> states;
    private CellState state;

    GeneralStateFactory(Map<CellCoordinates, CellState> map, CellState state){
        states = new HashMap<>();
        states.putAll(map);
        this.state = state;

    }

    public CellState initialState(CellCoordinates cellCoordinates) {

        if ( states.containsKey(cellCoordinates)){
            return states.get(cellCoordinates);
        } else {
            return state;
        }

    }
}
