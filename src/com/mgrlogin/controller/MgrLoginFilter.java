package com.mgrlogin.controller;

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
public class MgrLoginFilter implements Filter {
	private FilterConfig config;

    public MgrLoginFilter() {
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
		Object mgr_no = session.getAttribute("mgr_no");
		if (mgr_no == null) {
//			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/login.html");
//			session.setAttribute("mem_ac","mrbrown");
//			System.out.println("filterSet");
			System.out.println("url= "+req.getContextPath()+"/BackEnd/index.jsp");
			session.setAttribute("location", req.getServletPath());
			System.out.println("req.getServletPath()= "+req.getServletPath());
			res.sendRedirect(req.getContextPath()+"/BackEnd/index.jsp");
			
			chain.doFilter(request, response);
			return;
		} else {
//			System.out.println("filterElse");
		
			chain.doFilter(request, response);
		}
	}

	
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

}
