package org.LanceOfDestiny.database;

import org.LanceOfDestiny.domain.barriers.Barrier;
import org.LanceOfDestiny.domain.barriers.ExplosiveBarrier;
import org.LanceOfDestiny.domain.barriers.SimpleBarrier;
import org.LanceOfDestiny.domain.physics.Vector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseController {
    private final Connection connection;

    public DatabaseController() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11698733",
                "sql11698733",
                "UI1TfUfkQa"
        );
    }

    public boolean addUser(String username, String password) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO User (username, password) VALUES (?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean loginUser(String username, String password) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public boolean saveGame(String username, String saveName, List<Barrier> barrierList,int score, int chances, int numberOfSpells) throws SQLException {
        for (Barrier b: barrierList){
            try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO savedBarrier (savedByUser, saveName, barrierType, hitsleft, coordinate) VALUES (?, ?, ?, ?, ?)")) {
                pstmt.setString(1, username);
                pstmt.setString(2, saveName);
                pstmt.setString(3, b.getBarrierType().toString());
                pstmt.setInt(4, b.getHitsLeft());
                pstmt.setString(5, b.getPosition().toString());
                pstmt.executeUpdate();
            } catch (Exception e){
                System.out.println("Couldn't save");
                return false;
            }
        }
        try (PreparedStatement pstmt2 = connection.prepareStatement("INSERT INTO UserInfoSaved (savedByUser, score, chances, numberOfSpells, saveName) VALUES (?, ?, ?, ?, ?)")) {
            pstmt2.setString(1, username);
            pstmt2.setInt(2, score);
            pstmt2.setInt(3, chances);
            pstmt2.setInt(4, numberOfSpells);
            pstmt2.setString(5, saveName);
            pstmt2.executeUpdate();
        }
        return true;
    }
    public List<Barrier> loadBarriers(String username, String saveName){
        List<Barrier> rt = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM savedBarrier WHERE savedByuser = ? AND saveName = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, saveName);
            ResultSet resultSet = pstmt.executeQuery();

            // Check if there is at least one row
            if (resultSet.next()) {
                do {
                    // Process each row individually
                    String barrierType = resultSet.getString("barrierType");
                    int hitsLeft = resultSet.getInt("hitsLeft");
                    String coordinate = resultSet.getString("coordinate");
                    String[] parts = coordinate.split(", ");
                    // Do something with the retrieved information for each row
                    if(Objects.equals(barrierType, "SIMPLE")){
                        rt.add(new SimpleBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1]))));
                    }
                    else if(Objects.equals(barrierType, "EXPLOSIVE")){
                        rt.add(new ExplosiveBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1]))));
                    }
                } while (resultSet.next());
                return rt;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
    public List<Integer> loadUserInfo(String username, String saveName) throws SQLException {
        List<Integer> rt = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM UserInfoSaved WHERE savedByUser = ? AND saveName = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, saveName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int score = resultSet.getInt("score");
                int chances = resultSet.getInt("chances");
                int numberofspells = resultSet.getInt("numberOfSpells");
                rt.add(score);
                rt.add(chances);
                rt.add(numberofspells);
            }
        }
        return rt;
    }

}


