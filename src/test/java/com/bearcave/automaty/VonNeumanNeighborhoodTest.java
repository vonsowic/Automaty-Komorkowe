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

        VonNeumanNeighborhood vonNeumanNeighborhood = new VonNeumanNeighborhood(100, 100);
        Set set = vonNeumanNeighborhood.cellNeighbors(new Coords2d(width, height));

        Assert.assertTrue(set.contains(new Coords2d(width-1, height)));
        Assert.assertTrue(set.contains(new Coords2d(width+1, height)));
        Assert.assertTrue(set.contains(new Coords2d(width, height-1)));
        Assert.assertTrue(set.contains(new Coords2d(width, height+1)));

    }

    @Test
    public void cellFurtherNeighbors() throws Exception {

        int width = 13, height=5;

        VonNeumanNeighborhood vonNeumanNeighborhood = new VonNeumanNeighborhood(100, 100, 2);
        Set set = vonNeumanNeighborhood.cellNeighbors(new Coords2d(width, height));

        Assert.assertTrue(set.contains(new Coords2d(width-1, height)));
        Assert.assertTrue(set.contains(new Coords2d(width+1, height)));
        Assert.assertTrue(set.contains(new Coords2d(width, height-1)));
        Assert.assertTrue(set.contains(new Coords2d(width, height+1)));
        Assert.assertTrue(set.contains(new Coords2d(width-1, height-1)));
        Assert.assertTrue(set.contains(new Coords2d(width+1, height-1)));
        Assert.assertTrue(set.contains(new Coords2d(width+1, height+1)));
        Assert.assertTrue(set.contains(new Coords2d(width-1, height+1)));
        Assert.assertTrue(set.contains(new Coords2d(width-2, height)));
        Assert.assertTrue(set.contains(new Coords2d(width+2, height)));
        Assert.assertTrue(set.contains(new Coords2d(width, height-2)));
        Assert.assertTrue(set.contains(new Coords2d(width, height+2)));

    }

}