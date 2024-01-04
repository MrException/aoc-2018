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

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day12::toNum)
        .collect(Collectors.toList());
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
    assert one == 0;

    var two = run(input);
    log("Part Two Test: " + two);
    assert two == 0;

    two = run(readFile());
    log("Part Two Real: " + two);
    assert two == 0;
  }

  private static long run(String[] input) {
    long result = 0;

    for (String line : input) {
      int possibilities = 0;

      String pattern = line.split(" ")[0];
      String broken = line.split(" ")[1];

      String[] permutations = permute(pattern);

      for (String perm : permutations) {
        if (accept(perm, broken)) {
          possibilities++;
        }
      }

      log(String.format("%s - %s arrangements", line, possibilities));

      result += possibilities;
    }

    return result;
  }

  private static String[] permute(String pattern) {
    return new String[] { pattern };
  }

  private static boolean accept(String perm, String broken) {
    return true;
  }
}
