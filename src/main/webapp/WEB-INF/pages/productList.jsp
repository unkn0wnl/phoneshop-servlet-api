<%@ page contentType="text/html;charset=UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <tags:header/>
    <form>
        <input name="search" value="${param.search}">
        <button type="submit">Submit</button>
    </form>
    <a href="advancedSearch">Advance search</a>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sort text="&#8657;" sortBy="description" order="ascending"/>
                <tags:sort text="&#8659;" sortBy="description" order="descending"/>
            </td>
            <td class="price">
                Price
                <tags:sort text="&#8657;" sortBy="price" order="ascending"/>
                <tags:sort text="&#8659;" sortBy="price" order="descending"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td><a href="products/${product.id}">${product.description}</a></td>
                <td class="price">
                    <div class="box">
                        <c:choose>
                            <c:when test="${product.priceHistories.size() > 0}">
                                <a class="button" href="#popup${product.id}"><fmt:formatNumber value="${product.price}"
                                                                                               type="currency"
                                                                                               currencySymbol="${product.currency.symbol}"/></a>
                            </c:when>
                            <c:when test="${product.priceHistories.size() eq 0}">
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/>
                            </c:when>
                        </c:choose>
                    </div>
                </td>
            </tr>
            <c:if test="${product.priceHistories.size() > 0}">
                <div id="popup${product.id}" class="overlay">
                    <div class="popup">
                        <h2>Price History</h2>
                        <a class="close" href="#">&times;</a>
                        <div class="content">
                            <c:forEach var="priceHistory" items="${product.priceHistories}">
                                <div><fmt:formatNumber value="${priceHistory.oldPrice}" type="currency"
                                                       currencySymbol="${product.currency.symbol}"/> - <fmt:formatDate
                                        value="${priceHistory.date}"/></div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </table>
    <tags:recentViewed recentProducts="${param.recentProducts}"/>
</tags:master>
<tags:foter/>