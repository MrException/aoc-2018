package com.mrexception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class PathFindingTest {
    private MapNode start;
    private MapNode end;

    Function<Character, MapNode> mapNodeCreator = c -> {
        MapNode n = null;
        switch (c) {
            case '#':
                n = new MapNode(Integer.MAX_VALUE);
                break;
            case '.':
                n = new MapNode(0);
                break;
            case 'S':
                n = new MapNode(0);
                start = n;
                break;
            case 'E':
                n = new MapNode(0);
                end = n;
                break;
        }
        return n;
    };

    @BeforeEach
    public void reset() {
        start = null;
        end = null;
    }

    // @Test
    // Thid doesn't work yet
    public void testDoAStar() {
        String[] testMap = new String[]{
                "########",
                "#S.....#",
                "#......#",
                "#......#",
                "#.....E#",
                "########",
        };

        Map2D map = MapParser.parse(testMap, mapNodeCreator);
        assertThat(start).isNotNull();
        assertThat(end).isNotNull();

        List<MapNode> path = PathFinding.doAStar(map, start, end);
        assertThat(path.size()).isEqualTo(7);
    }

}
