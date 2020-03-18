package com.es.phoneshop.web.servlet;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.web.util.RequestParametersExtractor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.DELETE_ITEM_MESSAGE;

public class CartItemDeleteServlet extends HttpServlet {

    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Long id = RequestParametersExtractor.getProductId(request);
        Product product = productDao.getProductById(id);
        cartService.delete(cart, product);
        response.sendRedirect(String.format("%s/cart?message=%s", request.getContextPath(), DELETE_ITEM_MESSAGE));
    }

}