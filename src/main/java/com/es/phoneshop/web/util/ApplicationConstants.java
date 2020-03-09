package com.es.phoneshop.web.util;

public interface ApplicationConstants {

    interface WebConstants {
        String ORDER_PARAM = "order";
        String SEARCH_QUERY = "search";
        String SORT_BY_PARAM = "sortBy";

        String PRODUCTS = "products";
        String PRODUCT = "product";

        String CART = "cart";
        String ERROR = "error";
        String PRODUCT_QUANTITY = "quantity";
        String RECENT_PRODUCTS = "recentProducts";

        String ERRORS = "errors";
        String PRODUCT_ID = "productId";

        String DEFAULT_DATA_DAO_INIT = "initDaoWithDemoData";
        String DELETE_ITEM_MESSAGE = "Item delete successful.";
    }

    interface Messages {
        String NUMBER_FORMAT_EXCEPTION_MESSAGE = "Number format exception!";
        String ADDED_SUCCESSFULLY_MESSAGE = "Added successfully";
    }

}