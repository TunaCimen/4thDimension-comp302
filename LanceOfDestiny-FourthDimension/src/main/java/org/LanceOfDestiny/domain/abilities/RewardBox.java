package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.GameObject;
import org.LanceOfDestiny.domain.physics.Vector;

public class RewardBox extends GameObject {

    private Vector position;

    private SpellType spellType;

    private boolean isFalling;

    public RewardBox(Vector position, SpellType spellType) {
        super();
        this.position = position;
        this.spellType = spellType;
        this.isFalling = false;
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
}
