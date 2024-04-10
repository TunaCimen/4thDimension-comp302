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
        float length = (float) Math.sqrt(x * x + y * y);
        if (length != 0) {
            return new Vector(x / length, y / length);
        }
        return this; // this could only be the zero vector anyways
    }


}
