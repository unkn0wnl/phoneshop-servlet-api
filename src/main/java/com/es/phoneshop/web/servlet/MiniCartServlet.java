package com.es.phoneshop.web.servlet;

import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.HttpSessionCartService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.CART;

public class MiniCartServlet extends HttpServlet {

    private CartService cartService;

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(CART, cartService.getCart(request));
        request.getRequestDispatcher("/WEB-INF/parts/miniCart.jsp").include(request, response);
    }

}