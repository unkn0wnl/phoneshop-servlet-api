package com.es.phoneshop.model.order;

import java.math.BigDecimal;

import static com.es.phoneshop.web.util.ApplicationConstants.DEFAULT_DELIVERY_COURIER_PRICE;

public enum DeliveryMode {

    PICKUP("Pick Up", BigDecimal.ZERO),
    COURIER("Courier", new BigDecimal(DEFAULT_DELIVERY_COURIER_PRICE));

    private String frontName;
    private BigDecimal deliveryCost;

    DeliveryMode(String frontName, BigDecimal deliveryCost) {
        this.frontName = frontName;
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getFrontName() {
        return frontName;
    }

    public void setFrontName(String frontName) {
        this.frontName = frontName;
    }

}