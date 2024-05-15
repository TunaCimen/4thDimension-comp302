package org.LanceOfDestiny.domain.barriers;

import org.LanceOfDestiny.domain.physics.Vector;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

public class HollowBarrier extends SimpleBarrier {

    public HollowBarrier(Vector position) {
        super(position);
        this.sprite.setImage(ImageOperations.resizeImageToSprite(ImageLibrary.HollowBarrier.getImage(), this.sprite));
    }

    @Override
    public void kill() {
        destroy();
    }
}
