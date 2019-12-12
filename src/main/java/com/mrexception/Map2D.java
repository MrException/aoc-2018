package com.mrexception;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map2D {
    public final MapNode[][] grid;

    public Map2D(MapNode[][] grid) {
        this.grid = grid;
    }

    public Set<MapNode> getNeighbours(MapNode n) {
        Set<MapNode> neighbours = new HashSet<>();

        List<Point> possible = n.neighbour4();
        for (Point p : possible) {
            if (!(p.x < 0
                    || p.x > grid.length
                    || p.y < 0
                    || p.y > grid[0].length)) {
                neighbours.add(grid[p.x][p.y]);
            }
        }
        return neighbours;
    }
}
