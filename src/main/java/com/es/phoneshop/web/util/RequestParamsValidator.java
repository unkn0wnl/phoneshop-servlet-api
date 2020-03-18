package com.es.phoneshop.web.util;

import com.es.phoneshop.web.validation.Error;
import com.es.phoneshop.web.validation.Errors;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestParamsValidator {


    public RequestParamsValidator() {
    }

    public Map<Long, Integer> validateUpdateCartProducts(Map<String, String> products, final Errors errors) {

        Map<Long, Integer> validProducts = new HashMap<>();

        for (Map.Entry<String, String> entry : products.entrySet()) {

            final Long productId = Long.parseLong(entry.getKey());
            final Integer productQuantity = this.validateProductQuantity(entry.getValue());

            if (Objects.isNull(productQuantity)) {
                errors.addError(new Error(productId, "Incorrect input!"));
            } else {
                validProducts.put(productId, productQuantity);
            }
        }
        return validProducts;
    }

    public Integer validateProductQuantity(String stringQuantityValue) {
        Integer result;
        try {
            result = Integer.valueOf(stringQuantityValue);
        } catch (NumberFormatException ex) {
            result = null;
        }
        return result;
    }

}