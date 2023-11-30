package com.mrexception.aoc2019;

import com.mrexception.aoc2019.intcode.IntCodeComp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class Day2 {
    private String inputFile = "com/mrexception/aoc2019/day2.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        String testInput1 = "1,9,10,3,2,3,11,0,99,30,40,50";
        int[] testProg1 = toInts(splitLine(testInput1));
        assertThat(partOne(new IntCodeComp(testProg1), -1, -1)).isEqualTo(3500);

        String testInput2 = "1,0,0,0,99";
        int[] testProg2 = toInts(splitLine(testInput2));
        assertThat(partOne(new IntCodeComp(testProg2), -1, -1)).isEqualTo(2);

        String testInput3 = "2,3,0,3,99";
        int[] testProg3 = toInts(splitLine(testInput3));
        assertThat(partOne(new IntCodeComp(testProg3), -1, -1)).isEqualTo(2);

        String testInput4 = "2,4,4,5,99,0";
        int[] testProg4 = toInts(splitLine(testInput4));
        assertThat(partOne(new IntCodeComp(testProg4), -1, -1)).isEqualTo(2);

        String testInput5 = "1,1,1,4,99,5,6,0,99";
        int[] testProg5 = toInts(splitLine(testInput5));
        assertThat(partOne(new IntCodeComp(testProg5), -1, -1)).isEqualTo(30);

        String progLine = processFile(inputFile)[0];
        int[] program = toInts(splitLine(progLine));
        IntCodeComp computer = new IntCodeComp(program);
        assertThat(partOne(computer, 12, 2)).isEqualTo(3224742);
    }

    @Test
    public void testPartTwo() throws Exception {
        String progLine = processFile(inputFile)[0];
        int[] program = toInts(splitLine(progLine));
        IntCodeComp computer = new IntCodeComp(program);
        assertThat(partTwo(computer)).isEqualTo(7960);
    }

    int partOne(IntCodeComp computer, int noun, int verb) {
        if (noun > -1 && verb > -1) {
            return computer.runWithInput(noun, verb);
        } else {
            return computer.run();
        }
    }

    int partTwo(IntCodeComp computer) {
        final int desired = 19690720;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                int output = computer.runWithInput(i, j);
                if (output == desired) {
                    return (100 * i) + j;
                }
            }
        }
        return -1;
    }

}
