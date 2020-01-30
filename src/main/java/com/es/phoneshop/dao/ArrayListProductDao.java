package com.es.phoneshop.dao;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.LogManager.getLogger;

public class ArrayListProductDao implements ProductDao {

    public static final int EMPTY_STOCK_LEVEL = 0;

    private static final Logger LOGGER;
    private static volatile ArrayListProductDao instance;

    static {
        LOGGER = getLogger(ArrayListProductDao.class);
    }

    private List<Product> listOfProducts;

    private ArrayListProductDao() {
        listOfProducts = new CopyOnWriteArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao result = instance;

        if (result != null) {
            return result;
        }

        synchronized (ArrayListProductDao.class) {
            if (result == null) {
                instance = new ArrayListProductDao();
            }
            return instance;
        }
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
        listOfProducts.removeIf(
                product -> product.getId().equals(id)
        );
    }
}
