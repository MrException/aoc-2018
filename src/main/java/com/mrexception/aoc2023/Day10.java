package com.mrexception.aoc2023;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;

import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day10 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day10::toNum)
        .collect(Collectors.toList());
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
  }

  private static String[] input = new String[] {
      "..F7.",
      ".FJ|.",
      "SJ.L7",
      "|F--J",
      "LJ...",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day10.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    partTwo = false;
    var one = run(input);
    log("Part One Test: " + one);
    assert one == 8;

    one = run(readFile());
    log("Part One Real: " + one);
    assert one == 6856;

    partTwo = true;
    var two = run(input);
    log("Part Two Test: " + two);
    assert two == 0;

    two = run(readFile());
    log("Part Two Real: " + two);
    assert two == 0;
  }

  private static boolean partTwo = false;

  record Point(int x, int y, char type, Point prev) {
    Point move(int x, int y, char type) {
      return new Point(x, y, type, this);
    }
  }

  private static long run(String[] input) {
    char[][] map = new char[input.length][];
    int sx = 0, sy = 0;
    for (int i = 0; i < input.length; i++) {
      int x = input[i].trim().indexOf('S');
      if (x != -1) {
        sx = x;
        sy = i;
      }
      map[i] = input[i].trim().toCharArray();
    }

    Point S = new Point(sx, sy, 'S', null);
    int longest = walk(S, sx + 1, sy, map, 0);
    longest = walk(S, sx - 1, sy, map, longest);
    longest = walk(S, sx, sy + 1, map, longest);
    return walk(S, sx, sy - 1, map, longest);
  }

  private static int walk(Point S, int x, int y, char[][] map, int longest) {
    if (x < 0 || y < 0 || map[y][x] == '.')
      return longest;

    boolean end = false;
    int length = 1;
    Point cur = new Point(x, y, map[y][x], S);
    while (!end) {
      cur = move(cur, map);
      if (cur.x == S.x && cur.y == S.y) {
        end = true;
      }
      length++;
    }
    return Math.max(longest, length % 2 == 0 ? length / 2 : length / 2 + 1);
  }

  private static Point move(Point p, char[][] map) {
    int nx = -1, ny = -1;
    switch (p.type) {
      case 'S':
        if (map[p.y - 1][p.x] != '.') {
          nx = p.x;
          ny = p.y - 1;
          break;
        }
        if (map[p.y + 1][p.x] != '.') {
          nx = p.x;
          ny = p.y + 1;
          break;
        }
        if (map[p.y][p.x - 1] != '.') {
          nx = p.x - 1;
          ny = p.y;
          break;
        }
        if (map[p.y][p.x + 1] != '.') {
          nx = p.x + 1;
          ny = p.y;
          break;
        }
        break;
      case '-':
        if (p.prev.x < p.x) {
          nx = p.x + 1;
        } else {
          nx = p.x - 1;
        }
        ny = p.y;
        break;
      case '|':
        if (p.prev.y < p.y) {
          ny = p.y + 1;
        } else {
          ny = p.y - 1;
        }
        nx = p.x;
        break;
      case 'L':
        if (p.prev.y < p.y) {
          nx = p.x + 1;
          ny = p.y;
        } else {
          ny = p.y - 1;
          nx = p.x;
        }
        break;
      case 'J':
        if (p.prev.y < p.y) {
          nx = p.x - 1;
          ny = p.y;
        } else {
          ny = p.y - 1;
          nx = p.x;
        }
        break;
      case '7':
        if (p.prev.y > p.y) {
          nx = p.x - 1;
          ny = p.y;
        } else {
          ny = p.y + 1;
          nx = p.x;
        }
        break;
      case 'F':
        if (p.prev.y > p.y) {
          nx = p.x + 1;
          ny = p.y;
        } else {
          ny = p.y + 1;
          nx = p.x;
        }
        break;
      default:
        log("BAD");
        break;
    }

    return new Point(nx, ny, map[ny][nx], p);
  }
}
