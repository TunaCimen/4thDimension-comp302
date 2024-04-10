package org.LanceOfDestiny.domain.physics;

public class Vector {
    private float x;
    private float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector getNormalized() {
        float length = (float) Math.sqrt(getX() * getX() + getY() * getY());
        if (length != 0) {
            return new Vector(getX() / length, getY() / length);
        }
        return this; // this could only be the zero vector anyways
    }


    public float distanceTo(Vector pos2) {
        return (float) Math.sqrt(Math.pow(getX() - pos2.getX(), 2) + Math.pow(getY() - pos2.getY(), 2));
    }

    public Vector add(Vector pos2) {
        return new Vector(getX() + pos2.getX(), getY() + pos2.getY());
    }

    public Vector subtract(Vector pos2) {
        return new Vector(getX() - pos2.getX(), getY() - pos2.getY());
    }

    public Vector scale(float ratio) {
        return new Vector(getX() * ratio, getY() * ratio);
    }
}
