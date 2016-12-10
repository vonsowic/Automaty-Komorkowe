package com.bearcave.automaty;

import java.util.ArrayList;

/**
 * Created by miwas on 10.12.16.
 */
public class Rule {
    private ArrayList<Integer> rules;

    Rule(Integer rule){

        rules = new ArrayList<>();

        while( rule>0 ){
            rules.add(rule%2);
            rule/=2;
        }

        for ( int i = 0; i<rules.size()/2; i++){
            Integer pom = rules.get(i);
            rules.set(i, rules.get( rules.size()-1-i));
            rules.set(rules.size()-1-i, pom);
        }
    }

    public BinaryState getState(
            BinaryState left,
            BinaryState middle,
            BinaryState right){

        if ( rules.get( findPatterngetState(left, middle, right)) == 1)
            return BinaryState.ALIVE;
        else
            return BinaryState.DEAD;
    }

    private int findPatterngetState(
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
