package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.exception.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.CART;
import static org.apache.logging.log4j.LogManager.getLogger;

public class HttpSessionCartService implements CartService {

    private static final Logger LOGGER;
    private static volatile HttpSessionCartService instance;

    static {
        LOGGER = getLogger(HttpSessionCartService.class);
    }

    private HttpSessionCartService() {
    }

    public static HttpSessionCartService getInstance() {
        HttpSessionCartService localeInstance = instance;

        if (localeInstance == null) {
            synchronized (ArrayListProductDao.class) {
                localeInstance = instance;

                if (localeInstance == null) {
                    instance = new HttpSessionCartService();
                }
            }
        }
        return instance;
    }

    @Override
    public Cart getCart(HttpServletRequest request) {
        HttpSession currentSession = request.getSession();

        synchronized (currentSession) {
            Cart cart = (Cart) currentSession.getAttribute(CART);

            if (Objects.isNull(cart)) {
                cart = new Cart();
                currentSession.setAttribute(CART, cart);
            }
            return cart;
        }
    }

    @Override
    public void add(Cart cart, Product product, int quantity) {
        Objects.requireNonNull(cart);
        Objects.requireNonNull(product);
        synchronized (cart) {

            Optional<CartItem> existsCartItem = cart.getCartItems()
                    .stream()
                    .filter(cartItem -> cartItem
                            .getProduct()
                            .equals(product))
                    .findAny();


            int totalQuantity = this.validateProductQuantity(existsCartItem, product, quantity);

            CartItem cartItem;
            if (existsCartItem.isPresent()) {
                cartItem = existsCartItem.get();
                if (cartItem.getQuantity() + quantity > product.getStock()) {
                    throw new OutOfStockException("Not enough stock or incorrect stock input.", cartItem.getProduct().getId());
                }
                cartItem.setQuantity(totalQuantity);
                product.setStock(product.getStock() - quantity);
            } else {
                cartItem = new CartItem(product, totalQuantity);
                cart.getCartItems().add(cartItem);
                product.setStock(product.getStock() - quantity);
            }

            this.refresh(cart);

        }
    }

    @Override
    public void update(Cart cart, Product product, int quantity) {
        Objects.requireNonNull(cart);
        Objects.requireNonNull(product);
        if (quantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException("Not enough stock or incorrect stock input.", product.getId());
        }

        synchronized (cart) {
            cart.getCartItems().stream()
                    .filter(cartItem -> product.getId().equals(cartItem.getProduct().getId()))
                    .findAny()
                    .ifPresent(cartItem -> cartItem.setQuantity(quantity));
        }
        this.refresh(cart);
    }

    @Override
    public void updateEachProduct(Cart cart, Map<Long, Integer> products) {
        synchronized (cart) {
            for (CartItem cartItem : cart.getCartItems()) {
                final Long productId = cartItem.getProduct().getId();
                if (products.containsKey(productId)) {
                    this.update(cart, cartItem.getProduct(), products.get(productId));
                }
            }
        }
    }

    @Override
    public void delete(Cart cart, Product product) {
        synchronized (cart) {
            cart.getCartItems()
                    .removeIf(cartItem -> product.getId().equals(cartItem.getProduct().getId()));
            this.refresh(cart);
        }
    }

    private int validateProductQuantity(Optional<CartItem> existsCartItem, Product product, int quantity) {

        int totalQuantity = existsCartItem
                .map(cartItem -> cartItem.getQuantity() + quantity)
                .orElse(quantity);

        if (totalQuantity > product.getStock() || quantity <= 0) {
            throw new OutOfStockException("Not enough stock or incorrect stock input.", product.getId());
        }

        return totalQuantity;
    }

    public synchronized void clear(Cart cart) {
        cart.clear();
        cart.setTotalQuantity(Cart.DEFAULT_VALUE);
        cart.setTotalCost(new BigDecimal(Cart.DEFAULT_VALUE));
    }

    public void refresh(Cart cart) {
        BigDecimal newTotalCost = this.calculateNewTotalCost(cart);
        int newTotalQuantity = this.calculateNewTotalQuantity(cart);

        LOGGER.info("New total quantity: {}", newTotalQuantity);

        cart.setTotalCost(newTotalCost);
        cart.setTotalQuantity(newTotalQuantity);
    }

    private BigDecimal calculateNewTotalCost(Cart cart) {
        return cart.getCartItems()
                .stream()
                .map(cartItem -> cartItem.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                ).reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private int calculateNewTotalQuantity(Cart cart) {
        return cart.getCartItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

}