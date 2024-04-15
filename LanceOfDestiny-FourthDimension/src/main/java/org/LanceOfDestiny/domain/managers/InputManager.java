package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.EventSystem.Events;
import org.LanceOfDestiny.domain.spells.SpellType;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

    private static InputManager instance;
    public int moveKey;
    public int rotateKey;
    public int activateSpellKey;

    private InputManager() {}

    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) Events.TimedTestEvent.invoke(Color.BLACK);
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) rotateKey = e.getKeyCode();
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) moveKey = e.getKeyCode();
        if (e.getKeyCode() == KeyEvent.VK_O || e.getKeyCode() == KeyEvent.VK_E || e.getKeyCode() == KeyEvent.VK_C)
            activateSpellKey = e.getKeyCode();
        updateActions();
    }

    public void updateActions() {
        updateRotation();
        updateMovement();
        updateSpellActivation();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (moveKey == e.getKeyCode()) moveKey = -1;
        if (rotateKey == e.getKeyCode()) rotateKey = -1;
    }

    private void updateRotation() {
        if (rotateKey == KeyEvent.VK_A) Events.RotateStaff.invoke(-1d);
        if (rotateKey == KeyEvent.VK_D) Events.RotateStaff.invoke(1d);
        if (rotateKey == -1) Events.ResetStaff.invoke();
    }

    private void updateMovement() {
        if (moveKey == KeyEvent.VK_LEFT) Events.MoveStaff.invoke(-1);
        if (moveKey == KeyEvent.VK_RIGHT) Events.MoveStaff.invoke(1);
    }

    private void updateSpellActivation() {
        if (activateSpellKey == KeyEvent.VK_E) Events.TryUsingSpell.invoke(SpellType.EXPANSION);
        if (activateSpellKey == KeyEvent.VK_C) Events.TryUsingSpell.invoke(SpellType.CANON);
        if (activateSpellKey == KeyEvent.VK_O) Events.TryUsingSpell.invoke(SpellType.OVERWHELMING);
    }

}
