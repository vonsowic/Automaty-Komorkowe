package com.bearcave.automaty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by miwas on 05.11.16.
 */
public class GameOfLife extends Automaton2Dim {


    public GameOfLife(int x, int y, Map<CellCoordinates,CellState> initialMap){
        this.neighborsStrategy = new VonNeumanNeighborhood();
        this.setSize(x, y);


        Map<CellCoordinates, CellState> map = new HashMap();
        if (initialMap == null){
            this.stateFactory = new UniformStateFactory(BinaryState.DEAD);
        } else {
            this.stateFactory = new GeneralStateFactory(initialMap, BinaryState.DEAD);
        }

        for(int i = 0; i<x; i++) {
            for (int j = 0; j < y; j++) {
                Coords2d pomcoords = new Coords2d(i, j);
                map.put(pomcoords, stateFactory.initialState(pomcoords));
            }
        }

        insertStructure(map);
    }

    protected Automaton newInstance() {
        Automaton automaton = new GameOfLife(this.getWidth(), this.getHeight(), null);
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
            if (living_neighbors == 2 || living_neighbors == 3){
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

}

