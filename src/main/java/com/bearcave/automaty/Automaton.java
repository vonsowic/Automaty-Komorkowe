package com.bearcave.automaty;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Główna klasa
 * @author Michał Wąsowicz
 * @date 05.11.16
 * @version 1.0
 */
public abstract class Automaton {

    private Map<CellCoordinates, CellState> cells;
    protected CellNeighborhood neighborsStrategy;
    protected CellStateFactory stateFactory;


    protected abstract Automaton newInstance();

    protected abstract boolean hasNextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellCoordinates initialCoordinates();

    protected abstract CellCoordinates nextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates);

    public Automaton nextState(){

        Automaton automaton = newInstance();
        for ( Map.Entry<CellCoordinates, CellState> entry : cells.entrySet()){
            automaton.cells.put( entry.getKey(), nextCellState(entry.getValue(), neighborsStrategy.cellNeighbors( entry.getKey())));
        }

        return automaton;
    }

    //ZMIANA W STASUNKU DO DIAGRAMU Map<? extends CellCoordinates, ? extends CellState> map
    public void insertStructure(Map<CellCoordinates, CellState> map){
        cells =  map;
    }


    public CellState getCellState(CellCoordinates coords){
        return cells.get(coords);
    }

    public Map<CellCoordinates, CellState> getCellMap(){
        return cells;
    }

}
