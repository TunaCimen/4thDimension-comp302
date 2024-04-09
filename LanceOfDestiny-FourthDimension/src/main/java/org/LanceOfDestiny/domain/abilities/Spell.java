package org.LanceOfDestiny.domain.abilities;

import org.LanceOfDestiny.domain.GameObject;

public abstract class Spell extends GameObject {

    private int spellDuration = 30; // in seconds, might need to change later

    public Spell(){
        super();
    }

}
