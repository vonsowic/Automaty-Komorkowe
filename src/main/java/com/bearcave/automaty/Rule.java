package com.bearcave.automaty;

import java.util.ArrayList;

/**
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
        int i = 0;
        while( rule>0 ){
            rules.set(rules.size()-i-1, rule%2);
            rule/=2;
            i++;
        }
    }

    public BinaryState getState(
            BinaryState left,
            BinaryState middle,
            BinaryState right){

        if ( rules.get( findPattern(left, middle, right)) == 1)
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
                            case DEAD:
                                return 1;
                        }
                        break;
                    case DEAD:
                        switch (right){
                            case ALIVE:
                                return 2;
                            case DEAD:
                                return 3;
                        }
                        break;
                }
                break;
            case DEAD:
                switch (middle){
                    case ALIVE:
                        switch (right){
                            case ALIVE:
                                return 4;
                            case DEAD:
                                return 5;
                        }
                    case DEAD:
                        switch (right){
                            case ALIVE:
                                return 6;
                            case DEAD:
                                return 7;
                        }
                        break;
                }
                break;
        }
        return -1;
    }
}
