package com.mrexception.aoc2022;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.mrexception.Utils.processFile;
import static org.assertj.core.api.Assertions.assertThat;

public class Day15 {
  private final String inputFile = "com/mrexception/aoc2022/day15.txt";

  private final String[] testInput =
          new String[] {
                  "Sensor at x=2, y=18: closest beacon is at x=-2, y=15",
                  "Sensor at x=9, y=16: closest beacon is at x=10, y=16",
                  "Sensor at x=13, y=2: closest beacon is at x=15, y=3",
                  "Sensor at x=12, y=14: closest beacon is at x=10, y=16",
                  "Sensor at x=10, y=20: closest beacon is at x=10, y=16",
                  "Sensor at x=14, y=17: closest beacon is at x=10, y=16",
                  "Sensor at x=8, y=7: closest beacon is at x=2, y=10",
                  "Sensor at x=2, y=0: closest beacon is at x=2, y=10",
                  "Sensor at x=0, y=11: closest beacon is at x=2, y=10",
                  "Sensor at x=20, y=14: closest beacon is at x=25, y=17",
                  "Sensor at x=17, y=20: closest beacon is at x=21, y=22",
                  "Sensor at x=16, y=7: closest beacon is at x=15, y=3",
                  "Sensor at x=14, y=3: closest beacon is at x=15, y=3",
                  "Sensor at x=20, y=1: closest beacon is at x=15, y=3 "
          };

  @Test
  public void testPartOne() throws Exception {
    assertThat(partOne(testInput, 10, -10)).isEqualTo(26);
    assertThat(partOne(processFile(inputFile), 2000000, -1000000)).isEqualTo(5166077);
  }

  @Test
  public void testParseLine() {
    assertThat(parseLine("Sensor at x=8, y=7: closest beacon is at x=2, y=10").dist).isEqualTo(9);
  }

  private SensorDist parseLine(String line) {
    // return the manhattan distance b/w sensor and closest beacon
    String[] split = line.split("[,:]?\\s+");
    Integer[] ints =
        Arrays.stream(split)
            .filter(s -> s.startsWith("x=") || s.startsWith("y="))
            .map(s -> Integer.parseInt(s.split("=")[1]))
            .toArray(Integer[]::new);

    int dist = Math.abs(ints[2] - ints[0]) + Math.abs(ints[3] - ints[1]);
    return new SensorDist(ints[0], ints[1], ints[2], ints[3], dist);
  }

  private int partOne(String[] lines, int y, int minx) {
    SensorDist[] distances =
        Arrays.stream(lines)
            .map(this::parseLine)
            .filter(s -> Math.abs(s.sy - y) <= s.dist)
            .toArray(SensorDist[]::new);

    Set<Integer> covered = new HashSet<>();

    for (SensorDist sensor : distances) {
      int distOnLine;
      int x2 = minx;
      int yDist = Math.abs(sensor.sy - y);
      boolean foundFirst = false;
      while (true) {
        distOnLine = Math.abs(sensor.sx - x2) + yDist;
        if (distOnLine <= sensor.dist) {
          covered.add(x2);
          foundFirst = true;
        } else if (foundFirst) {
          break;
        }

        x2++;
      }
    }

    for (SensorDist sensorDist : distances) {
      if (sensorDist.by == y) {
        covered.remove(sensorDist.bx);
      }
    }

    return covered.size();
  }

  private record SensorDist(int sx, int sy, int bx, int by, int dist) {}
}
