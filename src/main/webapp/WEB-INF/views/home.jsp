<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
    <title>Home Page</title>
</head>
<body>

    <nav>
        <!-- Navigation bar -->
        <ul>
            <li><a href="/">Home</a></li>

            <!-- Load categories dynamically using JSTL -->
            <c:forEach var="category" items="${categories}">
                <li><a href="/category/${category.categoryId}">${category.name}</a></li>
            </c:forEach>
            <c:if test="${empty sessionScope.user}">
                <li style="float:right;"><a href="/login">Login</a></li>
                <li style="float:right;"><a href="/signup">Signup</a></li>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <li style="float:right;"><a href="/logout"><img src="cart-icon.png" alt="Logout"></a></li>
                <li style="float:right;"><a href="/cart/view"><img src="cart-icon.png" alt="Cart"></a></li>
                <li style="float:right;"><a href="/order/viewUserOrder"><img src="cart-icon.png" alt="Orders"></a></li>
            </c:if>
        </ul>
    </nav>

    <div>
        <c:if test="${not empty categories}">
            <c:forEach var="category" items="${categories}">
                <h2><c:out value="${category.name}"/></h2>
                <c:if test="${not empty category.products}">
                    <div class="product-grid">
                        <c:forEach var="product" items="${category.products}">
                            <div class="product-card">
                                <img src="<c:out value="${product.productId}"/>" alt="<c:out value="${product.name}"/> Image">
                                <h3><c:out value="${product.name}"/></h3>
                                <p><c:out value="${product.description}"/></p>
                                <p>$<c:out value="${product.price}"/></p>
                                <form action="/products/addToCart" method="post">
                                    <input type="hidden" name="productId" value="${product.productId}">
                                    <button type="submit">Add to Cart</button>
                                </form>
                                </div>
                        </c:forEach>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
    </div>

    <footer>
        <!-- Footer content or links -->
    </footer>

</body>
</html>
