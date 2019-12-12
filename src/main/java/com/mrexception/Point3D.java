package com.mrexception;

import java.util.Objects;

public class Point3D {
    public int x;
    public int y;
    public int z;

    public Point3D velocity;

    public Point3D(int x, int y, int z) {
        this(x, y, z, true);
    }

    public Point3D(int x, int y, int z, boolean initVel) {
        this.x = x;
        this.y = y;
        this.z = z;
        if (initVel) {
            velocity = new Point3D(0, 0, 0, false);
        }
    }

    public String toString() {
        return String.format("pos=<x=%s, y=%s, z=%s>, vel=<x=%s, y=%s, z=%s>", x, y, z, velocity.x, velocity.y, velocity.z);
    }

    public void setVelocity(int dx, int dy, int dz) {
        velocity = new Point3D(dx, dy, dz);
    }

    public void setVelocity(Point3D v) {
        velocity = v;
    }

    public void move() {
        this.x += velocity.x;
        this.y += velocity.y;
        this.z += velocity.z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point3D point = (Point3D) o;
        return x == point.x &&
                y == point.y &&
                z == point.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash("x", x, "y", y, "z", z, "vx", velocity.x, "vy", velocity.y, "vz", velocity.z);
    }
}
