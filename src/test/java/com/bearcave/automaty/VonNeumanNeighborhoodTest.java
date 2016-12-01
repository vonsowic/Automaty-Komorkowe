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

        Assert.assertTrue(set.contains(new Coords2d(3, 4)));
        Assert.assertTrue(set.contains(new Coords2d(3, 5)));
        Assert.assertTrue(set.contains(new Coords2d(3, 6)));
        Assert.assertTrue(set.contains(new Coords2d(4, 4)));
        Assert.assertTrue(set.contains(new Coords2d(4, 6)));
        Assert.assertTrue(set.contains(new Coords2d(5, 4)));
        Assert.assertTrue(set.contains(new Coords2d(5, 5)));
        Assert.assertTrue(set.contains(new Coords2d(5, 6)));



    }

}