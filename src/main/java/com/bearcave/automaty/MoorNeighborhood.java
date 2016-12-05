package com.bearcave.automaty;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by miwas on 05.11.16.
 */
public class MoorNeighborhood implements CellNeighborhood {

        /*
        @return zwraca obiekt klasy Set, skladajacy sie z Coords2d okalajacych komorke cell
        */
        public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
            Set neighbors = new CopyOnWriteArraySet();

            for ( int i=-1; i<2; i++){
                for (int j=-1; j<2; j++){
                    //warunek uniemozliwia dodanie wlasnych wpolrzednych
                    if (i!=0 || j!=0){
                        int x = cell.getWidth()+i;
                        int y = cell.getHeight()+j;
                        if ( x>=0 && y>=0){
                            neighbors.add(new Coords2d(x, y));
                        }
                    }
                }
            }

            return neighbors;
        }

}
