package org.LanceOfDestiny.domain.login;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Model {
    private static final String FILE_NAME = "user_data.txt";

    private static Model instance;

    public static Model getInstance(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }

    private Model() {
    }



    public boolean addUser(String username, String password) throws IOException {
        if (this.userExists(username)) {
            return false;
        } else {
            try {
                FileWriter writer = new FileWriter("user_data.txt", true);

                boolean var4;
                try {
                    writer.write("Username: " + username + ", Password: " + password + "\n");
                    var4 = true;
                } catch (Throwable var7) {
                    try {
                        writer.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }

                    throw var7;
                }

                writer.close();
                return var4;
            } catch (IOException var8) {
                var8.printStackTrace();
                return false;
            }
        }
    }

    public boolean loginUser(String username, String password) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"));

            boolean var5;
            label52: {
                String line;
                try {
                    while((line = reader.readLine()) != null) {
                        if (line.contains("Username: " + username) && line.contains("Password: " + password)) {
                            var5 = true;
                            break label52;
                        }
                    }
                } catch (Throwable var7) {
                    try {
                        reader.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }

                    throw var7;
                }

                reader.close();
                return false;
            }

            reader.close();
            return var5;
        } catch (IOException var8) {
            var8.printStackTrace();
            return false;
        }
    }

    private boolean userExists(String username) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("user_data.txt"));

            label35: {
                boolean var4;
                try {
                    String line;
                    do {
                        if ((line = reader.readLine()) == null) {
                            break label35;
                        }
                    } while(!line.contains("Username: " + username));

                    var4 = true;
                } catch (Throwable var6) {
                    try {
                        reader.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }

                    throw var6;
                }

                reader.close();
                return var4;
            }

            reader.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

        return false;
    }
}
