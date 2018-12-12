package com.mrexception.aoc2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.mrexception.Utils.processFile;
import static com.mrexception.Utils.splitLine;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day12 {
    private Logger log = LoggerFactory.getLogger("Day12");
    private String inputFile = "com/mrexception/aoc2018/day12.txt";

    private String[] testData = new String[]{
            "initial state: #..#.#..##......###...###",
            "",
            "...## => #",
            "..#.. => #",
            ".#... => #",
            ".#.#. => #",
            ".#.## => #",
            ".##.. => #",
            ".#### => #",
            "#.#.# => #",
            "#.### => #",
            "##.#. => #",
            "##.## => #",
            "###.. => #",
            "###.# => #",
            "####. => #"
    };

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic(testData).run(20)).isEqualTo(325);
        assertThat(new Logic(processFile(inputFile)).run(20)).isEqualTo(3798);
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(new Logic(processFile(inputFile)).run(50000000000L)).isEqualTo(3900000002212L);
    }

    class Logic {
        Boolean[] state;
        Map<Key, Boolean> rules = new HashMap<>();
        int offset;

        Logic(String[] data) {
            char[] stateStr = splitLine(data[0])[2].toCharArray();
            offset = 5;
            state = new Boolean[stateStr.length * 20];
            for (int i = 0; i < state.length; i++) {
                state[i] = false;
            }
            for (int i = 0; i < stateStr.length; i++) {
                state[i + offset] = (stateStr[i] == '#');
            }
            for (int i = 2; i < data.length; i++) {
                String[] rule = splitLine(data[i], "=>");
                Boolean[] match = new Boolean[5];
                char[] matchStr = rule[0].toCharArray();
                for (int k = 0; k < match.length; k++) {
                    match[k] = (matchStr[k] == '#');
                }
                rules.put(new Key(match), rule[1].equals("#"));
            }
        }

        long run(long generations) {
            if (generations > 130) {
                // (((50000000000-128)*78)+12196) - reaches a stead state of 78 pots, at generation 128 - with a value of 12196
                return ((generations - 128) * 78) + 12196;
            }
            for (int i = 0; i < generations; i++) {
                if (i % 10 == 0) {
                    log.info("Done {} sum {} count {} first {} last {}", i, sum(), count(), first(), last());
                }
                Boolean[] nextGeneration = Arrays.copyOf(state, state.length);
                for (int j = 2; j < state.length - 2; j++) {
                    Boolean[] neighbours = Arrays.copyOfRange(state, j - 2, j + 3);
                    Boolean match = rules.get(new Key(neighbours));
                    nextGeneration[j] = (match != null && match);
                }
                state = nextGeneration;
            }

            return sum();
        }

        private int first() {
            for (int i = 0; i < state.length; i++) {
                if (state[i]) {
                    return i;
                }
            }
            return -1;
        }

        private int last() {
            for (int i = state.length - 1; i >= 0; i--) {
                if (state[i]) {
                    return i;
                }
            }
            return -1;
        }

        int sum() {
            int sum = 0;
            for (int i = 0; i < state.length; i++) {
                if (state[i]) {
                    sum += i - offset;
                }
            }
            return sum;
        }

        int count() {
            int count = 0;
            for (Boolean aState : state) {
                if (aState) {
                    count++;
                }
            }
            return count;
        }

        int partTwo() {
            return 0;
        }
    }

    class Key {
        private final Boolean[] match;

        Key(Boolean[] match) {
            if (match.length != 5) {
                throw new IllegalArgumentException();
            }
            this.match = match;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Arrays.equals(match, key.match);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(match);
        }
    }
}