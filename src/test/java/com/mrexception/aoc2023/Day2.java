package com.mrexception.aoc2023;

import static com.mrexception.Utils.processFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class Day2 {

  private String[] input = new String[] {
      "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
      "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
      "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
      "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
      "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
  };

  private final String inputFile = "com/mrexception/aoc2023/day2.txt";

  Map<String, Integer> map = Map.ofEntries(
      Map.entry("red", 12),
      Map.entry("green", 13),
      Map.entry("blue", 14));

  @Test
  public void testPartOne() throws Exception {
    assertThat(partOne(input)).isEqualTo(8);
    assertThat(partOne(processFile(inputFile))).isEqualTo(2449);
  }

  @Test
  public void testPartTwo() throws Exception {
    assertThat(partTwo(input)).isEqualTo(2286);
    assertThat(partTwo(processFile(inputFile))).isEqualTo(63981);
  }

  private int partOne(String[] input) {

    int total = 0;
    for (String game : input) {
      String[] parts = game.split(":");
      Integer gameId = Integer.parseInt(parts[0].split(" ")[1]);
      String record = parts[1];

      if (gameValid(record)) {
        total += gameId;
      }
    }
    return total;
  }

  private int partTwo(String[] input) {
    int total = 0;
    for (String game : input) {
      String[] parts = game.split(":");
      String record = parts[1];

      total += gamePower(record);
    }
    return total;
  }

  private boolean gameValid(String record) {
    String[] games = record.split(";");
    for (String game : games) {
      String[] tokens = game.split(",");
      for (String token : tokens) {
        String[] subTokens = token.trim().split(" ");
        int count = Integer.parseInt(subTokens[0]);
        String color = subTokens[1];
        if (count > map.get(color)) {
          return false;
        }
      }
    }
    return true;
  }

  private int gamePower(String record) {
    String[] games = record.split(";");
    int red = 0;
    int green = 0;
    int blue = 0;
    for (String game : games) {
      String[] tokens = game.split(",");
      for (String token : tokens) {
        String[] subTokens = token.trim().split(" ");
        int count = Integer.parseInt(subTokens[0]);
        String color = subTokens[1];
        switch (color) {
          case "red":
            if (count > red) {
              red = count;
            }
            break;
          case "blue":
            if (count > blue) {
              blue = count;
            }
            break;
          case "green":
            if (count > green) {
              green = count;
            }
            break;

          default:
            break;
        }
      }
    }
    return red * green * blue;
  }
}
