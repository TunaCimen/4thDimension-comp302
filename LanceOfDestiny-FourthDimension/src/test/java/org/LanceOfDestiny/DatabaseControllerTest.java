package org.LanceOfDestiny;

import org.LanceOfDestiny.database.DatabaseController;
import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.SimpleBarrier;
import org.LanceOfDestiny.domain.physics.Vector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseControllerTest {
    private DatabaseController dbController;
    private Connection mockConnection;

    @BeforeEach
    public void setUp() throws SQLException {
        mockConnection = Mockito.mock(Connection.class);
        dbController = new DatabaseController(mockConnection);
    }

    @Test
    public void testSaveGame_ValidInput() throws SQLException {
        List<Barrier> barrierList = new ArrayList<>();
        barrierList.add(new SimpleBarrier(new Vector(0, 0)));

        PreparedStatement mockStmt1 = Mockito.mock(PreparedStatement.class);
        PreparedStatement mockStmt2 = Mockito.mock(PreparedStatement.class);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockStmt1).thenReturn(mockStmt2);

        boolean result = dbController.saveGame("testUser", "save1", barrierList, 100, 3, "spell1, spell2, spell3, spell4");
        assertTrue(result);
    }

    @Test
    public void testSaveGame_EmptyBarrierList() throws SQLException {
        List<Barrier> barrierList = new ArrayList<>();

        PreparedStatement mockStmt2 = Mockito.mock(PreparedStatement.class);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockStmt2);

        boolean result = dbController.saveGame("testUser", "save2", barrierList, 50, 2, "spell1, spell2, spell3, spell4");
        assertTrue(result);
    }

    @Test
    public void testSaveGame_InvalidUsername() throws SQLException {
        List<Barrier> barrierList = new ArrayList<>();
        barrierList.add(new SimpleBarrier(new Vector(0, 0)));

        boolean result = dbController.saveGame(null, "save3", barrierList, 20, 1, "spell1, spell2, spell3, spell4");
        assertFalse(result);
    }

    @Test
    public void testSaveGame_BarrierWithFallingState() throws SQLException {
        List<Barrier> barrierList = new ArrayList<>();
        Barrier fallingBarrier = Mockito.mock(Barrier.class);
        Mockito.when(fallingBarrier.isFalling()).thenReturn(true);
        barrierList.add(fallingBarrier);
        barrierList.add(new SimpleBarrier(new Vector(0, 0)));

        PreparedStatement mockStmt1 = Mockito.mock(PreparedStatement.class);
        PreparedStatement mockStmt2 = Mockito.mock(PreparedStatement.class);
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockStmt1).thenReturn(mockStmt2);

        boolean result = dbController.saveGame("testUser", "save4", barrierList, 70, 3, "spell1, spell2, spell3, spell4");
        assertTrue(result);
    }

    @Test
    public void testSaveGame_DatabaseConnectionError() throws SQLException {
        List<Barrier> barrierList = new ArrayList<>();
        barrierList.add(new SimpleBarrier(new Vector(0, 0)));

        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenThrow(new SQLException("Connection error"));

        boolean result = dbController.saveGame("testUser", "save5", barrierList, 80, 4, "spell1, spell2, spell3, spell4");
        assertFalse(result);
    }
}
