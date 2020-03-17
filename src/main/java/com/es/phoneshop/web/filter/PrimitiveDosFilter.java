package com.es.phoneshop.web.filter;

import com.es.phoneshop.service.DefaultDosService;
import com.es.phoneshop.service.DosService;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PrimitiveDosFilter implements Filter {

    public static final int SC_TOO_MANY_REQUESTS = 429;
    private DosService dosService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        dosService = DefaultDosService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (dosService.isAllowed(request.getRemoteAddr())) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(SC_TOO_MANY_REQUESTS);
        }
    }

    @Override
    public void destroy() {
        dosService = null;
    }

}
