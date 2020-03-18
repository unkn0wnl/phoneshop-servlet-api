
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Advanced search">

    <c:if test="${not empty errors}">
        <br><span style="color:red">${"Search fail!"}</span>
    </c:if>

    <br>
    <form method="post" action="advancedSearch">
        <p>
            <label for="description">Description:</label>
            <input id="description" name="description" value="${param.description}">
        </p>
        <p>
            <label for="minPrice">Min price:</label>
            <input id="minPrice" name="minPrice" value="${param.minPrice}">
            <c:if test="${errors.containsKey('minPrice')}">
                <span style="color: red">${errors.minPrice}</span>
            </c:if>
        </p>
        <p>
            <label for="maxPrice">Max price:</label>
            <input id="maxPrice" name="maxPrice" value="${param.maxPrice}">
            <c:if test="${errors.containsKey('maxPrice')}">
                <span style="color: red">${errors.maxPrice}</span>
            </c:if>
        </p>
        <p>
            <select id="searchOption" name="searchOption">
                <c:forEach var="option" items="${wordSearchOption}">
                    <option name="${option}" value="${option}">${option.frontName}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <button>Search</button>
        </p>
    </form>
    <c:if test="${empty error}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <a href="products/${product.id}">
                            <img class="product-tile"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                        </a>
                    </td>
                    <td>
                        <a href="products/${product.id}">${product.description}</a></td>
                    <td class="price">
                        <a href="<c:url value="/products/history/${product.id}"/>">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:master> 