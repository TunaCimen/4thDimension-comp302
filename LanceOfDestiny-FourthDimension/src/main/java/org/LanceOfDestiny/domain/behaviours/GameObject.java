package org.LanceOfDestiny.domain.behaviours;

import org.LanceOfDestiny.domain.physics.Collider;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.PhysicsManager;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameObject extends Behaviour {

    private static List<Behaviour> gameObjects = new ArrayList<>();
    protected Vector position;
    protected Collider collider;
    protected Sprite sprite;
    protected double angle;

    public GameObject() {
        super();
        gameObject = this;
        GameObject.gameObjects.add(this);
        this.sprite = new RectangleSprite(this, Color.magenta, 0, 0);
    }

    @Override
    public void start() {
        super.start();
    }

    public void update() {
        super.update();
        setPosition((getPosition().add(getCollider().getVelocity())));
    }

    public void destroy() {
        gameObjects.remove(this);
        PhysicsManager.getInstance().removeCollider(getCollider());
    }

    public void onCollisionEnter(Collision collision) {
    }

    public void onTriggerEnter(Collision collision) {
    }

    public static List<Behaviour> getGameObjects() {
        return gameObjects;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Vector getDirection() {
        return getCollider().getVelocity().normalize();
    }
}
