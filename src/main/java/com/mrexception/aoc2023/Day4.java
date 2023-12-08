package com.mrexception.aoc2023;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day4 {

  private static void log(Object o) {
    System.out.println(o);
  }

  private static String[] input = new String[] {
    "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
    "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
    "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
    "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
    "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
    "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day4.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    var one = partOne(input);
    log("Par One Test: " + one);
    assert one == 13;

    one = partOne(readFile());
    log("Par One Real: " + one);
    assert one == 24160;

    var two = partTwo(input);
    log("Part Two Test: " + two);
    assert two == 30;

    two = partTwo(readFile());
    log("Part Two Real: " + two);
    assert two == 5659035;
  }

  private static int partOne(String[] input) {
    int result = 0;
    for(int i = 0; i < input.length; i++) {
      String line = input[i];
      String[] sets = line.split(":")[1].trim().split("\\|");
      Set<Integer> left = new HashSet<>();
      Set<Integer> right = new HashSet<>();
      left.addAll(toInts(sets[0].trim().split(" ")));
      right.addAll(toInts(sets[1].trim().split(" ")));
      left.retainAll(right);
      var matches = left.size();
      if(matches > 0) {
        result += Math.pow(2, (matches - 1));
      }
    }
    return result;
  }

  private static int partTwo(String[] input) {
    Map<Integer, Integer> map = new HashMap<>();
    for(int i = 0; i < input.length; i++) {
      String line = input[i];
      int card = toInt(line.split(":")[0].trim().split(" +")[1].trim());
      int copies = map.getOrDefault(card, 0) + 1;
      map.put(card, copies);

      String[] sets = line.split(":")[1].trim().split("\\|");
      Set<Integer> left = new HashSet<>();
      Set<Integer> right = new HashSet<>();
      left.addAll(toInts(sets[0].trim().split(" ")));
      right.addAll(toInts(sets[1].trim().split(" ")));
      left.retainAll(right);
      var matches = left.size();

      for(int j = 1; j <= matches; j++) {
        int copy = card + j;
        map.put(copy, map.getOrDefault(copy, 0) + copies);
      }
    }
    return map.values().stream().reduce(Integer::sum).get();
  }

  public static List<Integer> toInts(String[] strs) {
    return Arrays.stream(strs)
    .filter(s -> !s.isBlank())
      .map(Day4::toInt)
      .collect(Collectors.toList());
  }

  public static int toInt(String str) {
    return Integer.parseInt(str.trim());
  }
}
