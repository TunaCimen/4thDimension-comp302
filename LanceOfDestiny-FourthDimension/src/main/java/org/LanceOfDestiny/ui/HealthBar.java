package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;

import javax.swing.*;
import java.util.Stack;

public class HealthBar extends JPanel {
    private final int maxHealth;
    ImageIcon healthImageIcon;
    private Stack<JLabel> healths;
    public HealthBar(int maxHealth){
        this.maxHealth = maxHealth;
        healthImageIcon = new ImageIcon(ImageLibrary.HealthBar.getImage());
        healths = new Stack<>();
        this.setVisible(true);
        addToPanel();
        Events.UpdateChance.addListener(e->updateHealth((int)e) );
    }

    public void addToPanel(){
        for(int i = 0;i<maxHealth;++i){
            JLabel newLabel = new JLabel();
            newLabel.setIcon(healthImageIcon);
            healths.push(newLabel);
            add(newLabel);
        }
    }

    public void reduceHealth(){
        JLabel reduced = healths.pop();
        remove(reduced);
        repaint();
    }

    public void addHealth(){
        JLabel newLabel = new JLabel();
        newLabel.setIcon(healthImageIcon);
        healths.push(newLabel);
        add(newLabel);
        repaint();
    }

    public void updateHealth(int val){
        if(val > 0){
            addHealth();
        }
        else{
            reduceHealth();
        }
    }





}
