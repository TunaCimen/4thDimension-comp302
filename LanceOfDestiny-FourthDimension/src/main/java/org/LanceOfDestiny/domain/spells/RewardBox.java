package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.barriers.RewardingBarrier;
import org.LanceOfDestiny.domain.physics.ColliderFactory;
import org.LanceOfDestiny.domain.physics.ColliderType;
import org.LanceOfDestiny.domain.physics.Collision;
import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.player.FireBall;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;

public class RewardBox extends GameObject {

    private final static int WIDTH = Constants.REWARD_BOX_WIDTH;
    private final static int HEIGHT = Constants.REWARD_BOX_HEIGHT;
    private final SpellType spellType;
    private Sprite sprite;
    private boolean isFalling;

    public RewardBox(Vector position, SpellType spellType) {
        super();
        this.position = position;
        this.spellType = spellType;
        this.isFalling = false;
        createSprite();
        createCollider();
    }

    public void createSprite() {
        this.sprite = new RectangleSprite(this, Color.MAGENTA, WIDTH, HEIGHT);
        getSprite().setVisible(false);
    }

    public void createCollider() {
        this.collider = ColliderFactory.createRectangleCollider(this, new Vector(0, 1), ColliderType.STATIC, WIDTH, HEIGHT);
        collider.setTrigger(true);
        collider.setEnabled(false);
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void update() {
        super.update();
        if (isFalling) {
            getSprite().setVisible(true);
            setPosition(getPosition().add(collider.getVelocity()));
        }

    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }

    public SpellType getSpellType() {
        return spellType;
    }


    @Override
    public void onTriggerEnter(Collision collision) {
        var other = collision.getOther(this);
        if (other instanceof RewardingBarrier) return;
        if (other instanceof MagicalStaff) {
            destroy();
            Events.GainSpell.invoke(spellType);
        }
        if (other instanceof FireBall) {
            return;
        }

        if (other == null) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        RewardBoxFactory.getInstance().removeRewardBox(this);
    }
}
