package com.user_access_servlet;


import com.user_access_servlet.dao.UserDAO;
import com.user_access_servlet.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("user_id", user.getId());
            session.setAttribute("role", user.getRole());
            
//            System.out.println("Role: "+ session.getAttribute("role"));

            if ("Employee".equals(user.getRole())) {
                response.sendRedirect("request");
            } else if ("Manager".equals(user.getRole())) {
                response.sendRedirect("approval");
            } else if ("Admin".equals(user.getRole())) {
                response.sendRedirect("createSoftware.jsp");
            }
        } else {
            response.sendRedirect("login.jsp?error=Invalid username or password");
        }
    }
}
