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
        CellCoordinates coord = initialCoordinates();
        boolean endLoop = false;
        System.out.println("Obecny stan \n ----------------------------------------------");
        printMap(cells);
        while ( !endLoop ){
            automaton.cells.put(
                    coord,
                    nextCellState(
                            cells.get(coord),
                            neighborsStrategy.cellNeighbors(coord)));

            if ( hasNextCoordinates(coord))
                coord = nextCoordinates(coord);
            else
                endLoop = true;
        }

        System.out.println(" ----------------------------------------------\nPo obliczeniu");
        printMap(automaton.cells);
        System.out.println(" ----------------------------------------------");


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
