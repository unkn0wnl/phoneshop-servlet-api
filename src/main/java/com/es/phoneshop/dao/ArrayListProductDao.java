package com.es.phoneshop.dao;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    public static final int EMPTY_STOCK_LEVEL = 0;

    private List<Product> listOfProducts;

    public ArrayListProductDao() {
        listOfProducts = new CopyOnWriteArrayList<>();
    }

    @Override
    public Product getProduct(Long id) {
        return listOfProducts.stream()
                .filter(product -> Objects.nonNull(product.getPrice()))
                .filter(product -> product.getStock() > EMPTY_STOCK_LEVEL)
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ProductNotFoundException(String.format("Product with id %s not found!", id))
                );
    }

    @Override
    public List<Product> findProducts() {
        return listOfProducts.stream()
                .filter(product -> Objects.nonNull(product.getPrice()))
                .filter(product -> product.getStock() > EMPTY_STOCK_LEVEL)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        listOfProducts.add(product);
    }

    @Override
    public void delete(Long id) {
        listOfProducts.removeIf(product -> product.getId().equals(id));
    }
}
