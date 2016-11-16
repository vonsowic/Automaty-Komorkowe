package com.bearcave.automaty;

import java.util.Iterator;
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
    private CellNeighborhood neighborsStrategy;
    private CellStateFactory stateFactory;


    protected abstract Automaton newInstance( CellStateFactory cellStateFactory, CellNeighborhood cellNeighborhood);

    protected abstract boolean hasNextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellCoordinates initialCoordiantes(CellCoordinates cellCoordinates);

    protected abstract CellCoordinates nextCoordinates(CellCoordinates cellCoordinates);

    protected abstract CellState nextCellState(CellState currentState, Set<Cell> neighborsStates);

    public Automaton nextState(){
        return null;
    }

    public void insertStructure(Map<? extends CellCoordinates, ? extends CellState> map){

    }

    private Set<Cell> mapCoordinates(Set<CellCoordinates> setOfCellCoordiantes){
        return null;
    }

    protected class CellIterator implements Iterator<Cell>{
        private CellCoordinates currentState;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public Cell next() {
            return null;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */

    }
}
