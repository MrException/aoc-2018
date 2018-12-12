package com.mrexception.aoc2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day11 {
    @Test
    public void testPowerLevel() {
        assertThat(new Logic(8).powerLevel(3, 5)).isEqualTo(4);
        assertThat(new Logic(57).powerLevel(122, 79)).isEqualTo(-5);
        assertThat(new Logic(39).powerLevel(217, 196)).isEqualTo(0);
        assertThat(new Logic(71).powerLevel(101, 153)).isEqualTo(4);
    }

    @Test
    public void testPartOne() {
        assertThat(new Logic(5719).partOne()).isEqualTo("21,34");
    }

    @Test
    public void testPartTwo() {
        assertThat(new Logic(5719).partTwo()).isEqualTo("90,244,16");
    }

    class Logic {

        private final int serial;
        private final int[][] grid;

        Logic(int serial) {
            this.serial = serial;

            grid = new int[300][300];
            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    grid[x][y] = powerLevel(x + 1, y + 1);
                }
            }
        }

        String partOne() {
            int[] ints = bestForSize(3);
            return (ints[0] + 1) + "," + (ints[1] + 1);
        }

        String partTwo() {
            Map<Integer, String> map = new HashMap<>();
            for (int i = 1; i <= 300; i++) {
                int[] ints = bestForSize(i);
                map.put(ints[2], (ints[0] + 1) + "," + (ints[1] + 1) + "," + i);
            }

            int bestVal = 0;

            for (Integer val : map.keySet()) {
                if (val > bestVal) {
                    bestVal = val;
                }
            }
            return map.get(bestVal);
        }

        int[] bestForSize(int size) {
            int bestX = -1;
            int bestY = -1;
            int bestSum = Integer.MIN_VALUE;
            for (int x = 0; x < 300 - size + 1; x++) {
                for (int y = 0; y < 300 - size + 1; y++) {
                    int sum = 0;
                    for (int dx = 0; dx < size; dx++) {
                        for (int dy = 0; dy < size; dy++) {
                            sum += grid[x + dx][y + dy];
                        }
                    }
                    if (sum > bestSum) {
                        bestX = x;
                        bestY = y;
                        bestSum = sum;
                    }
                }
            }
            return new int[]{bestX, bestY, bestSum};
        }

        int powerLevel(int x, int y) {
            int rackId = x + 10;
            int powerLevel = rackId * y;
            powerLevel += serial;
            powerLevel *= rackId;
            powerLevel /= 100;
            powerLevel %= 10;
            return powerLevel - 5;
        }
    }
}