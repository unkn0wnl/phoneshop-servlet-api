package com.es.phoneshop.web.util;

import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.*;

public class RequestParametersExtractor {

    public static ProductSortingField getProductSortingField(HttpServletRequest request) {
        String sortBy = request.getParameter(SORT_BY_PARAM);

        if (Objects.nonNull(sortBy)) {
            return ProductSortingField.valueOf(sortBy.toUpperCase());
        }
        return null;
    }

    public static ProductSortingOrder getProductSortingOrder(HttpServletRequest request) {
        String order = request.getParameter(ORDER_PARAM);

        if (Objects.nonNull(order)) {
            return ProductSortingOrder.valueOf(order.toUpperCase());
        }
        return null;
    }

    public static String getSearchQuery(HttpServletRequest request) {
        return request.getParameter(SEARCH_QUERY);
    }

    public static Long getProductId(HttpServletRequest request) {
        final int productIndex = 1;
        String path = request.getPathInfo();
        String pathId = path.substring(productIndex);
        return Long.valueOf(pathId);
    }

    public static Integer getProductQuantity(HttpServletRequest request) {
        String stringQuantityValue = request.getParameter(PRODUCT_QUANTITY);
        Integer quantity = null;

        try {
            quantity = Integer.valueOf(stringQuantityValue);
        } catch (NumberFormatException ex) {
            request.setAttribute(ERROR, "Not a number!");
        }
        return quantity;
    }

}
