package com.mrexception.aoc2023;

import static com.mrexception.Utils.processFile;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class Day3 {

  private String[] input = new String[] {
    "467..114..",
    "...*......",
    "..35..633.",
    "......#...",
    "617*......",
    ".....+.58.",
    "..592.....",
    "......755.",
    "...$.*....",
    ".664.598.."
  };

  private final String inputFile = "com/mrexception/aoc2023/day3.txt";

  @Test
  public void testPartOne() throws Exception {
    assertThat(partOne(input)).isEqualTo(4361);
    assertThat(partOne(processFile(inputFile))).isEqualTo(0);
  }

//  @Test
//  public void testPartTwo() throws Exception {
//    assertThat(partTwo(input)).isEqualTo(2286);
//    assertThat(partTwo(processFile(inputFile))).isEqualTo(63981);
//  }

  private int partOne(String[] input) {
    Arrays.stream(input)
      .map(Day3::findNums)
      .flatMap(Collection::stream)
      .peek(n -> System.out.println(n));
    return 0;
  }

  private record Num(int start, int end, int val){}

  private static List<Num> findNums(String line) {
    List<Num> nums = new ArrayList<>();
    int start = -1;
    for( int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);
      if(start == -1 && isNum(c)) {
        // the start of a number
        start = i;
      }
      if(start >= 0 && !isNum(c) ) {
        // the end of a number
        nums.add(new Num(start, i-1, Integer.parseInt(line.substring(start, i-1))));
        start = -1;
      }
    }
    if(start >= 0) {
      // special case of a number being at the end of a line
      nums.add(new Num(start, line.length(), Integer.parseInt(line.substring(start))));
    }
    log(nums);
    return nums;
  }

  private static boolean isNum(char c) {
    return c >= '0' && c <= '9';
  }

  private static void log(Object o) {
    System.out.println(o);
  }
}
