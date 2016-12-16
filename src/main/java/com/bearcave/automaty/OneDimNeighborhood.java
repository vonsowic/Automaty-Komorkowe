package com.bearcave.automaty;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by miwas on 10.12.16.
 */
public class OneDimNeighborhood implements CellNeighborhood {

    private int n ;
    private int size;

    public OneDimNeighborhood(int size) {
        this.size = size;
        n = 1;
    }

    public OneDimNeighborhood(int size, int n){
        this.size = size;
        if(n>0)
            this.n = n;
        else
            this.n = 1;
    }

    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates coord) {

        HashSet<CellCoordinates> set = new HashSet<>();
        for ( int i=-n; i<=n; i++){
            if ( i != 0) {
                int x = coord.getX() + i;
                /*
                if ( x<0)
                    x+=size;
                if (x>=size)
                    x-=size;
                */
                set.add(new Coords1D(x));
            }
        }
        return set;
    }
}
