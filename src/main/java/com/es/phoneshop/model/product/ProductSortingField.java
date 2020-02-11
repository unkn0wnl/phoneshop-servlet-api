package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum ProductSortingField implements Comparator<Product> {

    DESCRIPTION {
        @Override
        public int compare(Product firstProduct, Product secondProduct) {
            return firstProduct.getDescription().compareTo(secondProduct.getDescription());
        }
    },

    PRICE {
        @Override
        public int compare(Product firstProduct, Product secondProduct) {
            return firstProduct.getPrice().compareTo(secondProduct.getPrice());
        }
    }

}