package com.es.phoneshop.exception;

public class OutOfStockException extends ServiceException {

    private Long productId;

    public OutOfStockException() {
        super();
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Long productId) {
        super(message);
        this.productId = productId;
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfStockException(String message, Throwable cause, Long productId) {
        super(message, cause);
        this.productId = productId;
    }

    public OutOfStockException(Throwable cause) {
        super(cause);
    }

    public OutOfStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public String toString() {
        return "OutOfStockException{" +
                "productId=" + productId +
                '}';
    }

}