package com.mrexception.aoc2023;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day3b {

  private String[] input = new String[] {
    "467..114..",
    "...*......",
    "..35..633.",
    "......#...",
    "617*......",
    ".....+.58.",
    "..592.....",
    "......755.",
    "...$.*....",
    ".664.598.."
  };

  private String[] readFile() throws Exception {
    String filePath = "day3.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  void main() throws Exception {
    var one = partOne(input);
    log("Par One Test: " + one);
    assert one == 4361;

    one = partOne(readFile());
    log("Par One Real: " + one);
    assert one == 527369;

    var two = partTwo(input);
    log("Part Two Test: " + two);
    assert two == 467835;

    two = partTwo(readFile());
    log("Part Two Real: " + two);
    assert two == 73074886;
  }

  private static int partOne(String[] input) {
    int result = 0;
    for(int i = 0; i < input.length; i++) {
      String line = input[i];
      List<Num> nums = findNums(i, line);
      for(Num n : nums) {
        if(bySymbol(n, input)) {
          result += n.val;
        }
      }
    }
    return result;
  }

  private static boolean bySymbol(Num n, String[] input) {
    int xstart = n.start == 0 ? 0 : n.start - 1;
    int xend = n.end == input[0].length() ? n.end : n.end + 1;
    int ystart = n.lineNum == 0 ? 0 : n.lineNum - 1;
    int yend = n.lineNum == input.length-1 ? n.lineNum : n.lineNum + 1;

    return (
      containsSymbol(input[ystart], xstart, xend)
      || containsSymbol(input[yend], xstart, xend)
      || containsSymbol(input[n.lineNum], xstart, xend)
    );
  }

  private static boolean containsSymbol(String line, int start, int end) {
    String sub = line.substring(start, end);
    return sub.matches(".*[^.0-9].*");
  }

  private record Num(int lineNum, int start, int end, int val){}

  private static List<Num> findNums(int lineNum, String line) {
    List<Num> nums = new ArrayList<>();
    int start = -1;
    for( int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if(start == -1 && isNum(c)) {
        // the start of a number
        start = i;
      }
      if(start >= 0 && !isNum(c) ) {
        // the end of a number
        nums.add(new Num(lineNum, start, i, Integer.parseInt(line.substring(start, i))));
        start = -1;
      }
    }
    if(start >= 0) {
      // special case of a number being at the end of a line
      nums.add(new Num(lineNum, start, line.length(), Integer.parseInt(line.substring(start))));
    }
    return nums;
  }

  private static boolean isNum(char c) {
    return c >= '0' && c <= '9';
  }

  private static int partTwo(String[] input) {
    List<Num> nums = new ArrayList<>();
    List<Gear> gears = new ArrayList<>();
    for(int i = 0; i < input.length; i++) {
      String line = input[i];
      nums.addAll(findNums(i, line));
      gears.addAll(findGears(i, line));
    }

    log(String.format("Gears: %s Parts: %s", gears.size(), nums.size()));
    int result = 0;
    for(Gear g : gears) {
      int firstPart = -1;
      for(Num n : nums) {
        if(n.lineNum >= g.lineNum - 1 && n.lineNum <= g.lineNum + 1) {
          if(g.pos >= n.start - 1 && g.pos <= n.end) {
            if(firstPart > -1) {
              int val = firstPart * n.val;
              //log(String.format("Gear: %s, l: %s, r: %s, v: %s", g, firstPart, n.val, val));
              result += val;
              break;
            } else {
              firstPart = n.val;
            }
          }
        }
      }
    }
    return result;
  }

  private record Gear(int lineNum, int pos) {}

  private static List<Gear> findGears(int lineNum, String line) {
    List<Gear> gears = new ArrayList<>();

    for(int i = 0; i < line.length(); i++) {
      if(line.charAt(i) == '*') {
        gears.add(new Gear(lineNum, i));
      }
    }

    return gears;
  }

  private static void log(Object o) {
    System.out.println(o);
  }
}
