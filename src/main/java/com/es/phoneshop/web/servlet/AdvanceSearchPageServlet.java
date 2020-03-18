package com.es.phoneshop.web.servlet;

import com.es.phoneshop.model.advanceSeach.WordsSearchOptions;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.AdvanceSearchService;
import com.es.phoneshop.service.DefaultAdvanceSearchService;
import com.es.phoneshop.web.util.RequestParamsValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.es.phoneshop.web.util.ApplicationConstants.WebConstants.*;

public class AdvanceSearchPageServlet extends HttpServlet {

    private RequestParamsValidator requestParamsValidator;
    private AdvanceSearchService advanceSearchService;

    @Override
    public void init() throws ServletException {
        requestParamsValidator = new RequestParamsValidator();
        advanceSearchService = DefaultAdvanceSearchService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(WORD_SEARCH_OPTION, WordsSearchOptions.values());
        request.getRequestDispatcher("/WEB-INF/pages/advanceSearch.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter(DESCRIPTION_PARAM);
        String minPriceString = request.getParameter(MIN_PRICE_PARAM);
        String maxPriceString = request.getParameter(MAX_PRICE_PARAM);
        String wordsSearchOptionsString = request.getParameter(SEARCH_OPTION_PARAM);

        Map<String, String> errors = new HashMap<>();

        Integer minPrice = requestParamsValidator.validatePrice(minPriceString, MIN_PRICE_PARAM, errors);
        Integer maxPrice = requestParamsValidator.validatePrice(maxPriceString, MAX_PRICE_PARAM, errors);
        WordsSearchOptions wordsSearchOptions = requestParamsValidator.validateWordsSearchOptions(wordsSearchOptionsString, errors);

        if (errors.isEmpty()) {
            List<Product> productList = advanceSearchService.findProducts(searchQuery, minPrice, maxPrice, wordsSearchOptions);
            request.setAttribute(PRODUCTS, productList);
        } else {
            request.setAttribute(ERRORS, errors);
        }

        this.doGet(request, response);
    }

}
