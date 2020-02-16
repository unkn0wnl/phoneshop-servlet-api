package com.es.phoneshop.service;

import com.es.phoneshop.model.cart.RecentView;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;

public interface ViewedProductService {

    RecentView getRecentView(HttpServletRequest request);

    void add(Product product, RecentView recentView);

}
