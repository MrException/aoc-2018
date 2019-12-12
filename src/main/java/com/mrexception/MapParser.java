package com.mrexception;

import java.util.function.Function;

public class MapParser {
    public static Map2D parse(String[] lines, Function<Character, MapNode> mapNodeCreator) {
        MapNode[][] map = new MapNode[lines[0].length()][lines.length];

        for (int y = 0; y < lines.length; y++) {
            char[] chars = lines[y].toCharArray();
            for (int x = 0; x < chars.length; x++) {
                MapNode node = mapNodeCreator.apply(chars[x]);
                node.x = x;
                node.y = y;
                map[x][y] = node;

            }
        }

        return new Map2D(map);
    }
}
