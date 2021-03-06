<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details Page">
    <tags:header/>
    <table>
        <thead>
        <tr>
            <td>Param</td>
            <td>Value</td>
        </tr>
        </thead>
        <tr>
            <td>Image</td>
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
        </tr>
        <tr>
            <td>Description</td>
            <td>${product.description}</td>
        </tr>
        <tr>
            <td>Price</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
        <tr>
            <td>Phone Code</td>
            <td>${product.code}</td>
        </tr>
        <tr>
            <td>In stock</td>
            <td>${product.stock}</td>
        </tr>
    </table>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <p>
            <input name="quantity" placeholder="Quantity" value="${not empty param.quantity ? param.quantity : 1}">
            <button type="submit">Add to cart</button>
            <c:if test="${not empty error}">
                <br><span style="color: red">${error}</span>
            </c:if>
        </p>
    </form>
    <tags:recentViewed recentProducts="${param.recentProducts}"/>
</tags:master>
<tags:foter/>