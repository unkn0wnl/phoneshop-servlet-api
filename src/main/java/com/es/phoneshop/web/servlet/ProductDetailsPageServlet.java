package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.RecentView;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultViewedProductService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.service.ViewedProductService;
import com.es.phoneshop.web.util.RequestParametersExtractor;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.*;
import static org.apache.logging.log4j.LogManager.getLogger;

public class ProductDetailsPageServlet extends HttpServlet {

    private static final Logger LOGGER;

    static {
        LOGGER = getLogger(ProductDetailsPageServlet.class);
    }

    private ProductDao productDao;
    private CartService cartService;
    private ViewedProductService viewedProductService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        viewedProductService = DefaultViewedProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecentView recentView = viewedProductService.getRecentView(request);

        Long productId = RequestParametersExtractor.getProductId(request);
        Product product = productDao.getProductById(productId);

        request.setAttribute(PRODUCT, product);
        request.setAttribute(RECENT_PRODUCTS, recentView.getRecentViewedProducts());
        request.getRequestDispatcher("/WEB-INF/pages/productDetailsPage.jsp").forward(request, response);
        recentView.addProduct(product);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = RequestParametersExtractor.getProductId(request);
        Product product = productDao.getProductById(productId);
        Cart sessionCart = cartService.getCart(request);
        Integer quantity = RequestParametersExtractor.getProductQuantity(request);

        LOGGER.info("Product ID: {}, Quantity: {}.", productId, quantity);

        request.setAttribute(PRODUCT, product);
        request.setAttribute(CART, sessionCart);

        try {
            if (quantity != null) {
                cartService.add(sessionCart, product, quantity);
                response.sendRedirect(request.getRequestURI()
                        + "?message=Added successfully&quantity=" + quantity);
            } else {
                request.setAttribute(ERROR, "Number format exception!");
                this.doGet(request, response);
            }
        } catch (OutOfStockException ex) {
            request.setAttribute(ERROR, ex.getMessage());
            this.doGet(request, response);
        }

    }

}