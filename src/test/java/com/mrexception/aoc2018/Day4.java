package com.mrexception.aoc2018;

import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day4 {
    private Logger log = LoggerFactory.getLogger(getClass().getName());
    private String inputFile = "com/mrexception/aoc2018/day4.txt";

    @Test
    public void testData() throws Exception {
        processFile(inputFile, lines -> {
            String data = lines.collect(Collectors.joining("\n"));
            assertThat(data.length()).isGreaterThan(0);
        });
    }

    @Test
    public void testPartOne() throws Exception {
        String[] testData = new String[]{
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:05] falls asleep",
                "[1518-11-01 00:25] wakes up",
                "[1518-11-01 00:30] falls asleep",
                "[1518-11-01 00:55] wakes up",
                "[1518-11-01 23:58] Guard #99 begins shift",
                "[1518-11-02 00:40] falls asleep",
                "[1518-11-02 00:50] wakes up",
                "[1518-11-03 00:05] Guard #10 begins shift",
                "[1518-11-03 00:24] falls asleep",
                "[1518-11-03 00:29] wakes up",
                "[1518-11-04 00:02] Guard #99 begins shift",
                "[1518-11-04 00:36] falls asleep",
                "[1518-11-04 00:46] wakes up",
                "[1518-11-05 00:03] Guard #99 begins shift",
                "[1518-11-05 00:45] falls asleep",
                "[1518-11-05 00:55] wakes up"
        };
        List<String> testDataList = Arrays.asList(testData);
        Collections.shuffle(testDataList);
        assertThat(new Logic(testDataList).partOne()).isEqualTo(240);

        assertThat(new Logic(Arrays.asList(processFile(inputFile))).partOne()).isEqualTo(12169);
    }

    @Test
    public void testPartTwo() throws Exception {
        String[] testData = new String[]{
                "[1518-11-01 00:00] Guard #10 begins shift",
                "[1518-11-01 00:05] falls asleep",
                "[1518-11-01 00:25] wakes up",
                "[1518-11-01 00:30] falls asleep",
                "[1518-11-01 00:55] wakes up",
                "[1518-11-01 23:58] Guard #99 begins shift",
                "[1518-11-02 00:40] falls asleep",
                "[1518-11-02 00:50] wakes up",
                "[1518-11-03 00:05] Guard #10 begins shift",
                "[1518-11-03 00:24] falls asleep",
                "[1518-11-03 00:29] wakes up",
                "[1518-11-04 00:02] Guard #99 begins shift",
                "[1518-11-04 00:36] falls asleep",
                "[1518-11-04 00:46] wakes up",
                "[1518-11-05 00:03] Guard #99 begins shift",
                "[1518-11-05 00:45] falls asleep",
                "[1518-11-05 00:55] wakes up"
        };
        List<String> testDataList = Arrays.asList(testData);
        Collections.shuffle(testDataList);
        assertThat(new Logic(testDataList).partTwo()).isEqualTo(4455);

        assertThat(new Logic(Arrays.asList(processFile(inputFile))).partTwo()).isEqualTo(16164);
    }

    class Logic {
        private final List<Record> data;

        Logic(List<String> data) {
            this.data = new ArrayList<>();
            for (String str : data) {
                this.data.add(new Record(str));
            }
        }

        int partOne() {
            Map<Integer, int[]> guardMins = buildGuardMinMap();
            int mostMins = -1;
            int bestGuard = -1;
            int bestMin = -1;
            for (Map.Entry<Integer, int[]> guardMin : guardMins.entrySet()) {
                int curBestMinVal = -1;
                int curBestMin = -1;
                int curTotalMin = 0;
                for (int i = 0; i < guardMin.getValue().length; i++) {
                    int j = guardMin.getValue()[i];
                    curTotalMin += j;
                    if (j > curBestMinVal) {
                        curBestMinVal = j;
                        curBestMin = i;
                    }
                }
                if (curTotalMin > mostMins) {
                    mostMins = curTotalMin;
                    bestGuard = guardMin.getKey();
                    bestMin = curBestMin;
                }
            }
            return bestGuard * bestMin;
        }

        int partTwo() {
            Map<Integer, int[]> guardMins = buildGuardMinMap();
            int bestMinVal = -1;
            int bestGuard = -1;
            int bestMin = -1;
            for (Map.Entry<Integer, int[]> guardMin : guardMins.entrySet()) {

                int curBestMinVal = -1;
                int curBestMin = -1;
                for (int i = 0; i < guardMin.getValue().length; i++) {
                    int j = guardMin.getValue()[i];
                    if (j > curBestMinVal) {
                        curBestMinVal = j;
                        curBestMin = i;
                    }
                }
                if (curBestMinVal > bestMinVal) {
                    bestGuard = guardMin.getKey();
                    bestMin = curBestMin;
                    bestMinVal = curBestMinVal;
                }
            }
            return bestGuard * bestMin;
        }

        private Map<Integer, int[]> buildGuardMinMap() {
            PriorityQueue<Record> queue = new PriorityQueue<>(data.size(), Record::compareTo);
            queue.addAll(data);

            Record cur = queue.poll();
            Map<Integer, int[]> guardMins = new HashMap<>();
            int sleepStart = -1;
            int sleepEnd = -1;
            int curGuardId = -1;
            while (cur != null) {
                log.info("Date: {}, minute: {}, guard: {}, sleep: {}, awake: {}", cur.dateStr(), cur.minute, cur.guardId, cur.sleep, cur.awake);
                if (cur.guardId > -1) {
                    curGuardId = cur.guardId;
                }
                if (cur.sleep) {
                    sleepStart = cur.minute;
                }
                if (cur.awake) {
                    sleepEnd = cur.minute;
                    int[] minArr = guardMins.getOrDefault(curGuardId, new int[60]);
                    guardMins.put(curGuardId, minArr);
                    for (int i = sleepStart; i < sleepEnd; i++) {
                        minArr[i]++;
                    }
                }
                cur = queue.poll();
            }
            return guardMins;
        }

    }

    @Data
    class Record implements Comparable<Record> {
        LocalDateTime time;
        int guardId = -1;
        int minute = -1;
        boolean sleep = false;
        boolean awake = false;

        Record(String str) {
            String[] split = splitLine(str, " ");
            String timeStr = split[1].replace("]", "");
            minute = Integer.parseInt(splitLine(timeStr, ":")[1]);
            String dateStr = split[0].replace("[", "") + " " + timeStr;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            time = LocalDateTime.parse(dateStr, formatter);
            if ("Guard".equals(split[2])) {
                guardId = Integer.parseInt(split[3].replace("#", ""));
            } else if ("falls".equals(split[2])) {
                sleep = true;
            } else if ("wakes".equals(split[2])) {
                awake = true;
            } else {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public int compareTo(Record o) {
            return this.time.compareTo(o.time);
        }

        public String dateStr() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return formatter.format(time);
        }
    }
}