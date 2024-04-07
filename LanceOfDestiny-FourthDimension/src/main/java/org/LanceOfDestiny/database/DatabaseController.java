package org.LanceOfDestiny.database;

import java.io.*;

//ToDo: Save load game data methods
public class DatabaseController {

    private static final String USER_DATA_FILE = "user_data.txt";

    public boolean addUser(String username, String password) throws IOException {
        if (userExists(username)) {
            return false;
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, true))) {
                writer.write("Username: " + username + ", Password: " + password + "\n");
                return true;
            }
        }
    }

    public boolean loginUser(String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Username: " + username) && line.contains("Password: " + password)) {
                    return true;
                }
            }
            return false;
        }
    }

    private boolean userExists(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Username: " + username)) {
                    return true;
                }
            }
            return false;
        }
    }
}

