<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <title>Manage Categories</title>
</head>
<body>

    <h1>Manage Categories</h1>

    <c:if test="${not empty requestScope.successMessage}">
        <p style="color: green;">${requestScope.successMessage}</p>
    </c:if>

    <form:form action="/admin/manage-categories" method="post" modelAttribute="category">
        <form:hidden path="categoryId"/>
        <label for="name">Category Name:</label>
        <form:input type="text" id="name" path="name" required="true"/>

        <input type="submit" value="Save Category"/>
    </form:form>
    <c:if test="${not empty categories}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Category ID</th>
                        <th>Category Name</th>
                        <th>Action</th>
                        <!-- Add more columns as needed -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.categoryId}</td>
                            <td>${category.name}</td>
                            <td>
                                <a href="/admin/manage-categories?categoryId=${category.categoryId}">Edit</a>
                                |
                                <a href="/admin/delete-category?categoryId=${category.categoryId}">Delete</a>
                            </td>
                            <!-- Add more columns as needed -->
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>


</body>
</html>
