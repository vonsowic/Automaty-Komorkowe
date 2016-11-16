package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public abstract class Automaton2Dim extends Automaton {

    private int width = 0;
    private int height = 0;
    protected Board board;

    /*
        Metoda zwraca true tylko w przypadku gdy punkt coords znajduje sie wewnatrz automatu
     */
    protected boolean hasNextCoordinates(CellCoordinates coords) {
        if (    coords.getWidth() > width ||
                coords.getWidth() < 0 ||
                coords.getHeight() > height ||
                coords.getHeight() < 0){
            return false;
        } else {
            return true;
        }
    }

    protected CellCoordinates initialCoordiantes(CellCoordinates cellCoords){
        return null;
    }

    protected CellCoordinates nextCoordiantes(CellCoordinates cellCoords){
        return null;
    }
}
