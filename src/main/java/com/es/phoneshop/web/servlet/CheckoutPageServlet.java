package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.ContactDetails;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DefaultOrderService;
import com.es.phoneshop.service.HttpSessionCartService;
import com.es.phoneshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.*;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        request.setAttribute(ORDER_ATTRIBUTE, orderService.createOrder(cart));
        request.setAttribute(DELIVERY_MODE, DeliveryMode.values());
        request.setAttribute(PAYMENT_METHOD, PaymentMethod.values());
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request);
        Order order = orderService.createOrder(cart);

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phone");
        String deliveryAddress = request.getParameter("address");

        final ContactDetails contactDetails = new ContactDetails(firstName, lastName, phoneNumber, deliveryAddress);

        String deliveryModeString = request.getParameter("deliveryMode");
        DeliveryMode deliveryMode = DeliveryMode.valueOf(deliveryModeString);

        String paymentMethodString = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString);

        orderService.placeOrder(order, contactDetails, paymentMethod, deliveryMode);
        cartService.clearCart(cart);
        response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getUuid());
    }
}
