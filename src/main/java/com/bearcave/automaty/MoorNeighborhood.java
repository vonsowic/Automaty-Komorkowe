package com.bearcave.automaty;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by miwas on 05.11.16.
 */
public class MoorNeighborhood implements CellNeighborhood {

    /**
     * n equals the level of neighborhood
     */
    private int n ;

    public MoorNeighborhood(){
        n = 1;
    }

    public MoorNeighborhood(int n){
        if(n>0)
            this.n = n;
        else
            this.n = 1;
    }


    /*
    @return zwraca obiekt klasy Set, skladajacy sie z Coords2d okalajacych komorke cell
    */
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        Set neighbors = new CopyOnWriteArraySet();

        for ( int i=-n; i <= n; i++){
            for (int j=-n; j <= n; j++){
                //warunek uniemozliwia dodanie wlasnych wpolrzednych
                if (i!=0 || j!=0){
                    int x = cell.getX()+i;
                    int y = cell.getY()+j;
                    if ( x>=0 && y>=0){
                        neighbors.add(new Coords2d(x, y));
                    }
                }
            }
        }

        return neighbors;
    }



}
