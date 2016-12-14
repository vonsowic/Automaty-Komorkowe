package com.bearcave.automaty;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by miwas on 14.12.16.
 */
public class MoorNeighborhoodTest {
    @Test
    public void cellNeighbors() throws Exception {

        int width =24;
        int height = 42;

        CellNeighborhood moorNeighborhood = new MoorNeighborhood();
        Set set = moorNeighborhood.cellNeighbors(new Coords2d(width, height));

        Assert.assertTrue(set.contains(new Coords2d(width-1, height)));
        Assert.assertTrue(set.contains(new Coords2d(width+1, height)));
        Assert.assertTrue(set.contains(new Coords2d(width, height-1)));
        Assert.assertTrue(set.contains(new Coords2d(width, height+1)));
        Assert.assertTrue(set.contains(new Coords2d(width-1, height-1)));
        Assert.assertTrue(set.contains(new Coords2d(width+1, height-1)));
        Assert.assertTrue(set.contains(new Coords2d(width+1, height+1)));
        Assert.assertTrue(set.contains(new Coords2d(width-1, height+1)));
    }

    @Test
    public void cellFurtherNeighbors() throws Exception {

        int width =9;
        int height = 17;

        CellNeighborhood moorNeighborhood = new MoorNeighborhood(2);
        Set set = moorNeighborhood.cellNeighbors(new Coords2d(width, height));

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
        Assert.assertTrue(set.contains(new Coords2d(width-2, height-2)));
        Assert.assertTrue(set.contains(new Coords2d(width+2, height-2)));
        Assert.assertTrue(set.contains(new Coords2d(width+2, height+2)));
        Assert.assertTrue(set.contains(new Coords2d(width-2, height+2)));

        Assert.assertTrue(set.contains(new Coords2d(width-2, height-2)));
        Assert.assertTrue(set.contains(new Coords2d(width+2, height-2)));
        Assert.assertTrue(set.contains(new Coords2d(width+2, height+2)));
        Assert.assertTrue(set.contains(new Coords2d(width-2, height+2)));


    }

}