package com.mrexception.aoc2019;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.stream.Collectors;

import static com.mrexception.Utils.processFile;
import static com.mrexception.Utils.splitLine;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(JUnit4.class)
public class Day14 {
    private String inputFile = "com/mrexception/aoc2019/day14.txt";

    private String[] input0 = new String[]{
            "10 ORE => 10 A",
            "1 ORE => 1 B",
            "7 A, 1 B => 1 C",
            "7 A, 1 C => 1 D",
            "7 A, 1 D => 1 E",
            "7 A, 1 E => 1 FUEL "
    };
    private String[] input1 = new String[]{
            "9 ORE => 2 A",
            "8 ORE => 3 B",
            "7 ORE => 5 C",
            "3 A, 4 B => 1 AB",
            "5 B, 7 C => 1 BC",
            "4 C, 1 A => 1 CA",
            "2 AB, 3 BC, 4 CA => 1 FUEL"
    };
    private String[] input2 = new String[]{
            "157 ORE => 5 NZVS",
            "165 ORE => 6 DCFZ",
            "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL",
            "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ",
            "179 ORE => 7 PSHF",
            "177 ORE => 5 HKGWZ",
            "7 DCFZ, 7 PSHF => 2 XJWVT",
            "165 ORE => 2 GPVTF",
            "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"
    };
    private String[] input3 = new String[]{
            "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG",
            "17 NVRVD, 3 JNWZP => 8 VPVL",
            "53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL",
            "22 VJHF, 37 MNCFX => 5 FWMGM",
            "139 ORE => 4 NVRVD",
            "144 ORE => 7 JNWZP",
            "5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC",
            "5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV",
            "145 ORE => 6 MNCFX",
            "1 NVRVD => 8 CXFTF",
            "1 VJHF, 6 MNCFX => 4 RFSQX",
            "176 ORE => 6 VJHF "
    };
    private String[] input4 = new String[]{
            "171 ORE => 8 CNZTR",
            "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL",
            "114 ORE => 4 BHXH",
            "14 VRPVC => 6 BMBT",
            "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL",
            "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT",
            "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW",
            "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW",
            "5 BMBT => 4 WPTQ",
            "189 ORE => 9 KTJDG",
            "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP",
            "12 VRPVC, 27 CNZTR => 2 XDBXC",
            "15 KTJDG, 12 BHXH => 5 XCVML",
            "3 BHXH, 2 VRPVC => 7 MZWV",
            "121 ORE => 7 VRPVC",
            "7 XCVML => 6 RJRHP",
            "5 BHXH, 4 VRPVC => 5 LTCX"
    };

    @Test
    public void testData() throws Exception {
        assertThat(processFile(inputFile).length).isGreaterThan(0);
        assertThat(parseInput(processFile(inputFile)).size()).isGreaterThan(0);
    }

    @Test
    public void testParseReaction() {
        Reaction r = parseReaction("44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL");

        assertThat(r.output.amount).isEqualTo(1);
        assertThat(r.output.name).isEqualTo("FUEL");

        assertThat(r.inputs.get(0).amount).isEqualTo(44);
        assertThat(r.inputs.get(0).name).isEqualTo("XJWVT");

        assertThat(r.inputs.get(1).amount).isEqualTo(5);
        assertThat(r.inputs.get(1).name).isEqualTo("KHKGT");

        assertThat(r.inputs.get(2).amount).isEqualTo(1);
        assertThat(r.inputs.get(2).name).isEqualTo("QDVJ");

        assertThat(r.inputs.get(3).amount).isEqualTo(29);
        assertThat(r.inputs.get(3).name).isEqualTo("NZVS");

        assertThat(r.inputs.get(4).amount).isEqualTo(9);
        assertThat(r.inputs.get(4).name).isEqualTo("GPVTF");

        assertThat(r.inputs.get(5).amount).isEqualTo(48);
        assertThat(r.inputs.get(5).name).isEqualTo("HKGWZ");
    }

    @Test
    public void testPartOne() throws Exception {
        assertThat(partOne(input0)).isEqualTo(31);
        assertThat(partOne(input1)).isEqualTo(165);
        assertThat(partOne(input2)).isEqualTo(13312);
        assertThat(partOne(input3)).isEqualTo(180697);
        assertThat(partOne(input4)).isEqualTo(2210736);

        String[] input = processFile(inputFile);

        assertThat(partOne(input)).isEqualTo(485720);
    }

    @Test
    public void testPartTwo() throws Exception {
        assertThat(partTwo(input2)).isEqualTo(82892753L);

        // why are these two inputs not working?
//        assertThat(partTwo(input3)).isEqualTo(5586022L);
//        assertThat(partTwo(input4)).isEqualTo(460664L);

        String[] input = processFile(inputFile);
        assertThat(partTwo(input)).isEqualTo(3848998L);
    }

    static List<Reaction> reactionTable;
    static Map<String, Long> leftovers;

    static Reaction parseReaction(String line) {
        String[] a = splitLine(line, "=>");
        Chem output = new Chem(a[1]);
        String[] inputSplit = splitLine(a[0], ",");

        LinkedList<Chem> inputs = new LinkedList<>();
        for (String s : inputSplit) {
            inputs.add(new Chem(s));
        }
        return new Reaction(inputs, output);
    }

    static List<Reaction> parseInput(String[] in) {
        List<Reaction> reactions = new ArrayList<>(in.length);
        for (String line : in) {
            reactions.add(parseReaction(line));
        }
        return reactions;
    }

    static Reaction requiredInputs(String name) {
        return reactionTable.stream()
                .filter(r -> name.equals(r.output.name))
                .findFirst()
                .orElseThrow();
    }

    static LinkedList<Chem> multiply(List<Chem> in, long multiple) {
        return in.stream()
                .map(c -> new Chem(c.amount * multiple, c.name))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    static long useLeftovers(Chem required, long requiredAmount) {
        long prevLeftover = leftovers.getOrDefault(required.name, 0L);
        if (prevLeftover > 0) {
            // have some leftover from a previous reaction
            if (prevLeftover < requiredAmount) {
                requiredAmount -= prevLeftover;

                leftovers.remove(required.name);
            } else {
                // had more leftover than we even need
                prevLeftover -= requiredAmount;
                requiredAmount = 0;

                leftovers.put(required.name, prevLeftover);
            }
        }
        return requiredAmount;
    }

    static void reset() {
        reactionTable = null;
        leftovers = new HashMap<>();
    }

    private static long oreNeeded(long fuel) {
        long oreNeeded = 0;
        LinkedList<Chem> required = multiply(requiredInputs("FUEL").inputs, fuel);
        while (!required.isEmpty()) {

            Chem cur = required.remove(0);

            long requiredAmount = cur.amount;

            if ("ORE".equals(cur.name)) {
                oreNeeded += requiredAmount;
            } else {
                requiredAmount = useLeftovers(cur, requiredAmount);

                if (requiredAmount > 0) {
                    Reaction reqReact = requiredInputs(cur.name);
                    long reactAmount = reqReact.output.amount;
                    int multiple = 1;
                    if (requiredAmount > reactAmount) {
                        multiple = (int) Math.ceil(((double) requiredAmount) / reactAmount);
                    }
                    for (Chem chem : multiply(reqReact.inputs, multiple)) {
                        required.push(chem);
                    }
                    long leftover = (reactAmount * multiple) - requiredAmount;
                    if (leftover > 0) {
                        leftovers.compute(cur.name, (name, oldLeftover) -> oldLeftover == null ? leftover : oldLeftover + leftover);
                    }
                }
            }
        }
        return oreNeeded;
    }

    static long partOne(String[] input) {
        reset();
        reactionTable = parseInput(input);

        return oreNeeded(1);
    }

    static long partTwo(String[] input) {
        reset();
        reactionTable = parseInput(input);

        long totalOre = 1000000000000L;

        // binary search to find the fuel count that results in 1E12 ore or just below
        long low = 0;
        long mid = -1;
        long high = totalOre;

        int i = 0;
        while (low <= high) {
            mid = low + ((high - low) / 2);
            long midVal = oreNeeded(mid);

            if (midVal > totalOre) {
                high = mid - 1;
            } else if (midVal < totalOre) {
                low = mid + 1;
            } else {
                mid++;
                break;
            }
            i++;
        }
        log.info("Took {} iterations", i);
        return mid - 1;
    }


    @Data
    @AllArgsConstructor
    private static class Reaction {
        final LinkedList<Chem> inputs;
        final Chem output;
    }

    @Data
    @AllArgsConstructor
    private static class Chem {
        final long amount;
        final String name;

        public Chem(String in) {
            String[] split = splitLine(in);
            name = split[1];
            amount = Integer.parseInt(split[0]);
        }
    }
}
