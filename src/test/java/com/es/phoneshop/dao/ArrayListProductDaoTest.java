package com.es.phoneshop.dao;

import com.es.phoneshop.exception.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {

    private static ProductDao productDao;

    @BeforeClass
    public static void setup() {
        productDao = ArrayListProductDao.getInstance();
        productDao.saveAll(
                Arrays.asList(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), null, 0, null),
                        new Product("nokia3310", "Nokia 3310", null, null, 100, null),
                        new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), null, 100, null),
                        new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), null, 30, null),
                        new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), null, 100, null)));
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByIdNullIdRefException() {
        productDao.getProductById(null);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByCodeNegativeIdException() {
        productDao.getProductById(-1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByIdStockIsZeroException() {
        productDao.getProductById(1L);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByCodePriceIsNullException() {
        productDao.getProductByCode("nokia3310");
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByCodeNullCodeRefException() {
        productDao.getProductByCode(null);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByCodeEmptyCodeException() {
        productDao.getProductByCode("");
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByCodeStockIsZeroException() {
        productDao.getProductByCode("htces4g");
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductByIdPriceIsNullException() {
        productDao.getProductById(2L);
    }

    @Test
    public void getProductByIdExistIdReturnProduct() {
        Long expectedId = 5L;
        Product actual = productDao.getProductById(expectedId);
        assertEquals(expectedId, actual.getId());
    }

    @Test
    public void getProductByCodeCodeIdReturnProduct() {
        String expectedCode = "xperiaxz";
        Product actual = productDao.getProductByCode(expectedCode);
        assertEquals(expectedCode, actual.getCode());
    }

    @Test
    public void findProductsTest() {
        List<Product> actualProductList = productDao.findProducts();
        actualProductList.forEach(product -> assertTrue((
                product.getStock() > ArrayListProductDao.EMPTY_STOCK_LEVEL) && (Objects.nonNull(product.getPrice()))
        ));
    }

    @Test
    public void saveNormalProductTest() {
        Product expectedProduct = new Product("htc4g", "HTC EV 4G", new BigDecimal(320), null, 100, null);
        productDao.save(expectedProduct);
        assertEquals(expectedProduct, productDao.getProductByCode("htc4g"));
    }

    @Test(expected = NullPointerException.class)
    public void saveNullProductNPE() {
        productDao.save(null);
    }

    @Test(expected = ProductNotFoundException.class)
    public void deleteExistedProductVoid() {
        Product product = productDao.getProductByCode("htc4g");
        productDao.delete(product.getId());
        productDao.getProductById(product.getId());
    }

}
