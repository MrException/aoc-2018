package com.mrexception.aoc2023;

import java.util.*;
import java.nio.file.*;

public class Day12 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static Integer[] toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day12::toNum)
        .toArray(Integer[]::new);
  }

  public static int toNum(String str) {
    return Integer.parseInt(str.trim());
  }

  private static String[] input = new String[] {
      "???.### 1,1,3",
      ".??..??...?##. 1,1,3",
      "?#?#?#?#?#?#?#? 1,3,1,6",
      "????.#...#... 4,1,1",
      "????.######..#####. 1,6,5",
      "?###???????? 3,2,1",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day12.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    var one = run(input);
    log("Part One Test: " + one);
    assert one == 21;

    one = run(readFile());
    log("Part One Real: " + one);
    assert one == 8270;

    var two = run(expand(input));
    log("Part Two Test: " + two);
    assert two == 525152;

    two = run(expand(readFile()));
    log("Part Two Real: " + two);
    assert two == 204640299929836L;
  }

  private static long run(String[] input) {
    long result = 0;

    Map<String, Long> cache = new HashMap<>();
    for (int i = 0; i < input.length; i++) {
      String line = input[i];

      String pattern = line.split(" ")[0];
      Integer[] broken = toNums(line.split(" ")[1].split(","));

      // if (i % 100 == 0) {
      // log("Completed " + i);
      // }

      // log(String.format("-------\nStarting: %s - %s", pattern,
      // Arrays.toString(broken)));
      result += recurse(pattern, broken, cache);
    }

    return result;
  }

  private static String[] expand(String[] input) {
    String[] result = new String[input.length];

    for (int i = 0; i < input.length; i++) {
      String line = input[i];
      String pattern = line.split(" ")[0];
      String broken = line.split(" ")[1];
      String expandedPattern = String.format("%s?%s?%s?%s?%s", pattern, pattern, pattern, pattern, pattern);
      String expandedBroken = String.format("%s,%s,%s,%s,%s", broken, broken, broken, broken, broken);
      result[i] = String.format("%s %s", expandedPattern, expandedBroken);
    }

    return result;
  }

  private static long recurse(String pattern, Integer[] group, Map<String, Long> memo) {
    String key = String.format("%s-%s", Arrays.toString(group), pattern);
    if (memo.containsKey(key)) {
      long result = memo.get(key);
      // log(String.format("Return from cache - %s - %s - %s", pattern,
      // Arrays.toString(group), result));
      return result;
    }

    if (pattern.isEmpty()) {

      // if we hit the end of the string, and we haven't finished all groups
      if (group.length > 0) {
        // log(String.format("A - %s is invalid - %s", pattern,
        // Arrays.toString(group)));
        memo.put(key, 0L);
        return 0;
      }

      // // off the end of the pattern
      // log(String.format("A - %s is valid %s", pattern, Arrays.toString(group)));
      memo.put(key, 1L);
      return 1;
    }

    if (group.length == 0) {
      // all groups done - make sure there are no more '#'
      for (int i = 0; i < pattern.length(); i++) {
        if (pattern.charAt(i) == '#') {
          // log(String.format("B - %s is invalid - %s", pattern,
          // Arrays.toString(group)));
          memo.put(key, 0L);
          return 0;
        }
      }

      // log(String.format("B - %s is valid %s", pattern, Arrays.toString(group)));
      memo.put(key, 1L);
      return 1;
    }

    if (pattern.charAt(0) == '#') {
      var len = group[0];

      // there must be at least enough chars to fill the group
      if (len > pattern.length()) {
        // log(String.format("C - %s is invalid - %s", pattern,
        // Arrays.toString(group)));
        memo.put(key, 0L);
        return 0;
      }

      // the next group of chars must not contain a '.'
      // it can contain a '?' since we can substitute that for a '#'
      for (int i = 0; i < len; i++) {
        if (pattern.charAt(i) == '.') {
          // log(String.format("D - %s is invalid - %s", pattern,
          // Arrays.toString(group)));
          memo.put(key, 0L);
          return 0;
        }
      }

      // the immediate next char must not be a '#'
      if (len < pattern.length() && pattern.charAt(len) == '#') {
        // log(String.format("E - %s is invalid - %s", pattern,
        // Arrays.toString(group)));
        memo.put(key, 0L);
        return 0;
      }

      // special case, if the next char is a '?' it MUST be a '.'
      var nextGroup = Arrays.copyOfRange(group, 1, group.length);
      String nextPattern;
      long result = 0;
      if (len < pattern.length() && pattern.charAt(len) == '?') {
        nextPattern = '.' + pattern.substring(len + 1);
      } else {
        nextPattern = pattern.substring(len);
      }
      result = recurse(nextPattern, nextGroup, memo);

      memo.put(key, result);
      return result;
    }

    if (pattern.charAt(0) == '.') {
      long result = recurse(pattern.substring(1), group, memo);
      memo.put(key, result);
      return result;
    }

    long result = 0;
    if (pattern.charAt(0) == '?') {
      // recurse twice for two new patterns
      String nextPattern = '.' + pattern.substring(1);
      // log(pattern + " -> " + nextPattern);
      result += recurse(nextPattern, group, memo);
      nextPattern = '#' + pattern.substring(1);
      result += recurse(nextPattern, group, memo);

      memo.put(key, result);
      return result;
    }

    // should never get here
    log("HOW DID WE GET HERE?");
    return 0;
  }
}
