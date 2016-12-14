package com.bearcave.automaty;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by miwas on 14.12.16.
 */
public class RuleTest {

    @Test
    public void setRule() throws Exception {
        Rule rule = new Rule();
        rule.setRule(8);
    }

    @Test
    public void getStateFromRule30() throws Exception {
        Rule rule = new Rule();
        rule.setRule(30);
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE));

    }

    @Test
    public void getStateFromRule110() throws Exception {
        Rule rule = new Rule();
        rule.setRule(110);
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.DEAD, BinaryState.DEAD, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.DEAD, BinaryState.ALIVE, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.ALIVE, BinaryState.DEAD, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE));

    }

    @Test
    public void getStateFromRule89WithNullStates() throws Exception {

        Rule rule = new Rule();
        rule.setRule(89); //01011001
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(null, BinaryState.DEAD, null));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.DEAD, BinaryState.DEAD, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(null, BinaryState.ALIVE, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.DEAD, BinaryState.ALIVE, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.ALIVE, null, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.ALIVE, BinaryState.DEAD, BinaryState.ALIVE));
        Assert.assertEquals(BinaryState.ALIVE, rule.getState(BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.DEAD));
        Assert.assertEquals(BinaryState.DEAD, rule.getState(BinaryState.ALIVE, BinaryState.ALIVE, BinaryState.ALIVE));

    }



}