package com.user_access_servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user_access_servlet.dao.RequestDAO;
import com.user_access_servlet.dao.SoftwareDAO;
import com.user_access_servlet.model.Software;

@WebServlet("/request")
public class RequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private RequestDAO requestDAO;
    private SoftwareDAO softwareDAO;

    @Override
    public void init() {
        requestDAO = new RequestDAO();
        softwareDAO = new SoftwareDAO();
    }

    // Handle form submissions
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int softwareId = Integer.parseInt(request.getParameter("software_id"));
        String accessType = request.getParameter("access_type");
        String reason = request.getParameter("reason");

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");

        boolean isRequestSubmitted = requestDAO.addRequest(userId, softwareId, accessType, reason);

        if (isRequestSubmitted) {
            response.sendRedirect("request?success=Request submitted successfully!");
        } else {
            response.sendRedirect("request?error=Failed to submit request. Please try again.");
        }
    }

    // Handle GET request to display the software list
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        if (!("Employee".equals(session.getAttribute("role")))) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Software> softwareList = softwareDAO.getSoftwareList();
        request.setAttribute("softwareList", softwareList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("requestAccess.jsp");
        dispatcher.forward(request, response);
    }
}
