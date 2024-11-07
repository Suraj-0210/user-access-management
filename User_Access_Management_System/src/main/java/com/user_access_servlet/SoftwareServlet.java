package com.user_access_servlet;

import com.user_access_servlet.dao.SoftwareDAO;
import com.user_access_servlet.model.Software;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/SoftwareServlet")
public class SoftwareServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // DAO instance
    private SoftwareDAO softwareDAO = new SoftwareDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check if the user is logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Check if the logged-in user is an "Admin"
        if (!"Admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve form data
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String[] accessLevels = request.getParameterValues("accessLevels");

        // Convert accessLevels array to a comma-separated string
        String accessLevelsString = (accessLevels != null) ? String.join(",", accessLevels) : "";

        // Create a new Software object and populate its fields
        Software software = new Software(name, description, accessLevelsString);

        // Add software to the database using SoftwareDAO
        try {
            softwareDAO.addSoftware(software);
            // Redirect on success
            response.sendRedirect("createSoftware.jsp?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect on error
            response.sendRedirect("createSoftware.jsp?error=true");
        }
    }
}
