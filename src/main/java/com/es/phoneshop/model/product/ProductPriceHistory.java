package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class ProductPriceHistory implements Serializable {

    private Timestamp date;
    private BigDecimal oldPrice;

    public ProductPriceHistory() {
    }

    public ProductPriceHistory(Timestamp date, BigDecimal oldPrice) {
        this.date = date;
        this.oldPrice = oldPrice;
    }

    public ProductPriceHistory(ProductPriceHistory priceHistory) {
        this.date = priceHistory.date;
        this.oldPrice = priceHistory.oldPrice;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public BigDecimal getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(BigDecimal oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPriceHistory that = (ProductPriceHistory) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(oldPrice, that.oldPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, oldPrice);
    }

    @Override
    public String toString() {
        return "ProductPriceHistory{" +
                "date=" + date +
                ", oldPrice=" + oldPrice +
                '}';
    }

}