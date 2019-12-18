package com.mrexception.aoc2019.intcode;

import lombok.Value;

@Value
public class Instruction {

    private final Opcode opcode;
    private final int arg1Ptr;
    private final int arg2Ptr;
    private final int outPtr;
    private final int length;

    public Instruction(int[] memory, int ptr) {
        int opcode = memory[ptr];
        switch (opcode) {
            case 1:
                this.opcode = Opcode.ADD;
                arg1Ptr = memory[ptr + 1];
                arg2Ptr = memory[ptr + 2];
                outPtr = memory[ptr + 3];
                length = 4;
                break;
            case 2:
                this.opcode = Opcode.MULT;
                arg1Ptr = memory[ptr + 1];
                arg2Ptr = memory[ptr + 2];
                outPtr = memory[ptr + 3];
                length = 4;
                break;
            case 99:
                this.opcode = Opcode.HALT;
                arg1Ptr = -1;
                arg2Ptr = -1;
                outPtr = -1;
                length = 1;
                break;
            default:
                throw new IllegalStateException("Illegal opcode: " + opcode);
        }
    }

    public void exec(int[] memory) {
        switch (opcode) {
            case ADD:
                memory[outPtr] = memory[arg1Ptr] + memory[arg2Ptr];
                break;
            case MULT:
                memory[outPtr] = memory[arg1Ptr] * memory[arg2Ptr];
                break;
            default:
                throw new IllegalStateException("Opcode not executable: " + opcode);
        }
    }
}
