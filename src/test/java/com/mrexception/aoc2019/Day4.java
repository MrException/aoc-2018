package com.mrexception.aoc2019;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.mrexception.Utils.toInts;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day4 {
    // puzzle input
    int start = 172930;
    int end = 683082;

    @Test
    public void testPartOne() {
        assertThat(passesP1(1)).isFalse();
        assertThat(passesP1(11)).isTrue();
        assertThat(passesP1(10)).isFalse();
        assertThat(passesP1(111111)).isTrue();
        assertThat(passesP1(223450)).isFalse();
        assertThat(passesP1(123789)).isFalse();
        assertThat(passesP1(111123)).isTrue();
        assertThat(passesP1(123456789)).isFalse();
        assertThat(passesP1(11223344)).isTrue();

        assertThat(partOne(start, end)).isEqualTo(1675);
    }

    @Test
    public void testPartTwo() {
        assertThat(passesP2(1)).isFalse();
        assertThat(passesP2(11)).isTrue();
        assertThat(passesP2(10)).isFalse();
        assertThat(passesP2(111111)).isFalse();
        assertThat(passesP2(223450)).isFalse();
        assertThat(passesP2(123789)).isFalse();
        assertThat(passesP2(111123)).isFalse();
        assertThat(passesP2(112233)).isTrue();
        assertThat(passesP2(123444)).isFalse();
        assertThat(passesP2(111122)).isTrue();
        assertThat(passesP2(123456789)).isFalse();
        assertThat(passesP2(11223344)).isTrue();

        assertThat(partTwo(start, end)).isEqualTo(1675);
    }

    static boolean passesP1(int in) {
        int lastDigit = in % 10;
        int nextDigit;
        boolean pair = false;
        boolean increasing = true;
        while (in >= 10) {
            in = in / 10;
            nextDigit = in % 10;
            if (lastDigit == nextDigit) {
                pair = true;
            }
            if (lastDigit < nextDigit) {
                increasing = false;
                break;
            }
            lastDigit = nextDigit;
        }
        return pair && increasing;
    }

    static int partOne(int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (passesP1(i)) {
                count++;
            }
        }
        return count;
    }

    static boolean passesP2(int in) {
        int[] arr = toInts(Integer.toString(in).toCharArray());

        int l = arr.length;
        boolean pair = false;
        for (int i = 0; i < l; i++) {
            if (i + 1 < l && arr[i] > arr[i + 1]) {
                // not incrementing or equal
                return false;
            }

            boolean curPair = false;
            if (i + 1 < l && arr[i] == arr[i + 1]) {
                // found a pair, but is it a triplet or more?
                if (i > 0 && arr[i] == arr[i - 1]) {
                    // a triplet with the left, not a pair
                    curPair = false;
                } else if (i + 2 < l && arr[i] == arr[i + 2]) {
                    // a triplet with the right, not a pair
                    curPair = false;
                } else {
                    // this is a real pair!
                    curPair = true;
                }
            }
            if (curPair) {
                pair = true;
            }
        }
        return pair;
    }

    private int partTwo(int start, int end) {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (passesP2(i)) {
                count++;
            }
        }
        return count;
    }

}
