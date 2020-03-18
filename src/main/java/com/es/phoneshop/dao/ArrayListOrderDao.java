package com.es.phoneshop.dao;

import com.es.phoneshop.exception.DuplicateOrderException;
import com.es.phoneshop.exception.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.apache.logging.log4j.LogManager.getLogger;

public class ArrayListOrderDao implements OrderDao {

    private static final Logger LOGGER;

    private static volatile ArrayListOrderDao instance;

    static {
        LOGGER = getLogger(ArrayListOrderDao.class);
    }

    private List<Order> listOfOrders;

    private ArrayListOrderDao() {
        listOfOrders = new CopyOnWriteArrayList<>();
    }

    public static ArrayListOrderDao getInstance() {
        ArrayListOrderDao localeInstance = instance;

        if (localeInstance == null) {
            synchronized (ArrayListOrderDao.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new ArrayListOrderDao();
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized void save(Order order) {
        if (listOfOrders.contains(order)) {
            throw new DuplicateOrderException();
        }
        listOfOrders.add(order);
    }

    @Override
    public Order getOrder(String uuid) {
        return listOfOrders
                .stream()
                .filter(order -> order.getUuid().equals(uuid))
                .findAny()
                .orElseThrow(OrderNotFoundException::new);
    }

}