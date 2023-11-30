package com.mrexception.aoc2019;

import com.mrexception.aoc2019.intcode.IntCodeComp;
import org.junit.jupiter.api.Test;

import static com.mrexception.Utils.*;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(computer.run(1)).isEqualTo(11193703);
    }

    @Test
    public void testPartTwo() throws Exception {
        final int[] testProg1 = toInts(splitLine("3,9,8,9,10,9,4,9,99,-1,8"));
        final IntCodeComp testComp1 = new IntCodeComp(testProg1);
        final int[] testProg2 = toInts(splitLine("3,9,7,9,10,9,4,9,99,-1,8"));
        final IntCodeComp testComp2 = new IntCodeComp(testProg2);
        final int[] testProg3 = toInts(splitLine("3,3,1108,-1,8,3,4,3,99"));
        final IntCodeComp testComp3 = new IntCodeComp(testProg3);
        final int[] testProg4 = toInts(splitLine("3,3,1107,-1,8,3,4,3,99"));
        final IntCodeComp testComp4 = new IntCodeComp(testProg4);

        assertThat(testComp1.run(8)).isEqualTo(1);
        assertThat(testComp1.run(7)).isEqualTo(0);
        assertThat(testComp1.run(9)).isEqualTo(0);

        assertThat(testComp2.run(8)).isEqualTo(0);
        assertThat(testComp2.run(7)).isEqualTo(1);
        assertThat(testComp2.run(9)).isEqualTo(0);

        assertThat(testComp3.run(8)).isEqualTo(1);
        assertThat(testComp3.run(7)).isEqualTo(0);
        assertThat(testComp3.run(9)).isEqualTo(0);

        assertThat(testComp4.run(8)).isEqualTo(0);
        assertThat(testComp4.run(7)).isEqualTo(1);
        assertThat(testComp4.run(9)).isEqualTo(0);

        final int[] testProg5 = toInts(splitLine("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9"));
        final IntCodeComp testComp5 = new IntCodeComp(testProg5);
        final int[] testProg6 = toInts(splitLine("3,3,1105,-1,9,1101,0,0,12,4,12,99,1"));
        final IntCodeComp testComp6 = new IntCodeComp(testProg6);
        final int[] testProg7 = toInts(splitLine("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"));
        final IntCodeComp testComp7 = new IntCodeComp(testProg7);

        assertThat(testComp5.run(0)).isEqualTo(0);
        assertThat(testComp5.run(100)).isEqualTo(1);

        assertThat(testComp6.run(0)).isEqualTo(0);
        assertThat(testComp6.run(100)).isEqualTo(1);

        assertThat(testComp7.run(0)).isEqualTo(999);
        assertThat(testComp7.run(8)).isEqualTo(1000);
        assertThat(testComp7.run(100)).isEqualTo(1001);

        final String progLine = processFile(inputFile)[0];
        final int[] program = toInts(splitLine(progLine));
        final IntCodeComp computer = new IntCodeComp(program);
        assertThat(computer.run(5)).isEqualTo(12410607);
    }
}
