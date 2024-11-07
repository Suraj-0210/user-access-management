package com.user_access_servlet.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.user_access_servlet.model.Request;

public class RequestDAO {
    private static final String DB_URL = "jdbc:postgresql://dpg-csg7sphu0jms738s1e20-a.singapore-postgres.render.com/user_access_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "FXey7czYXv79X7WKgI3aUgP1mNu21Iwh";

    
    public boolean addRequest(int userId, int softwareId, String accessType, String reason) {
        String sql = "INSERT INTO requests (user_id, software_id, access_type, reason, status) VALUES (?, ?, ?, ?, 'Pending')";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, softwareId);
            ps.setString(3, accessType);
            ps.setString(4, reason);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Request> getAllPendingRequests() {
        List<Request> pendingRequests = new ArrayList<>();

        String sql = "SELECT r.id, u.username AS employee_name, s.name AS software_name, r.access_type, r.reason " +
                     "FROM requests r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "JOIN software s ON r.software_id = s.id " +
                     "WHERE r.status = 'Pending'";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Request request = new Request();
                request.setId(rs.getInt("id"));
                request.setEmployeeName(rs.getString("employee_name"));
                request.setSoftwareName(rs.getString("software_name"));
                request.setAccessType(rs.getString("access_type"));
                request.setReason(rs.getString("reason"));
                pendingRequests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pendingRequests;
    }

    public void updateRequestStatus(int requestId, String status) {
        String sql = "UPDATE requests SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, requestId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
