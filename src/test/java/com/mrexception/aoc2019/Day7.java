package com.mrexception.aoc2019;

import com.mrexception.aoc2019.intcode.IntCodeComp;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void testPartTwo() throws Exception {
        final int[] testProg1 = toInts(splitLine("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"));
        final int[] testProg2 = toInts(splitLine("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"));

        assertThat(partTwo(testProg1)).isEqualTo(139629729);
        assertThat(partTwo(testProg2)).isEqualTo(18216);

        final String progLine = processFile(inputFile)[0];
        final int[] program = toInts(splitLine(progLine));
        assertThat(partTwo(program)).isEqualTo(39431233);
    }

    private int partOne(int[] program) {
        final IntCodeComp comp = new IntCodeComp(program);
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

    private int partTwo(int[] program) {
        IntCodeComp a, b, c, d, e;
        int max = Integer.MIN_VALUE;

        List<List<Integer>> permutations = permutations(new Integer[]{5, 6, 7, 8, 9});
        for (List<Integer> phases : permutations) {
            a = new IntCodeComp(program).boot();
            a.inputs.offer(phases.get(0));
            a.inputs.offer(0);
            b = new IntCodeComp(program).boot();
            b.inputs.offer(phases.get(1));
            c = new IntCodeComp(program).boot();
            c.inputs.offer(phases.get(2));
            d = new IntCodeComp(program).boot();
            d.inputs.offer(phases.get(3));
            e = new IntCodeComp(program).boot();
            e.inputs.offer(phases.get(4));

            int output;
            while (true) {
                a.main();
                b.inputs.offer(a.outputs.poll());
                b.main();
                c.inputs.offer(b.outputs.poll());
                c.main();
                d.inputs.offer(c.outputs.poll());
                d.main();
                e.inputs.offer(d.outputs.poll());
                boolean done = e.main();
                output = e.outputs.poll();
                if (done) {
                    if (output > max) {
                        max = output;
                    }
                    break;
                } else {
                    a.inputs.offer(output);
                }
            }
        }
        return max;
    }
}
