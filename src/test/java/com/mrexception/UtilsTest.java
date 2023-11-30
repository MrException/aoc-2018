package com.mrexception;


import org.junit.jupiter.api.Test;

import static com.mrexception.Utils.splitLine;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {
    @Test
    public void testProcessLine1() {
        String[] strs = splitLine("1, 2, 3");
        assertThat(strs.length).isEqualTo(3);
        assertThat(strs[0]).isEqualTo("1");
        assertThat(strs[1]).isEqualTo("2");
        assertThat(strs[2]).isEqualTo("3");
    }

    @Test
    public void testProcessLine2() {
        String[] strs = splitLine("1 2   3");
        assertThat(strs.length).isEqualTo(3);
        assertThat(strs[0]).isEqualTo("1");
        assertThat(strs[1]).isEqualTo("2");
        assertThat(strs[2]).isEqualTo("3");
    }
}
