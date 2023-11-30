package com.mrexception.aoc2018;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.mrexception.Utils.processFile;
import static com.mrexception.Utils.splitLine;
import static org.assertj.core.api.Assertions.assertThat;

public class Day7 {
    private String inputFile = "com/mrexception/aoc2018/day7.txt";

    private String[] testData = new String[]{
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."
    };

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(new Logic(testData).partOne()).isEqualTo("CABDFE");

        assertThat(new Logic(processFile(inputFile)).partOne()).isEqualTo("IOFSJQDUWAPXELNVYZMHTBCRGK");
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(new Logic(testData).partTwo(2, 0)).isEqualTo(15);

        assertThat(new Logic(processFile(inputFile)).partTwo(5, 60)).isEqualTo(931);
    }

    class Logic {
        private final Map<Character, Set<Character>> stepsToDo = new HashMap<>();
        private final SortedSet<Character> allSteps = new TreeSet<>();
        private final List<Character> stepsCompleted = new ArrayList<>();

        Logic(String[] data) {
            // key requires set
            for (String line : data) {
                String[] strs = splitLine(line);
                Character step = strs[7].charAt(0);
                Character required = strs[1].charAt(0);
                allSteps.add(required);
                allSteps.add(step);
                Set<Character> requirements = stepsToDo.getOrDefault(step, new HashSet<>());
                requirements.add(required);
                stepsToDo.put(step, requirements);
            }
        }

        String partOne() {
            while (!stepsToDo.isEmpty()) {
                SortedSet<Character> remaining = new TreeSet<>();
                for (Character i : allSteps) {
                    if (!stepsCompleted.contains(i)) {
                        remaining.add(i);
                    }
                }
                for (Character i : remaining) {
                    if (stepsToDo.containsKey(i)) {
                        Set<Character> requirements = stepsToDo.get(i);
                        for (Character c : stepsCompleted) {
                            requirements.remove(c);
                        }
                        if (requirements.isEmpty()) {
                            stepsCompleted.add(i);
                            stepsToDo.remove(i);
                            break;
                        }
                    } else {
                        stepsCompleted.add(i);
                        break;
                    }
                }
            }

            return result();
        }

        int partTwo(int workerCount, int stepTime) {
            Map<Character, Integer> workers = new HashMap<>();
            int totalTime = 0;
            while (stepsCompleted.size() != allSteps.size()) {
                SortedSet<Character> notStarted = new TreeSet<>();
                for (Character i : allSteps) {
                    if (!stepsCompleted.contains(i) && !workers.containsKey(i)) {
                        notStarted.add(i);
                    }
                }

                boolean added = false;
                for (Character i : notStarted) {
                    if (stepsToDo.containsKey(i)) {
                        Set<Character> requirements = stepsToDo.get(i);
                        for (Character c : stepsCompleted) {
                            requirements.remove(c);
                        }
                        if (requirements.isEmpty()) {
                            workers.put(i, requiredTime(i, stepTime));
                            stepsToDo.remove(i);
                            added = true;
                            break;
                        }
                    } else {
                        workers.put(i, requiredTime(i, stepTime));
                        added = true;
                        break;
                    }
                }

                if (!added || workers.size() >= workerCount) {
                    int closestTime = Integer.MAX_VALUE;
                    for (Integer time : workers.values()) {
                        if (time < closestTime) {
                            closestTime = time;
                        }
                    }

                    Map<Character, Integer> newWorkers = new HashMap<>();
                    for (Map.Entry<Character, Integer> worker : workers.entrySet()) {
                        Integer timeLeft = worker.getValue();
                        timeLeft -= closestTime;
                        if (timeLeft == 0) {
                            stepsCompleted.add(worker.getKey());
                        } else {
                            newWorkers.put(worker.getKey(), timeLeft);
                        }
                    }
                    workers = newWorkers;
                    totalTime += closestTime;
                }
            }
            return totalTime;
        }

        private int requiredTime(char i, int stepTime) {
            return stepTime + (i - 'A' + 1);
        }

        private String result() {
            StringBuilder str = new StringBuilder(26);
            for (Character c : stepsCompleted) {
                str.append(c);
            }
            return str.toString();

        }
    }
}