package org.LanceOfDestiny;

import org.LanceOfDestiny.domain.events.Event;
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
        Event.clearAllListeners(); // Clear all listeners before each test
        resetAllEventsFired(); // Reset all event fired flags before each test
    }

    @Test
    void testUpdateActionsWithRotateKey() {
        inputManager.rotateKey = KeyEvent.VK_A;
        inputManager.updateActions();
        assertTrue(Event.RotateStaff.eventsFired());

        inputManager.rotateKey = KeyEvent.VK_D;
        inputManager.updateActions();
        assertTrue(Event.RotateStaff.eventsFired());

        inputManager.rotateKey = -1;
        inputManager.updateActions();
        assertTrue(Event.ResetStaff.eventsFired());
    }

    @Test
    void testUpdateActionsWithMoveKey() {
        inputManager.moveKey = KeyEvent.VK_LEFT;
        inputManager.updateActions();
        assertTrue(Event.MoveStaff.eventsFired());

        inputManager.moveKey = KeyEvent.VK_RIGHT;
        inputManager.updateActions();
        assertTrue(Event.MoveStaff.eventsFired());
    }

    @Test
    void testUpdateActionsWithShootFlag() {
        inputManager.isShootFlag = true;
        inputManager.updateActions();
        assertTrue(Event.ShootBall.eventsFired());
    }

    @Test
    void testUpdateActionsWithSpellActivation() {
        inputManager.activateSpellKey = KeyEvent.VK_E;
        inputManager.updateActions();
        assertTrue(Event.ActivateSpell.eventsFired());

        inputManager.activateSpellKey = KeyEvent.VK_C;
        inputManager.updateActions();
        assertTrue(Event.ActivateSpell.eventsFired());

        inputManager.activateSpellKey = KeyEvent.VK_O;
        inputManager.updateActions();
        assertTrue(Event.ActivateSpell.eventsFired());
    }

    @Test
    void testUpdateActionsWithCurseActivation() {
        inputManager.activateCurseKey = KeyEvent.VK_1;
        inputManager.updateActions();
        assertTrue(Event.ActivateCurse.eventsFired());

        inputManager.activateCurseKey = KeyEvent.VK_2;
        inputManager.updateActions();
        assertTrue(Event.ActivateCurse.eventsFired());

        inputManager.activateCurseKey = KeyEvent.VK_3;
        inputManager.updateActions();
        assertTrue(Event.ActivateCurse.eventsFired());
    }

    private void resetAllEventsFired() {
        for (Event event : Event.values()) {
            event.resetEventFired();
        }
    }
}
