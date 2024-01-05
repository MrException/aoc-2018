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

  public static Queue<Long> toNums(String[] strs) {
    Queue<Long> q = new LinkedList<>();
    Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day12::toNum)
        .forEach(q::add);
    return q;
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

    var two = run(input);
    log("Part Two Test: " + two);
    assert two == 525152;

    two = run(readFile());
    log("Part Two Real: " + two);
    assert two == 0;
  }

  private static long run(String[] input) {
    long result = 0;

    for (String line : input) {
      // String line = input[0];
      int possibilities = 0;

      String pattern = line.split(" ")[0];
      String broken = line.split(" ")[1];

      Set<String> permutations = permute(pattern, broken);

      possibilities += permutations.size();

      // log(String.format("%s - %s arrangements", line, possibilities));

      result += possibilities;
    }

    return result;

  }

  private static Set<String> permute(String original, String broken) {
    Set<String> result = new HashSet<>();
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
        result.add(pattern);
      }
    }
    return result;

  }

  private static boolean accept(String perm, String broken) {
    char[] chars = perm.toCharArray();
    Queue<Long> q = toNums(broken.split(","));

    boolean complete = !perm.contains("?");

    int l = 0;
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '?') {
        break;
      }

      if (chars[i] == '#') {
        l++;
      }

      if (chars[i] == '.' && l > 0) {
        var n = q.poll();
        if (n == null || n != l) {
          return false;
        }
        l = 0;
      }
    }

    if (l > 0) {
      var n = q.poll();
      if (n == null) {
        return false;
      }
      if (!complete && n < l) {
        return false;
      }
      if (complete && n != l) {
        return false;
      }
    }

    if (complete) {
      return q.isEmpty();
    }

    return true;
  }
}
