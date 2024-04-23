package org.LanceOfDestiny.database;

import org.LanceOfDestiny.domain.barriers.*;
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

    public boolean saveGame(String username, String saveName, List<Barrier> barrierList,int score, int chances, String numberOfSpells) throws SQLException {
        for (Barrier b: barrierList){
            if(b instanceof ExplosiveBarrier explosiveBarrier && explosiveBarrier.isFalling()) continue;;
            try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO savedBarrier (savedByUser, saveName, barrierType, hitsleft, coordinate) VALUES (?, ?, ?, ?, ?)")) {
                pstmt.setString(1, username);
                pstmt.setString(2, saveName);
                pstmt.setString(3, b.getBarrierType().toString());
                pstmt.setInt(4, b.getHitsLeft());
                if(b instanceof ExplosiveBarrier explosiveBarrier) {
                    pstmt.setString(5, explosiveBarrier.getInitPos().toString());
                } else {
                    pstmt.setString(5, b.getPosition().toString());
                }
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
            pstmt2.setString(4, numberOfSpells);
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
                    String barrierType = resultSet.getString("barrierType");
                    int hitsLeft = resultSet.getInt("hitsLeft");
                    String coordinate = resultSet.getString("coordinate");
                    String[] parts = coordinate.split(", ");

                    if(Objects.equals(barrierType, "SIMPLE")){
                        rt.add(BarrierFactory.createBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1])),BarrierTypes.SIMPLE));
                    }
                    else if(Objects.equals(barrierType, "EXPLOSIVE")){
                        rt.add(BarrierFactory.createBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1])),BarrierTypes.EXPLOSIVE));
                    }
                    else if(Objects.equals(barrierType, "REINFORCED")){
                        rt.add(BarrierFactory.createBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1])),BarrierTypes.REINFORCED));
                    }
                    else if(Objects.equals(barrierType, "REWARDING")){
                        rt.add(BarrierFactory.createBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1])),BarrierTypes.REWARDING));
                    }
                } while (resultSet.next());
                return rt;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
    public List<String> loadUserInfo(String username, String saveName) throws SQLException {
        List<String> rt = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM UserInfoSaved WHERE savedByUser = ? AND saveName = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, saveName);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                int score = resultSet.getInt("score");
                int chances = resultSet.getInt("chances");
                String numberofspells = resultSet.getString("numberOfSpells");
                String[] parts = numberofspells.split(", ");
                rt.add(String.valueOf(score));
                rt.add(String.valueOf(chances));
                rt.add(parts[0]);
                rt.add(parts[1]);
                rt.add(parts[2]);
                rt.add(parts[3]);
            }
        }
        return rt;
    }
    public List<String> loadSavedNames(String username){
        List<String> rt = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT saveName FROM UserInfoSaved WHERE savedByUser = ?")) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                do {
                    rt.add(resultSet.getString("saveName"));
                } while (resultSet.next());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
}




