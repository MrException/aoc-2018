package com.mrexception.aoc2023;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.nio.file.*;

public class Day5 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
    .filter(s -> !s.isBlank())
      .map(Day5::toNum)
      .collect(Collectors.toList());
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
  }




  private static String[] input = new String[] {
    "seeds: 79 14 55 13",
    "",
    "seed-to-soil map:",
    "50 98 2",
    "52 50 48",
    "",
    "soil-to-fertilizer map:",
    "0 15 37",
    "37 52 2",
    "39 0 15",
    "",
    "fertilizer-to-water map:",
    "49 53 8",
    "0 11 42",
    "42 0 7",
    "57 7 4",
    "",
    "water-to-light map:",
    "88 18 7",
    "18 25 70",
    "",
    "light-to-temperature map:",
    "45 77 23",
    "81 45 19",
    "68 64 13",
    "",
    "temperature-to-humidity map:",
    "0 69 1",
    "1 0 69",
    "",
    "humidity-to-location map:",
    "60 56 37",
    "56 93 4",
  };

  private static String[] readFile() throws Exception {
    String filePath = "day5.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    var one = partOne(input);
    log("Par One Test: " + one);
    assert one == 35;

    one = partOne(readFile());
    log("Par One Real: " + one);
    assert one == 323142486;

    // var two = partTwo(input);
    // log("Part Two Test: " + two);
    // assert two == 30;
    //
    // two = partTwo(readFile());
    // log("Part Two Real: " + two);
    // assert two == 5659035;
  }

  private static long partOne(String[] input) {
    List<Long> seeds = toNums(input[0].split(":")[1].trim().split(" +"));

    List<List<DestMap>> maps = parse(input);

    return seeds.stream().map(s -> findDest(s, maps)).reduce(Long::min).get();
  }

  private static long findDest(long seed, List<List<DestMap>> maps) {
    long src = seed;
    for(List<DestMap> mapSet : maps) {
      DestMap found = null;
      for(DestMap dest : mapSet) {
        if(dest.fits(src)) {
          found = dest;
          break;
        }
      }

      long dest;
      if(found != null) {
        dest = found.toDest(src);
      } else {
        dest = src;
      }
      //log(String.format("%s -> %s : %s", src, dest, found));
      src = dest;
    }

    //log(String.format("Seed %s -> Location %s", seed, src));

    return src;
  }

  private static List<List<DestMap>> parse(String[] input) {
    List<List<DestMap>> maps = new ArrayList<>();
    List<DestMap> curMap = null;
    for(int i = 2; i < input.length; i++) {
      String line = input[i];

      if(line.isBlank()) continue;

      if(!line.matches("^[0-9].*")) {
        // doesn't start with a number so new map set
        curMap = new ArrayList<>();
        maps.add(curMap);
        continue;
      }

      List<Long> map = toNums(line.trim().split(" +"));
      curMap.add(new DestMap(map.get(0), map.get(1), map.get(2)));
    }
    return maps;
  }

  private record DestMap(long destStart, long srcStart, long len){
    boolean fits(long src) {
      return src >= srcStart && src <= srcStart + len;
    }

    long toDest(long src) {
      return destStart + (src - srcStart);
    }
  }
}
