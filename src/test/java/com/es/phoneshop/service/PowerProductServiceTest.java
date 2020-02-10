package com.es.phoneshop.service;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.product.ProductSortingOrder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PowerProductServiceTest {

    private static ProductDao productDao;
    private static ProductService productService;

    @BeforeClass
    public static void setUp() throws Exception {
        productDao = ArrayListProductDao.getInstance();
        productService = PowerProductService.getInstance();

        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), null, 30, null, Collections.emptyList()));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), null, 20, null, Collections.emptyList()));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), null, 30, null, Collections.emptyList()));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), null, 40, null, Collections.emptyList()));
    }

    @Test
    public void findProductsNullQueryNullFieldNullOrderTest() {
        List<Product> actualProducts = productService.findProducts(null, null, null);
        assertEquals(productDao.findProducts(), actualProducts);
    }

    @Test
    public void findProductsNormalQueryNullFieldNullOrderTest() {
        List<Product> expectedProducts = Arrays.asList(productDao.findProducts().get(0), productDao.findProducts().get(1));
        List<Product> actualProducts = productService.findProducts("pal c56", null, null);
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void findProductsBadQueryNullFieldNullOrderTest() {
        List<Product> actualProducts = productService.findProducts("11111111111", null, null);
        assertEquals(Collections.emptyList(), actualProducts);
    }

    @Test
    public void findProductsEmptyStringQueryNullFieldNullOrderTest() {
        List<Product> actualProducts = productService.findProducts("", null, null);
        assertEquals(productDao.findProducts(), actualProducts);
    }

    @Test
    public void findProductsEmptyQueryPriceFieldAscOrderTest() {
        List<Product> expectedProducts = productDao.findProducts();
        expectedProducts.sort(Comparator.comparing(Product::getPrice));
        List<Product> actualProducts = productService.findProducts(null, ProductSortingField.PRICE, ProductSortingOrder.ASCENDING);
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void findProductsEmptyQueryPriceFieldDescOrderTest() {
        List<Product> expectedProducts = productDao.findProducts();
        expectedProducts.sort(Comparator.comparing(Product::getPrice).reversed());
        List<Product> actualProducts = productService.findProducts(null, ProductSortingField.PRICE, ProductSortingOrder.DESCENDING);
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void findProductsEmptyQueryDescrFieldDescOrderTest() {
        List<Product> expectedProducts = productDao.findProducts();
        expectedProducts.sort(Comparator.comparing(Product::getDescription).reversed());
        List<Product> actualProducts = productService.findProducts(null, ProductSortingField.DESCRIPTION, ProductSortingOrder.DESCENDING);
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void findProductsEmptyQueryDescrFieldAscOrderTest() {
        List<Product> expectedProducts = productDao.findProducts();
        expectedProducts.sort(Comparator.comparing(Product::getDescription));
        List<Product> actualProducts = productService.findProducts(null, ProductSortingField.DESCRIPTION, ProductSortingOrder.ASCENDING);
        assertEquals(expectedProducts, actualProducts);
    }

}