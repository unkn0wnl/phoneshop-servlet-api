package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;
import com.google.common.collect.EvictingQueue;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

public class RecentView {

    public static final int DEFAULT_MAX_RECENT_VIEW_SIZE = 3;
    private static final Logger LOGGER;

    static {
        LOGGER = getLogger(RecentView.class);
    }

    private EvictingQueue<Product> recentViewedProducts;

    public RecentView() {
        recentViewedProducts = EvictingQueue.create(DEFAULT_MAX_RECENT_VIEW_SIZE);
    }

    public RecentView(int size) {
        recentViewedProducts = EvictingQueue.create(size);
    }

    public EvictingQueue<Product> getRecentViewedProducts() {
        return recentViewedProducts;
    }

    public void addProduct(Product product) {
        if (!recentViewedProducts.contains(product)) {
            recentViewedProducts.add(product);
            LOGGER.info("Product {} was added.", product);
            LOGGER.info("RV Content: ");
            recentViewedProducts.forEach(LOGGER::info);
        }
    }

    public void clear() {
        recentViewedProducts.clear();
    }

}