package com.mrexception.aoc2018;

import com.mrexception.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Day6 {
    private String inputFile = "com/mrexception/aoc2018/day6.txt";

    private String[] testData = new String[]{
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
    };

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic(testData).partOne()).isEqualTo(17);

        assertThat(new Logic(processFile(inputFile)).partOne()).isEqualTo(4143);
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(new Logic(testData).partTwo(32)).isEqualTo(16);

        assertThat(new Logic(processFile(inputFile)).partTwo(10000)).isEqualTo(35039);
    }

    class Logic {
        private final String[] data;

        Logic(String[] data) {
            this.data = data;
        }

        int partOne() {
            Point[] points = new Point[data.length];
            for (int i = 0; i < data.length; i++) {
                int[] coords = toInts(splitLine(data[i]));
                points[i] = new Point(coords[0], coords[1]);
            }
            int maxX = -1;
            int maxY = -1;
            for (Point point : points) {
                if (point.x > maxX) {
                    maxX = point.x;
                }
                if (point.y > maxY) {
                    maxY = point.y;
                }
            }

            Map<Point, Integer> map = new HashMap<>();

            List<Point> infinites = new ArrayList<>();
            for (int x = 0; x <= maxX; x++) {
                for (int y = 0; y <= maxY; y++) {
                    Point closest = null;
                    int closestDistance = Integer.MAX_VALUE;
                    boolean found2 = false;
                    for (Point cur : points) {
                        int distance = cur.manhattanDistance(new Point(x, y));
                        if (distance == closestDistance) {
                            found2 = true;
                        }
                        if (distance < closestDistance) {
                            closest = cur;
                            closestDistance = distance;
                            found2 = false;
                        }
                    }
                    if (!found2) {
                        Integer area = map.getOrDefault(closest, 0);
                        map.put(closest, area + 1);
                    }
                    if (x == 0 || x == maxX || y == 0 || y == maxY) {
                        infinites.add(closest);
                    }
                }
            }

            int max = 0;
            for (Map.Entry<Point, Integer> entry : map.entrySet()) {
                if (!infinites.contains(entry.getKey())) {
                    if (entry.getValue() > max) {
                        max = entry.getValue();
                    }
                }
            }
            return max;
        }

        int partTwo(int target) {
            Point[] points = new Point[data.length];
            for (int i = 0; i < data.length; i++) {
                int[] coords = toInts(splitLine(data[i]));
                points[i] = new Point(coords[0], coords[1]);
            }
            int maxX = -1;
            int maxY = -1;
            for (Point point : points) {
                if (point.x > maxX) {
                    maxX = point.x;
                }
                if (point.y > maxY) {
                    maxY = point.y;
                }
            }

            int area = 0;
            for (int x = 0; x <= maxX; x++) {
                for (int y = 0; y <= maxY; y++) {
                    Point cur = new Point(x, y);
                    int distanceSum = 0;
                    for (Point other : points) {
                        distanceSum += other.manhattanDistance(cur);
                    }
                    if (distanceSum < target) {
                        area++;
                    }
                }
            }
            return area;
        }
    }
}