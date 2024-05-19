package org.LanceOfDestiny;

import org.LanceOfDestiny.domain.events.Events;
import org.LanceOfDestiny.domain.managers.InputManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class InputManagerTest {

    private InputManager inputManager;

    @BeforeEach
    void setUp() {
        inputManager = InputManager.getInstance();
        inputManager.reset();
        Events.clearAllListeners(); // Clear all listeners before each test
        resetAllEventsFired(); // Reset all event fired flags before each test
    }

    @Test
    void testUpdateActionsWithRotateKey() {
        inputManager.rotateKey = KeyEvent.VK_A;
        inputManager.updateActions();
        assertTrue(Events.RotateStaff.eventsFired());

        inputManager.rotateKey = KeyEvent.VK_D;
        inputManager.updateActions();
        assertTrue(Events.RotateStaff.eventsFired());

        inputManager.rotateKey = -1;
        inputManager.updateActions();
        assertTrue(Events.ResetStaff.eventsFired());
    }

    @Test
    void testUpdateActionsWithMoveKey() {
        inputManager.moveKey = KeyEvent.VK_LEFT;
        inputManager.updateActions();
        assertTrue(Events.MoveStaff.eventsFired());

        inputManager.moveKey = KeyEvent.VK_RIGHT;
        inputManager.updateActions();
        assertTrue(Events.MoveStaff.eventsFired());
    }

    @Test
    void testUpdateActionsWithShootFlag() {
        inputManager.isShootFlag = true;
        inputManager.updateActions();
        assertTrue(Events.ShootBall.eventsFired());
    }

    @Test
    void testUpdateActionsWithSpellActivation() {
        inputManager.activateSpellKey = KeyEvent.VK_E;
        inputManager.updateActions();
        assertTrue(Events.TryUsingSpell.eventsFired());

        inputManager.activateSpellKey = KeyEvent.VK_C;
        inputManager.updateActions();
        assertTrue(Events.TryUsingSpell.eventsFired());

        inputManager.activateSpellKey = KeyEvent.VK_O;
        inputManager.updateActions();
        assertTrue(Events.TryUsingSpell.eventsFired());
    }

    @Test
    void testUpdateActionsWithCurseActivation() {
        inputManager.activateCurseKey = KeyEvent.VK_1;
        inputManager.updateActions();
        assertTrue(Events.TryUsingCurse.eventsFired());

        inputManager.activateCurseKey = KeyEvent.VK_2;
        inputManager.updateActions();
        assertTrue(Events.TryUsingCurse.eventsFired());

        inputManager.activateCurseKey = KeyEvent.VK_3;
        inputManager.updateActions();
        assertTrue(Events.TryUsingCurse.eventsFired());
    }

    private void resetAllEventsFired() {
        for (Events event : Events.values()) {
            event.resetEventFired();
        }
    }
}
