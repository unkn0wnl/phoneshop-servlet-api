package com.es.phoneshop.dao;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.logging.log4j.LogManager.getLogger;

public class ArrayListProductDao implements ProductDao {

    public static final int EMPTY_STOCK_LEVEL = 0;
    public static final long DEFAULT_ID_COUNTER_VALUE = 1L;

    private static final Logger LOGGER;

    private static volatile AtomicLong idCounter;
    private static volatile ArrayListProductDao instance;

    static {
        LOGGER = getLogger(ArrayListProductDao.class);
    }

    private List<Product> listOfProducts;

    private ArrayListProductDao() {
        listOfProducts = new CopyOnWriteArrayList<>();
        idCounter = new AtomicLong(DEFAULT_ID_COUNTER_VALUE);
    }

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao localeInstance = instance;

        if (localeInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new ArrayListProductDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Product getProductById(Long id) {
        return this.getFilteredProductStream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found!")
                );
    }

    @Override
    public Product getProductByCode(String code) {
        return this.getFilteredProductStream()
                .filter(product -> product.getCode().equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found!")
                );
    }

    private Stream<Product> getFilteredProductStream() {
        return listOfProducts.stream()
                .filter(product -> Objects.nonNull(product.getPrice()))
                .filter(product -> product.getStock() > EMPTY_STOCK_LEVEL);
    }

    @Override
    public List<Product> findProducts() {
        return this.getFilteredProductStream()
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        product.setId(idCounter.getAndIncrement());
        listOfProducts.add(product);
    }

    @Override
    public synchronized void saveAll(Collection<? extends Product> products) {
        products.forEach(this::save);
    }

    @Override
    public void delete(Long id) {
        listOfProducts.removeIf(
                product -> product.getId().equals(id)
        );
    }
}
