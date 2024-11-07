<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");	
	
		if(session.getAttribute("username") == null)
		{
			response.sendRedirect("login.jsp");
			return;
		}
	
		if (!("Manager".equals(session.getAttribute("role")))) {
	        response.sendRedirect("login.jsp");
	        return;
	    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pending Access Requests</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">

	
    <!-- Navbar -->
	<nav class="bg-blue-600 p-4 flex justify-between items-center">
	    <div class="text-white text-xl font-bold">User Access Management</div>
	    <div class="flex items-center space-x-4 text-white">
	        <span>Username: ${username} |</span>  
	        <span>User ID: ${user_id} |</span>  
	        <span>Role: ${role}</span>
	        <form action="logout" method="get" class="inline-block">
	            <button type="submit" class="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 ml-4">
	                Logout
	            </button>
	        </form>
	    </div>
	</nav>

    <!-- Main Content -->
    <div class="container mx-auto my-8 p-8 bg-white rounded-lg shadow-md">
        <h2 class="text-2xl font-semibold text-gray-800 mb-6">Pending Access Requests</h2>

        <!-- Conditional Rendering -->
        <c:if test="${empty requestsList}">
            <!-- Message when no requests are available -->
            <div class="text-center text-gray-500 py-8">
                <p class="text-lg font-semibold">No requests yet.</p>
                <p class="mt-2">All access requests will appear here once submitted by employees.</p>
            </div>
        </c:if>

        <!-- Table when requests are available -->
        <c:if test="${not empty requestsList}">
            <div class="overflow-x-auto">
                <table class="min-w-full bg-white border border-gray-200 rounded-md">
                    <thead>
                        <tr class="bg-gray-200 text-gray-700">
                            <th class="px-4 py-2 text-left">Employee Name</th>
                            <th class="px-4 py-2 text-left">Software Name</th>
                            <th class="px-4 py-2 text-left">Access Type</th>
                            <th class="px-4 py-2 text-left">Reason</th>
                            <th class="px-4 py-2 text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="request" items="${requestsList}">
                            <tr class="border-b border-gray-200">
                                <td class="px-4 py-2">${request.employeeName}</td>
                                <td class="px-4 py-2">${request.softwareName}</td>
                                <td class="px-4 py-2">${request.accessType}</td>
                                <td class="px-4 py-2">${request.reason}</td>
                                <td class="px-4 py-2 text-center">
                                    <form action="approval" method="POST" class="inline">
                                        <input type="hidden" name="request_id" value="${request.id}">
                                        <button type="submit" name="action" value="approve" class="bg-green-500 text-white px-4 py-1 rounded mr-2 hover:bg-green-600">
                                            Approve
                                        </button>
                                        <button type="submit" name="action" value="reject" class="bg-red-500 text-white px-4 py-1 rounded hover:bg-red-600">
                                            Reject
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

</body>
</html>
