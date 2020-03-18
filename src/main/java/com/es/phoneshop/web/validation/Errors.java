package com.es.phoneshop.web.validation;

import com.es.phoneshop.exception.ProductNotFoundException;

import java.util.List;

public class Errors {

    public static Error getErrorByProductId(List<Error> errors, Long productId) {
        return errors
                .stream()
                .filter(error -> error.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    public static boolean hasProductError(List<Error> errors, Long productId) {
        return errors
                .stream()
                .anyMatch(error -> error.getProductId().equals(productId));
    }

}