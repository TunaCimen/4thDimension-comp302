package org.LanceOfDestiny.domain.spells;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.physics.*;
import org.LanceOfDestiny.domain.player.MagicalStaff;
import org.LanceOfDestiny.domain.sprite.RectangleSprite;
import org.LanceOfDestiny.domain.sprite.Sprite;

import java.awt.*;

public class RewardBox extends GameObject {

    private SpellType spellType;
    private Collider collider;
    private Sprite sprite;
    private static int WIDTH = Constants.REWARD_BOX_WIDTH;
    private static int HEIGHT = Constants.REWARD_BOX_HEIGHT;

    private boolean isFalling;

    public RewardBox(Vector position, SpellType spellType) {
        super();
        this.position = position;
        this.spellType = spellType;
        this.isFalling = false;

    }

    public void createSpriteAndCollider(){
        this.collider = ColliderFactory.createRectangleCollider(this, new Vector(0, 1), ColliderType.STATIC, WIDTH, HEIGHT);
        this.sprite = new RectangleSprite(this, Color.MAGENTA, WIDTH, HEIGHT);
    }

    @Override
    public void update() {
        super.update();
        if (isFalling) {
            if(this.collider == null) createSpriteAndCollider();
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
    public void onCollisionEnter(Collision collision) {
        super.onCollisionEnter(collision);
        var other = collision.getOther(this);

        if(other instanceof MagicalStaff){
            var spell = SpellFactory.createSpell(spellType);
            Events.GainSpell.invoke(spellType);
        }
    }


}
