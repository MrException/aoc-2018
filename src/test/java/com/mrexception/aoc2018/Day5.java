package com.mrexception.aoc2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.mrexception.Utils.*;
import static java.lang.Character.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day5 {
    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private String inputFile = "com/mrexception/aoc2018/day5.txt";


    private String testData = "dabAcCaCBAcCcaDA";

    @Test
    public void testData() throws Exception {
        processFile(inputFile, lines -> {
            String data = lines.collect(Collectors.joining("\n"));
            assertThat(data.length()).isGreaterThan(0);
        });
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic(testData).partOne()).isEqualTo(10);

        assertThat(new Logic(processFile(inputFile)[0]).partOne()).isEqualTo(9808);
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(new Logic(testData).partTwo()).isEqualTo(4);

        assertThat(new Logic(processFile(inputFile)[0]).partTwo()).isEqualTo(6484);
    }

    class Logic {
        private final char[] orig;

        Logic(String str) {
            this.orig = str.toCharArray();
        }

        int partOne() {
            return partOne(orig);
        }

        int partOne(char[] data) {
            while (true) {
                boolean changed = false;
                for (int i = 0; i < data.length - 1; i++) {
                    char cur = data[i];
                    if (cur == 0) {
                        continue;
                    }
                    int j = i + 1;
                    char next = data[j];
                    while (next == 0) {
                        j += 1;
                        if (j >= data.length) {
                            break;
                        }
                        next = data[j];
                    }
                    if (next == 0) {
                        break;
                    }
                    if (Math.abs(cur - next) == 32) {
                        data[i] = 0;
                        data[j] = 0;
                        changed = true;
                    }
                }
                if (!changed) {
                    int i = 0;
                    for (char cur : data) {
                        if (cur != 0) {
                            i++;
                        }
                    }

                    return i;
                }
            }
        }

        int partTwo() {
            int best = Integer.MAX_VALUE;
            char[] data;
            for (char cur = 'a'; cur <= 'z'; cur++) {
                data = Arrays.copyOf(orig, orig.length);
                for (int i = 0; i < data.length; i++) {
                    if (cur == toLowerCase(data[i])) {
                        data[i] = 0;
                    }
                }
                int result = partOne(data);
                if (result < best) {
                    best = result;
                }
            }
            return best;
        }

    }
}