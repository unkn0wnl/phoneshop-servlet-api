package com.es.phoneshop.exception;

public class DuplicateOrderException extends PhoneShopAppException {

    public DuplicateOrderException() {
        super();
    }

    public DuplicateOrderException(String message) {
        super(message);
    }

    public DuplicateOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateOrderException(Throwable cause) {
        super(cause);
    }

}