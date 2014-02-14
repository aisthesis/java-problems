/**
 * 
 */
package com.codemelon.problems;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

/**
 * @author marshallfarrier
 *
 */
public class MakeChangeTest {
    private MakeChange makeChange;

    /**
     * Test method for {@link com.codemelon.problems.MakeChange#MakeChange(int[])}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testMakeChangeZeroCoin() {
        int[] input = { 1, 0, 3 };
        makeChange = new MakeChange(input);
    }
    
    /**
     * Test method for {@link com.codemelon.problems.MakeChange#MakeChange(int[])}.
     */
    @Test
    public void testMakeChange() {
        int[] input = { 1, 5, 3, 5, 4 };
        int[] expected = { 1, 3, 4, 5 };
        makeChange = new MakeChange(input);
        assertEquals(expected.length, makeChange.getCoinCount());
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], makeChange.getCoinValueFromIndex(i));
        }
    }

    /**
     * Test method for {@link com.codemelon.problems.MakeChange#getCoinValueFromIndex(int)}.
     */
    @Test
    public void testGetCoinValueFromIndex() {
        int[] input = { 6, 5, 8, 2, 8, 8, 8 };
        int[] expected = { 2, 5, 6, 8 };
        makeChange = new MakeChange(input);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], makeChange.getCoinValueFromIndex(i));
        }
    }

    /**
     * Test method for {@link com.codemelon.problems.MakeChange#getAnswer(int)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testGetAnswerNegativeValue() {
        int[] input = { 1, 7, 10 };
        makeChange = new MakeChange(input);
        makeChange.getAnswer(-1);
    }

    /**
     * Test method for {@link com.codemelon.problems.MakeChange#getCoinCount(int)}.
     */
    @Test
    public void testGetCoinCountIntSolvable() {
        int[] input = { 1, 7, 10 };
        makeChange = new MakeChange(input);
        for (int val : input) {
            assertEquals(1, makeChange.getCoinCount(val));
        }
        assertEquals(2, makeChange.getCoinCount(14));
    }
    
    /**
     * Test method for {@link com.codemelon.problems.MakeChange#getCoinCount(int)}.
     */
    @Test
    public void testGetCoinCountIntUnsolvable() {
        int[] input = { 2, 4 };
        makeChange = new MakeChange(input);
        assertEquals(-1, makeChange.getCoinCount(5));
        assertEquals(-1, makeChange.getCoinCount(1));
    }

    /**
     * Test method for {@link com.codemelon.problems.MakeChange#getAnswer(int)}.
     */
    @Test
    public void testGetAnswerSolvable() {
        int i, j;
        List<Integer> answer;
        int[] input = { 1, 7, 10 };
        makeChange = new MakeChange(input);
        for (i = 0; i < input.length; i++) {
            answer = makeChange.getAnswer(makeChange.getCoinValueFromIndex(i));
            for (j = 0; j < input.length; j++) {
                if (i == j) {
                    assertEquals(1, answer.get(j).intValue());
                }
                else {
                    assertEquals(0, answer.get(j).intValue());
                }
            }
        }
        answer = makeChange.getAnswer(14);
        int[] expected1 = { 0, 2, 0 };
        for (i = 0; i < expected1.length; i++) {
            assertEquals(expected1[i], answer.get(i).intValue());
        }

        answer = makeChange.getAnswer(32);
        int[] expected2 = { 2, 0, 3 };
        for (i = 0; i < expected2.length; i++) {
            assertEquals(expected2[i], answer.get(i).intValue());
        }
    }
    /**
     * Test method for {@link com.codemelon.problems.MakeChange#getAnswer(int)}.
     */
    @Test
    public void testGetAnswerUnsolvable() {
        int[] input = { 2, 4 };
        makeChange = new MakeChange(input);
        List<Integer> answerFor3 = makeChange.getAnswer(3);
        List<Integer> answerFor1 = makeChange.getAnswer(1);
        for (int i = 0; i < input.length; i++) {
            assertEquals(-1, answerFor1.get(i).intValue());
            assertEquals(-1, answerFor3.get(i).intValue());
        }
    }

}
