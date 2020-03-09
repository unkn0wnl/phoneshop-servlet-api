package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CartService {

    Cart getCart(HttpServletRequest request);

    void add(Cart cart, Product product, int quantity);

    void update(Cart cart, Product product, int quantity);

    void updateEachProduct(Cart cart, Map<Long, Integer> products);

    void delete(Cart cart, Product product);

}
