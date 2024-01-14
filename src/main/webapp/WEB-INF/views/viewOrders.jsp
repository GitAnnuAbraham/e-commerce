<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <title>View Orders</title>
</head>
<body>

    <h1>Your Orders</h1>

    <c:if test="${not empty orders}">
        <table border="1">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Unit Price</th>
                    <th>Order Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                        <c:forEach var="orderItem" items="${order.orderItems}">
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${orderItem.product.name}</td>
                                <td>${orderItem.quantity}</td>
                                <td>${orderItem.unitPrice}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.status}</td>
                            </tr>
                        </c:forEach>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

</body>
</html>
