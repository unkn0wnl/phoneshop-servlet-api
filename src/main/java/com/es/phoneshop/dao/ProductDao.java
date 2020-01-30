package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(Long id);

    Product getProductByCode(String code);

    List<Product> findProducts();

    void save(Product product);

    void delete(Long id);
}
