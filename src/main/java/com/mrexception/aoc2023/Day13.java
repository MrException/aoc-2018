package com.mrexception.aoc2023;

import java.util.*;
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
    int result = 0;

    // loop until an empty line is found
    int start = 0;
    int end;
    for (int i = 0; i < input.length; i++) {
      if (input[i].isBlank() || i == input.length - 1) {
        // found the last line
        end = i - 1;

        var mirrors = Arrays.copyOfRange(input, start, end + 1);
        // log(String.join("\n", mirrors));
        // log("-----");
        // log(String.join("\n", rotated));

        // do stuff
        var focal = findFocal(mirrors);
        if (focal == 0) {
          focal = findFocal(rotate(mirrors));
          result += focal;
        } else {
          result += 100 * focal;
        }

        // reset
        start = i + 1;
        end = 0;
      }
    }

    return result;
  }

  private static int findFocal(String[] mirrors) {
    int left = 0;
    int right = mirrors.length - 1;
    boolean found = false;
    while (!found && left < right) {
      if (!mirrors[left].equals(mirrors[right])) {
        if (right == mirrors.length - 1) {
          left++;
        }
        right = mirrors.length - 1;
        found = false;
      } else if (mirrors[left].equals(mirrors[right])) {
        if (left == right - 1) {
          // found the focal point between left/right
          found = true;
          return left + 1;
        } else {
          left++;
          right--;
        }
      }
    }

    return 0;

  }

  private static String[] rotate(String[] in) {
    int m = in.length;
    int n = in[0].length();

    String[] out = new String[n];

    int i = 0;
    for (int x = 0; x < n; x++) {
      StringBuilder sb = new StringBuilder();
      for (int y = m - 1; y >= 0; y--) {
        sb.append(in[y].charAt(x));
      }
      out[i] = sb.toString();
      i++;
    }

    return out;
  }
}
