package com.es.phoneshop.web.servlet;

import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.web.util.RequestParametersExtractor;
import com.es.phoneshop.web.util.RequestParamsValidator;
import com.es.phoneshop.web.validation.Error;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.CART;
import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.ERRORS;
import static org.apache.logging.log4j.LogManager.getLogger;

public class CartPageServlet extends HttpServlet {

    private static final Logger LOGGER;

    static {
        LOGGER = getLogger(CartPageServlet.class);
    }

    private CartService cartService;
    private RequestParamsValidator requestParamsValidator;

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        requestParamsValidator = new RequestParamsValidator();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(CART, cartService.getCart(request));
        request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String> products = RequestParametersExtractor.getProductsMap(request);
        final List<Error> errors = new ArrayList<>();
        Map<Long, Integer> validProducts = requestParamsValidator.validateUpdateCartProducts(products, errors);

        if (!errors.isEmpty()) {
            request.setAttribute(ERRORS, errors);
            this.doGet(request, response);
        } else {
            Cart cart = cartService.getCart(request);
            errors.clear();

            try {
                cartService.updateEachProduct(cart, validProducts);
            } catch (OutOfStockException ex) {
                errors.add(new Error(ex.getProductId(), ex.getMessage()));
            }

            if (errors.isEmpty()) {
                response.sendRedirect(String.format("%s?message=%s", request.getRequestURI(), "Cart updated!"));
            } else {
                request.setAttribute(ERRORS, errors);
                this.doGet(request, response);
            }
        }
    }

}