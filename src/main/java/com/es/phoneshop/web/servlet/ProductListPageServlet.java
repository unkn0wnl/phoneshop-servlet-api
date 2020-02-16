package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.RecentView;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;
import com.es.phoneshop.service.DefaultProductService;
import com.es.phoneshop.service.DefaultViewedProductService;
import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.service.ViewedProductService;
import com.es.phoneshop.web.util.RequestParametersExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.PRODUCTS;
import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.RECENT_PRODUCTS;

public class ProductListPageServlet extends HttpServlet {

    private ProductService productService;
    private ViewedProductService viewedProductService;

    @Override
    public void init() throws ServletException {
        productService = DefaultProductService.getInstance();
        viewedProductService = DefaultViewedProductService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = RequestParametersExtractor.getSearchQuery(request);
        ProductSortingField field = RequestParametersExtractor.getProductSortingField(request);
        ProductSortingOrder order = RequestParametersExtractor.getProductSortingOrder(request);
        RecentView recentView = viewedProductService.getRecentView(request);

        request.setAttribute(PRODUCTS, productService.findProducts(searchQuery, field, order));
        request.setAttribute(RECENT_PRODUCTS, recentView.getRecentViewedProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
