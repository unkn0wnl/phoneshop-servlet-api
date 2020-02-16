<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recentProducts" required="true" type="com.google.common.collect.EvictingQueue" %>

<c:if test="${not empty recentProducts}">
    <br>
    <h3>Recently viewed</h3>
    <table>
        <thead>
            <th>
                <c:forEach var="recentProduct" items="${recentProducts}">
                    <td align="center">
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${recentProduct.imageUrl}">
                        <br>
                        <c:url value="/products/${recentProduct.id}" var="productPage"/>
                        <a href="${productPage}">${recentProduct.description}</a>
                        <br>
                        <fmt:formatNumber value="${recentProduct.price}" type="currency"
                                          currencySymbol="${recentProduct.currency.symbol}"/>
                    </td>
                </c:forEach>
            </th>
        </thead>
    </table>
</c:if>