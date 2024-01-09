package com.mrexception.aoc2023;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day12 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static Long[] toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day12::toNum)
        .toArray(Long[]::new);
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
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

    // two = run(expand(readFile()));
    // log("Part Two Real: " + two);
    // assert two == 0;
  }

  private static long run(String[] input) {
    long result = 0;

    for (String line : input) {
      String pattern = line.split(" ")[0];
      Long[] broken = toNums(line.split(" ")[1].split(","));

      // result += permute(pattern, broken);
      result += recurse(0, broken, 0, 0, pattern);
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

  private static long permute(String original, Long[] broken) {
    long result = 0;
    Queue<String> q = new LinkedList<>();
    q.add(original);

    while (!q.isEmpty()) {
      var pattern = q.poll();
      var pos = pattern.indexOf('?');
      if (pos > -1) {
        String newPattern = pattern.substring(0, pos) + '.' + pattern.substring(pos + 1);
        if (accept(newPattern, broken)) {
          q.add(newPattern);
        }
        newPattern = pattern.substring(0, pos) + '#' + pattern.substring(pos + 1);
        if (accept(newPattern, broken)) {
          q.add(newPattern);
        }
      } else {
        result++;
      }
    }
    return result;

  }

  private static long recurse(int groupIdx, Long[] group, int pos, int curLen, String pattern) {
    if (pos >= pattern.length()) {
      // off the end of the pattern
      log(String.format("%s is valid %s", pattern, Arrays.toString(group)));
      return 1;
      // if (groupIdx == group.length - 1 && group[groupIdx] == curLen) {
      // // found a valid pattern
      // log(pattern + " is valid");
      // return 1;
      // } else {
      // log(pattern + " is invalid B");
      // return 0;
      // }
    }

    if (pattern.charAt(pos) == '#') {
      curLen++;

      if (groupIdx >= group.length || curLen > group[groupIdx]) {
        // found an invalid pattern
        log(pattern + " is invalid A " + Arrays.toString(group));
        return 0;
      }
    }

    // PROBLEM! - we aren't catching the error of if we hit a '.' and the previous
    // group was to short

    if (pattern.charAt(pos) == '.') {
      groupIdx++;
      curLen = 0;
    }

    if (pattern.charAt(pos) == '?') {
      // recurse twice for two new patterns
      long result = 0;
      String newPattern = pattern.substring(0, pos) + '.' + pattern.substring(pos + 1);
      result += recurse(groupIdx, group, pos + 1, curLen, newPattern);
      newPattern = pattern.substring(0, pos) + '#' + pattern.substring(pos + 1);
      result += recurse(groupIdx, group, pos + 1, curLen, newPattern);
      return result;
    } else {
      return recurse(groupIdx, group, pos + 1, curLen, pattern);
    }
  }

  private static boolean accept(String perm, Long[] broken) {
    char[] chars = perm.toCharArray();

    boolean complete = !perm.contains("?");

    int q = 0;
    int l = 0;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '?') {
        break;
      }

      if (chars[i] == '#') {
        l++;
      }

      if (chars[i] == '.' && l > 0) {
        if (q >= broken.length) {
          return false;
        }
        var n = broken[q++];
        if (n != l) {
          return false;
        }
        l = 0;
      }
    }

    if (l > 0) {
      if (q >= broken.length) {
        return false;
      }
      var n = broken[q++];
      if (!complete && n < l) {
        return false;
      }
      if (complete && n != l) {
        return false;
      }
    }

    if (complete) {
      return q >= broken.length;
    }

    return true;
  }
}
