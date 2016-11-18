package com.bearcave.automaty;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by miwas on 16.11.16.
 */
public class VonNeumanNeighborhoodTest {

    @Test
    public void cellNeighbors() throws Exception {

        int width = 4, height=5;

        VonNeumanNeighborhood vonNeumanNeighborhood = new VonNeumanNeighborhood();
        Set set = vonNeumanNeighborhood.cellNeighbors(new Coords2d(width, height));

        Iterator<CellCoordinates> iterator = set.iterator();

        for ( int i=-1; i<2; i++){
            for (int j=-1; j<2 && iterator.hasNext(); j++){
                if (i!=0 || j!=0){
                    CellCoordinates cellCoordinates = iterator.next();
                    Assert.assertEquals(width + i, cellCoordinates.getWidth());
                    Assert.assertEquals(height + j, cellCoordinates.getHeight());
                }
            }
        }
    }

}