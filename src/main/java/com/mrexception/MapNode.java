package com.mrexception;

import java.util.Objects;

public class MapNode extends Point {
    private int value; // how hard it is to get to this point

    public MapNode(int x, int y) {
        super(x, y);
        this.value = 0;
    }

    public MapNode(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    public MapNode(int value) {
        super(-1, -1);
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getTraversalCost(MapNode neighbour) {
        return 1 + neighbour.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MapNode mapNode = (MapNode) o;
        return value == mapNode.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }
}
