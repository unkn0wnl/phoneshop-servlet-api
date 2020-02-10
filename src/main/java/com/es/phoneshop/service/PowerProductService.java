package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PowerProductService implements ProductService {

    public static final String SPACE_PATTERN = "\\s+";

    private ProductDao productDao;

    private static PowerProductService instance;

    private PowerProductService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static PowerProductService getInstance() {
        PowerProductService localeInstance = instance;

        if (localeInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new PowerProductService();
                }
            }
        }
        return instance;
    }

    private Stream<Product> getFilteredProductStreamBySearchQuery(String query) {
        final String[] wordsOfQuery = query.split(SPACE_PATTERN);

        return productDao.findProducts()
                .stream()
                .filter(
                        product -> Arrays.stream(wordsOfQuery)
                                .anyMatch(word -> product.getDescription().toLowerCase().contains(word.toLowerCase()))
                );
    }

    @Override
    public List<Product> findProducts(final String query, final ProductSortingField field,
                                      final ProductSortingOrder order) {
        final Stream<Product> mainStream;

        if (Objects.nonNull(query)) {
            mainStream = this.getFilteredProductStreamBySearchQuery(query);
        } else {
            mainStream = productDao.findProducts().stream();
        }

        if (field != null && order != null) {
            return mainStream
                    .sorted(order == ProductSortingOrder.ASCENDING ? field : field.reversed())
                    .collect(Collectors.toList());
        }
        return mainStream
                .collect(Collectors.toList());
    }

}