package com.mrexception.aoc2018;

import com.mrexception.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day14 {
    @Test
    public void testPartOne() {
        buildScoreboard();
        assertThat(partOne(9)).isEqualTo("5158916779");
        assertThat(partOne(5)).isEqualTo("0124515891");
        assertThat(partOne(18)).isEqualTo("9251071085");
        assertThat(partOne(2018)).isEqualTo("5941429882");

        assertThat(partOne(846021)).isEqualTo("5482326119");
    }

    @Test
    public void testPartTwo() {
        buildScoreboard();
        assertThat(partTwo("51589")).isEqualTo(9);
        assertThat(partTwo("01245")).isEqualTo(5);
        assertThat(partTwo("92510")).isEqualTo(18);
        assertThat(partTwo("59414")).isEqualTo(2018);

        assertThat(partTwo("846021")).isEqualTo(20368140);
    }

    private int[] scoreboard = null;
    private int elfOne;
    private int elfTwo;
    private int end;

    private void buildScoreboard() {
        if (scoreboard != null) {
            return;
        }
        int scoreboardSize = 21000000;
        scoreboard = new int[scoreboardSize];
        for (int i = 0; i < scoreboard.length; i++) {
            scoreboard[i] = -1;
        }
        scoreboard[0] = 3;
        scoreboard[1] = 7;
        end = 1;
        elfOne = 0;
        elfTwo = 1;
        while (end < scoreboardSize - 2) {
            newRecipe();
            move();
        }
    }

    String partOne(int target) {
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(scoreboard[target + i]);
        }
        return sb.toString();
    }

    int partTwo(String input) {
        int[] search = Utils.toInts(input.toCharArray());
        int recipeCount = 0;
        for (int i = 0; i < scoreboard.length; i++) {
            if (scoreboard[i] == search[0]) {
                boolean match = true;
                for (int j = 1; j < search.length; j++) {
                    if (scoreboard[i + j] != search[j]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    recipeCount = i;
                    break;
                }
            }
        }
        return recipeCount;
    }

    private void newRecipe() {
        int sum = scoreboard[elfOne] + scoreboard[elfTwo];
        if (sum > 9) {
            scoreboard[end + 1] = sum / 10;
            scoreboard[end + 2] = sum % 10;
            end += 2;
        } else {
            scoreboard[end + 1] = sum;
            end++;
        }
    }

    private void move() {
        int elfOneMove = 1 + scoreboard[elfOne];
        int elfTwoMove = 1 + scoreboard[elfTwo];

        for (int i = 0; i < elfOneMove; i++) {
            elfOne++;
            if (scoreboard[elfOne] == -1) {
                elfOne = 0;
            }
        }

        for (int i = 0; i < elfTwoMove; i++) {
            elfTwo++;
            if (scoreboard[elfTwo] == -1) {
                elfTwo = 0;
            }
        }
    }
}