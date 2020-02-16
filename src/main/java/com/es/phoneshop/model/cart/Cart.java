package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cart {

    public static final Integer DEFAULT_VALUE = 0;

    private BigDecimal totalCost;
    private Integer totalQuantity;
    private List<CartItem> cartItems;

    public Cart() {
        totalQuantity = DEFAULT_VALUE;
        cartItems = new CopyOnWriteArrayList<>();
        totalCost = new BigDecimal(DEFAULT_VALUE);
    }

    public void clear() {
        this.cartItems.clear();
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}