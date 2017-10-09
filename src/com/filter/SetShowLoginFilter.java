package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter("/SetShowLoginFilter")
public class SetShowLoginFilter implements Filter {

	protected String encoding = null;
	protected FilterConfig config = null;
 
    public SetShowLoginFilter() {
        // TODO Auto-generated constructor stub
    }
    
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {
		this.config = null;
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		
		//first time false
		if(session.getAttribute("showLogin")==null){
			session.setAttribute("showLogin", false); 
		}
	
		chain.doFilter(request, response);

	}


}
