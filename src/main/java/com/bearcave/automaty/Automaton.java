package com.bearcave.automaty;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.Map;

/**
 * Created by miwas on 05.11.16.
 */
public abstract class Automaton {

    private Map<CellCoordinates, CellState> cells;
    private CellNeighborhood neighborsStrategy;
    private CellStateFactory stateFactory;


    private class CellIterator{
        private CellCoordinates currentState;

    }
}
