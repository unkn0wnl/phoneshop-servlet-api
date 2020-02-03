package com.es.phoneshop.dao;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.LogManager.getLogger;

public class ArrayListProductDao implements ProductDao {

    public static final int EMPTY_STOCK_LEVEL = 0;
    public static final long DEFAULT_ID_COUNTER_VALUE = 1L;

    private static final Logger LOGGER;

    private static volatile Lock lock;
    private static volatile AtomicLong idCounter;
    private static volatile ArrayListProductDao instance;

    static {
        LOGGER = getLogger(ArrayListProductDao.class);
    }

    private List<Product> listOfProducts;

    private ArrayListProductDao() {
        lock = new ReentrantLock();
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
        return listOfProducts.stream()
                .filter(product -> Objects.nonNull(product.getPrice()))
                .filter(product -> product.getStock() > EMPTY_STOCK_LEVEL)
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found!")
                );
    }

    @Override
    public Product getProductByCode(String code) {
        return listOfProducts.stream()
                .filter(product -> Objects.nonNull(product.getPrice()))
                .filter(product -> product.getStock() > EMPTY_STOCK_LEVEL)
                .filter(product -> product.getCode().equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new ProductNotFoundException("Product not found!")
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
    public synchronized void save(Product product) {
        product.setId(idCounter.getAndIncrement());
        listOfProducts.add(product);
    }

    @Override
    public void saveAll(Collection<? extends Product> products) {
        try {
            lock.lock();
            listOfProducts.addAll(products);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void delete(Long id) {
        listOfProducts.removeIf(
                product -> product.getId().equals(id)
        );
    }
}
