package com.mrexception;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

@SuppressWarnings("WeakerAccess")
public class Utils {
    public static String[] processFile(String path) throws Exception {
        return processFile(path, true);
    }

    public static String[] processFile(String path, boolean trim) throws Exception {
        Resource dataFile = new ClassPathResource(path);

        InputStream resource = dataFile.getInputStream();
        String[] lines;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource))) {
            lines = reader
                    .lines()
                    .map(s -> trim ? s.trim() : s)
                    .toArray(String[]::new);
        }
        return lines;
    }

    public static String[] splitLine(String line) {
        return splitLine(line, ",", true);
    }

    public static String[] splitLine(String line, String delimiter, boolean andSpace) {
        if (andSpace) {
            return splitLine(line.replaceAll(delimiter, " "), "\\s+");
        } else {
            return splitLine(line, delimiter);
        }
    }

    public static String[] splitLine(String line, String delimiter) {
        return trim(line.split(delimiter));
    }

    public static String[] trim(String[] strs) {
        return Arrays.stream(strs)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    public static int[] toInts(String[] strs) {
        int[] ints = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = toInt(strs[i]);
        }
        return ints;
    }

    public static int[] toInts(char[] chars) {
        int[] ints = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            ints[i] = toInt(chars[i]);
        }
        return ints;
    }

    public static int toInt(String str) {
        return Integer.parseInt(str.trim());
    }

    public static int toInt(char c) {
        return Integer.parseInt("" + c);
    }

    public static <E> E[] shuffle(E[] array) {
        List<E> list = Arrays.asList(array);
        Collections.shuffle(list);
        return list.toArray(array);
    }

    public static BiFunction<Double, Double, Double> strToOperator(String str) {
        switch (str) {
            case "+":
                return Double::sum;
            case "*":
                return (x, y) -> x * y;
            case "/":
                return (x, y) -> x / y;
            case "**":
                return Math::pow;
            default:
                return null;
        }
    }

    public static BiFunction<Double, Double, Boolean> strToComparator(String str) {
        switch (str) {
            case ">":
                return (x, y) -> x > y;
            case "<":
                return (x, y) -> x < y;
            case ">=":
                return (x, y) -> x >= y;
            case "<=":
                return (x, y) -> x <= y;
            case "==":
                return Double::equals;
            case "!=":
                return (x, y) -> !x.equals(y);
            default:
                return null;
        }
    }

    public static List<List<Integer>> permutations(Integer[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(0, nums, result);
        return result;
    }

    private static void permuteHelper(int start, Integer[] nums, List<List<Integer>> result) {
        if (start == nums.length - 1) {
            ArrayList<Integer> list = new ArrayList<>(Arrays.asList(nums));
            result.add(list);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            swap(nums, i, start);
            permuteHelper(start + 1, nums, result);
            swap(nums, i, start);
        }
    }

    private static <T> void swap(T[] input, int a, int b) {
        T tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}
