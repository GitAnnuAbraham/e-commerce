<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <title>User Signup</title>
</head>
<body>
   <h1>User Signup</h1>

       <form:form action="/signup" method="post" modelAttribute="user" class="container">
           <label for="username">Username:</label>
           <form:input type="text" id="username" path="username" required="true" />

           <label for="password">Password:</label>
           <form:input type="password" id="password" path="password" required="true" />

           <label for="email">Email:</label>
           <form:input type="email" id="email" path="email" required="true" />

           <label for="firstName">First Name:</label>
           <form:input type="text" id="firstName" path="firstName" required="true" />

           <label for="lastName">Last Name:</label>
           <form:input type="text" id="lastName" path="lastName" required="true" />

           <input type="submit" value="Sign Up" />
       </form:form>

</body>
</html>
