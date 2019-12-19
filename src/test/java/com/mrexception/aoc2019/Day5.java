package com.mrexception.aoc2019;

import com.mrexception.aoc2019.intcode.IntCodeComp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day5 {
    private final String inputFile = "com/mrexception/aoc2019/day5.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        final String testInput = "1002,4,3,4,33";
        int[] testProg = toInts(splitLine(testInput));
        final IntCodeComp testComp = new IntCodeComp(testProg);
        testComp.run();
        assertThat(testComp.valueAt(0)).isEqualTo(1002);
        assertThat(testComp.valueAt(4)).isEqualTo(99);

        final String progLine = processFile(inputFile)[0];
        final int[] program = toInts(splitLine(progLine));
        final IntCodeComp computer = new IntCodeComp(program);
        assertThat(partOne(computer)).isEqualTo(11193703);
    }

    @Test
    public void testPartTwo() throws Exception {
        // final String progLine = processFile(inputFile)[0];
        // final int[] program = toInts(splitLine(progLine));
        // final IntCodeComp computer = new IntCodeComp(program);
        // assertThat(partTwo(computer)).isEqualTo(7960);
    }

    int partOne(final IntCodeComp computer) {
        List<Integer> outputs = computer.runWithInput(new int[]{1});
        return outputs.get(outputs.size() - 1);
    }

    int partTwo(final IntCodeComp computer) {
        final int desired = 19690720;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                computer.reboot();
                computer.runWithInput(i, j);
                final int output = computer.valueAt(0);
                if (output == desired) {
                    return (100 * i) + j;
                }
            }
        }
        return -1;
    }

}
