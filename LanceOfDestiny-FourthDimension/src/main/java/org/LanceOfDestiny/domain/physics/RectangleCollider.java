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
        Vector topLeft = getPosition(framesAhead); // Top-left corner as the base position

        // Calculate the effective center of the rectangle for rotation
        double centerX = topLeft.getX() + width / 2.0;
        double centerY = topLeft.getY() + height / 2.0;

        // Define corners relative to the center for rotation
        Vector[] corners = new Vector[4];
        corners[0] = new Vector(topLeft.getX() - centerX, topLeft.getY() - centerY); // Top-left
        corners[1] = new Vector(topLeft.getX() + width - centerX, topLeft.getY() - centerY); // Top-right
        corners[2] = new Vector(topLeft.getX() + width - centerX, topLeft.getY() + height - centerY); // Bottom-right
        corners[3] = new Vector(topLeft.getX() - centerX, topLeft.getY() + height - centerY); // Bottom-left

        // Get current rotation angle in radians
        double angle = getAngle();

        // Rotate each corner around the effective center
        for (int i = 0; i < corners.length; i++) {
            double xRotated = corners[i].getX() * Math.cos(angle) - corners[i].getY() * Math.sin(angle);
            double yRotated = corners[i].getX() * Math.sin(angle) + corners[i].getY() * Math.cos(angle);
            // Update the corner position relative to the world position (re-translate back)
            corners[i] = new Vector(centerX + xRotated, centerY + yRotated);
        }

        return corners;
    }


}
