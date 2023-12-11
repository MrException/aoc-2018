package com.mrexception.aoc2023;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day8 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day8::toNum)
        .collect(Collectors.toList());
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
  }

  private static String[] input1 = new String[] {
      "RL",
      "",
      "AAA = (BBB, CCC)",
      "BBB = (DDD, EEE)",
      "CCC = (ZZZ, GGG)",
      "DDD = (DDD, DDD)",
      "EEE = (EEE, EEE)",
      "GGG = (GGG, GGG)",
      "ZZZ = (ZZZ, ZZZ)",
  };

  private static String[] input2 = new String[] {
      "LLR",
      "",
      "AAA = (BBB, BBB)",
      "BBB = (AAA, ZZZ)",
      "ZZZ = (ZZZ, ZZZ)",
  };

  private static String[] input3 = new String[] {
      "LR",
      "",
      "11A = (11B, XXX)",
      "11B = (XXX, 11Z)",
      "11Z = (11B, XXX)",
      "22A = (22B, XXX)",
      "22B = (22C, 22C)",
      "22C = (22Z, 22Z)",
      "22Z = (22B, 22B)",
      "XXX = (XXX, XXX)",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day8.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    partTwo = false;
    var one = run(input1);
    log("Part One Test 1: " + one);
    assert one == 2;

    one = run(input2);
    log("Part One Test 2: " + one);
    assert one == 6;

    one = run(readFile());
    log("Part One Real: " + one);
    assert one == 19199;

    partTwo = true;
    var two = run(input3);
    log("Part Two Test: " + two);
    assert two == 6;

    two = run(readFile());
    log("Part Two Real: " + two);
    assert two == 13663968099527L;
  }

  private static boolean partTwo = false;
  private static Map<String, Network> map;
  private static List<String> pos;
  private static char[] dirs;

  private record Network(String left, String right) {
  }

  private static long run(String[] input) {
    pos = new ArrayList<>();
    map = new HashMap<>();
    parseMap(input);

    long allSteps = 0;
    if (partTwo) {
      long[] steps = new long[pos.size()];
      for (int i = 0; i < pos.size(); i++) {
        String curPos = pos.get(i);
        steps[i] = findPath(curPos);
      }
      allSteps = steps[0];
      for (int i = 1; i < steps.length; i++) {
        allSteps = lcm(allSteps, steps[i]);
      }
    } else {
      allSteps = findPath(pos.get(0));
    }
    return allSteps;
  }

  private static long findPath(String curPos) {
    long steps = 0;

    boolean end = false;
    while (!end) {
      for (int i = 0; i < dirs.length; i++) {
        Network net = map.get(curPos);
        curPos = (dirs[i] == 'L' ? net.left : net.right);

        steps++;
        if (!partTwo && curPos.equals("ZZZ")) {
          end = true;
          break;
        } else if (partTwo && curPos.endsWith("Z")) {
          end = true;
          break;
        }
      }
    }

    return steps;
  }

  private static void parseMap(String[] input) {
    dirs = input[0].toCharArray();

    for (int i = 2; i < input.length; i++) {
      String[] parts = input[i].split("=");
      String key = parts[0].trim();
      String[] nets = parts[1].trim().split(",");
      String l = nets[0].trim().replace("(", "");
      String r = nets[1].trim().replace(")", "");
      map.put(key, new Network(l, r));

      if (key.endsWith("A")) {
        pos.add(key);
      }
    }

    if (!partTwo) {
      pos.clear();
      pos.add("AAA");
    }
  }

  private static long gcd(long a, long b) {
    if (b == 0) {
      return a;
    }
    return gcd(b, a % b);
  }

  private static long lcm(long a, long b) {
    return (a * b) / gcd(a, b);
  }
}
