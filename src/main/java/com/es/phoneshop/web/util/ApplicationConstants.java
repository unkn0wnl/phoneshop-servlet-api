package com.es.phoneshop.web.util;

public interface ApplicationConstants {

    int DEFAULT_DELIVERY_COURIER_PRICE = 100;

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

        String ORDER_ATTRIBUTE = "order";
        String DELIVERY_MODE = "delivery";
        String PAYMENT_METHOD = "payment";
        String ORDER = "order";
        String FIRST_NAME_PARAM = "firstName";
        String LAST_NAME_PARAM = "lastName";
        String PHONE_PARAM = "phone";
        String ADDRESS_PARAM = "address";
        String DELIVERY_MODE_PARAM = "deliveryMode";
        String PAYMENT_METHOD_PARAM = "paymentMethod";
        String WORD_SEARCH_OPTION = "wordSearchOption";
        String DESCRIPTION_PARAM = "description";
        String MIN_PRICE_PARAM = "minPrice";
        String MAX_PRICE_PARAM = "maxPrice";
        String SEARCH_OPTION_PARAM = "searchOption";
    }

    interface Messages {
        String NUMBER_FORMAT_EXCEPTION_MESSAGE = "Number format exception!";
        String ADDED_SUCCESSFULLY_MESSAGE = "Added successfully";
    }

}