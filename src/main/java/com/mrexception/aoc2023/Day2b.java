package com.mrexception.aoc2023;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day2b {
  String[] readFile() throws Exception {
    String filePath = "day2.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  void main() throws Exception {
    String[] testInput = new String[] {
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
    };

    assert partOne(testInput) == 8 : "Part one failed";
    assert partOne(readFile()) == 2449;
    System.out.println("Part one passed.");

    assert partTwo(testInput) == 2286 : "Part two failed";
    assert partTwo(readFile()) == 63981;
    System.out.println("Part two passed.");
  }

  private record BlockPair(String color, int count) {
  }

  private record Game(int id, List<BlockPair> pairs) {
    public Game(int id, String gameLine) {
      this(id, parseGameLine(gameLine));
    }

    private static List<BlockPair> parseGameLine(String gameLine) {
      return Arrays.stream(gameLine.split(";"))
          .map(game -> game.split(","))
          .flatMap(Arrays::stream)
          .map(s -> s.trim().split(" "))
          .map(t -> new BlockPair(t[1], Integer.parseInt(t[0])))
          .collect(Collectors.toList());
    }
  }

  private Stream<Game> parseGames(String[] input) {
    return Arrays.stream(input)
        .map(line -> {
          String[] parts = line.split(":");
          Integer gameId = Integer.parseInt(parts[0].split(" ")[1]);
          return new Game(gameId, parts[1].trim());
        });
  }

  private int partOne(String[] input) {
    return parseGames(input)
        .filter(Day2b::gameValid)
        .map(game -> game.id)
        .mapToInt(Integer::intValue)
        .sum();
  }

  private int partTwo(String[] input) {
    return parseGames(input)
        .map(Day2b::gamePower)
        .mapToInt(Integer::intValue).sum();
  }

  private static boolean gameValid(Game game) {
    Map<String, Integer> maxPair = Map.ofEntries(
        Map.entry("red", 12),
        Map.entry("green", 13),
        Map.entry("blue", 14));

    return game.pairs.stream()
        .allMatch(pair -> pair.count <= maxPair.get(pair.color));
  }

  private static int gamePower(Game game) {
    Map<String, Integer> cubes = new HashMap<>();
    game.pairs.stream()
        .forEach(pair -> {
          int curCount = cubes.getOrDefault(pair.color, 0);
          if (pair.count > curCount) {
            cubes.put(pair.color, pair.count);
          }
        });

    return cubes.values().stream()
        .mapToInt(Integer::intValue)
        .reduce(1, (a, b) -> a * b);
  }

  private static void log(Object msg) {
    System.out.println(msg);
  }
}
