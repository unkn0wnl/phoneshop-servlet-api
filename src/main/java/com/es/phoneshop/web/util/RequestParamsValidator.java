package com.es.phoneshop.web.util;

import com.es.phoneshop.model.order.ContactDetails;
import com.es.phoneshop.web.validation.Error;

import java.util.*;
import java.util.regex.Pattern;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.*;

public class RequestParamsValidator {

    public static final String DEFAULT_PHONE_PATTERN = "\\+\\d{12}";

    public RequestParamsValidator() {
    }

    public Map<Long, Integer> validateUpdateCartProducts(Map<String, String> products, final List<Error> errors) {

        Map<Long, Integer> validProducts = new HashMap<>();

        for (Map.Entry<String, String> entry : products.entrySet()) {

            final Long productId = Long.parseLong(entry.getKey());
            final Integer productQuantity = this.validateProductQuantity(entry.getValue());

            if (Objects.isNull(productQuantity)) {
                errors.add(new Error(productId, "Incorrect input!"));
            } else {
                validProducts.put(productId, productQuantity);
            }
        }
        return validProducts;
    }

    public Optional<ContactDetails> validateDeliveryContactParam(String firstName, String lastName, String phoneNumber,
                                                                 String deliveryAddress, final Map<String, String> errors) {
        if (!isStringValid(firstName)) {
            errors.put(FIRST_NAME_PARAM, "Invalid first name!");
        }
        if (!isStringValid(lastName)) {
            errors.put(LAST_NAME_PARAM, "Invalid last name!");
        }
        if (!isPhoneValid(phoneNumber)) {
            errors.put(PHONE_PARAM, "Invalid phone number format!");
        }
        if (!isStringValid(deliveryAddress)) {
            errors.put(ADDRESS_PARAM, "Invalid address!");
        }

        if (!errors.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(new ContactDetails(firstName, lastName, phoneNumber, deliveryAddress));
        }

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

    public boolean isStringValid(String str) {
        return str != null && !str.isEmpty();
    }

    public boolean isPhoneValid(String phone) {
        final Pattern phonePattern = Pattern.compile(DEFAULT_PHONE_PATTERN);
        return phonePattern.matcher(phone).matches();
    }

}