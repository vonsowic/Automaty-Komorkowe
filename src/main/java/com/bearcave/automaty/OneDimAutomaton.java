package com.bearcave.automaty;

import java.util.*;

/**
 * Created by miwas on 10.12.16.
 */
public class OneDimAutomaton extends Automaton1Dim {

    private Rule rule;

    public OneDimAutomaton(
            int size,
            int levelOfNeighborhood,
            Integer rule,
            Map<CellCoordinates, CellState> initialMap){

        stateFactory = new UniformStateFactory(BinaryState.DEAD);
        this.setSize(size);
        this.rule = new Rule(rule);

        neighborsStrategy = new OneDimNeighborhood(levelOfNeighborhood);

        if (initialMap == null){
            initialMap = new HashMap<>();
            for( int i=0; i<size; i++){
                Coords1D coords = new Coords1D(i);
                initialMap.put(coords, stateFactory.initialState(coords));
            }
        }

        insertStructure(initialMap);
    }

    @Override
    protected Automaton newInstance() {
        return null;
    }


    @Override
    protected CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates) {

        return null;
    }
}
