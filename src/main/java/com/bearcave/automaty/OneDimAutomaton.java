package com.bearcave.automaty;

import javafx.scene.control.Cell;

import java.util.*;

/**
 * Created by miwas on 10.12.16.
 */
public class OneDimAutomaton extends Automaton1Dim {

    private Rule rule;

    public OneDimAutomaton(
            int size,
            Integer rule,
            Map<CellCoordinates, CellState> initialMap){

        stateFactory = new UniformStateFactory(BinaryState.DEAD);
        this.setSize(size);
        this.rule = new Rule(rule);

        neighborsStrategy = new OneDimNeighborhood();

        if (initialMap == null){
            initialMap = new HashMap<>();
            for( int i=0; i<size; i++){
                Coords1D coords = new Coords1D(i);
                initialMap.put(coords, stateFactory.initialState(coords));
            }
        }

        insertStructure(initialMap);
    }

    public OneDimAutomaton(
            int size,
            Rule rule){

        stateFactory = new UniformStateFactory(BinaryState.DEAD);
        this.setSize(size);
        this.rule = rule;

        neighborsStrategy = new OneDimNeighborhood();

        Map<CellCoordinates, CellState> initialMap = new HashMap<>();
        for( int i=0; i<size; i++){
            Coords1D coords = new Coords1D(i);
            initialMap.put(coords, stateFactory.initialState(coords));
        }
        insertStructure(initialMap);
    }

    @Override
    protected Automaton newInstance() {
        Automaton automaton = new OneDimAutomaton(getSize(), rule);
        return automaton;
    }


    @Override
    protected CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates) {
        Iterator<CellCoordinates> iterator = neighborsStates.iterator();
        CellState
                right,
                left;
        CellCoordinates
                pom1 = iterator.next(),
                pom2 = iterator.next();
        if ( pom1.getX() < pom2.getX()){
            left = getCellState(pom1);
            right = getCellState(pom2);
        } else {
            left = getCellState(pom2);
            right = getCellState(pom1);
        }

        return rule.getState((BinaryState) left, (BinaryState)currentState, (BinaryState) right);
    }
}
