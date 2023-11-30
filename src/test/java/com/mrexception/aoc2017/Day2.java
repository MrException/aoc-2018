package com.mrexception.aoc2017;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Day2 {
    private Logger log = LoggerFactory.getLogger(Day2.class.getName());
    private String inputFile = "com/mrexception/aoc2017/day2.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        String[] testData = new String[]{
                "5 1 9 5",
                "7 5 3",
                "2 4 6 8"
        };
        assertThat(new Logic(testData).partOne()).isEqualTo(18);

        assertThat(new Logic(processFile(inputFile)).partOne()).isEqualTo(58975);
    }

    @Test
    public void testPartTwo() throws Exception {
        String[] testData = new String[]{
                "5 9 2 8",
                "9 4 7 3",
                "3 8 6 5"
        };
        assertThat(new Logic(testData).partTwo()).isEqualTo(9);

        assertThat(new Logic(processFile(inputFile)).partTwo()).isEqualTo(308);
    }

    class Logic {
        private final int[][] data;

        Logic(String[] strs) {
            data = new int[strs.length][];
            for (int i = 0; i < strs.length; i++) {
                String[] strings = splitLine(strs[i]);
                data[i] = toInts(strings);
            }
        }

        int partOne() {
            int checksum = 0;
            for (int[] line : data) {
                int smallest = Integer.MAX_VALUE;
                int largest = Integer.MIN_VALUE;
                for (int i : line) {
                    if (i < smallest) {
                        smallest = i;
                    }
                    if (i > largest) {
                        largest = i;
                    }
                }
                checksum += largest - smallest;
            }
            return checksum;
        }

        int partTwo() {
            int checksum = 0;
            for (int[] line : data) {
                for (int i = 0; i < line.length - 1; i++) {
                    int a = line[i];
                    boolean found = false;
                    for (int j = i + 1; j < line.length; j++) {
                        int b = line[j];
                        if (a % b == 0) {
                            checksum += a / b;
                            found = true;
                            break;
                        }
                        if (b % a == 0) {
                            checksum += b / a;
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
            }
            return checksum;
        }
    }
}
