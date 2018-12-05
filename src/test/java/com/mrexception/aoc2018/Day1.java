package com.mrexception.aoc2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

import static com.mrexception.Utils.processFile;
import static com.mrexception.Utils.toInts;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day1 {
    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private String inputFile = "com/mrexception/aoc2018/day1.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic(new String[]{"+1", "+1", "+1"}).partOne()).isEqualTo(3);
        assertThat(new Logic(new String[]{"+1", "+1", "-2"}).partOne()).isEqualTo(0);
        assertThat(new Logic(new String[]{"-1", "-2", "-3"}).partOne()).isEqualTo(-6);

        assertThat(new Logic(processFile(inputFile)).partOne()).isEqualTo(569);
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(new Logic(new String[]{"+1", "-1"}).partTwo()).isEqualTo(0);
        assertThat(new Logic(new String[]{"+3", "+3", "+4", "-2", "-4"}).partTwo()).isEqualTo(10);
        assertThat(new Logic(new String[]{"-6", "+3", "+8", "+5", "-6"}).partTwo()).isEqualTo(5);
        assertThat(new Logic(new String[]{"+7", "+7", "-2", "-7", "-4"}).partTwo()).isEqualTo(14);

        assertThat(new Logic(processFile(inputFile)).partTwo()).isEqualTo(77666);
    }

    class Logic {
        private final int[] data;

        Logic(String[] strs) {
            this.data = toInts(strs);
        }

        int partOne() {
            int res = 0;
            for (int cur : data) {
                res += cur;
            }
            return res;
        }

        int partTwo() {
            var found = new HashSet<Integer>();
            found.add(0);
            int last = 0;
            while (true) {
                for (int cur : data) {
                    int result = last + cur;
                    if (found.contains(result)) {
                        return result;
                    } else {
                        last = result;
                        found.add(result);
                    }
                }
            }
        }
    }
}