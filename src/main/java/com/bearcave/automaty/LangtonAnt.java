package com.bearcave.automaty;

import java.util.*;

/**
 * Created by miwas on 05.11.16.
 */
public class LangtonAnt extends Automaton2Dim {

    private Map<CellCoordinates, AntState> antsMap;

    public LangtonAnt(
            int x,
            int y,
            Map<CellCoordinates,CellState> initialMap,
            Map<CellCoordinates,AntState> antsMap){

        this.neighborsStrategy = new VonNeumanNeighborhood();
        this.setSize(x, y);

        Map<CellCoordinates, CellState> map = new HashMap();
        if (initialMap == null){
            this.stateFactory = new UniformStateFactory(BinaryState.DEAD);
        } else {
            this.stateFactory = new GeneralStateFactory(initialMap, BinaryState.DEAD);
        }

        this.antsMap = new HashMap<>( antsMap );

        for(int i = 0; i<x; i++) {
            for (int j = 0; j < y; j++) {
                Coords2d pomcoords = new Coords2d(i, j);
                map.put(pomcoords, stateFactory.initialState(pomcoords));
            }
        }

        insertStructure(map);
    }

    protected Automaton newInstance() {
        Automaton automaton = new LangtonAnt(
                this.getWidth(),
                this.getHeight(),
                this.getCellMap(),
                this.antsMap
                );
        return automaton;
    }

    protected CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates) {

        if ( antsMap.get(currentState) != AntState.NONE){
            if ( currentState == BinaryState.ALIVE){

                return BinaryState.DEAD;
            } else {

            }
        }

        if ( currentState == BinaryState.ALIVE){
            //ant has to turn left
            Iterator iterator = neighborsStates.iterator();
            while (iterator.hasNext()){

            }
        } else {
            //ant has to turn right

        }
        return null;
    }
}
