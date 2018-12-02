package com.mrexception;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.mrexception.Utils.processLine;
import static org.assertj.core.api.Assertions.assertThat;

public class UtilsTest {
    @Test
    public void testProcessLine1() {
        List<String> strs = processLine("1, 2, 3").collect(Collectors.toList());
        assertThat(strs.size()).isEqualTo(3);
        assertThat(strs.get(0)).isEqualTo("1");
        assertThat(strs.get(1)).isEqualTo("2");
        assertThat(strs.get(2)).isEqualTo("3");
    }

    @Test
    public void testProcessLine2() {
        List<String> strs = processLine("1 2   3").collect(Collectors.toList());
        assertThat(strs.size()).isEqualTo(3);
        assertThat(strs.get(0)).isEqualTo("1");
        assertThat(strs.get(1)).isEqualTo("2");
        assertThat(strs.get(2)).isEqualTo("3");
    }
}
