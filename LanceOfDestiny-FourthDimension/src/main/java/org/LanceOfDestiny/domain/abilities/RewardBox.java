package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.GameObject;

public class RewardBox extends GameObject {
    public int x;
    public int y;

    private SpellType spellType;

    private boolean isFalling;

    public RewardBox(int x, int y, SpellType spellType) {
        super();
        this.x = x;
        this.y = y;
        this.spellType = spellType;
        this.isFalling = false;
    }

    public boolean isFalling() {
        return isFalling;
    }
    public void setFalling(boolean isFalling) {
        this.isFalling = isFalling;
    }
}
