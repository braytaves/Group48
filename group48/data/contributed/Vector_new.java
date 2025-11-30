package com.javagame.utils;

import java.awt.Point;

public class Vector {
    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector opposite() {
        return new Vector(-x, -y);
    }
    public Vector mul(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector normalize() {
        double len = Math.sqrt(x * x + y * y);
        return len == 0 ? new Vector(0, 0) : new Vector(x / len, y / len);
    }

    public Vector perpendicular() {
        return new Vector(-y, x); // 90° CCW rotation
    }

    public Point roundToPoint() {
        return new Point((int) Math.round(x), (int) Math.round(y));
    }

    public Vector roundToVector() {
        return new Vector(Math.round(x), Math.round(y));
    }
    

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public String   toString  () {
        return "(" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ")";
    }
}
