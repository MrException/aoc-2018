package com.mrexception.aoc2019.intcode;

import java.util.ArrayList;
import java.util.List;

public class IntCodeComp {
    private final int[] program;
    private int[] memory;
    private int ptr;
    private final List<Integer> outputs = new ArrayList<>();

    public IntCodeComp(int[] program) {
        this.program = program;
    }

    public void boot() {
        ptr = 0;
        memory = new int[program.length];
        System.arraycopy(program, 0, memory, 0, program.length);
    }

    public int runWithInput(int noun, int verb) {
        program[1] = noun;
        program[2] = verb;
        return run();
    }

    protected Instruction nextInstruction() {
        Instruction instruction = new Instruction(memory, ptr);
        ptr += instruction.getLength();
        return instruction;
    }

    public int run(int... inputs) {
        boot();

        int nextInput = 0;
        Instruction next = nextInstruction();
        while (next.getOpcode() != Opcode.HALT) {
            Integer input = null;
            if (next.getOpcode() == Opcode.INPUT) {
                input = inputs[nextInput];
                nextInput++;
            }
            int output = next.exec(memory, input);
            if (next.getOpcode() == Opcode.OUTPUT) {
                outputs.add(output);
            } else if (next.getOpcode() == Opcode.JIT || next.getOpcode() == Opcode.JIF) {
                if (output > -1) {
                    ptr = output;
                }
            }
            next = nextInstruction();
        }

        if (outputs.isEmpty()) {
            return memory[0];
        }

        return outputs.get(outputs.size() - 1);
    }

    public int valueAt(int address) {
        return memory[address];
    }
}
