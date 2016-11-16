package com.bearcave.automaty;

import java.util.ArrayList;

/**
 * Created by miwas on 16.11.16.

 */
public class Board {
    private ArrayList<ArrayList<Cell>> map;

    Board(){
        map = new ArrayList<ArrayList<Cell>>();
    }

    Board(int width, int height) {
        this.setSize(width, height);
    }

    public void setSize(int width, int height) {
        map = new ArrayList<ArrayList<Cell>>();
        for (int i = 0; i < width; i++) {
            map.add(new ArrayList<Cell>());
            for (int j = 0; j < height; j++) {
                map.get(i).add(new Cell());
            }
        }
    }

    public ArrayList<ArrayList<Cell>> getMap(){
        return map;
    }

}
