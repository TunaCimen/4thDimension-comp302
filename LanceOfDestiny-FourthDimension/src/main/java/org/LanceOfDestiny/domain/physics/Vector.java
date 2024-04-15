package org.LanceOfDestiny.domain.physics;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector normalize() {
        double length = (double) Math.sqrt(getX() * getX() + getY() * getY());
        if (length != 0) {
            return new Vector(getX() / length, getY() / length);
        }
        return this; // this could only be the zero vector anyways
    }

    public Vector add(Vector pos2) {
        return new Vector(getX() + pos2.getX(), getY() + pos2.getY());
    }

    public Vector subtract(Vector pos2) {
        return new Vector(getX() - pos2.getX(), getY() - pos2.getY());
    }

    public Vector scale(double ratio) {
        return new Vector(getX() * ratio, getY() * ratio);
    }

    public static Vector getZeroVector() {
        return new Vector(0,0);
    }

    public double dotProduct(Vector v){
        return getX() * v.getX() + getY()* v.getY();
    }

    public Vector perpendicular() {
        return new Vector(-y, x); // Clockwise rotation
    }
    public static Vector getVelocityByAngleAndMagnitude(int speed, double angle) {
        angle += Math.PI / 2;
        double vx = speed * Math.cos(angle);
        double vy = speed * Math.sin(angle);
        return new Vector(vx,vy);
    }
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
