package com.javatest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CommonFilter implements Filter {

    public CommonFilter() {
    }

	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse)response;
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		res.setHeader("Content-Security-Policy",
				"default-src 'self';style-src 'self' 'unsafe-inline';script-src 'self' 'unsafe-inline';");
		res.setHeader("X-Content-Security-Policy",
				"allow 'self';style-src 'self' 'unsafe-inline';script-src 'self' 'unsafe-inline';");
		res.setHeader("X-WebKit-CSP",
				"default-src 'self';style-src 'self' 'unsafe-inline';script-src 'self' 'unsafe-inline';");
		res.addHeader("x-frame-options", "SAMEORIGIN");
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
