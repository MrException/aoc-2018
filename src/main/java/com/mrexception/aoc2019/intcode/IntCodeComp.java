package com.mrexception.aoc2019.intcode;

import java.util.ArrayList;
import java.util.List;

public class IntCodeComp {
    private final int[] program;
    private int[] memory;
    private int ptr;
    private List<Integer> outputs = new ArrayList<>();

    public IntCodeComp(int[] program) {
        this.program = program;
        this.reboot();
    }

    protected Instruction nextInstruction() {
        Instruction instruction = new Instruction(memory, ptr);
        ptr += instruction.getLength();
        return instruction;
    }

    public void reboot() {
        ptr = 0;
        memory = new int[program.length];
        System.arraycopy(program, 0, memory, 0, program.length);
    }

    public void run() {
        Instruction next = nextInstruction();
        while (next.getOpcode() != Opcode.HALT) {
            next.exec(memory);
            next = nextInstruction();
        }
    }

    public void runWithInput(int noun, int verb) {
        memory[1] = noun;
        memory[2] = verb;
        run();
    }

    public List<Integer> runWithInput(int[] inputs) {
        int nextInput = 0;
        Instruction next = nextInstruction();
        while (next.getOpcode() != Opcode.HALT) {
            if (next.getOpcode() == Opcode.INPUT) {
                next.input(inputs[nextInput]);
                nextInput++;
            }
            int output = next.exec(memory);
            if (next.getOpcode() == Opcode.OUTPUT) {
                outputs.add(output);
            }
            next = nextInstruction();
        }
        return outputs;
    }

    public int valueAt(int address) {
        return memory[address];
    }
}
