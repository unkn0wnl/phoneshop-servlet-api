package com.es.phoneshop.model.order;

import java.math.BigDecimal;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.DEFAULT_DELIVERY_COURIER_PRICE;

public enum DeliveryMode {

    PICKUP(BigDecimal.ZERO),
    COURIER(new BigDecimal(DEFAULT_DELIVERY_COURIER_PRICE));

    private BigDecimal deliveryCost;

    DeliveryMode(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

}