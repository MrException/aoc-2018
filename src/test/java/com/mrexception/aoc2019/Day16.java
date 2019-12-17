package com.mrexception.aoc2019;

import org.junit.Test;

import static com.mrexception.Utils.processFile;
import static com.mrexception.Utils.toInts;
import static org.assertj.core.api.Assertions.assertThat;

public class Day16 {
    private String inputFile = "com/mrexception/aoc2019/day16.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic("12345678").partOne(4)).isEqualTo("01029498");
        assertThat(new Logic("80871224585914546619083218645595").partOne(100)).isEqualTo("24176176");
        assertThat(new Logic("19617804207202209144916044189917").partOne(100)).isEqualTo("73745418");
        assertThat(new Logic("69317163492948606335995924319873").partOne(100)).isEqualTo("52432133");

        assertThat(new Logic(processFile(inputFile)[0]).partOne(100)).isEqualTo("22122816");
    }

    private class Logic {
        int[] data;
        int len;
        private int[][] mask;

        public Logic(String in) {
            this.data = toInts(in.toCharArray());
            this.len = data.length;
        }

        private int doMask(int[] mask, int[] line) {
            int out = 0;
            for (int i = 0; i < len; i++) {
                out += mask[i] * line[i];
            }
            return Math.abs(out % 10);
        }

        private void phase() {
            int[] next = new int[len];
            for (int i = 0; i < len; i++) {
                next[i] = doMask(mask[i], data);
            }

            this.data = next;
        }

        private void createMask() {
            int[] pattern = new int[]{0, 1, 0, -1};

            int[][] mask = new int[len][len];
            int x, y;

            for (int i = 1; i <= len; i++) {
                x = 0;
                y = 0;
                for (int j = 1; j <= len; j++) {
                    y++;
                    if (y >= i) {
                        x++;
                        y = 0;
                    }
                    if (x == 4) {
                        x = 0;
                    }

                    mask[i - 1][j - 1] = pattern[x];

                }
            }
            this.mask = mask;
        }

        public String partOne(int iterations) {
            createMask();
            for (int j = 0; j < iterations; j++) {
                phase();
            }

            return "" + data[0] + data[1] + data[2] + data[3] + data[4] + data[5] + data[6] + data[7];
        }
    }
}
