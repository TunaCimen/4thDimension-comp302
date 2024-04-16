package org.LanceOfDestiny.domain.physics;

import org.LanceOfDestiny.domain.behaviours.GameObject;

public class RectangleCollider extends Collider {
    private double width;
    private double height;
    public RectangleCollider(GameObject gameObject, Vector velocity, ColliderType colliderType, double width, double height) {
        super(velocity, colliderType,gameObject);
        this.width = width;
        this.height = height;
    }

    // Additional functionality specific to RectangleCollider can go here

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getAngle() {
        return gameObject.getAngle();
    }

    public void setAngle(double angle) {
        gameObject.setAngle(angle);
    }
    public Vector[] getCorners(double framesAhead) {
        Vector position = getPosition(framesAhead); // Assuming there's a method getPosition that considers frames ahead
        double centerX = position.getX();
        double centerY = position.getY();

        // Half dimensions to calculate relative corners
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        // Corner points relative to center
        Vector[] corners = new Vector[4];
        corners[0] = new Vector(-halfWidth, -halfHeight); // Top-left
        corners[1] = new Vector(halfWidth, -halfHeight);  // Top-right
        corners[2] = new Vector(halfWidth, halfHeight);   // Bottom-right
        corners[3] = new Vector(-halfWidth, halfHeight);  // Bottom-left

        // Get current rotation angle
        double angle = getAngle();

        // Rotate each corner around the center
        for (int i = 0; i < corners.length; i++) {
            double xRotated = corners[i].getX() * Math.cos(angle) - corners[i].getY() * Math.sin(angle);
            double yRotated = corners[i].getX() * Math.sin(angle) + corners[i].getY() * Math.cos(angle);
            // Update the corner position relative to the world position
            corners[i] = new Vector(centerX + xRotated, centerY + yRotated);
        }

        return corners;
    }

}
