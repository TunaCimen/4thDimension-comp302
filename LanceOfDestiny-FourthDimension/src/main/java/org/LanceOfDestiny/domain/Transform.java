package org.LanceOfDestiny.domain;

import javax.swing.*;

public class Transform {

    private JPanel sprite;
    float x,y,angle;
    public Transform(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void setSprite(JPanel sprite) {
        this.sprite = sprite;
    }

    public JPanel getSprite() {
        return sprite;
    }
}
