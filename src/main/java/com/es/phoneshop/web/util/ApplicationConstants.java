package com.es.phoneshop.web.util;

public final class ApplicationConstants {

    private ApplicationConstants() {
        throw new IllegalStateException("You can't create an instance of this class.");
    }

    public final static class WebConstants {

        public static final String ORDER_PARAM = "order";
        public static final String SEARCH_QUERY = "search";
        public static final String SORT_BY_PARAM = "sortBy";

        public static final String PRODUCTS = "products";

        public static final String DEFAULT_DATA_DAO_INIT = "initDaoWithDemoData";

        private WebConstants() {
        }
    }


}
