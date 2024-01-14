<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <title>Add Product</title>
</head>
<body>

    <h1>Add Product</h1>

    <c:if test="${not empty requestScope.errorMessage}">
        <p style="color: red;">${requestScope.errorMessage}</p>
    </c:if>

    <form:form action="/admin/manage-products" method="post" modelAttribute="product">
        <form:hidden path="productId"/>

        <label for="productName">Product Name:</label>
        <form:input type="text" id="productName" path="name" required="true"/>

        <label for="productDesc">Product Description:</label>
        <form:input type="text" id="productDesc" path="description" required="true"/>

        <label for="productCategory">Category:</label>
        <form:select path="category.categoryId">
            <form:options items="${categories}" itemValue="categoryId" itemLabel="name"/>
        </form:select>

        <label for="productPrice">Price:</label>
        <form:input type="number" id="productPrice" path="price" required="true"/>

        <label for="stockQuantity">Stock Quantity:</label>
        <form:input type="number" id="stockQuantity" path="stockQuantity" required="true"/>

        <input type="submit" value="Add Product"/>
    </form:form>

    <!-- Display existing products for each category -->
    <c:if test="${not empty productDetails}">
        <table border="1">
            <thead>
                <tr>
                    <th>Category Name</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Stock Quantity</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${productDetails}">
                    <tr>
                        <td>${product.category.name}</td>
                        <td>${product.name}</td>
                        <td>${product.description}</td>
                        <td>${product.price}</td>
                        <td>${product.stockQuantity}</td>
                        <td>
                            <a href="/admin/manage-products?productId=${product.productId}">Edit</a>
                            |
                            <a href="/admin/delete-product?productId=${product.productId}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

</body>
</html>