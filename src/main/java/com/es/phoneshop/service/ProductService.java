package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;

import java.util.List;

public interface ProductService {

    List<Product> findProducts(String query, ProductSortingField field, ProductSortingOrder order);

}
