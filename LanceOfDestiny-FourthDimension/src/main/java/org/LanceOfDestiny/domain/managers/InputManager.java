package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.EventSystem.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InputManager implements KeyListener {

    public static InputManager instance;
    public int moveKey;
    public int rotateKey;

    //Manager Hub tripping using that public constructor.
    //TODO:Change it
    public InputManager(){

    }

    public static InputManager getInstance() {
        if(instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_A || e.getKeyCode()==KeyEvent.VK_D)moveKey = e.getKeyCode();
        if(e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT)rotateKey = e.getKeyCode();
        updateActions();

    }

    public void updateActions() {
            if(moveKey==KeyEvent.VK_A)Events.MoveStaff.invoke(-1);
            if(moveKey==KeyEvent.VK_D)Events.MoveStaff.invoke(1);
            if(rotateKey==KeyEvent.VK_LEFT)Events.RotateStaff.invoke(-1d);
            if(rotateKey==KeyEvent.VK_RIGHT)Events.RotateStaff.invoke(1d);
            if(rotateKey==-1)Events.ResetStaff.invoke();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(moveKey == e.getKeyCode())moveKey=-1;
        if(rotateKey == e.getKeyCode())rotateKey=-1;
    }
}
