package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.EventSystem.Events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

    public static InputManager instance;

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
        //System.out.println("Key Pressed:" + e.getKeyCode());
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A -> Events.MoveStaff.invoke(-1);
            case KeyEvent.VK_D -> Events.MoveStaff.invoke(1);
            case KeyEvent.VK_L -> Events.RotateStaff.invoke(0.2);
            case KeyEvent.VK_K -> Events.RotateStaff.invoke(-0.2);
        }

        //Events.MoveStaff.invoke(1);

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
