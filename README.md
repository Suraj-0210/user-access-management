Here's the refined and professional version of your project setup in markdown format for the README:

# User Access Management System

## Overview

The **User Access Management System** is a web-based application designed to manage user access to various software applications within an organization. It includes user registration, login functionality, software management for admins, and access request submissions with approval workflows.

## Features

- **User Registration**: Users can create an account, and their role is set as "Employee" by default.
- **User Authentication**: Secure login for users with different roles (Employee, Manager, Admin).
- **Software Management** (Admin Only): Admins can create new software applications and define access levels.
- **Access Request** (Employee Only): Employees can request access to software applications.
- **Access Request Approval** (Manager Only): Managers can approve or reject access requests.
- **Database Integration**: PostgreSQL is used for data storage, storing users, software, and access requests.

## Technologies Used

- **Java** (Servlets and JSP for backend)
- **PostgreSQL** (Database)
- **HTML/CSS/JavaScript** (Frontend)
- **Apache Tomcat** (Servlet Container)
- **Maven** (Project management)

## Prerequisites

To run this project, you will need the following:

- **JDK 8 or higher**
- **Apache Tomcat** or any other Servlet container
- **PostgreSQL Database**
- **Maven** for dependency management

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository_url>

### 2. Import Project into IDE

Import the project into your preferred IDE (e.g., Eclipse, IntelliJ IDEA).

### 3. Set Up Database

1. **Create Database and Tables**:
   Execute the following SQL script to set up the PostgreSQL database.

```sql
-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT CHECK (role IN ('Employee', 'Manager', 'Admin')) NOT NULL
);

-- Create software table
CREATE TABLE software (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT,
    access_levels TEXT CHECK (access_levels IN ('Read', 'Write', 'Admin')) NOT NULL
);

-- Create requests table
CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    software_id INTEGER REFERENCES software(id) ON DELETE CASCADE,
    access_type TEXT CHECK (access_type IN ('Read', 'Write', 'Admin')) NOT NULL,
    reason TEXT,
    status TEXT CHECK (status IN ('Pending', 'Approved', 'Rejected')) DEFAULT 'Pending'
);
```

2. **Update Database Connection Details**:
   Modify the following variables in your servlet code (e.g., `SoftwareServlet.java`, `LoginServlet.java`) to match your database configuration:
   - `DB_URL`
   - `DB_USER`
   - `DB_PASSWORD`

### 4. Deploy to Apache Tomcat

- Deploy the project to Apache Tomcat by configuring it correctly in your IDE and starting the server.

### 5. Access the System

1. Open your web browser and navigate to the application URL.
2. **Sign Up**: Go to `signup.jsp` to create a new user account.
3. **Login**: Log in via `login.jsp` as either an Employee, Manager, or Admin.
4. **Employee**: Request access to software from `requestAccess.jsp`.
5. **Manager**: Approve or reject access requests from `pendingRequests.jsp`.
6. **Admin**: Create new software entries from `createSoftware.jsp`.

## Directory Structure

```plaintext
├── src
│   ├── com.user_access_servlet
│   │   ├── dao
│   │   │   ├── RequestDAO.java
│   │   │   ├── SoftwareDAO.java
│   │   │   └── UserDAO.java
│   │   ├── model
│   │   │   ├── Request.java
│   │   │   ├── Software.java
│   │   │   └── User.java
│   │   ├── servlet
│   │   │   ├── ApprovalServlet.java
│   │   │   ├── LoginServlet.java
│   │   │   ├── LogoutServlet.java
│   │   │   ├── RequestServlet.java
│   │   │   ├── SignUpServlet.java
│   │   │   └── SoftwareServlet.java
│   └── web
│       ├── WEB-INF
│       ├── login.jsp
│       ├── signup.jsp
│       ├── createSoftware.jsp
│       ├── requestAccess.jsp
│       ├── pendingRequests.jsp
├── pom.xml
└── README.md
```

## JSP Pages

### `signup.jsp`

- User registration page for signing up as an Employee.

### `login.jsp`

- Login page where users authenticate.

### `createSoftware.jsp`

- Admin can create new software here.

### `requestAccess.jsp`

- Employees can request access to software applications.

### `pendingRequests.jsp`

- Managers can approve or reject access requests here.

## How the System Works

### 1. **User Registration**:

- New users can register via `signup.jsp`, where they provide their username and password. Their role is set as "Employee" by default.

### 2. **User Authentication**:

- Users log in via `login.jsp`, and based on their role (Employee, Manager, Admin), they are redirected to the corresponding page:
  - **Employee**: Redirected to `requestAccess.jsp` to request software access.
  - **Manager**: Redirected to `pendingRequests.jsp` to approve/reject requests.
  - **Admin**: Redirected to `createSoftware.jsp` to add new software.

### 3. **Software Management (Admin Only)**:

- Admins can add software through `createSoftware.jsp`, entering the name, description, and access levels (Read, Write, Admin).
- The software is stored in the PostgreSQL database's `software` table.

### 4. **Access Request (Employee)**:

- Employees can submit access requests for software via `requestAccess.jsp`. They select the software, specify access type, and provide a reason. These requests are stored in the `requests` table with a "Pending" status.

### 5. **Access Request Approval (Manager)**:

- Managers can view pending requests on `pendingRequests.jsp`. They can approve or reject them, which updates the status of the request in the database.

## Code Explanation

### Servlet: `LoginServlet.java`

- Handles user authentication and session management. Redirects users based on their role after a successful login.

```java
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    // Handle POST request to log in
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user credentials from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validate user credentials
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsernameAndPassword(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", user.getRole());
            
            // Redirect user based on role
            if ("Employee".equals(user.getRole())) {
                response.sendRedirect("request");
            } else if ("Manager".equals(user.getRole())) {
                response.sendRedirect("approval");
            } else if ("Admin".equals(user.getRole())) {
                response.sendRedirect("createSoftware.jsp");
            }
        } else {
            response.sendRedirect("login.jsp?error=Invalid credentials");
        }
    }
}
```

### Servlet: `ApprovalServlet.java`

- Allows managers to view and approve/reject access requests.

```java
@WebServlet("/approval")
public class ApprovalServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch all pending requests and forward to JSP
        List<Request> pendingRequests = requestDAO.getAllPendingRequests();
        request.setAttribute("requestsList", pendingRequests);
        request.getRequestDispatcher("pendingRequests.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle approval/rejection actions
        String action = request.getParameter("action");
        String status = (action.equals("approve")) ? "Approved" : "Rejected";
        requestDAO.updateRequestStatus(Integer.parseInt(request.getParameter("request_id")), status);
        response.sendRedirect("approval");
    }
}
```

## Video Guide

You can access the video guide for the system on [this link](#).
