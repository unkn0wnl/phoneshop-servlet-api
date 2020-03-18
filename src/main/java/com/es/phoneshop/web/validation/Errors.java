package com.es.phoneshop.web.validation;

import com.es.phoneshop.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Errors {

    private List<Error> errorList;

    public Errors() {
        errorList = new ArrayList<>();
    }

    public void addError(Error error) {
        errorList.add(error);
    }

    public Error getErrorByProductId(Long productId) {
        return errorList.stream()
                .filter(error -> error.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(ProductNotFoundException::new);
    }

    public boolean hasProductError(Long productId) {
        return errorList
                .stream()
                .anyMatch(error -> error.getProductId().equals(productId));
    }

    public boolean hasErrors() {
        return !errorList.isEmpty();
    }

    public void clear() {
        errorList.clear();
    }

    @Override
    public String toString() {
        return "Errors{" +
                "errorList=" + errorList +
                '}';
    }

}
