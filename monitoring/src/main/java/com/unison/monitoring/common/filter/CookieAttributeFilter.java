package com.unison.monitoring.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

//TODO I need to check action of this class
//if this class not working in my app, then delete this class
@Component
public class CookieAttributeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        chain.doFilter(request, response);
        // you can use this when you use https protocol
        addSameSite(httpServletResponse , "Lax");
    }

    private void addSameSite(HttpServletResponse response, String sameSite) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        for (String header : headers) {
            response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
        }
    }

    private void addHttpOnly(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        for (String header : headers) {
            response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure;", header));
        }
    }
}