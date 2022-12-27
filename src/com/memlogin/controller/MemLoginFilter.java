package com.memlogin.controller;

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

/**
 * Servlet Filter implementation class MemLogin
 */
@WebFilter("/MemLogin")
public class MemLoginFilter implements Filter {
	private FilterConfig config;

    public MemLoginFilter() {
    }

	
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}


	public void destroy() {
		config = null;
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否登入過】
		Object mem_ac = session.getAttribute("mem_ac");
		String location = (String)session.getAttribute("location");
		
		
		if((Boolean)session.getAttribute("showLogin")){
			chain.doFilter(request, response);
			return;
		}

		//go login
		if (mem_ac == null) {

			session.setAttribute("showLogin", true); 
			if(location!=null){
				res.sendRedirect(location); 
			} else {
				res.sendRedirect(req.getContextPath()+"/FrontEnd/index/index.jsp"); 
			}
			return;
		//pass save this URI to session
		} else {
			session.setAttribute("showLogin", false); 
			session.setAttribute("location", req.getRequestURI());
			chain.doFilter(request, response);
		}
		

	}


}
