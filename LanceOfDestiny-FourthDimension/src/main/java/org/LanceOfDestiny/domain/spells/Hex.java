package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.behaviours.GameObject;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.sprite.BallSprite;

import java.awt.*;

public class Hex extends GameObject {


    public static final int HEX_RADIUS = Constants.HEX_RADIUS;
    public static final double HEX_SPEED = Constants.HEX_SPEED;
    public final Vector velocity = new Vector(0, -5);
    private final Canon canon;
    private boolean isVisible = false;
    private Vector initialPosition;


    public Hex(Canon canon) {
        super();
        this.canon = canon;
        this.initialPosition = canon.getPosition().add(new Vector(Constants.CANON_WIDTH, -HEX_RADIUS*2));
        this.position = initialPosition;
        this.sprite = new BallSprite(this, Color.BLACK, HEX_RADIUS);
        this.collider = ColliderFactory.createBallCollider(this, velocity, ColliderType.STATIC, HEX_RADIUS);
        collider.setEnabled(false);
        sprite.setVisible(false);
    }

    @Override
    public void update() {
        super.update();
        if (isVisible) {
            setPosition(getPosition().add(collider.getVelocity()));
        }
    }

    @Override
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if (other instanceof FireBall) return;

        sprite.setVisible(false);
        collider.setEnabled(false);
        isVisible = false;
        this.position = initialPosition;

    }

    public void shoot() {
        sprite.setVisible(true);
        collider.setEnabled(true);
        isVisible = true;

        this.position = canon.getPosition().add(new Vector(Constants.CANON_WIDTH, -HEX_RADIUS*2));
        var magicalStaff = SessionManager.getInstance().getMagicalStaff();
        var velocity = Vector.getVelocityByAngleAndMagnitude((int) -HEX_SPEED, magicalStaff.getAngle());
        collider.setVelocity(velocity);

    }
}
