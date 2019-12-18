package com.mrexception.aoc2019.intcode;

public enum Opcode {
    ADD(1), MULT(2), HALT(99);

    private final int code;

    Opcode(int code) {
        this.code = code;
    }
}
