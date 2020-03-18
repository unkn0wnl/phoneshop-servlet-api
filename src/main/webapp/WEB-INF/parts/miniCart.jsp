<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"
         trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"
         language="java"
         isErrorPage="false" %>
<jsp:useBean id="cart" class="com.es.phoneshop.model.cart.Cart" scope="session"/>
<a class="cart" href="${pageContext.request.contextPath}/cart" style="float: right">Cart</a>
<a class="cart" style="float: right">
    <div>Total cost: ${cart.totalProductsCost}</div>
    <div>Total quantity: ${cart.totalQuantity}</div>
    <h3>Products:</h3>
    <div>
        <c:forEach var="item" items="${cart.cartItems}">
            <div>${item.product.description}</div>
        </c:forEach>
    </div>
</a>