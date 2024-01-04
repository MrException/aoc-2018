package com.mrexception.aoc2023;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day11 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day11::toNum)
        .collect(Collectors.toList());
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
  }

  private static String[] input = new String[] {
      "...#......",
      ".......#..",
      "#.........",
      "..........",
      "......#...",
      ".#........",
      ".........#",
      "..........",
      ".......#..",
      "#...#.....",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day11.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    var one = run(input, 1);
    log("Part One Test: " + one);
    assert one == 374;

    one = run(readFile(), 1);
    log("Part One Real: " + one);
    assert one == 9522407;

    var two = run(input, 10);
    log("Part Two Test A: " + two);
    assert two == 1030;

    two = run(input, 100);
    log("Part Two Test B: " + two);
    assert two == 8470;

    two = run(readFile(), 1000000);
    log("Part Two Real: " + two);
    assert two == 0;
  }

  private static class Galaxy {
    public long startx;
    public long starty;
    public long x;
    public long y;

    public Galaxy(long x, long y) {
      this.startx = x;
      this.starty = y;
      this.x = x;
      this.y = y;
    }
  }

  private static long run(String[] input, long expandSize) {
    int[] rows = new int[input.length];
    int[] cols = new int[input[0].length()];

    List<Galaxy> galaxies = new ArrayList<>();

    // find all galaxies, and find all rows/cols that have galaxies
    for (int y = 0; y < input.length; y++) {
      String row = input[y];
      if (row.contains("#")) {
        rows[y] = 1;

        char[] chars = row.toCharArray();
        for (int x = 0; x < chars.length; x++) {
          if (chars[x] == '#') {
            cols[x] = 1;
            galaxies.add(new Galaxy(x, y));
          }
        }
      }
    }

    // "expand" the map by doubling the empty rows/cols
    for (int x = 0; x < cols.length; x++) {
      if (cols[x] == 0) {
        for (Galaxy g : galaxies) {
          if (g.startx > x) {
            g.x = g.x + expandSize;
          }
        }
      }
    }
    for (int y = 0; y < rows.length; y++) {
      if (rows[y] == 0) {
        for (Galaxy g : galaxies) {
          if (g.starty > y) {
            g.y = g.y + expandSize;
          }
        }
      }
    }

    long result = 0;
    for (int i = 0; i < galaxies.size() - 1; i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        long dist = distance(galaxies.get(i), galaxies.get(j));
        // log(String.format("Between galaxy %s and galaxy %s: %s", i + 1, j + 1,
        // dist));
        result += dist;
      }
    }

    return result;
  }

  private static long distance(Galaxy g1, Galaxy g2) {
    return Math.abs(g1.x - g2.x) + Math.abs(g1.y - g2.y);
  }
}
