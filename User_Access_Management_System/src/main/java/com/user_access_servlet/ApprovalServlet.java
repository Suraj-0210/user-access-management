package com.user_access_servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user_access_servlet.dao.RequestDAO;
import com.user_access_servlet.model.Request;

@WebServlet("/approval")
public class ApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RequestDAO requestDAO = new RequestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// Check if the user is loggedin.
    	HttpSession session = request.getSession();
        if(session.getAttribute("username") == null) {
        	response.sendRedirect("login.jsp");
        	return;
        }
        
        if (!("Manager".equals(session.getAttribute("role")))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        
    	// Fetch all pending requests
        List<Request> pendingRequests = requestDAO.getAllPendingRequests();

        // Set the pending requests as a request attribute
        request.setAttribute("requestsList", pendingRequests);

        // Forward to the JSP page for display
        request.getRequestDispatcher("pendingRequests.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestId = request.getParameter("request_id");
        String action = request.getParameter("action");

        // Determine the new status based on the action
        String newStatus = getStatus(action);

        if (newStatus != null) {
            // Update the request status in the database
            requestDAO.updateRequestStatus(Integer.parseInt(requestId), newStatus);
        }

        // Redirect back to the list of pending requests
        response.sendRedirect("approval");
    }

    private String getStatus(String action) {
        switch (action) {
            case "approve":
                return "Approved";
            case "reject":
                return "Rejected";
            default:
                return null;
        }
    }
}
