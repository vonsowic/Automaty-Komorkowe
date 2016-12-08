package com.bearcave.automaty;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by miwas on 05.11.16.
 */
public class VonNeumanNeighborhood implements CellNeighborhood {

    /**
     * n equals the level of neighborhood
     */
    private int n ;

    public VonNeumanNeighborhood(){
        n = 1;
    }

    public VonNeumanNeighborhood(int n){
        this.n = n;
    }

    /*
    @return zwraca obiekt klasy Set, skladajacy sie z Coords2d okalajacych komorke cell
     */
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        Set<CellCoordinates> neighbors = new HashSet<>();

        for ( int i=-n; i<=0; i++){
            for (int j=-i-n; j<=i+n; j++){
                //warunek uniemozliwia dodanie samego siebie
                if (i!=0 || j!=0){
                    neighbors.add(new Coords2d(cell.getX()+i, cell.getY()+j));
                }
            }
        }

        for ( int i=1; i<=n; i++){
            for (int j=-n+i; j<=n-i; j++){
                neighbors.add(new Coords2d(cell.getX()+i, cell.getY()+j));
            }
        }

        return neighbors;
    }
}
