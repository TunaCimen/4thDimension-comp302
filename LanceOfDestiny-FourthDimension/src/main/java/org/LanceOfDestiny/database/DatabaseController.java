package org.LanceOfDestiny.database;

import java.io.*;
import java.sql.*;

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

    public boolean userExists(String username) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM User WHERE username = ?")) {
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            return resultSet.next();
        }
    }
}


