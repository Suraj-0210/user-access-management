package com.user_access_servlet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user_access_servlet.model.Software;

public class SoftwareDAO {
    private static final String DB_URL = "jdbc:postgresql://dpg-csg7sphu0jms738s1e20-a.singapore-postgres.render.com/user_access_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "FXey7czYXv79X7WKgI3aUgP1mNu21Iwh";

    public SoftwareDAO() {
        // Default constructor; no additional initialization needed for basic JDBC connection
    }

    public List<Software> getSoftwareList() {
        List<Software> softwareList = new ArrayList<>();
        String sql = "SELECT id, name FROM software";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Software software = new Software();
                software.setId(resultSet.getInt("id"));
                software.setName(resultSet.getString("name"));
                softwareList.add(software);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return softwareList;
    }

    public void addSoftware(Software software) {
        String sql = "INSERT INTO software (name, description, access_levels) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, software.getName());
            statement.setString(2, software.getDescription());
            statement.setString(3, software.getAccessLevels());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
