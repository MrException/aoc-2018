package com.mrexception.aoc2018;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.mrexception.Utils.processFile;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day2 {
    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private String inputFile = "com/mrexception/aoc2018/day2.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        String[] testData = new String[]{
                "abcdef",
                "bababc",
                "abbcde",
                "abcccd",
                "aabcdd",
                "abcdee",
                "ababab"
        };
        assertThat(new Logic(testData).partOne()).isEqualTo(12);

        assertThat(new Logic(processFile(inputFile)).partOne()).isEqualTo(6888);
    }

    @Test
    public void testPartTwo() throws Exception {
        String[] testData = new String[]{
                "abcde",
                "fghij",
                "klmno",
                "pqrst",
                "fguij",
                "axcye",
                "wvxyz",
        };
        assertThat(new Logic(testData).partTwo()).isEqualTo("fgij");

        assertThat(new Logic(processFile(inputFile)).partTwo()).isEqualTo("icxjvbrobtunlelzpdmfkahgs");
    }

    class Logic {
        private final String[] data;

        Logic(String[] data) {
            this.data = data;
        }

        int partOne() {
            int[][] checksums = new int[data.length][2];
            for (int i = 0; i < data.length; i++) {
                checksums[i] = checksum(data[i]);
            }

            int twos = 0;
            int threes = 0;
            for (int[] checksum : checksums) {
                twos += checksum[0];
                threes += checksum[1];
            }
            return twos * threes;
        }

        int[] checksum(String str) {
            Map<Character, Integer> map = new HashMap<>();
            for (char c : str.toCharArray()) {
                Integer cur = map.getOrDefault(c, 0);
                map.put(c, cur + 1);
            }

            int found2 = 0;
            int found3 = 0;

            for (Integer i : map.values()) {
                if (i == 2) {
                    found2 = 1;
                }
                if (i == 3) {
                    found3 = 1;
                }
            }
            return new int[]{found2, found3};
        }

        String partTwo() {
            boolean found = false;
            String first = "";
            String second = "";
            LevenshteinDistance lev = new LevenshteinDistance(1);
            for (int i = 0; i < data.length; i++) {
                first = data[i];
                for (int j = i; j < data.length; j++) {
                    second = data[j];
                    if (lev.apply(first, second) == 1) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
//            log.info("first: {}, second: {}", first, second);
            String similar = "";
            char[] fChars = first.toCharArray();
            char[] sChars = second.toCharArray();
            for (int i = 0; i < fChars.length; i++) {
                if (fChars[i] != sChars[i]) {
                    similar = first.substring(0, i) + first.substring(i + 1);
                    break;
                }
            }
//            log.info("similar: {}", similar);
            return similar;
        }
    }
}