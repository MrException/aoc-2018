package com.mrexception;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class Utils {
    public static String[] processFile(String path) throws Exception {
        Resource dataFile = new ClassPathResource(path);

        InputStream resource = dataFile.getInputStream();
        String[] lines;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource))) {
            lines = reader
                    .lines()
                    .map(String::trim)
                    .toArray(String[]::new);
        }
        return lines;
    }

    public static String[] splitLine(String line) {
        return splitLine(line, ",");
    }

    public static String[] splitLine(String line, String delimiter) {
        return trim(line.replaceAll(delimiter, " ").split("\\s+"));
    }

    public static String[] trim(String[] strs) {
        return Arrays.stream(strs)
                .map(String::trim)
                .toArray(String[]::new);
    }

    public static int[] toInts(String[] strs) {
        int[] ints = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = toInt(strs[i]);
        }
        return ints;
    }

    public static int toInt(String str) {
        return Integer.parseInt(str.trim());
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
}
