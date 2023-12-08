package com.mrexception.aoc2023;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day6 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
    .filter(s -> !s.isBlank())
      .map(Day6::toNum)
      .collect(Collectors.toList());
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
  }




  private static String[] input = new String[] {
    "Time:      7  15   30",
    "Distance:  9  40  200",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day6.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    var test = range(7, 9);
    log("Test z=7, d=9 : " + test);
    assert test == 4;

    var one = partOne(input);
    log("Par One Test: " + one);
    assert one == 288;

    one = partOne(readFile());
    log("Par One Real: " + one);
    assert one == 771628;

    var two = partTwo(input);
    log("Part Two Test: " + two);
    assert two == 71503;

    two = partTwo(readFile());
    log("Part Two Real: " + two);
    assert two == 27363861;
  }

  private static long partOne(String[] input) {
    List<Long> times = toNums(input[0].split(":")[1].trim().split(" +"));
    List<Long> distances = toNums(input[1].split(":")[1].trim().split(" +"));

    return IntStream.range(0, times.size())
      .mapToLong(i -> range(times.get(i), distances.get(i)))
        .reduce(1, (a, b) -> a * b);
  }

  private static long partTwo(String[] input) {
    Long time = toNum(input[0].split(":")[1].trim().replaceAll(" +", ""));
    Long distance = toNum(input[1].split(":")[1].trim().replaceAll(" +", ""));

    //log(String.format("time: %s distance: %s", time, distance));

    return range(time, distance);
  }

  private static long range(long z, long d) {
    double t = Math.sqrt(((long)z*z) - (4*d));

    long left = (z - (long)t) / 2;
    long right = (z + (long)t) / 2;

    if(left * (z - left) <= d) {
      left++;
    }

    if(right * (z - right) <= d) {
      right--;
    }

    long result = right - left + 1;
    //log(left + " - " + right + " = " + result);
    return result;
  }
}
