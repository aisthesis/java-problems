/**
 * Find the minimal number of coins required to make a given amount of change.
 */
package com.codemelon.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marshall Farrier
 * @since 2014-02-12
 * 
 * TODO:
 * 1) Test cases
 * 2) Case of no solution, e.g. coins { 2, 4 } and value 5 (or 1)
 *
 */
public class MakeChange {
    private List<Integer> coins;
    private List<List<Integer>> solutions;
    
    /**
     * Constructs a new MakeChange object from an array specifying 
     * the values of all coins to be used.
     * 
     * @param input
     */
    public MakeChange(int[] input) {
        Arrays.sort(input);
        coins = new ArrayList<Integer>(input.length);
        if (input[0] <= 0) {
            throw new IllegalArgumentException("coin value must be greater than 0");
        }
        coins.add(input[0]);
        for (int i = 1; i < input.length; i++) {
            if (input[i] != input[i - 1]) {
                coins.add(input[i]);
            }
        }
        solutions = new ArrayList<List<Integer>>();
        this.setBaseSolutions();
    }
    
    public int getCoinValueFromIndex(int index) {
        return coins.get(index);
    }
    
    public int getCoinCount() {
        return coins.size();
    }
    
    /**
     * Returns -1 if no solution is possible, e.g. coins { 2, 4 }, value 5
     * 
     * @param value
     * @return
     */
    public int getCoinCount(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value cannot be negative");
        }
        
        // answer hasn't been computed
        if (value >= solutions.size() || solutions.get(value).size() == 0) {
            setSolutions(value);
        }
        return solutions.get(value).get(coins.size());
    }
    
    /**
     * All values are -1 if no solution is possible
     * 
     * @param value
     * @return
     */
    public List<Integer> getAnswer(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value cannot be negative");
        }
        int i;
        List<Integer> answer = new ArrayList<Integer>(coins.size());
        
        // answer hasn't been computed
        if (value >= solutions.size() || solutions.get(value).size() == 0) {
            setSolutions(value);
        }

        for (i = 0; i < coins.size(); i++) {
            answer.add(solutions.get(value).get(i));
        }
        return answer;
    }
    
    private void setBaseSolutions() {
        int i, j;
        
        for (i = 0; i <= coins.get(coins.size() - 1); i++) {
            solutions.add(new ArrayList<Integer>(coins.size() + 1));
        }
        
        // solution for value 0 (0 coins)
        for (j = 0; j <= coins.size(); j++) {
            solutions.get(0).add(0);
        }
        
        // solutions for values matching a coin
        for (i = 0; i < coins.size(); i++) {
            for (j = 0; j < coins.size(); j++) {
                solutions.get(coins.get(i)).add(0);
            }
            // 1 at the end of the list for total number of coins used
            solutions.get(coins.get(i)).add(1);
            // 1 at the i-th position to specify which coin was used
            solutions.get(coins.get(i)).set(i, 1);
        }
    }
    
    private void setSolutions(int max) {
        int currentMax = solutions.size() - 1;
        int diff = max - currentMax;
        int i;
        
        for (i = 0; i < diff; i++) {
            solutions.add(new ArrayList<Integer>(coins.size() + 1));
        }
        
        for (i = 0; i <= max; i++) {
            if (solutions.get(i).size() == 0) {
                setLowestUnsolved(i);
            }
        }
    }
    
    /**
     * Assumes that solutions for all lower values have been set
     * @param value
     */
    private void setLowestUnsolved(int value) {
        int indexOfTotal = coins.size();
        int indexOfCoinToUse = 0, j;
        
        for (j = 0; j < coins.size(); j++) {
            solutions.get(value).add(0);
        }        
        // worse than worst possible solution (all 1 cent coins)
        solutions.get(value).add(value + 1);
        
        for (j = 0; j < coins.size(); j++) {
            if (value > coins.get(j) && 
                    solutions.get(value - coins.get(j)).get(indexOfTotal) + 1 < solutions.get(value).get(indexOfTotal) &&
                    solutions.get(value - coins.get(j)).get(indexOfTotal) != -1) {
                indexOfCoinToUse = j;
                solutions.get(value).set(indexOfTotal, solutions.get(value - coins.get(j)).get(indexOfTotal) + 1);
            }
        }
        
        if (solutions.get(value).get(indexOfTotal) <= value) {
            for (j = 0; j < coins.size(); j++) {
                solutions.get(value).set(j, solutions.get(value - coins.get(indexOfCoinToUse)).get(j));
            }
            solutions.get(value).set(indexOfCoinToUse, solutions.get(value).get(indexOfCoinToUse) + 1);
        }
        else {
            setAllToMinusOne(solutions.get(value));
        }
    }
    
    private static void setAllToMinusOne(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, -1);
        }
    }
}
