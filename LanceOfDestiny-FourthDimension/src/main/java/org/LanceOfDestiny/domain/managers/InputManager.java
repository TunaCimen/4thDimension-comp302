package org.LanceOfDestiny.domain.managers;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.spells.SpellType;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class InputManager implements KeyListener {

    private static InputManager instance;
    public int moveKey;
    public int rotateKey;
    public int activateSpellKey;
    public int activateCurseKey;

    public boolean isShootFlag;
    private HashMap<SpellType, Long> lastSpellActivationTime = new HashMap<>();
    private static final long DEBOUNCE_DELAY = 1000;

    private InputManager() {
        reset();
    }

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
        if (e.getKeyCode() == KeyEvent.VK_SPACE) isShootFlag = true;
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) rotateKey = e.getKeyCode();
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) moveKey = e.getKeyCode();
        handleSpellActivation(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) isShootFlag = false;
        if (moveKey == e.getKeyCode()) moveKey = -1;
        if (rotateKey == e.getKeyCode()) rotateKey = -1;
        if (activateSpellKey == e.getKeyCode()) activateSpellKey = -1;
        if (activateCurseKey == e.getKeyCode()) activateCurseKey = -1;
    }

    /**
     * Updates the actions based on the current state of inputs.
     *
     * Requires:
     * - `moveKey`, `rotateKey`, `activateSpellKey`, `activateCurseKey` to be integer key codes.
     * - `isShootFlag` to be a boolean indicating if the shoot action is triggered.
     *
     * Modifies:
     * - Triggers events corresponding to the current state of inputs.
     *
     * Effects:
     * - For each key pressed, triggers the corresponding event.
     * - For rotation keys (`A` and `D`), triggers `RotateStaff` event.
     * - For movement keys (`LEFT` and `RIGHT`), triggers `MoveStaff` event.
     * - For the shoot key (`SPACE`), triggers `ShootBall` event.
     * - For spell activation keys (`O`, `E`, `C`), triggers `TryUsingSpell` event.
     * - For curse activation keys (`1`, `2`, `3`), triggers `TryUsingCurse` event.
     */
    public void updateActions() {
        updateRotation();
        updateMovement();
    }

    private void updateRotation() {
        if (rotateKey == KeyEvent.VK_A) Events.RotateStaff.invoke(-1d);
        if (rotateKey == KeyEvent.VK_D) Events.RotateStaff.invoke(1d);
        if (rotateKey == -1) Events.ResetStaff.invoke();
    }

    private void updateMovement() {
        if (moveKey == KeyEvent.VK_LEFT) Events.MoveStaff.invoke(-1);
        if (moveKey == KeyEvent.VK_RIGHT) Events.MoveStaff.invoke(1);
        if (isShootFlag) Events.ShootBall.invoke();
    }

    public void reset() {
        moveKey = -1;
        rotateKey = -1;
        activateSpellKey = -1;
        activateCurseKey = -1;
        isShootFlag = false;
    }

    private void handleSpellActivation(KeyEvent e) {
        SpellType spellType = switch (e.getKeyCode()) {
            case KeyEvent.VK_E -> SpellType.EXPANSION;
            case KeyEvent.VK_C -> SpellType.CANON;
            case KeyEvent.VK_O -> SpellType.OVERWHELMING;
            case KeyEvent.VK_1 -> SpellType.INFINITE_VOID;
            case KeyEvent.VK_2 -> SpellType.DOUBLE_ACCEL;
            case KeyEvent.VK_3 -> SpellType.HOLLOW_PURPLE;
            default -> null;
        };

        if (spellType != null && canActivateSpell(spellType)) {
            Events.ActivateSpell.invoke(spellType);
            lastSpellActivationTime.put(spellType, System.currentTimeMillis());
        }
    }

    private boolean canActivateSpell(SpellType spellType) {
        Long lastActivation = lastSpellActivationTime.get(spellType);
        long currentTime = System.currentTimeMillis();
        return (lastActivation == null || (currentTime - lastActivation >= DEBOUNCE_DELAY));
    }
}
