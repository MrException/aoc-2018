package com.mrexception;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Utils {
    public static void processFile(String path, Consumer<Stream<String>> processor) throws Exception {
        Resource dataFile = new ClassPathResource(path);

        InputStream resource = dataFile.getInputStream();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource))) {
            Stream<String> lines = reader.lines();
            processor.accept(lines);
        }
    }

    public static String[] processFile(String path) throws Exception {
        Resource dataFile = new ClassPathResource(path);

        InputStream resource = dataFile.getInputStream();
        String[] lines ;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource))) {
            lines = reader.lines().toArray(String[]::new);
        }
        return lines;
    }

    public static String[] splitLine(String line) {
        return splitLine(line, ",");
    }

    public static String[] splitLine(String line, String delimiter) {
        return line.replaceAll(delimiter, " ").split("\\s+");
    }

    public static Stream<Integer> toInts(Stream<String> strs) {
        return strs.map(Integer::parseInt);
    }

    public static int[] toInts(String[] strs) {
        int[] ints = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = Integer.parseInt(strs[i]);
        }
        return ints;
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
                return (x,y) -> x > y;
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
