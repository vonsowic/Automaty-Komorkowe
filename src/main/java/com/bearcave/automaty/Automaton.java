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

    protected abstract CellCoordinates initialCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellCoordinates nextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighborsStates);

    public Automaton nextState(){

        Automaton automaton = newInstance();
        CellIterator iterator = cellIterator();


        while (iterator.hasNext()){
            automaton.cells.put( nextCoordinates(iterator.next().coords),
                    nextCellState(cells.get(iterator.currentCoords),
                            mapCoordinates(neighborsStrategy.cellNeighbors(iterator.currentCoords))
                            ));
        }

        return automaton;
    }

    //ZMIANA W STASUNKU DO DIAGRAMU Map<? extends CellCoordinates, ? extends CellState> map
    public void insertStructure(Map<CellCoordinates, CellState> map){
        cells =  map;
    }

    public CellIterator cellIterator(){
        CellIterator iterator = new CellIterator();
        return iterator;
    }

    protected CellState getCellState(CellCoordinates coords){
        return cells.get(coords);
    }

    private Set<Cell> mapCoordinates(Set<CellCoordinates> setOfCellCoordinates){

        Set<Cell> set = new TreeSet<Cell>();
        Iterator<CellCoordinates> iterator = setOfCellCoordinates.iterator();
        while (iterator.hasNext()){
            Cell cell = new Cell();
            cell.coords = iterator.next();
            cell.state = cells.get(cell.coords);
            set.add(cell);
        }
        return set;
    }

    protected class CellIterator{
        protected CellCoordinates currentCoords;

        public boolean hasNext(){
            return hasNextCoordinates(currentCoords);
        }

        /**
         * currentState ustawia sie na nastepnej pozycji i zwracana jest nowa komorka o nowo ustawionej pozycji
         * @return nowy Cell
         */
        public Cell next(){
            return null;
        }

        public void setState(CellState cellState){
            cells.put(currentCoords, cellState);
        }
    }
}
