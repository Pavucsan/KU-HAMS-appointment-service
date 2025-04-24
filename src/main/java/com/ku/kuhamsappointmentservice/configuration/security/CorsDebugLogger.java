package com.ku.kuhamsappointmentservice.configuration.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CorsDebugLogger implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("🔍 CORS check: " + req.getMethod() + " " + req.getRequestURI());
        chain.doFilter(request, response);
    }
}
