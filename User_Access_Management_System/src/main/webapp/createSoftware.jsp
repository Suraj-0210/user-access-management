<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		if(session.getAttribute("username") == null)
		{
			response.sendRedirect("login.jsp");
			return;
		}
		
		if (!("Admin".equals(session.getAttribute("role")))) {
            response.sendRedirect("login.jsp");
            return;
        }
	%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Software</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
</head>


<body class="bg-gray-100">

	<!-- Navbar -->
	<nav class="bg-blue-600 p-4 flex justify-between items-center">
	    <div class="text-white text-xl font-bold">User Access Management</div>
	    <div class="flex items-center space-x-4 text-white">
	        <span>Username: ${username} |</span>  
	        <span>User ID: ${user_id} |</span>  
	        <span>Role: ${role} |</span>
	        <form action="logout" method="get" class="inline-block">
	            <button type="submit" class="bg-red-500 text-white py-1 px-3 rounded hover:bg-red-600 ml-4">
	                Logout
	            </button>
	        </form>
	    </div>
	</nav>

    <div class="container mx-auto p-6">
        <h1 class="text-2xl font-bold mb-4">Create New Software</h1>

        <!-- Success/Error Message -->
        <c:if test="${param.success}">
            <div class="bg-green-500 text-white p-2 mb-4 rounded">Software added successfully!</div>
        </c:if>
        <c:if test="${param.error}">
            <div class="bg-red-500 text-white p-2 mb-4 rounded">Error adding software. Please try again.</div>
        </c:if>

        <!-- Software Creation Form -->
        <form action="SoftwareServlet" method="POST" class="bg-white p-6 rounded shadow-md">
            <div class="mb-4">
                <label for="name" class="block text-sm font-semibold text-gray-700">Software Name</label>
                <input type="text" name="name" id="name" class="w-full p-2 border border-gray-300 rounded" required>
            </div>

            <div class="mb-4">
                <label for="description" class="block text-sm font-semibold text-gray-700">Description</label>
                <textarea name="description" id="description" rows="4" class="w-full p-2 border border-gray-300 rounded" required></textarea>
            </div>

		    <div class="mb-4">
		    	<label class="block text-sm font-semibold text-gray-700">Access Levels</label>
			    <div class="flex items-center space-x-4">
			        <label><input type="radio" name="accessLevels" value="Read" class="mr-2"> Read</label>
			        <label><input type="radio" name="accessLevels" value="Write" class="mr-2"> Write</label>
			        <label><input type="radio" name="accessLevels" value="Admin" class="mr-2"> Admin</label>
			    </div>
			</div>


            <div class="mb-4">
                <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">Add Software</button>
            </div>
        </form>
    </div>

</body>
</html>
