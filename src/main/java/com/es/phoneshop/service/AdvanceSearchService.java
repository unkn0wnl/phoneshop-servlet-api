package com.es.phoneshop.service;

import com.es.phoneshop.model.advanceSeach.WordsSearchOptions;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface AdvanceSearchService {

    List<Product> findProducts(String searchQuery, Integer minPrice, Integer maxPrice,
                               WordsSearchOptions wordsSearchOptions);

}
