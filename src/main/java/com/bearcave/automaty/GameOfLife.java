package com.bearcave.automaty;

import java.util.*;

/**
 * Created by miwas on 05.11.16.
 */
public class GameOfLife extends Automaton2Dim {

    static TreeSet<Integer> liveRule;
    static TreeSet<Integer> deathRule;

    public GameOfLife(int x,
                      int y,
                      Map<CellCoordinates,CellState> previousMap,
                      CellStateFactory factory,
                      CellNeighborhood strategy){

        this.neighborsStrategy = strategy;
        this.stateFactory = factory;
        this.setSize(x, y);
        insertStructure(previousMap);
    }

    public GameOfLife(int x, int y, Map<CellCoordinates,CellState> initialMap){

        this.setSize(x, y);
        this.neighborsStrategy = new MoorNeighborhood(getWidth(), getHeight());


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

        this.liveRule = new TreeSet<>();
        this.liveRule.add(2);
        this.liveRule.add(3);

        this.deathRule = new TreeSet<>();
        this.deathRule.add(3);

        insertStructure(map);
    }

    public GameOfLife(int x, int y, Map<CellCoordinates,CellState> initialMap, int levelOfNeighborhood){
        this(x, y, initialMap);
        this.neighborsStrategy = new MoorNeighborhood(getWidth(), getHeight(), levelOfNeighborhood);
    }

    public GameOfLife(int x, int y, Map<CellCoordinates,CellState> initialMap, int levelOfNeighborhood, String liveRule, String deathRule){
        this(x, y, initialMap, levelOfNeighborhood);
        if ( !liveRule.isEmpty()){
            this.liveRule = new TreeSet<>();
            Integer pom = Integer.valueOf(liveRule);
            while( pom>0 ){
                this.liveRule.add(pom%10);
                pom/=10;
            }
        }

        if ( !deathRule.isEmpty()){
            this.deathRule = new TreeSet<>();
            Integer pom = Integer.valueOf(deathRule);
            while( pom>0 ){
                this.deathRule.add(pom%10);
                pom/=10;
            }
        }
    }

    protected Automaton newInstance() {
        Automaton automaton = new GameOfLife(
                this.getWidth(),
                this.getHeight(),
                this.getCellMap(),
                stateFactory,
                neighborsStrategy);

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
            if (liveRule.contains(living_neighbors)){
                return BinaryState.ALIVE;
            } else {
                return BinaryState.DEAD;
            }
        } else {
            if ( deathRule.contains( living_neighbors)){
                return BinaryState.ALIVE;
            } else {
                return BinaryState.DEAD;
            }
        }
    }

}

