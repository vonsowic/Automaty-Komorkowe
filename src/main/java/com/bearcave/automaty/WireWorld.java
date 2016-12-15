package com.bearcave.automaty;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.bearcave.automaty.QuadState.DEAD;

/**
 * Created by miwas on 05.11.16.
 */
public class WireWorld extends Automaton2Dim {
    public WireWorld(int x, int y, Map<CellCoordinates,CellState> initialMap){
        this.neighborsStrategy = new MoorNeighborhood(x, y);
        this.setSize(x, y);

        Map<CellCoordinates, CellState> map = new HashMap();
        if (initialMap == null){
            this.stateFactory = new UniformStateFactory(WireElectronState.VOID);
        } else {
            this.stateFactory = new GeneralStateFactory(initialMap, WireElectronState.VOID);
        }

        for(int i = 0; i<x; i++) {
            for (int j = 0; j < y; j++) {
                Coords2d pomcoords = new Coords2d(i, j);
                map.put(pomcoords, stateFactory.initialState(pomcoords));
            }
        }

        insertStructure(map);
    }

    public WireWorld(int x, int y, Map<CellCoordinates,CellState> initialMap, int levelOfNeighborhood){
        this(x, y, initialMap);
        this.neighborsStrategy = new MoorNeighborhood(x, y, levelOfNeighborhood);
    }

    public WireWorld(int x,
                     int y,
                     Map<CellCoordinates,CellState> previousMap,
                     CellStateFactory factory,
                     CellNeighborhood strategy){

        this.neighborsStrategy = strategy;
        this.stateFactory = factory;
        this.setSize(x, y);

        insertStructure(previousMap);
    }



    protected Automaton newInstance() {
        Automaton automaton = new WireWorld(
                this.getWidth(),
                this.getHeight(),
                getCellMap(),
                this.stateFactory,
                this.neighborsStrategy);
        return automaton;
    }

    protected CellState nextCellState(CellState currentState, Set<CellCoordinates> neighborsStates) {
        switch ((WireElectronState) currentState){
            case ELECTRON_HEAD:
                return WireElectronState.ELECTRON_TAIL;
            case ELECTRON_TAIL:
                return WireElectronState.WIRE;
            // przewodnik
            case WIRE:
                int headNeighbors = 0;
                Iterator<CellCoordinates> iterator = neighborsStates.iterator();
                while (iterator.hasNext()){
                    if ( getCellState( iterator.next() ) == WireElectronState.ELECTRON_HEAD)
                        headNeighbors++ ;
                }
                if ( headNeighbors == 1 || headNeighbors == 2){
                    return WireElectronState.ELECTRON_HEAD;
                }
            default:
                return currentState;
        }
    }
}
