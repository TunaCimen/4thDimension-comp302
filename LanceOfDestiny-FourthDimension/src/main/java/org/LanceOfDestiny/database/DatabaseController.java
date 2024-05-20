package org.LanceOfDestiny.database;

import org.LanceOfDestiny.domain.barriers.*;
import org.LanceOfDestiny.domain.managers.BarrierManager;
import org.LanceOfDestiny.domain.managers.SessionManager;
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

    public boolean saveGame(String username, String saveName, List<Barrier> barrierList, int score, int chances, String numberOfSpells) throws SQLException {
        for (Barrier b : barrierList) {
            if (!b.isFalling()) {
                try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO savedBarrier (savedByUser, saveName, barrierType, hitsleft, coordinate, moving) VALUES (?, ?, ?, ?, ?, ?)")) {
                    String pos;
                    if (b.getBarrierType().equals(BarrierTypes.EXPLOSIVE)) {
                        ExplosiveBarrier explosiveBarrier = (ExplosiveBarrier) b;
                        pos = explosiveBarrier.getInitPos().toString();
                    } else {
                        pos = b.getPosition().toString();
                    }
                    pstmt.setString(1, username);
                    pstmt.setString(2, saveName);
                    pstmt.setString(3, b.getBarrierType().toString());
                    pstmt.setInt(4, b.getHitsLeft());
                    pstmt.setString(5, pos);
                    pstmt.setBoolean(6, b.isMoving());
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Couldn't save");
                    return false;
                }
            }
        }
        try (PreparedStatement pstmt2 = connection.prepareStatement("INSERT INTO UserInfoSaved (savedByUser, score, chances, numberOfSpells, saveName, time) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt2.setString(1, username);
            pstmt2.setInt(2, score);
            pstmt2.setInt(3, chances);
            pstmt2.setString(4, numberOfSpells);
            pstmt2.setString(5, saveName);
            pstmt2.setInt(6, SessionManager.getInstance().getLoopExecutor().getSecondsPassed());
            pstmt2.executeUpdate();
        }
        return true;
    }

    public List<Barrier> loadBarriers(String username, String saveName) {
        List<Barrier> barriers = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM savedBarrier WHERE savedByuser = ? AND saveName = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, saveName);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String barrierInfo = resultSet.getString("barrierType") + ","
                        + resultSet.getInt("hitsLeft") + ","
                        + resultSet.getString("coordinate") + ","
                        + resultSet.getBoolean("moving");
                loadBarrierFromString(barrierInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return barriers;
    }


    private void loadBarrierFromString(String barrierInfo) {
        String[] parts = barrierInfo.split(",");
        if (parts.length < 4) return;

        String barrierType = parts[0].trim();
        int hitsLeft = Integer.parseInt(parts[1].trim());
        String[] coordinateParts = parts[2].trim().split(" ");
        boolean moving = Boolean.parseBoolean(parts[3].trim());
        Vector position = new Vector(Float.parseFloat(coordinateParts[0]), Float.parseFloat(coordinateParts[1]));

        Barrier barrier = null;

        if(Objects.equals(barrierType, "SIMPLE")){
            //rt.add(BarrierFactory.createBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1])),BarrierTypes.SIMPLE));
            SimpleBarrier sp = new SimpleBarrier(position);
            sp.setMoving(moving);
            sp.initDirection();
            sp.start();
            BarrierManager.getInstance().addBarrier(sp);
        }
        else if(Objects.equals(barrierType, "EXPLOSIVE")){
            //rt.add(BarrierFactory.createBarrier(position,BarrierTypes.EXPLOSIVE));
            ExplosiveBarrier ep = new ExplosiveBarrier(position);
            ep.setMoving(moving);
            ep.initDirection();
            ep.start();
            BarrierManager.getInstance().addBarrier(ep);
        }
        else if(Objects.equals(barrierType, "REINFORCED")){
            //rt.add(BarrierFactory.createBarrier(position,BarrierTypes.REINFORCED));
            ReinforcedBarrier rb = new ReinforcedBarrier(position, hitsLeft);
            rb.setMoving(moving);
            rb.initDirection();
            rb.start();
            BarrierManager.getInstance().addBarrier(rb);
        }
        else if(Objects.equals(barrierType, "REWARDING")){
            //rt.add(BarrierFactory.createBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1])),BarrierTypes.REWARDING));
            RewardingBarrier wb = new RewardingBarrier(new Vector(Float.parseFloat(parts[0]),Float.parseFloat(parts[1])));
            wb.setMoving(moving);
            wb.initDirection();
            wb.start();
            BarrierManager.getInstance().addBarrier(wb);
        }

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
                int time = resultSet.getInt("time");
                String numberofspells = resultSet.getString("numberOfSpells");
                String[] parts = numberofspells.split(", ");
                rt.add(String.valueOf(score));
                rt.add(String.valueOf(chances));
                rt.add(parts[0]);
                rt.add(parts[1]);
                rt.add(parts[2]);
                rt.add(parts[3]);
                rt.add(String.valueOf(time));
            }
        }
        return rt;
    }

    public List<String> loadSavedNames(String username) {
        List<String> rt = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT saveName FROM UserInfoSaved WHERE savedByUser = ?")) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                rt.add(resultSet.getString("saveName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
}
