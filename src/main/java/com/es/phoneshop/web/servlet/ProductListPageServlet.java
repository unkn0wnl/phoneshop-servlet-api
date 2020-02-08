package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;
import com.es.phoneshop.web.util.RequestParametersExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.PRODUCTS;

public class ProductListPageServlet extends HttpServlet {

    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String searchQuery = RequestParametersExtractor.getSearchQuery(request);
        ProductSortingField field = RequestParametersExtractor.getProductSortingField(request);
        ProductSortingOrder order = RequestParametersExtractor.getProductSortingOrder(request);

        request.setAttribute(PRODUCTS, productDao.findProducts(searchQuery, field, order));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
