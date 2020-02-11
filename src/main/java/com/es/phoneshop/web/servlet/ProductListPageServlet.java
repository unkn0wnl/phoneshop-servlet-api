package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;
import com.es.phoneshop.service.DefaultProductService;
import com.es.phoneshop.service.ProductService;
import com.es.phoneshop.web.util.RequestParametersExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.PRODUCTS;

public class ProductListPageServlet extends HttpServlet {

    private ProductService productService;

    @Override
    public void init() throws ServletException {
        productService = DefaultProductService.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchQuery = RequestParametersExtractor.getSearchQuery(request);
        ProductSortingField field = RequestParametersExtractor.getProductSortingField(request);
        ProductSortingOrder order = RequestParametersExtractor.getProductSortingOrder(request);

        request.setAttribute(PRODUCTS, productService.findProducts(searchQuery, field, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
