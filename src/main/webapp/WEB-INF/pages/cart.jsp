<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <p>Cart</p>
    <c:if test="${errors.hasErrors()}">
        <span style="color:red">Error updating cart</span>
    </c:if>
    <c:if test="${not empty param.message}">
        <span style="color:forestgreen">${param.message}</span>
    </c:if>
    <form method="post" action="cart">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td>Quantity</td>
                <td class="price">Price</td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/products/${cartItem.product.id}">${cartItem.product.description}</a>
                    </td>
                    <td>
                        <input name="quantity"
                               value="${not empty paramValues.quantity[status.index] ? paramValues.quantity[status.index] : cartItem.quantity}"
                               style="text-align: right">
                        <input type="hidden" name="productId" value="${cartItem.product.id}">
                        <c:if test="${errors.hasProductError(cartItem.product.id)}">
                            <br><span style="color:red">${errors.getErrorByProductId(cartItem.product.id).errorMessage}</span>
                        </c:if>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${cartItem.product.price}" type="currency"
                                          currencySymbol="${cartItem.product.currency.symbol}"/>
                    </td>
                    <td>
                        <button formaction="cart/deleteCartItem/${cartItem.product.id}">Remove</button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3" style="text-align: right">Total</td>
                <td>${cart.totalCost}</td>
            </tr>
        </table>
        <br>
        <button>Update</button>
    </form>
    <form>
        <button formaction="checkout">Checkout</button>
    </form>
</tags:master>