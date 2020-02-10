package com.es.phoneshop.web.listener;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductPriceHistory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.DEFAULT_DATA_DAO_INIT;

public class ProductDemoDataServletContextListener implements ServletContextListener {

    private ProductDao productDao;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Boolean param = Boolean.valueOf(sce.getServletContext().getInitParameter(DEFAULT_DATA_DAO_INIT));

        if (param) {
            productDao = ArrayListProductDao.getInstance();
            loadSampleDataToProductDao(productDao);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        productDao = null;
    }

    private void loadSampleDataToProductDao(ProductDao productDao) {

        final List<ProductPriceHistory> firstProductPriceHistory = Collections.unmodifiableList(new ArrayList<ProductPriceHistory>() {
            {
                add(new ProductPriceHistory(new Timestamp(1140004860L), new BigDecimal(200)));
                add(new ProductPriceHistory(new Timestamp(1180883040L), new BigDecimal(250)));
                add(new ProductPriceHistory(new Timestamp(1222128000L), new BigDecimal(150)));
                add(new ProductPriceHistory(new Timestamp(1262304000L), new BigDecimal(110)));
            }
        });

        final List<ProductPriceHistory> secondProductPriceHistory = Collections.unmodifiableList(new ArrayList<ProductPriceHistory>() {
            {
                add(new ProductPriceHistory(new Timestamp(1180004860L), new BigDecimal(100)));
                add(new ProductPriceHistory(new Timestamp(1190883040L), new BigDecimal(400)));
                add(new ProductPriceHistory(new Timestamp(1282128000L), new BigDecimal(350)));
                add(new ProductPriceHistory(new Timestamp(1292304000L), new BigDecimal(260)));
            }
        });

        Currency usd = Currency.getInstance("USD");
        productDao.save(new Product("sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg", firstProductPriceHistory));
        productDao.save(new Product("sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg", Collections.emptyList()));
        productDao.save(new Product("sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg", secondProductPriceHistory));
        productDao.save(new Product("iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg", Collections.emptyList()));
        productDao.save(new Product("iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg", Collections.emptyList()));
        productDao.save(new Product("htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg", firstProductPriceHistory));
        productDao.save(new Product("sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg", secondProductPriceHistory));
        productDao.save(new Product("xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg", secondProductPriceHistory));
        productDao.save(new Product("nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg", Collections.emptyList()));
        productDao.save(new Product("palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg", secondProductPriceHistory));
        productDao.save(new Product("simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg", firstProductPriceHistory));
        productDao.save(new Product("simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg", Collections.emptyList()));
        productDao.save(new Product("simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg", Collections.emptyList()));
    }

}