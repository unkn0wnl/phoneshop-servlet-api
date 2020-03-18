package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.advanceSeach.WordsSearchOptions;
import com.es.phoneshop.model.product.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultAdvanceSearchService implements AdvanceSearchService {

    public static final String SPACE_PATTERN = "\\s+";

    private static volatile DefaultAdvanceSearchService instance;

    private ProductDao productDao;

    private DefaultAdvanceSearchService() {
        productDao = ArrayListProductDao.getInstance();
    }

    public static DefaultAdvanceSearchService getInstance() {
        DefaultAdvanceSearchService localeInstance = instance;

        if (localeInstance == null) {
            synchronized (DefaultAdvanceSearchService.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new DefaultAdvanceSearchService();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Product> findProducts(String searchQuery, Integer minPrice, Integer maxPrice,
                                               WordsSearchOptions wordsSearchOptions) {
        Stream<Product> mainStream;

        if (Objects.nonNull(searchQuery)) {
            mainStream = wordsSearchOptions == WordsSearchOptions.ALL ? this.getProductStreamByAllWords(searchQuery)
                    : this.getProductStreamByAnyWords(searchQuery);
        } else {
            mainStream = productDao.findProducts().stream();
        }

        if (minPrice != null) {
            mainStream = mainStream.filter(product -> product.getPrice().intValue() >= minPrice);
        }

        if (maxPrice != null) {
            mainStream = mainStream.filter(product -> product.getPrice().intValue() <= maxPrice);
        }

        return mainStream.collect(Collectors.toList());
    }

    private Stream<Product> getProductStreamByAnyWords(String query) {
        final String[] wordsOfQuery = query.split(SPACE_PATTERN);

        return productDao.findProducts()
                .stream()
                .filter(
                        product -> Arrays.stream(wordsOfQuery)
                                .anyMatch(word -> product.getDescription().toLowerCase().contains(word.toLowerCase()))
                );
    }

    private Stream<Product> getProductStreamByAllWords(String query) {
        final String[] wordsOfQuery = query.split(SPACE_PATTERN);

        return productDao.findProducts()
                .stream()
                .filter(
                        product -> Arrays.stream(wordsOfQuery)
                                .allMatch(word -> product.getDescription().toLowerCase().contains(word.toLowerCase()))
                );
    }

}