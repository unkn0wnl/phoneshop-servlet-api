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
import com.es.phoneshop.web.util.RequestParamsValidator;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.*;
import static org.apache.logging.log4j.LogManager.getLogger;

public class CheckoutPageServlet extends HttpServlet {

    public static final String ORDER_OVERVIEW_PATH = "/order/overview";
    private static final Logger LOGGER;

    static {
        LOGGER = getLogger(CheckoutPageServlet.class);
    }

    private CartService cartService;
    private OrderService orderService;
    private RequestParamsValidator requestParamsValidator;

    @Override
    public void init() throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
        requestParamsValidator = new RequestParamsValidator();
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

        String firstName = request.getParameter(FIRST_NAME_PARAM);
        String lastName = request.getParameter(LAST_NAME_PARAM);
        String phoneNumber = request.getParameter(PHONE_PARAM);
        String deliveryAddress = request.getParameter(ADDRESS_PARAM);
        String deliveryModeString = request.getParameter(DELIVERY_MODE_PARAM);
        String paymentMethodString = request.getParameter(PAYMENT_METHOD_PARAM);

        Map<String, String> errors = new HashMap<>();
        Optional<ContactDetails> contactDetailsOptional = requestParamsValidator
                .validateDeliveryContactParam(firstName, lastName, phoneNumber, deliveryAddress, errors);
        errors.entrySet().forEach(LOGGER::info);
        if (contactDetailsOptional.isPresent()) {
            ContactDetails contactDetails = contactDetailsOptional.get();
            DeliveryMode deliveryMode = DeliveryMode.valueOf(deliveryModeString);
            PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodString);
            orderService.placeOrder(order, contactDetails, paymentMethod, deliveryMode);
            cartService.clearCart(cart);
            response.sendRedirect(String.format("%s%s/%s", request.getContextPath(), ORDER_OVERVIEW_PATH, order.getUuid()));
        } else {
            request.setAttribute(ERRORS, errors);
            this.doGet(request, response);
        }

    }

}