package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Vector;

import java.awt.*;

public class HollowBarrier extends SimpleBarrier {

    public HollowBarrier(Vector position) {
        super(position);
        this.sprite.color = Color.MAGENTA;
        this.sprite.setImage(null);
    }

    @Override
    public void kill() {
        destroy();
    }
}
