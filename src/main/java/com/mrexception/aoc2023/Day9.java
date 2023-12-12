package com.mrexception.aoc2023;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day9 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day9::toNum)
        .collect(Collectors.toList());
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
  }

  private static String[] input = new String[] {
      "0 3 6 9 12 15",
      "1 3 6 10 15 21",
      "10 13 16 21 30 45",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day9.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    partTwo = false;
    var one = run(input);
    log("Part One Test: " + one);
    assert one == 114;

    one = run(readFile());
    log("Part One Real: " + one);
    assert one == 1955513104;

    partTwo = true;
    var two = run(input);
    log("Part Two Test: " + two);
    assert two == 2;

    two = run(readFile());
    log("Part Two Real: " + two);
    assert two == 1131;
  }

  private static boolean partTwo = false;

  record Seq(boolean end, List<Long> seq) {
  }

  private static long run(String[] input) {
    long sum = 0;
    for (String line : input) {
      List<Long> nums = toNums(line.trim().split(" +"));
      Seq seq = new Seq(false, nums);
      List<Seq> history = history(seq);
      // log("-----");
      // log(seq);
      // log(history);

      if (!partTwo) {
        sum = sum + forwardHistory(seq, history);
      } else {
        sum = sum + backwardHistory(seq, history);
      }
    }
    return sum;
  }

  private static long backwardHistory(Seq seq, List<Seq> history) {
    Long x = 0L;
    Long n;
    for (int i = history.size() - 2; i >= 0; i--) {
      n = history.get(i).seq.getFirst();
      // log(String.format("%s - %s = %s", n, x, (n - x)));
      x = n - x;
    }

    n = seq.seq.getFirst();
    // log(String.format("%s - %s = %s", n, x, (n - x)));
    return n - x;
  }

  private static long forwardHistory(Seq seq, List<Seq> history) {
    Long x = 0L;
    Long n;
    for (int i = history.size() - 2; i >= 0; i--) {
      n = history.get(i).seq.getLast();
      x = x + n;
    }

    return seq.seq.getLast() + x;
  }

  private static List<Seq> history(Seq start) {
    List<Seq> history = new ArrayList<>();
    Seq nextSeq = nextSeq(start);
    history.add(nextSeq);
    while (!nextSeq.end) {
      nextSeq = nextSeq(nextSeq);
      history.add(nextSeq);
    }
    return history;
  }

  private static Seq nextSeq(Seq seq) {
    List<Long> nums = new ArrayList<>();
    long cur = seq.seq.get(0);
    long next;
    for (int i = 1; i < seq.seq.size(); i++) {
      next = seq.seq.get(i);
      nums.add(next - cur);
      cur = next;
    }
    return new Seq(nums.getLast() == 0, nums);
  }
}
