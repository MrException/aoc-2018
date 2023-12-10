package com.mrexception.aoc2023;

import java.util.*;
import java.util.Map.Entry;
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
      Map.entry('2', 1),
      Map.entry('S', 0));

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
    assert rankHand("AA4KK") == HandRank.TWO_P : "AA4KK";
    assert rankHand("AAAAA") == HandRank.FIVE_OK : "AAAAA";
    assert rankHand("AA4AA") == HandRank.FOUR_OK : "AA4AA";
    assert rankHand("AAKKK") == HandRank.FULL_H : "AAKKK";
    assert rankHand("2AKKK") == HandRank.THREE_OK : "2AKKK";
    assert rankHand("AA43K") == HandRank.ONE_P : "AA43K";
    assert rankHand("AT43K") == HandRank.HIGH_C : "AT43K";

    var one = partOne(input);
    log("Part One Test: " + one);
    assert one == 6440;

    one = partOne(readFile());
    log("Part One Real: " + one);
    assert one == 248179786;

    partTwo = true;
    assert rankHand("AAJKK") == HandRank.FULL_H : "AAJKK";
    assert rankHand("AAAJJ") == HandRank.FIVE_OK : "AAAJJ";
    assert rankHand("AA4JA") == HandRank.FOUR_OK : "AA4JA";
    assert rankHand("AAKKK") == HandRank.FULL_H : "AAKKK";
    assert rankHand("2AJKK") == HandRank.THREE_OK : "2AJKK";
    assert rankHand("AA43K") == HandRank.ONE_P : "AA43K";
    assert rankHand("AT43J") == HandRank.ONE_P : "AT43J";

    var two = partTwo(input);
    log("Part Two Test: " + two);
    assert two == 5905;

    two = partTwo(readFile());
    log("Part Two Real: " + two);
    assert two == 247885995;
  }

  private static boolean partTwo = false;

  private record Hand(String str, long bet, HandRank rank) implements Comparable<Hand> {
    public int compareTo(Hand o) {
      int rankSort = rank.compareTo(o.rank);
      if (rankSort == 0) {
        char[] left = str.toCharArray();
        char[] right = o.str.toCharArray();

        for (int i = 0; i < left.length; i++) {
          char l = left[i];
          char r = right[i];
          if (partTwo) {
            l = (l == 'J' ? l = 'S' : l);
            r = (r == 'J' ? r = 'S' : r);
          }
          if (l != r) {
            return order.get(l).compareTo(order.get(r));
          }
        }
      }
      return rankSort;
    }

  }

  private static long partOne(String[] input) {
    partTwo = false;
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
    partTwo = true;
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

  private static HandRank rankHand(String hand) {
    Map<Integer, Integer> map = hand.chars()
        .mapToObj(i -> i)
        .reduce(
            new HashMap<Integer, Integer>(),
            (m, i) -> {
              m.merge(i, 1, Integer::sum);
              return m;
            },
            (m, m2) -> {
              m.putAll(m2);
              return m;
            });

    if (partTwo) {
      int numJ = map.getOrDefault((int) 'J', 0);
      map.remove((int) 'J');
      if (numJ == 5) {
        return HandRank.FIVE_OK;
      }

      Entry<Integer, Integer> best = map.entrySet().stream()
          .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
          .findFirst()
          .get();
      best.setValue(best.getValue() + numJ);
    }

    var uniques = map.values();
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
