package com.mrexception.aoc2019.intcode;

import java.util.HashMap;
import java.util.Map;

public enum Opcode {
    ADD(1), MULT(2), INPUT(3), OUTPUT(4), HALT(99);

    private static final Map<Integer, Opcode> map = new HashMap<>();

    static {
        for (Opcode value : Opcode.values()) {
            map.put(value.code, value);
        }
    }

    private final int code;

    Opcode(int code) {
        this.code = code;
    }

    public static Opcode fromCode(int code) {
        return map.get(code);
    }
}
