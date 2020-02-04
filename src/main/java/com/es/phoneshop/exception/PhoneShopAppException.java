package com.es.phoneshop.exception;

public class PhoneShopAppException extends RuntimeException {

    public PhoneShopAppException() {
    }

    public PhoneShopAppException(String message) {
        super(message);
    }

    public PhoneShopAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneShopAppException(Throwable cause) {
        super(cause);
    }

    public PhoneShopAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
