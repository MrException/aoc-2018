package com.mrexception;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;

public class Point {
    public static final Point ORIGIN = new Point(0, 0);
    public static final Point UP = new Point(0, -1);
    public static final Point DOWN = new Point(0, 1);
    public static final Point LEFT = new Point(-1, 0);
    public static final Point RIGHT = new Point(1, 0);
    public int x;
    public int y;

    public Point velocity;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return x + "," + y;
    }

    public Point up() {
        return new Point(x, y + 1);
    }

    public Point down() {
        return new Point(x, y - 1);
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public int manhattanDistance() {
        return manhattanDistance(ORIGIN);
    }

    public int manhattanDistance(Point b) {
        return abs(x - b.x) + abs(y - b.y);
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

    public void setVelocity(int dx, int dy) {
        velocity = new Point(dx, dy);
    }

    public void setVelocity(Point v) {
        velocity = v;
    }

    public void move() {
        this.x += velocity.x;
        this.y += velocity.y;
    }

    public void turnLeft() {
        if (velocity.equals(UP)) {
            velocity = LEFT;
        } else if (velocity.equals(DOWN)) {
            velocity = RIGHT;
        } else if (velocity.equals(LEFT)) {
            velocity = DOWN;
        } else if (velocity.equals(RIGHT)) {
            velocity = UP;
        }
    }

    public void turnRight() {
        if (velocity.equals(UP)) {
            velocity = RIGHT;
        } else if (velocity.equals(DOWN)) {
            velocity = LEFT;
        } else if (velocity.equals(LEFT)) {
            velocity = UP;
        } else if (velocity.equals(RIGHT)) {
            velocity = DOWN;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
