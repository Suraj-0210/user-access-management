package com.user_access_servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Database URL, username, and password
    private static final String DB_URL = "jdbc:postgresql://dpg-csg7sphu0jms738s1e20-a.singapore-postgres.render.com/user_access_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "FXey7czYXv79X7WKgI3aUgP1mNu21Iwh";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            // Connect to the database
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // SQL query to insert a new user with a default role of "Employee"
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "Employee");  // Default role is Employee
            
            // Execute the query
            int result = statement.executeUpdate();
            
            if (result > 0) {
                // Redirect to the login page upon successful registration
                response.sendRedirect("login.jsp?message=Registration successful. Please log in.");
            } else {
                // If insertion fails, redirect back to the sign-up page with an error
                response.sendRedirect("signup.jsp?error=Registration failed. Please try again.");
            }
            
            // Clean up
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("signup.jsp?error=An error occurred. Please try again.");
        }
    }
}
