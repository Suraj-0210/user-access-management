<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">

    <!-- Login Container -->
    <div class="w-full max-w-md p-8 bg-white rounded-lg shadow-md">
        <h2 class="text-3xl font-semibold text-center text-blue-600 mb-6">Login</h2>

        <%-- Display error message if any --%>
        <c:if test="${param.error != null}">
            <div class="bg-red-100 text-red-600 p-3 mb-4 rounded">
                <%= request.getParameter("error") %>
            </div>
        </c:if>
        
        <c:if test="${param.message != null}">
            <div class="bg-green-100 text-green-600 p-3 mb-4 rounded">
                <%= request.getParameter("message") %>
            </div>
        </c:if>

        <!-- Login Form -->
        <form action="login" method="post" class="space-y-6">
            <div>
                <label for="username" class="block text-gray-700 font-medium">Username</label>
                <input type="text" name="username" id="username" required
                    class="w-full mt-2 p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            </div>

            <div>
                <label for="password" class="block text-gray-700 font-medium">Password</label>
                <input type="password" name="password" id="password" required
                    class="w-full mt-2 p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent">
            </div>

            <div class="flex justify-between items-center">
                <button type="submit"
                    class="w-full py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition duration-300">
                    Login
                </button>
            </div>
        </form>

        <div class="mt-6 text-center text-gray-600">
            <p>Don't have an account? <a href="./signup.jsp" class="text-blue-600 hover:underline">Sign up here</a></p>
        </div>
    </div>

</body>
</html>
