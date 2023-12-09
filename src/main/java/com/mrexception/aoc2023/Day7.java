package com.mrexception.aoc2023;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.nio.file.*;
import java.text.Collator;

public class Day7 {

  private static void log(Object o) {
    System.out.println(o);
  }

  public static List<Long> toNums(String[] strs) {
    return Arrays.stream(strs)
        .filter(s -> !s.isBlank())
        .map(Day7::toNum)
        .collect(Collectors.toList());
  }

  public static long toNum(String str) {
    return Long.parseLong(str.trim());
  }

  private static Map<Character, Integer> order = Map.ofEntries(
      Map.entry('A', 13),
      Map.entry('K', 12),
      Map.entry('Q', 11),
      Map.entry('J', 10),
      Map.entry('T', 9),
      Map.entry('9', 8),
      Map.entry('8', 7),
      Map.entry('7', 6),
      Map.entry('6', 5),
      Map.entry('5', 4),
      Map.entry('4', 3),
      Map.entry('3', 2),
      Map.entry('2', 1));

  enum HandRank {
    HIGH_C, ONE_P, TWO_P, THREE_OK, FULL_H, FOUR_OK, FIVE_OK
  }

  private static String[] input = new String[] {
      "32T3K 765",
      "T55J5 684",
      "KK677 28",
      "KTJJT 220",
      "QQQJA 483"
  };

  private static String[] readFile() throws Exception {
    String filePath = "day7.txt";
    String content = new String(Files.readAllBytes(Paths.get(filePath)));
    return content.split("\n");
  }

  public static void main(String[] args) throws Exception {
    assert rankHand("AA4KK") == HandRank.TWO_P;
    assert rankHand("AAAAA") == HandRank.FIVE_OK;
    assert rankHand("AA4AA") == HandRank.FOUR_OK;
    assert rankHand("AAKKK") == HandRank.FULL_H;
    assert rankHand("2AKKK") == HandRank.THREE_OK;
    assert rankHand("AA43K") == HandRank.ONE_P;
    assert rankHand("AT43K") == HandRank.HIGH_C;

    var one = partOne(input);
    log("Part One Test: " + one);
    assert one == 6440;

    one = partOne(readFile());
    log("Part One Real: " + one);
    assert one == 248179786;

    assert rankHand2("AAJKK") == HandRank.FULL_H;
    assert rankHand2("AAAJJ") == HandRank.FIVE_OK;
    assert rankHand2("AA4JA") == HandRank.FOUR_OK;
    assert rankHand2("AAKKK") == HandRank.FULL_H;
    assert rankHand2("2AJKK") == HandRank.THREE_OK;
    assert rankHand2("AA43K") == HandRank.ONE_P;
    assert rankHand2("AT43J") == HandRank.ONE_P;

    var two = partTwo(input);
    log("Part Two Test: " + two);
    assert two == 5905;

    two = partTwo(readFile());
    log("Part Two Real: " + two);
    assert two == 999;
  }

  private record Hand(String str, long bet, HandRank rank) implements Comparable<Hand> {
    public int compareTo(Hand o) {
      int rankSort = rank.compareTo(o.rank);
      if (rankSort == 0) {
        char[] left = str.toCharArray();
        char[] right = o.str.toCharArray();

        for (int i = 0; i < left.length; i++) {
          if (left[i] != right[i]) {
            return order.get(left[i]).compareTo(order.get(right[i]));
          }
        }
      }
      return rankSort;
    }
  }

  private static long partOne(String[] input) {
    List<Hand> sorted = Arrays.stream(input)
        .map(line -> {
          String[] parts = line.trim().split(" +");
          String str = parts[0].trim();
          long bet = toNum(parts[1].trim());
          return new Hand(str, bet, rankHand(str));
        })
        .sorted((h1, h2) -> h1.compareTo(h2))
        .collect(Collectors.toList());

    return IntStream
        .range(0, sorted.size())
        .mapToObj(i -> (i + 1) * sorted.get(i).bet)
        .reduce(Long::sum).get();
  }

  private static long partTwo(String[] input) {
    return 0;
  }

  private static HandRank rankHand(String hand) {
    Collection<Integer> uniques = hand.chars()
        .mapToObj(i -> i)
        .reduce(
            new HashMap<Integer, Integer>(),
            (map, i) -> {
              map.merge(i, 1, Integer::sum);
              return map;
            },
            (m, m2) -> {
              m.putAll(m2);
              return m;
            })
        .values();

    HandRank rank = null;
    switch (uniques.size()) {
      case 1:
        rank = HandRank.FIVE_OK;
        break;
      case 2:
        if (uniques.contains(4)) {
          rank = HandRank.FOUR_OK;
        } else {
          rank = HandRank.FULL_H;
        }
        break;
      case 3:
        if (uniques.contains(3)) {
          rank = HandRank.THREE_OK;
        } else {
          rank = HandRank.TWO_P;
        }
        break;
      case 4:
        rank = HandRank.ONE_P;
        break;
      case 5:
        rank = HandRank.HIGH_C;
        break;
      default:
        rank = HandRank.HIGH_C;
        break;
    }
    return rank;
  }
}
