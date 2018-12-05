package com.mrexception;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {
    public static final Point ORIGIN = new Point( 0, 0 );
    private final int x;
    private final int y;

    public Point( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public Point up() {
        return new Point( x, y + 1 );
    }

    public Point down() {
        return new Point( x, y - 1 );
    }

    public Point left() {
        return new Point( x - 1, y );
    }

    public Point right() {
        return new Point( x + 1, y );
    }

    public int manhattanDistance() {
        return manhattanDistance( ORIGIN );
    }

    public int manhattanDistance( Point b ) {
        return abs( x - b.x ) + abs( y - b.y );
    }

    public List<Point> neighbour4() {
        var points = new ArrayList<Point>(8);
        points.add(right());
        points.add(up());
        points.add(left());
        points.add(down());
        return points;
    }

    public List<Point> neighbour8() {
        var points = new ArrayList<Point>(8);
        points.add(right());
        points.add(right().up());
        points.add(up());
        points.add(up().left());
        points.add(left());
        points.add(left().down());
        points.add(down());
        points.add(down().right());
        return points;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x &&
          y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash( x, y );
    }
}
