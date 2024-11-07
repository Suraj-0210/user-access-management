package com.user_access_servlet.dao;


import com.user_access_servlet.model.User;
import java.sql.*;

public class UserDAO {

    private static final String DB_URL = "jdbc:postgresql://dpg-csg7sphu0jms738s1e20-a.singapore-postgres.render.com/user_access_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "FXey7czYXv79X7WKgI3aUgP1mNu21Iwh";

    static {
        try {
            // Registering the PostgreSQL JDBC driver explicitly
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;
        String query = "SELECT id, username, role FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String role = resultSet.getString("role");
                user = new User(id, username, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, 'Employee')";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

