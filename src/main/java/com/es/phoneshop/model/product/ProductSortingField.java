package com.es.phoneshop.model.product;

import java.util.Comparator;

public enum ProductSortingField {

    DESCRIPTION(Comparator.comparing(Product::getDescription)),
    PRICE(Comparator.comparing(Product::getPrice));

    private Comparator<Product> productComparator;

    ProductSortingField(Comparator<Product> productComparator) {
        this.productComparator = productComparator;
    }

    public Comparator<Product> getComparator() {
        return productComparator;
    }

}