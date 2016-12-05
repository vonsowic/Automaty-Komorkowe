package com.bearcave.automaty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by miwas on 05.11.16.
 */
public class GameOfLife extends Automaton2Dim {

    public GameOfLife(int x,
                      int y,
                      CellStateFactory factory){

        this.neighborsStrategy = new MoorNeighborhood();
        this.stateFactory = factory;
        this.setSize(x, y);

        Map<CellCoordinates, CellState> map = new HashMap();
        insertStructure(map);
    }


    public GameOfLife(int x, int y, Map<CellCoordinates,CellState> initialMap){

        this.neighborsStrategy = new MoorNeighborhood();
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
        Automaton automaton = new GameOfLife(this.getWidth(), this.getHeight(), stateFactory);
        return automaton;
    }

    /**
     *
     * @param currentState
     * @param neighborsStates
     * @return stan komorki zgodny z zasadami gry
     */
    protected CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates) {

        Iterator<CellCoordinates> iterator = neighborsStates.iterator();
        // licze zywych sasiadow
        int living_neighbors = 0;
        while (iterator.hasNext()){
            if ( getCellMap().get(iterator.next()) == BinaryState.ALIVE){
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

