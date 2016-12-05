package com.bearcave.automaty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by miwas on 05.11.16.
 */
public class LangtonAnt extends Automaton2Dim {

    private AntState antDirection = AntState.NORTH;

    public LangtonAnt(int x, int y, Map<CellCoordinates,CellState> initialMap){
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
        Automaton automaton = new LangtonAnt(this.getWidth(), this.getHeight(), this.getCellMap());
        return automaton;
    }

    protected CellCoordinates nextCoordinates(CellCoordinates cellCoordinates) {

        return null;
    }

    protected CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates) {
        if ( currentState == BinaryState.ALIVE){

        } else {

        }
        return null;
    }

    @Override
    public void iterate(Automaton automaton) {

    }
}
