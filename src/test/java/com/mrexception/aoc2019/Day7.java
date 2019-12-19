package com.mrexception.aoc2019;

import com.mrexception.aoc2019.intcode.IntCodeComp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class Day7 {
    private final String inputFile = "com/mrexception/aoc2019/day7.txt";

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
    }

    @Test
    public void testPartOne() throws Exception {
        final int[] testProg1 = toInts(splitLine("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"));
        final int[] testProg2 = toInts(splitLine("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"));
        final int[] testProg3 = toInts(splitLine("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"));

        assertThat(partOne(testProg1)).isEqualTo(43210);
        assertThat(partOne(testProg2)).isEqualTo(54321);
        assertThat(partOne(testProg3)).isEqualTo(65210);

        final String progLine = processFile(inputFile)[0];
        final int[] program = toInts(splitLine(progLine));
        assertThat(partOne(program)).isEqualTo(398674);
    }

    private int partOne(int[] testProg1) {
        final IntCodeComp comp = new IntCodeComp(testProg1);
        int a, b, c, d, e;
        int max = Integer.MIN_VALUE;

        List<List<Integer>> permutations = permutations(new Integer[]{0, 1, 2, 3, 4});

        for (List<Integer> phases : permutations) {
            a = comp.run(phases.get(0), 0);
            b = comp.run(phases.get(1), a);
            c = comp.run(phases.get(2), b);
            d = comp.run(phases.get(3), c);
            e = comp.run(phases.get(4), d);

            if (e > max) {
                max = e;
            }
        }
        return max;
    }

    @Test
    public void testPartTwo() throws Exception {
    }
}
