package com.bearcave.automaty;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by miwas on 05.11.16.
 */
public class GameOfLife extends Automaton2Dim {

    public GameOfLife(CellStateFactory initialState){

    }

    public GameOfLife(){

    }

    protected Automaton newInstance() {
        Automaton automaton = new GameOfLife();
        this.neighborsStrategy = new VonNeumanNeighborhood();

        return automaton;
    }

    /**
     *
     * @param currentState
     * @param neighborsStates
     * @return stan komorki zgodny z zasadami gry
     */
    protected CellState nextCellState(CellState currentState, Set<Cell> neighborsStates) {

        Iterator<Cell> iterator = neighborsStates.iterator();

        // licze zywych sasiadow
        int living_neighbors = 0;
        while (iterator.hasNext()){
            Cell cell = iterator.next();
            if ( cell.state == BinaryState.ALIVE){
                living_neighbors++;
            }
        }

        //zwracany wynik zgodnie z zasadami gry
        if ( currentState == BinaryState.ALIVE){
            if (living_neighbors<2 || living_neighbors>3){
                return BinaryState.ALIVE;
            } else {
                return BinaryState.DEAD;
            }
        } else {
            if ( living_neighbors == 3){
                return BinaryState.ALIVE;
            } else {
                return BinaryState.DEAD;
            }
        }
    }

    protected class CellIterator extends Automaton.CellIterator{

        CellIterator(){
            currentCoords = new Coords2d(0, 0);
        }


        @Override
        public Cell next(){
            Cell cell = new Cell();
            if (currentCoords.getHeight() < getHeight()-1){
                currentCoords.setHeight( currentCoords.getHeight()+1);

            } else if (currentCoords.getWidth() < getWidth()-1){
                currentCoords.setWidth( currentCoords.getWidth()+1);
                currentCoords.setHeight(0);
            }
            cell.coords = currentCoords;
            cell.state = getCellState(cell.coords);
            return cell;
        }

    }
}
