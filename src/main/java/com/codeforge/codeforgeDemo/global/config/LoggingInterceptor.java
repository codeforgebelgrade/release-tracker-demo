package com.codeforge.codeforgeDemo.global.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class LoggingInterceptor implements Filter {

    private final static String CORRELATION_ID = "X-Correlation-Id";
    private final static Logger log = LogManager.getLogger(LoggingInterceptor.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            var req = (HttpServletRequest) servletRequest;
            var resp = (HttpServletResponse) servletResponse;
            var correlationId = req.getHeader(CORRELATION_ID);
            if (correlationId == null || correlationId.equals("")) {
                correlationId = UUID.randomUUID().toString();
            }
            MDC.put("cid", correlationId);
            log.info("[Correlation-Id: {}] Request {} {}?{}", correlationId, req.getMethod(), req.getRequestURL(), req.getQueryString());
            resp.addHeader(CORRELATION_ID, correlationId);
            filterChain.doFilter(req, resp);
            log.info("[Correlation-Id: {}] Response: {}", correlationId, resp.getStatus());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
