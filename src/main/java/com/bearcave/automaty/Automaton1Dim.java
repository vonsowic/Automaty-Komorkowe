package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public abstract class Automaton1Dim extends Automaton {
    private int size;

    protected boolean hasNextCoordinates(CellCoordinates coords) {
        return false;
    }

    protected CellCoordinates initialCoordiantes(CellCoordinates cellCoords){
        return null;
    }

    protected CellCoordinates nextCoordiantes(CellCoordinates cellCoords){
        return null;
    }
}
