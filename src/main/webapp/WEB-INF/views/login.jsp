<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>

    <h1>Login</h1>

    <form action="/login" method="post" class="container">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>

        <input type="submit" value="Login" />
    </form>

    <% if(request.getParameter("error") != null) { %>
        <p style="color: red;">Invalid credentials. Please try again.</p>
    <% } %>

</body>
</html>
