package com.mrexception.aoc2023;

import static com.mrexception.Utils.processFile;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class Day1 {

  private final String inputFile = "com/mrexception/aoc2023/day1.txt";

  Map<String, Integer> map =
      Map.ofEntries(
          entry("zero", 0),
          entry("one", 1),
          entry("two", 2),
          entry("three", 3),
          entry("four", 4),
          entry("five", 5),
          entry("six", 6),
          entry("seven", 7),
          entry("eight", 8),
          entry("nine", 9),
          entry("0", 0),
          entry("1", 1),
          entry("2", 2),
          entry("3", 3),
          entry("4", 4),
          entry("5", 5),
          entry("6", 6),
          entry("7", 7),
          entry("8", 8),
          entry("9", 9));

  @Test
  public void testData() throws Exception {
    assertThat(processFile(inputFile).length).isGreaterThan(0);
  }

  @Test
  public void testPartOne() throws Exception {
    String[] testInput =
        new String[] {
          "1abc2", "pqr3stu8vwx",
          "a1b2c3d4e5f", "treb7uchet"
        };

    assertThat(partOne(testInput)).isEqualTo(142);
    assertThat(partOne(processFile(inputFile))).isEqualTo(55386);
  }

  @Test
  public void testPartTwo() throws Exception {
    String[] testInput =
        new String[] {
          "two1nine",
          "eightwothree",
          "abcone2threexyz",
          "xtwone3four",
          "4nineeightseven2",
          "zoneight234",
          "7pqrstsixteen"
        };

    assertThat(partTwo(testInput)).isEqualTo(281);
    assertThat(partTwo(processFile(inputFile))).isEqualTo(54824);
  }

  @Test
  public void testParseString() {
    assertThat(parseString("1abc2")).isEqualTo(12);
    assertThat(parseString("pqr3stu8vwx")).isEqualTo(38);
    assertThat(parseString("a1b2c3d4e5f")).isEqualTo(15);
    assertThat(parseString("treb7uchet")).isEqualTo(77);
  }

  @Test
  public void testParseString2() {
    assertThat(parseString2("two1nine")).isEqualTo(29);
    assertThat(parseString2("eightwothree")).isEqualTo(83);
    assertThat(parseString2("abcone2threexyz")).isEqualTo(13);
    assertThat(parseString2("xtwone3four")).isEqualTo(24);
    assertThat(parseString2("4nineeightseven2")).isEqualTo(42);
    assertThat(parseString2("zoneight234")).isEqualTo(14);
    assertThat(parseString2("7pqrstsixteen")).isEqualTo(76);
  }

  private int parseString(String s) {
    int[] ints = s.chars().filter(c -> isNum((char) c)).map(c -> c - '0').toArray();
    return (ints[0] * 10) + ints[ints.length - 1];
  }

  private boolean isNum(char c) {
    return c >= '0' && c <= '9';
  }

  private int partOne(String[] input) {
    return Arrays.stream(input).map(this::parseString).reduce(Integer::sum).orElse(0);
  }

  private int parseString2(String s) {
    return (findFirst(s) * 10) + findLast(s);
  }

  private int findFirst(String s) {
    return map.entrySet().stream()
        .map(entry -> new Pair(s.indexOf(entry.getKey()), entry.getValue()))
        .sorted()
        .filter(p -> p.pos > -1)
        .map(p -> p.val)
        .findFirst()
        .orElse(0);
  }

  private int findLast(String s) {
    Integer[] ints =
        map.entrySet().stream()
            .map(entry -> new Pair(s.lastIndexOf(entry.getKey()), entry.getValue()))
            .sorted()
            .filter(p -> p.pos > -1)
            .map(p -> p.val)
            .toArray(Integer[]::new);
    return ints[ints.length - 1];
  }

  private int partTwo(String[] input) {
    return Arrays.stream(input).map(this::parseString2).reduce(Integer::sum).orElse(0);
  }

  private record Pair(int pos, int val) implements Comparable<Pair> {
    @Override
    public int compareTo(Pair o) {
      return Integer.compare(pos, o.pos);
    }
  }
}
