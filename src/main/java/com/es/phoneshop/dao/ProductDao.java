package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;

import java.util.Collection;
import java.util.List;

public interface ProductDao {
    Product getProductById(Long id);

    Product getProductByCode(String code);

    List<Product> findProducts();

    void save(Product product);

    void saveAll(Collection<? extends Product> products);

    void delete(Long id);

    List<Product> findProducts(String query, ProductSortingField field, ProductSortingOrder order);
}
