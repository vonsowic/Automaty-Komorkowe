package com.bearcave.automaty;

/**
 * Created by miwas on 05.11.16.
 */
public enum BinaryState implements CellState {
    DEAD(false),
    ALIVE(true);

    public boolean alive;
    BinaryState(boolean isAlive){
        alive = isAlive;
    }

    public boolean isAlive(BinaryState state){
        return alive;
    }
}
