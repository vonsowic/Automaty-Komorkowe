package com.bearcave.automaty;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by miwas on 10.12.16.
 */
public class OneDimNeighborhood implements CellNeighborhood {

    private int n ;

    public OneDimNeighborhood(){
        n = 1;
    }

    public OneDimNeighborhood(int n){
        if(n>0)
            this.n = n;
        else
            this.n = 1;
    }

    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates coord) {

        HashSet<CellCoordinates> set = new HashSet<>();
        for ( int i=-n; i<=n; i++){
            if ( n != 0) {
                set.add(new Coords1D(coord.getX() + i));
            }
        }
        return set;
    }
}
