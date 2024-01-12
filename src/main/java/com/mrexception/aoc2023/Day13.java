package com.mrexception.aoc2023;

import java.util.*;
import java.util.stream.IntStream;
import java.nio.file.*;

public class Day13 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static Integer[] toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day13::toNum)
        .toArray(Integer[]::new);
  }

  public static int toNum(String str) {
    return Integer.parseInt(str.trim());
  }

  private static String[] input = new String[] {
      "#.##..##.",
      "..#.##.#.",
      "##......#",
      "##......#",
      "..#.##.#.",
      "..##..##.",
      "#.#.##.#.",
      "",
      "#...##..#",
      "#....#..#",
      "..##..###",
      "#####.##.",
      "#####.##.",
      "..##..###",
      "#....#..#",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day13.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    var one = run(input);
    log("Part One Test: " + one);
    assert one == 405;

    one = run(readFile());
    log("Part One Real: " + one);
    assert one == 0;

    // var two = run(expand(input));
    // log("Part Two Test: " + two);
    // assert two == 525152;
    //
    // two = run(expand(readFile()));
    // log("Part Two Real: " + two);
    // assert two == 204640299929836L;
  }

  private static long run(String[] input) {
    // loop until an empty line is found
    String[] mirrors;
    int start = 0;
    int end;
    for (int i = 0; i < input.length; i++) {
      if (input[i].isBlank()) {
        // found the last line
        end = i - 1;

        mirrors = Arrays.copyOfRange(input, start, end + 1);
        log(String.join("\n", mirrors));
        log("-----");
        log(String.join("\n", rotate(mirrors)));

        // do stuff

        // reset
        start = i + 1;
        end = 0;
        mirrors = null;
        return 0;
      }
    }

    return 0;
  }

  private static String[] rotate(String[] in) {
    int m = in.length;
    int n = in[0].length();

    String[] out = new String[n];

    int i = 0;
    for (int x = n - 1; x >= 0; x--) {
      StringBuilder sb = new StringBuilder();
      for (int y = 0; y < m; y++) {
        sb.append(in[y].charAt(x));
      }
      out[i] = sb.toString();
      i++;
    }

    return out;
  }
}
