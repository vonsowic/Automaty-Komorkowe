package com.bearcave.automaty;

import java.util.Set;

/**
 * Created by miwas on 05.11.16.
 */
public class GameOfLife extends Automaton2Dim {

    protected Automaton newInstance(CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood) {

        return null;
    }

    protected CellCoordinates nextCoordinates(CellCoordinates cellCoordinates) {
        return null;
    }

    protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) {
        return null;
    }
}
