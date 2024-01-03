package com.mrexception.aoc2023;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day15 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static int toNum(String str) {
    return Integer.parseInt(str.trim());
  }

  private static String input = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7";

  private static String readFile() throws Exception {
    String filePath = "day15.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n")[0];
  }

  public static void main(String[] args) throws Exception {
    partTwo = false;
    var one = part1(input);
    log("Part One Test: " + one);
    assert one == 1320;

    one = part1(readFile());
    log("Part One Real: " + one);
    assert one == 515210;

    partTwo = true;
    var two = part2(input);
    log("Part Two Test: " + two);
    assert two == 145;

    two = part2(readFile());
    log("Part Two Real: " + two);
    assert two == 246762;
  }

  private static boolean partTwo = false;

  private static long part1(String input) {
    String[] parts = input.split(",");

    long sum = 0;

    for (String part : parts) {
      sum += hash(part);
    }

    return sum;
  }

  private static long part2(String input) {
    Map<Integer, List<Lens>> boxes = new HashMap<>();

    String[] parts = input.split(",");

    for (String part : parts) {

      if (part.contains("-")) {

        String label = part.split("-")[0];
        List<Lens> box = boxes.get(hash(label));

        if (box == null) {
          box = new ArrayList<>();
          boxes.put(hash(label), box);
        }

        for (int i = 0; i < box.size(); i++) {
          Lens lens = box.get(i);
          if (lens.label.equals(label)) {
            box.remove(i);
            break;
          }
        }

      } else {

        String label = part.split("=")[0];
        int focal = toNum(part.split("=")[1]);

        List<Lens> box = boxes.get(hash(label));
        if (box == null) {
          box = new ArrayList<>();
          boxes.put(hash(label), box);
        }

        boolean found = false;
        for (int i = 0; i < box.size(); i++) {
          Lens lens = box.get(i);
          if (lens.label.equals(label)) {
            box.set(i, new Lens(label, focal));
            found = true;
            break;
          }
        }

        if (!found) {
          box.add(new Lens(label, focal));
        }
      }
    }

    long sum = 0;
    for (var entry : boxes.entrySet()) {
      int boxNum = entry.getKey() + 1;
      List<Lens> lenses = entry.getValue();
      for (int i = 0; i < lenses.size(); i++) {
        Lens lens = lenses.get(i);
        sum += (boxNum * (i + 1) * lens.focal);
      }
    }
    return sum;
  }

  private static record Lens(String label, int focal) {
  }

  private static int hash(String in) {
    int sub = 0;

    for (char c : in.toCharArray()) {
      sub = sub + c;
      sub = sub * 17;
      sub = sub % 256;
      // log(String.format("%s: %d", c, sub));
    }

    return sub;
  }
}
