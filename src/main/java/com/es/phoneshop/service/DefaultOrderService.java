package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.ContactDetails;
import com.es.phoneshop.model.order.DeliveryMode;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;

import java.util.UUID;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

    private static volatile DefaultOrderService instance;

    private OrderDao orderDao;

    public DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static DefaultOrderService getInstance() {
        DefaultOrderService localeInstance = instance;

        if (localeInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new DefaultOrderService();
                }
            }
        }
        return instance;
    }

    @Override
    public Order createOrder(Cart cart) {
        final Order order = new Order();
        order.setCartItems(cart.getCartItems()
                .stream()
                .map(CartItem::new)
                .collect(Collectors.toList()));
        order.setTotalProductsCost(cart.getTotalProductsCost());
        return order;
    }

    @Override
    public void placeOrder(Order order, ContactDetails contactDetails,
                           PaymentMethod paymentMethod, DeliveryMode deliveryMode) {
        order.setUuid(UUID.randomUUID().toString());
        order.setContactDetails(contactDetails);
        order.setPaymentMethod(paymentMethod);
        order.setDeliveryMode(deliveryMode);
        order.setTotalOrderCost(order.getTotalProductsCost().add(deliveryMode.getDeliveryCost()));
        orderDao.save(order);
    }

}