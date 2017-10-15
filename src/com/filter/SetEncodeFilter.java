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


@WebFilter("/SetEncodeFilter")
public class SetEncodeFilter implements Filter {

	protected String encoding = null;
	protected FilterConfig config = null;
 
    public SetEncodeFilter() {
        // TODO Auto-generated constructor stub
    }
    
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		this.encoding = config.getInitParameter("encoding");
	}

	public void destroy() {
		this.encoding = null;
		this.config = null;
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		
		//first time false
//		if(session.getAttribute("showLogin")==null){
//			session.setAttribute("showLogin", false); 
//		}
	
		chain.doFilter(request, response);
		
		request.setCharacterEncoding(encoding);
		session.setAttribute("location", req.getRequestURI()+"?"+req.getQueryString());
	}


}
