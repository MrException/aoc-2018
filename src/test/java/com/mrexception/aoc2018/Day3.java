package com.mrexception.aoc2018;

import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day3 {
    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private String inputFile = "com/mrexception/aoc2018/day3.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        String[] testData = new String[]{
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"
        };
        assertThat(new Logic(testData).partOne()).isEqualTo(4);

        assertThat(new Logic(processFile(inputFile)).partOne()).isEqualTo(113716);
    }

    @Test
    public void testPartTwo() throws Exception {
        String[] testData = new String[]{
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2"
        };
        assertThat(new Logic(testData).partTwo()).isEqualTo("#3");

        assertThat(new Logic(processFile(inputFile)).partTwo()).isEqualTo("#742");
    }

    class Logic {
        private final Claim[] data;

        Logic(String[] data) {
            this.data = new Claim[data.length];
            for (int i = 0; i < data.length; i++) {
                this.data[i] = new Claim(data[i]);
            }
        }

        int partOne() {
            int[][] matrix = toMatrix();
            int totalArea = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] > 1) {
                        totalArea++;
                    }
                }
            }
            return totalArea;
        }

        String partTwo() {
            for (int i = 0; i < data.length; i++) {
                Claim cur = data[i];
                boolean foundOverlap = false;
                for (int j = 0; j < data.length; j++) {
                    if (i == j) {
                        continue;
                    }
                    if(doesOverlap(cur, data[j]) || doesOverlap(data[j], cur)) {
                        foundOverlap = true;
                        break;
                    }
                }
                if(!foundOverlap) {
                    return cur.id;
                }
            }
            return null;
        }

        boolean doesOverlap(Claim a, Claim b) {
            return
                    a.tl.x < b.br.x &&
                            a.tl.y < b.br.y &&
                            a.br.x > b.tl.x &&
                            a.br.y > b.tl.y;
        }

        int[][] toMatrix() {
            int[][] matrix = new int[1000][1000];
            for (Claim cur : data) {
                Point tl = cur.tl;
                Point br = cur.br;
                for (int j = tl.x; j < br.x; j++) {
                    for (int k = tl.y; k < br.y; k++) {
                        matrix[j][k]++;
                    }
                }
            }
            return matrix;
        }
    }

    @Data
    class Claim {
        String id;
        Point tl;
        Point br;

        Claim(String str) {
            String[] split = splitLine(str, " ");
            id = split[0];
            tl = new Point(toInts(splitLine(split[2].replaceAll(":", ""))));
            int[] areaXY = toInts(splitLine(split[3], "x"));
            int width = areaXY[0];
            int height = areaXY[1];
            br = new Point(tl.x + width, tl.y + height);
        }
    }

    @Data
    class Point {
        int x;
        int y;

        Point(int[] xy) {
            x = xy[0];
            y = xy[1];
        }

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}