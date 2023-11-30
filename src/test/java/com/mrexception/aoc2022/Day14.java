package com.mrexception.aoc2022;

import static com.mrexception.Utils.processFile;
import static com.mrexception.Utils.splitLine;
import static com.mrexception.Utils.toInts;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class Day14 {
  private final String[] testInput1 =
      new String[] {"498,4 -> 498,6 -> 496,6", "503,4 -> 502,4 -> 502,9 -> 494,9"};

  private final String inputFile = "com/mrexception/aoc2022/day14.txt";

  @Test
  public void testData() throws Exception {
    assertThat(processFile(inputFile).length).isGreaterThan(0);
  }

  @Test
  public void testFindSize() throws Exception {

    assertThat(findSize(testInput1).sizeY()).isEqualTo(6);
    assertThat(findSize(testInput1).sizeX()).isEqualTo(10);

    assertThat(findSize(processFile(inputFile)).sizeY()).isEqualTo(154);
    assertThat(findSize(processFile(inputFile)).sizeX()).isEqualTo(82);
  }

  @Test
  public void testBuildMap() {
    /*
    ....#...##
    ....#...#.
    ..###...#.
    ........#.
    .....oo.#.
    #########.
         */
    String[] input = new String[] {"4,0 -> 4,2 -> 2,2", "9,0 -> 8,0 -> 8,5 -> 0,5"};
    int[][] expectedMap =
        new int[][] {
          new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
          new int[] {0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
          new int[] {0, 0, 1, 1, 1, 0, 0, 0, 1, 0},
          new int[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
          new int[] {0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
          new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 0}
        };

    assertThat(buildMap(6, 10, input)).isEqualTo(expectedMap);
  }

  @Test
  public void testLineToPoints() {
    assertThat(lineToPoints(new int[] {4, 10}, new int[] {6, 10}))
        .isEqualTo(
            new int[][] {
              new int[] {4, 10},
              new int[] {5, 10},
              new int[] {6, 10}
            });
    assertThat(lineToPoints(new int[] {6, 10}, new int[] {4, 10}))
        .isEqualTo(
            new int[][] {
              new int[] {4, 10},
              new int[] {5, 10},
              new int[] {6, 10}
            });
    assertThat(lineToPoints(new int[] {4, 10}, new int[] {4, 12}))
        .isEqualTo(
            new int[][] {
              new int[] {4, 10},
              new int[] {4, 11},
              new int[] {4, 12}
            });
    assertThat(lineToPoints(new int[] {4, 10}, new int[] {4, 8}))
        .isEqualTo(
            new int[][] {
              new int[] {4, 8},
              new int[] {4, 9},
              new int[] {4, 10}
            });
  }

  @Test
  public void testPartOne() throws Exception {
    assertThat(partOne(testInput1)).isEqualTo(24);
    assertThat(partOne(processFile(inputFile))).isEqualTo(843);
  }

  @Test
  public void testPartTwo() throws Exception {
    assertThat(partTwo(testInput1)).isEqualTo(93);
    assertThat(partTwo(processFile(inputFile))).isEqualTo(27625);
  }

  private MapSize findSize(String[] input) {
    int maxX = 0;
    int maxY = 0;
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    for (String line : input) {
      String[] blocks = line.split(" -> ");
      for (String block : blocks) {
        int[] coords = toInts(splitLine(block));
        maxX = Math.max(coords[0], maxX);
        maxY = Math.max(coords[1], maxY);
        minX = Math.min(coords[0], minX);
        minY = Math.min(coords[1], minY);
      }
    }
    return new MapSize(minX, maxX, minY, maxY);
  }

  private int[][] buildMap(int y, int x, String[] input) {
    int[][] map = new int[y][x];

    for (String line : input) {
      String[] blocks = line.split(" -> ");
      for (int i = 1; i < blocks.length; i++) {

        int[] coordsL = toInts(splitLine(blocks[i - 1]));
        int[] coordsR = toInts(splitLine(blocks[i]));

        int[][] points =
            lineToPoints(new int[] {coordsL[1], coordsL[0]}, new int[] {coordsR[1], coordsR[0]});
        for (int[] point : points) {
          map[point[0]][point[1]] = 1;
        }
      }
    }
    return map;
  }

  private int[][] lineToPoints(int[] start, int[] end) {
    int[][] out;
    if (start[0] == end[0]) {
      if (start[1] > end[1]) {
        return lineToPoints(end, start);
      }

      out = new int[Math.abs(end[1] - start[1]) + 1][];
      int j = 0;
      for (int i = start[1]; i <= end[1]; i++) {
        out[j] = new int[] {start[0], i};
        j++;
      }

    } else {
      if (start[0] > end[0]) {
        return lineToPoints(end, start);
      }

      out = new int[Math.abs(end[0] - start[0]) + 1][];
      int j = 0;
      for (int i = start[0]; i <= end[0]; i++) {
        out[j] = new int[] {i, start[1]};
        j++;
      }
    }
    return out;
  }

  private int partOne(String[] input) {
    int[][] map = buildMap(1000, 1000, input);

    int count = 0;
    int x = 500;
    int y = 0;
    while (x > 0 && y < 999 && x < 999) {
      if (map[y + 1][x] != 1) {
        // free spot directly below
        y++;
      } else if (map[y + 1][x - 1] != 1) {
        // free spot directly below and left
        y++;
        x--;
      } else if (map[y + 1][x + 1] != 1) {
        // free spot directly below and right
        y++;
        x++;
      } else {
        // we stop here, and a new sand grain falls
        map[y][x] = 1;
        y = 0;
        x = 500;
        count++;
      }
    }

    return count;
  }

  private int partTwo(String[] input) {
    var mapSize = findSize(input);
    int[][] map = buildMap(1000, 1000, input);

    int floor = mapSize.maxY + 2;
    for (int i = 0; i < 1000; i++) {
      map[floor][i] = 1;
    }

    int count = 0;
    int x = 500;
    int y = 0;
    while (map[0][500] != 1) {
      if (map[y + 1][x] != 1) {
        // free spot directly below
        y++;
      } else if (map[y + 1][x - 1] != 1) {
        // free spot directly below and left
        y++;
        x--;
      } else if (map[y + 1][x + 1] != 1) {
        // free spot directly below and right
        y++;
        x++;
      } else {
        // we stop here, and a new sand grain falls
        map[y][x] = 1;
        y = 0;
        x = 500;
        count++;
      }
    }

    return count;
  }

  private record MapSize(int minX, int maxX, int minY, int maxY) {
    public int sizeX() {
      return maxX - minX + 1;
    }

    public int sizeY() {
      return maxY - minY + 1;
    }
  }
}
