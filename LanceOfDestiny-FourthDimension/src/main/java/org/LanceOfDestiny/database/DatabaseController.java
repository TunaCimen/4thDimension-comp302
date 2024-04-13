package org.LanceOfDestiny.database;

import java.io.*;
import java.sql.*;

public class DatabaseController {
    private final Connection connection;

    public DatabaseController() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/Users302",
                "root",
                "Whitewolf2206"
        );
        Statement statement = connection.createStatement();
        ResultSet resultset = statement.executeQuery("SELECT * FROM USERS");
        while(resultset.next()) {
            System.out.println(resultset.getString("username"));
        }
    }

    public boolean addUser(String username, String password) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USERS (username, password) VALUES (?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean loginUser(String username, String password) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM USERS WHERE username = ? AND password = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }

    public boolean userExists(String username) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM USERS WHERE username = ?")) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}


