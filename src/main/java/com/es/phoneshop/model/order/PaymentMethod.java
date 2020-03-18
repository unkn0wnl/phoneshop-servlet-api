package com.es.phoneshop.model.order;

public enum PaymentMethod {
    CASH("Cash"), CREDIT_CARD("Credit card");

    private String frontName;

    PaymentMethod(String frontName) {
        this.frontName = frontName;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

}