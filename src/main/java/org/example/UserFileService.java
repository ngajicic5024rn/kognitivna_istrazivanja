package org.example;

import java.io.*;

public class UserFileService {

    private static final String FILE_PATH = "users.txt";

    public static void saveUser(String username, String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + ":" + password);
            writer.newLine();
        }
    }

    public static boolean userExists(String username) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2 && parts[0].equals(username)) return true;
            }
        }
        return false;
    }

    public static boolean authenticate(String username, String password) throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password))
                    return true;
            }
        }
        return false;
    }
}