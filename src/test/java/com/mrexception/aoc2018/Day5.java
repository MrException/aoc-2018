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
        private final Character[] orig;

        Logic(String str) {
            this.orig = new Character[str.length()];
            char[] chars = str.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                orig[i] = chars[i];
            }
        }

        int partOne() {
            return partOne(orig);
        }

        int partOne(Character[] data) {
            while (true) {
                boolean changed = false;
                for (int i = 0; i < data.length - 1; i++) {
                    Character cur = data[i];
                    if (cur == null) {
                        continue;
                    }
                    int j = i + 1;
                    Character next = data[j];
                    while (next == null) {
                        j += 1;
                        if (j >= data.length) {
                            break;
                        }
                        next = data[j];
                    }
                    if(next == null) {
                        break;
                    }
                    if (isLowerCase(cur) && isUpperCase(next)) {
                        if (cur == toLowerCase(next)) {
                            data[i] = null;
                            data[j] = null;
                            changed = true;
                        }
                    }
                    if (isUpperCase(cur) && isLowerCase(next)) {
                        if (cur == toUpperCase(next)) {
                            data[i] = null;
                            data[j] = null;
                            changed = true;
                        }
                    }
                }
                if (!changed) {
                    int i = 0;
                    for (Character cur : data) {
                        if (cur != null) {
                            i++;
                        }
                    }

                    return i;
                }
            }
        }

        int partTwo() {
            int best = Integer.MAX_VALUE;
            Character[] data;
            for (char cur = 'a'; cur <= 'z'; cur++) {
                data = Arrays.copyOf(orig, orig.length);
                for (int i = 0; i < data.length; i++) {
                    if (cur == toLowerCase(data[i])) {
                        data[i] = null;
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