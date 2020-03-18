package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.model.cart.RecentView;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.RECENT_PRODUCTS;

public class DefaultViewedProductService implements ViewedProductService {

    private static volatile DefaultViewedProductService instance;

    private DefaultViewedProductService() {
    }

    public static DefaultViewedProductService getInstance() {
        DefaultViewedProductService localeInstance = instance;

        if (localeInstance == null) {
            synchronized (DefaultViewedProductService.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new DefaultViewedProductService();
                }
            }
        }
        return instance;
    }

    @Override
    public RecentView getRecentView(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        synchronized (session) {
            RecentView recentView = (RecentView) session.getAttribute(RECENT_PRODUCTS);
            if (recentView == null) {
                recentView = new RecentView();
                session.setAttribute(RECENT_PRODUCTS, recentView);
            }
            return recentView;
        }
    }

    @Override
    public synchronized void add(Product product, RecentView recentView) {
        recentView.addProduct(product);
    }

}