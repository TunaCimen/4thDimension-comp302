package org.LanceOfDestiny;

import static org.junit.jupiter.api.Assertions.*;

import org.LanceOfDestiny.domain.Constants;
import org.LanceOfDestiny.domain.events.Event;
import org.LanceOfDestiny.domain.looper.LoopExecutor;
import org.LanceOfDestiny.domain.managers.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.LanceOfDestiny.domain.managers.SessionManager;
import org.LanceOfDestiny.domain.player.Player;

class SessionManagerTest {

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        // Reset the singleton instance for each test
        resetSingleton(SessionManager.class, "instance");
        sessionManager = SessionManager.getInstance();
    }

    private void resetSingleton(Class<?> clazz, String fieldName) {
        try {
            java.lang.reflect.Field instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testPlayerIsInitializedWhenNull() {
        // Ensure player is null before calling initializePlayer
        sessionManager.initializeSession();

        assertNotNull(sessionManager.getPlayer(), "Player should be initialized when it is null.");
    }

    @Test
    void testPlayerIsNotReinitializedWhenNotNull() {
        // Initialize player
        sessionManager.initializeSession();
        Player originalPlayer = sessionManager.getPlayer();

        sessionManager.initializeSession();

        assertSame(originalPlayer, sessionManager.getPlayer(), "Player should not be re-initialized when it is already initialized.");
    }

    @Test
    void testPlayerIsInitializedWithDefaultValues() {
        sessionManager.initializeSession();
        Player player = sessionManager.getPlayer();

        assertEquals(3, player.getChancesLeft(), "Player should be initialized with the default number of chances.");
        // Add more default value checks as needed
    }


    @Test
    void testInitializeSessionInitializesPlayer() {
        sessionManager.initializeSession();

        assertNotNull(sessionManager.getPlayer(), "Initializing the session should initialize the player.");
    }

    @Test
    void testPlayerInitializationAndInteractionWithLoopExecutor() { // Comprehensive test ensuring the player's initialization and state changes correctly trigger responses in LoopExecutor.
        // Step 1: Initialize the session
        sessionManager.initializeSession();

        // Step 2: Ensure player is initialized
        Player player = sessionManager.getPlayer();
        assertNotNull(player, "Player should be initialized.");

        // Step 3: Verify default player values
        assertEquals(Constants.DEFAULT_CHANCES, player.getChancesLeft(), "Player should be initialized with the default number of chances.");

        // Step 4: Check the initial state of LoopExecutor
        LoopExecutor loopExecutor = sessionManager.getLoopExecutor();
        assertNotNull(loopExecutor, "LoopExecutor should be initialized.");
        assertFalse(loopExecutor.isStarted(), "LoopExecutor should not be active initially.");

        // Step 5: Simulate player losing a chance and check the response
        player.setChancesLeft(2);
        assertEquals(2, player.getChancesLeft(), "Player's chances should be updated to 2.");

        // Step 6: Simulate the game start and check LoopExecutor response
        sessionManager.setStatus(Status.RunningMode);
        Event.StartGame.invoke();
        assertTrue(loopExecutor.isStarted(), "LoopExecutor should be active after starting the game.");

        // Step 7: Simulate a reset and verify all state resets correctly
        Event.Reset.invoke();
        assertEquals(Constants.DEFAULT_CHANCES, player.getChancesLeft(), "Player's chances should be reset to default.");
        assertEquals(0, loopExecutor.getSecondsPassed(), "LoopExecutor's timePassed should be reset to 0.");
    }
}
