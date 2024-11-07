<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List, com.user_access_servlet.model.Software" %>

<%
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		if(session.getAttribute("username") == null)
		{
			response.sendRedirect("login.jsp");
			return;
		}
	
		if (!("Employee".equals(session.getAttribute("role")))) {
	        response.sendRedirect("login.jsp");
	        return;
	    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Access</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">


    <!-- Navbar -->
	<nav class="bg-blue-600 p-4 flex justify-between items-center">
	    <div class="text-white text-xl font-bold">User Access Management</div>
	    <div class="flex items-center space-x-4 text-white">
	        <span>Username: ${username} |</span>  
	        <span>User ID: ${user_id} |</span>  
	        <span>Role: ${role} </span>
	        <form action="logout" method="get" class="inline-block">
	            <button type="submit" class="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 ml-4">
	                Logout
	            </button>
	        </form>
	    </div>
	</nav>

    <!-- Main Content -->
    <div class="container mx-auto my-8 p-8 bg-white rounded-lg shadow-md">
        <h2 class="text-2xl font-semibold text-gray-800">Request Software Access</h2>
        <form action="request" method="POST" class="mt-6">
            <div class="mb-4">
                <label for="software" class="block text-gray-700">Select Software</label>
                <select id="software" name="software_id" class="w-full p-2 border border-gray-300 rounded mt-2" required>
                    <!-- Populate dynamically from database -->
                    <c:forEach var="software" items="${softwareList}">
                        <option value="${software.id}">${software.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-4">
                <label for="access_type" class="block text-gray-700">Access Type</label>
                <select id="access_type" name="access_type" class="w-full p-2 border border-gray-300 rounded mt-2">
                    <option value="Read">Read</option>
                    <option value="Write">Write</option>
                    <option value="Admin">Admin</option>
                </select>
            </div>

            <div class="mb-4">
                <label for="reason" class="block text-gray-700">Reason for Request</label>
                <textarea id="reason" name="reason" rows="4" class="w-full p-2 border border-gray-300 rounded mt-2" required></textarea>
            </div>

            <button type="submit" class="bg-blue-600 text-white p-2 rounded">Submit Request</button>
        </form>

        <!-- Display success or error messages -->
        <c:if test="${not empty param.success}">
            <div class="mt-4 text-green-600">${param.success}</div>
        </c:if>
        <c:if test="${not empty param.error}">
            <div class="mt-4 text-red-600">${param.error}</div>
        </c:if>

    </div>

</body>
</html>