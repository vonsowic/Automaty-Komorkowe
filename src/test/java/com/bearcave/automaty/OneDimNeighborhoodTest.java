package com.bearcave.automaty;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by miwas on 14.12.16.
 */
public class OneDimNeighborhoodTest {
    @Test
    public void cellNeighbors() throws Exception {

        int x = 42;
        CellNeighborhood oneDimNeighborhood = new OneDimNeighborhood();
        Set set = oneDimNeighborhood.cellNeighbors(new Coords1D(x));

        Assert.assertTrue(set.contains(new Coords1D(x-1)));
        Assert.assertTrue(set.contains(new Coords1D(x+1)));
        Assert.assertEquals(2, set.size());
    }

    @Test
    public void cellFurtherNeighbors() throws Exception {

        int x = 89;
        CellNeighborhood oneDimNeighborhood = new OneDimNeighborhood(2);
        Set set = oneDimNeighborhood.cellNeighbors(new Coords1D(x));

        Assert.assertTrue(set.contains(new Coords1D(x-1)));
        Assert.assertTrue(set.contains(new Coords1D(x+1)));
        Assert.assertTrue(set.contains(new Coords1D(x-2)));
        Assert.assertTrue(set.contains(new Coords1D(x+2)));


    }

}