package org.LanceOfDestiny.ui;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.sprite.ImageLibrary;
import org.LanceOfDestiny.domain.sprite.ImageOperations;

import javax.swing.*;
import java.util.Stack;

public class HealthBar extends JPanel {
    public int init_health;
    ImageIcon healthImageIcon;
    private Stack<JLabel> healths;

    public HealthBar(int maxHealth){
        this.init_health = maxHealth;
        healthImageIcon = new ImageIcon(ImageOperations.resizeImage(ImageLibrary.Heart.getImage(),20,20));
        healths = new Stack<>();
        this.setVisible(true);
        initializePanel();
        Events.UpdateChance.addListener(e->updateHealth((int)e) );
    }

    public void initializePanel(){
        for(int i = 0; i< init_health; ++i){
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
        revalidate();
    }

    public void addHealth(){
        System.out.println("Chance Added");
        JLabel newLabel = new JLabel();
        newLabel.setIcon(healthImageIcon);
        healths.push(newLabel);
        add(newLabel);
        repaint();
        revalidate();
    }

    public void updateHealth(int val){
        if(val > 0){
            addHealth();
        }
        else{
            reduceHealth();
        }
    }

    public void setHealth(int health){
        healths.clear();
        removeAll();
        for(int i = 0; i< health; ++i){
            JLabel newLabel = new JLabel();
            newLabel.setIcon(healthImageIcon);
            healths.push(newLabel);
            add(newLabel);
        }
        repaint();
        revalidate();
    }
}
