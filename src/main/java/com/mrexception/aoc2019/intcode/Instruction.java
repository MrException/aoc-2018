package com.mrexception.aoc2019.intcode;

import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
@FieldDefaults(makeFinal = false)
public class Instruction {

    private final Opcode opcode;
    private final int arg1Mode;
    private final int arg1Ptr;
    private final int arg2Mode;
    private final int arg2Ptr;
    private final int outPtr;
    private final int length;

    private Integer input = null;

    public Instruction(int[] memory, int ptr) {
        int i = memory[ptr];
        int code;
        if (i > 99) {
            code = i % 100;
            arg1Mode = (i / 100) % 10;
            arg2Mode = (i / 1000);
        } else {
            code = i;
            arg1Mode = 0;
            arg2Mode = 0;
        }
        this.opcode = Opcode.fromCode(code);
        switch (opcode) {
            case ADD:
            case MULT:
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
            default:
                throw new IllegalStateException("Illegal opcode: " + opcode);
        }
    }

    public int exec(int[] memory) {
        switch (opcode) {
            case ADD:
                memory[outPtr] =
                        (arg1Mode == 1 ? arg1Ptr : memory[arg1Ptr])
                                + (arg2Mode == 1 ? arg2Ptr : memory[arg2Ptr]);
                break;
            case MULT:
                memory[outPtr] =
                        (arg1Mode == 1 ? arg1Ptr : memory[arg1Ptr])
                                * (arg2Mode == 1 ? arg2Ptr : memory[arg2Ptr]);
                break;
            case INPUT:
                if (input == null) {
                    throw new IllegalStateException("Input not provided!");
                }
                memory[outPtr] = input;
                break;
            case OUTPUT:
                int output = memory[outPtr];
                log.info("Output: {}", output);
                return output;
            default:
                throw new IllegalStateException("Opcode not executable: " + opcode);
        }
        return -1;
    }

    public void input(int input) {
        this.input = input;
    }
}
