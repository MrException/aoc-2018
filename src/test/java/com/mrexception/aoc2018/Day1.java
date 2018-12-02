package com.mrexception.aoc2018;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mrexception.Utils.processFile;
import static com.mrexception.Utils.toInts;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day1 {
    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private String inputFile = "com/mrexception/aoc2018/day1.txt";

    @Test
    public void testData() throws Exception {
        processFile(inputFile, lines -> {
            String data = lines.collect(Collectors.joining("\n"));
            assertThat(data.length()).isGreaterThan(0);
        });
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic(Stream.of("+1", "+1", "+1")).partOne()).isEqualTo(3);
        assertThat(new Logic(Stream.of("+1", "+1", "-2")).partOne()).isEqualTo(0);
        assertThat(new Logic(Stream.of("-1", "-2", "-3")).partOne()).isEqualTo(-6);

        processFile(inputFile, lines -> {
            int result = new Logic(lines).partOne();
            log.info("Day One - Part one: {}", result);
            assertThat(result).isEqualTo(569);
        });
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(new Logic(Stream.of("+1", "-1")).partTwo()).isEqualTo(0);
        assertThat(new Logic(Stream.of("+3", "+3", "+4", "-2", "-4")).partTwo()).isEqualTo(10);
        assertThat(new Logic(Stream.of("-6", "+3", "+8", "+5", "-6")).partTwo()).isEqualTo(5);
        assertThat(new Logic(Stream.of("+7", "+7", "-2", "-7", "-4")).partTwo()).isEqualTo(14);

        processFile(inputFile, lines -> {
            int result = new Logic(lines).partTwo();
            log.info("Day One - Part two: {}", result);
            assertThat(result).isEqualTo(77666);
        });
    }

    class Logic {
        private final List<Integer> data;

        Logic(Stream<String> data) {
            this.data = toInts(data).collect(Collectors.toList());
        }

        int partOne() {
            return data
                    .stream()
                    .reduce(Integer::sum)
                    .orElseThrow(IllegalArgumentException::new);
        }

        int partTwo() {
            ArrayList<Integer> found = new ArrayList<>();
            found.add(0);
            return partTwo(found);
        }

        int partTwo(final List<Integer> found) {
            return data
                    .stream()
                    .map(cur -> {
                        Integer result = found.get(found.size() - 1) + cur;
                        if (found.contains(result)) {
                            return result;
                        }
                        found.add(result);
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseGet(() -> partTwo(found));
        }
    }
}