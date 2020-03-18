package com.es.phoneshop.web.validation;

import java.io.Serializable;

public class Error implements Serializable {

    private static final long serialVersionUID = 1539299835046339546L;

    private Long productId;
    private String errorMessage;

    public Error() {
    }

    public Error(Long productId, String errorMessage) {
        this.productId = productId;
        this.errorMessage = errorMessage;
    }

    public Error(Error error) {
        this.productId = error.productId;
        this.errorMessage = error.errorMessage;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}