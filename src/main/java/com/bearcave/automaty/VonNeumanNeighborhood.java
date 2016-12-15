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
    private int lastX, lastY;

    public VonNeumanNeighborhood(int lastX, int lastY){
        this.lastX = lastX;
        this.lastY = lastY;
        n = 1;
    }

    public VonNeumanNeighborhood(int lastX, int lastY, int n){
        this.lastX = lastX;
        this.lastY = lastY;
        if(n>0)
            this.n = n;
        else
            this.n = 1;
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
                    CellCoordinates cellCoordinates = new Coords2d(cell.getX()+i, cell.getY()+j);
                    if (cellCoordinates.getX() < 0){
                        cellCoordinates.setX(lastX + cellCoordinates.getX() );
                    }
                    if (cellCoordinates.getX() >= lastX){
                        cellCoordinates.setX(cellCoordinates.getX() - lastX);
                    }
                    neighbors.add(cellCoordinates);

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
