package com.mrexception.aoc2019.intcode;

import lombok.Value;

@Value
public class Instruction {

    private final Opcode opcode;
    private final int arg1Ptr;
    private final int arg2Ptr;
    private final int outPtr;
    private final int mode1;
    private final int mode2;
    private final int length;

    public Instruction(int[] memory, int ptr) {
        int i = memory[ptr];
        int code;
        if (i > 99) {
            code = i % 100;
            mode1 = (i / 100) % 10;
            mode2 = (i / 1000);
        } else {
            code = i;
            mode1 = 0;
            mode2 = 0;
        }
        this.opcode = Opcode.fromCode(code);
        switch (opcode) {
            case ADD:
            case MULT:
            case LT:
            case EQ:
                length = 4;
                arg1Ptr = memory[ptr + 1];
                arg2Ptr = memory[ptr + 2];
                outPtr = memory[ptr + 3];
                break;

            case INPUT:
            case OUTPUT:
                length = 2;
                arg1Ptr = -1;
                arg2Ptr = -1;
                outPtr = memory[ptr + 1];
                break;

            case HALT:
                arg1Ptr = -1;
                arg2Ptr = -1;
                outPtr = -1;
                length = 1;
                break;

            case JIT:
            case JIF:
                length = 3;
                arg1Ptr = memory[ptr + 1];
                arg2Ptr = memory[ptr + 2];
                outPtr = -1;
                break;

            default:
                throw new IllegalStateException("Illegal opcode: " + opcode);
        }
    }

    public int exec(int[] memory, Integer input) {
        switch (opcode) {
            case ADD:
                add(memory);
                break;
            case MULT:
                mult(memory);
                break;
            case INPUT:
                input(memory, input);
                break;
            case OUTPUT:
                return output(memory);
            case JIT:
                return jumpTrue(memory);
            case JIF:
                return jumpFalse(memory);
            case LT:
                lessThan(memory);
                break;
            case EQ:
                eq(memory);
                break;
            default:
                throw new IllegalStateException("Opcode not executable: " + opcode);
        }
        return -1;
    }

    private void eq(int[] memory) {
        boolean eq = (mode1 == 1 ? arg1Ptr : memory[arg1Ptr])
                == (mode2 == 1 ? arg2Ptr : memory[arg2Ptr]);
        memory[outPtr] = eq ? 1 : 0;
    }

    private void lessThan(int[] memory) {
        boolean lt = (mode1 == 1 ? arg1Ptr : memory[arg1Ptr])
                < (mode2 == 1 ? arg2Ptr : memory[arg2Ptr]);
        memory[outPtr] = lt ? 1 : 0;
    }

    private int jumpFalse(int[] memory) {
        boolean isZero = (mode1 == 1 ? arg1Ptr : memory[arg1Ptr]) == 0;
        return isZero ? (mode2 == 1 ? arg2Ptr : memory[arg2Ptr]) : -1;
    }

    private int jumpTrue(int[] memory) {
        boolean isNonZero = (mode1 == 1 ? arg1Ptr : memory[arg1Ptr]) != 0;
        return isNonZero ? (mode2 == 1 ? arg2Ptr : memory[arg2Ptr]) : -1;
    }

    private int output(int[] memory) {
        return mode1 == 1 ? outPtr : memory[outPtr];
    }

    private void input(int[] memory, Integer input) {
        if (input == null) {
            throw new IllegalStateException("Input not provided!");
        }
        memory[outPtr] = input;
    }

    private void mult(int[] memory) {
        memory[outPtr] =
                (mode1 == 1 ? arg1Ptr : memory[arg1Ptr])
                        * (mode2 == 1 ? arg2Ptr : memory[arg2Ptr]);
    }

    private void add(int[] memory) {
        memory[outPtr] =
                (mode1 == 1 ? arg1Ptr : memory[arg1Ptr])
                        + (mode2 == 1 ? arg2Ptr : memory[arg2Ptr]);
    }
}
