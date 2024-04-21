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
        double length = magnitude();
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
    public boolean isZero() {
        return getX() == 0 && getY() == 0;
    }

    public boolean isSameDirectionX(Vector other) {
        return (other.getX() > 0 && getX() > 0) || (other.getX() < 0 && getX() < 0);
    }

    public boolean isSameDirectionY(Vector other) {
        return (other.getY() > 0 && getY() > 0) || (other.getY() < 0 && getY() < 0);
    }

    public String toString() {
        return x + ", " + y ;
    }
    public boolean isPerpendicular(Vector other) {
        // Two vectors are perpendicular if their dot product is zero
        return Math.abs(this.dotProduct(other)) < 1e-10;  // Use a small threshold to handle floating point precision issues
    }

    public Vector rotateVector(double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);
        return new Vector(
                getX() * cosTheta - getY() * sinTheta,
                getX() * sinTheta + getY() * cosTheta
        );
    }

    public double magnitude() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    public Vector getDirectionSignVector() {
        int xSign;
        int ySign;
        int x = (int) getX();
        int y = (int) getY();
        xSign = Integer.compare(x, 0);
        ySign = Integer.compare(y, 0);
        return new Vector(xSign, ySign);
    }
}
