package com.mrexception.aoc2017;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mrexception.Utils.processFile;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day3 {
    private Logger log = LoggerFactory.getLogger(Day3.class.getName());
    private String inputFile = "com/mrexception/aoc2017/day3.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() {
        assertThat(new Logic(1).partOne()).isEqualTo(0);
        assertThat(new Logic(12).partOne()).isEqualTo(3);
        assertThat(new Logic(23).partOne()).isEqualTo(2);
        assertThat(new Logic(1024).partOne()).isEqualTo(31);

        assertThat(new Logic(27768).partOne()).isEqualTo(58975);
    }

//    @Test
//    public void testPartTwo() throws Exception {
//        String[] testData = new String[]{
//                "5 9 2 8",
//                "9 4 7 3",
//                "3 8 6 5"
//        };
//        assertThat(new Logic(testData).partTwo()).isEqualTo(9);
//
//        assertThat(new Logic(processFile(inputFile)).partTwo()).isEqualTo(308);
//    }

    class Logic {
        private final int data;

        Logic(int data) {
            this.data = data;
        }

        private int[][] makeGrid() {
            int size = findSquare();
            if (size % 2 == 0) {
                size++;
            }
            var grid = new int[size][size];
            int x = (size / 2) + 1;
            int y = x;
            for (int i = 1; i <= data; i++) {
                grid[x][y] = 1;
            }
            return grid;
        }

        private int findSquare() {
            double x = Math.sqrt(data);
            if (x * x == data) {
                return data;
            }

            int i = data + 1;
            while (true) {
                x = Math.sqrt(i);
                if (x * x == i) {
                    return i;
                }
                i++;
            }
        }

        int partOne() {
            return 0;
        }

        int partTwo() {
            return 0;
        }
    }
}
