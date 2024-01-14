<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <title>View Cart</title>

    <script>
        function showEditForm(cartItemId) {
            // Hide all other forms
            var allForms = document.getElementsByClassName('editForm');
            for (var i = 0; i < allForms.length; i++) {
                allForms[i].style.display = 'none';
            }

            // Show the form corresponding to the clicked 'Edit' button
            var formId = 'editForm_' + cartItemId;
            var form = document.getElementById(formId);
            form.style.display = 'block';
        }
    </script>

</head>
<body>

    <h1>Your Cart</h1>

    <c:if test="${not empty cartItems}">
        <table border="1">
            <thead>
                <tr>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Price</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cartItem" items="${cartItems}">
                    <tr>
                        <td>${cartItem.product.name}</td>
                        <td>${cartItem.quantity}</td>
                        <td>${cartItem.product.price}</td>
                        <td>${cartItem.quantity * cartItem.product.price}</td>
                        <td>
                            <a href="javascript:void(0);" onclick="showEditForm(${cartItem.cartItemId});">Edit</a> |
                            |
                            <a href="/cart/delete/${cartItem.cartItemId}">Delete</a>
                        </td>
                    </tr>

                    <tr style="display: none;" id="editForm_${cartItem.cartItemId}" class="editForm">
                        <td colspan="5">
                            <form:form action="/cart/edit/${cartItem.cartItemId}" method="post" modelAttribute="cartItem">
                                <form:hidden path="cartItemId" value="${cartItem.cartItemId}"/>
                                <label for="quantity">Quantity:</label>
                                    <form:input type="text" id="quantity" path="quantity" value="${cartItem.quantity}" required="true"/>
                                    <input type="submit" value="Update"/>
                                </form:form>
                        </td>
                    </tr>

                </c:forEach>
            </tbody>
        </table>
        <br/>
        <!-- Place Order button -->
        <form:form action="/order/checkout/${userCartId}" method="get">
            <input type="submit" value="Place Order"/>
        </form:form>
    </c:if>
</body>
</html>