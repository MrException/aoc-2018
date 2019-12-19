package com.mrexception.aoc2019.intcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class IntCodeComp {
    private final int[] program;
    private int[] memory;
    private int ptr;
    public final Deque<Integer> inputs = new ArrayDeque<>();
    public final Deque<Integer> outputs = new ArrayDeque<>();

    public IntCodeComp(int[] program) {
        this.program = program;
    }

    public IntCodeComp boot() {
        ptr = 0;
        memory = new int[program.length];
        System.arraycopy(program, 0, memory, 0, program.length);
        return this;
    }

    public int runWithInput(int noun, int verb) {
        program[1] = noun;
        program[2] = verb;
        boot();
        main();
        return defaultOutput();
    }

    private int defaultOutput() {
        if (outputs.isEmpty()) {
            return memory[0];
        }

        return outputs.removeLast();
    }

    protected Instruction nextInstruction() {
        return new Instruction(memory, ptr);
    }

    public int run(int... in) {
        boot();
        for (int input : in) {
            inputs.offer(input);
        }
        main();
        return defaultOutput();
    }

    public boolean main() {
        Instruction next = nextInstruction();
        while (next.getOpcode() != Opcode.HALT) {
            Integer input = null;
            if (next.getOpcode() == Opcode.INPUT) {
                input = inputs.poll();
                if (input == null) {
                    return false; // not complete - pause
                }
            }

            int output = next.exec(memory, input);

            if (next.getOpcode() == Opcode.OUTPUT) {
                outputs.offer(output);
            }
            if (output > -1 && (next.getOpcode() == Opcode.JIT || next.getOpcode() == Opcode.JIF)) {
                ptr = output;
            } else {
                ptr += next.getLength();
            }
            next = nextInstruction();
        }
        return true;
    }

    public int valueAt(int address) {
        return memory[address];
    }
}
