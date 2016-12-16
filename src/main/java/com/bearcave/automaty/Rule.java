package com.bearcave.automaty;

import java.util.ArrayList;

/**
 * Set rule for OneDimAutomaton
 *
 * Created by miwas on 10.12.16.
 */
public class Rule {
    private ArrayList<Integer> rules;

    public Rule(){
        rules = new ArrayList<>();
        for(int i = 0; i<8; i++){
            rules.add(0);
        }
    }

    Rule(Integer rule){
        this();
        setRule(rule);
    }

    /**
     * @param rule
     */
    public void setRule(Integer rule){
        int i = rules.size()-1;
        while( rule>0 ){
            rules.set(i, rule%2);
            rule/=2;
            i--;
        }
    }

    public BinaryState getState(
            BinaryState left,
            BinaryState middle,
            BinaryState right){


        if ( left == null ) left = BinaryState.DEAD;
        if ( middle == null ) middle = BinaryState.DEAD;
        if ( right == null ) right = BinaryState.DEAD;

        if (rules.get(findPattern(left, middle, right)) == 1)
                return BinaryState.ALIVE;
            else
                return BinaryState.DEAD;

    }


    private int findPattern(
            BinaryState left,
            BinaryState middle,
            BinaryState right){

        switch (left){
            case ALIVE:
                switch (middle){
                    case ALIVE:
                        switch (right){
                            case ALIVE:
                                return 0;
                            default:
                                return 1;
                        }
                    default:
                        switch (right){
                            case ALIVE:
                                return 2;
                            case DEAD:
                                return 3;
                        }
                        break;
                }
                break;
            default:
                switch (middle){
                    case ALIVE:
                        switch (right){
                            case ALIVE:
                                return 4;
                            default:
                                return 5;
                        }
                    default:
                        switch (right){
                            case ALIVE:
                                return 6;
                            default:
                                return 7;
                        }
                }
        }

        return -1;  // unreachable, but sth must be returned

    }
}
