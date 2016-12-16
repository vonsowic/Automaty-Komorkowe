package com.bearcave.automaty;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Main class of project. It is responsiable for creating next generations of any automaton
 * @author Michał Wąsowicz
 * @version 1.0
 */
public abstract class Automaton {

    private Map<CellCoordinates, CellState> cells;
    protected CellNeighborhood neighborsStrategy;
    protected CellStateFactory stateFactory;

    /**
     * @return empty Automaton for next generation
     */
    protected abstract Automaton newInstance();

    /**
     * @param cellCoordinates used in
     * @return true if there are still cells in cells to check
     */
    protected abstract boolean hasNextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellCoordinates initialCoordinates();

    protected abstract CellCoordinates nextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates);


    /**
     * @return next generation of Automaton
     */
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


    /**
     * @param map is a new instance of cells in Automaton
     */
    public void insertStructure(Map<CellCoordinates, CellState> map){
        cells = new HashMap<>();
        cells.putAll(map);
    }


    /**
     * @param coords that are used to check state of cell in cells of Automaton
     * @return CellState taken from cells of Automaton
     */
    public CellState getCellState(CellCoordinates coords){
        return cells.get(coords);
    }


    /**
     * @return Automaton's cellMap with coordinates and states
     */
    public Map<CellCoordinates, CellState> getCellMap(){
        return cells;
    }

    /**
     * @param map is printed in standard entry. Method was used for empirical tests.
     */
    public static void printMap(Map<CellCoordinates, CellState> map){
        for(Map.Entry<CellCoordinates, CellState> entry : map.entrySet()){
            System.out.println(entry.getKey().getX()+" : "+entry.getKey().getY()+" - "+entry.getValue());
        }
    }
}
