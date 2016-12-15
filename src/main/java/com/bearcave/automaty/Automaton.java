package com.bearcave.automaty;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
        CellCoordinates coords = initialCoordinates();
        boolean endLoop = false;
        while ( !endLoop ){
            automaton.cells.put(
                    coords,
                    nextCellState(
                            cells.get(coords),
                            neighborsStrategy.cellNeighbors(coords)));

            if ( hasNextCoordinates(coords))
                coords = nextCoordinates(coords);
            else
                endLoop = true;
        }

        return automaton;
    }

    public void insertStructure(Map<CellCoordinates, CellState> map){
        cells = new HashMap<>();
        cells.putAll(map);
    }

    public CellState getCellState(CellCoordinates coords){
        return cells.get(coords);
    }

    public Map<CellCoordinates, CellState> getCellMap(){
        return cells;
    }

    public static void printMap(Map<CellCoordinates, CellState> map){
        for(Map.Entry<CellCoordinates, CellState> entry : map.entrySet()){
            System.out.println(entry.getKey().getX()+" : "+entry.getKey().getY()+" - "+entry.getValue());
        }
    }
}
