package com.mem_management.controller;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.mgr.model.MgrService;
import com.mgr.model.MgrVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 500* 5 * 1024 * 1024)
public class Mem_managementServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		
		if("log_out".equals(action)){
			HttpSession session=req.getSession();
			session.removeAttribute("mgr_no");
			String url="/BackEnd/index.jsp";
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
			
		}
		
		
		
		
		if("mgr_login".equals(action)){
			
			 Set<String> errorMsgs = new HashSet<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			
		try{	
			
		
			
			MgrService mgrSvc=new MgrService();
			MgrVO mgr_vo = null;
			String mgr_no = null;
			try{
				
				 mgr_no=req.getParameter("mgr_no");
			
				
		    mgr_vo=mgrSvc.getOneMgr(mgr_no);
		
			if(mgr_vo==null){
				
				
				errorMsgs.add("帳密錯誤");
			}
		    
		    
		
			
			}catch(Exception e){
			
				errorMsgs.add("帳密錯誤");
			}
			
			String mgr_pwd=null;
		
			try{
			
			 mgr_pwd=req.getParameter("mgr_pwd");
	
			}catch(Exception e){
				errorMsgs.add("帳密錯誤");
			}
			
	try{
			if(!mgr_pwd.equals(mgr_vo.getMgr_pwd())){
				
				errorMsgs.add("帳密錯誤");
				
			}
	}catch(Exception e){
		errorMsgs.add("帳密錯誤");
	}
if (!errorMsgs.isEmpty()) {
	System.out.println("not empty error , url= "+req.getParameter("index.jsp"));
				String url=req.getParameter("index.jsp");
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			
			HttpSession session=req.getSession();
			session.setAttribute("mgr_no", mgr_no);

			String url=(String) session.getAttribute("backEnd_location");
			if(url==null){
			 url="/BackEnd/main.jsp";
			}
			System.out.println("success url= "+url);
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
			
			
		}catch(Exception e){
		
			String url=req.getParameter("index.jsp");
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
			
			
		}
			
			
			
		}
		
		
		
		if("reverse_mem_stat".equals(action)){
			
			try{
			String mem_ac= req.getParameter("mem_ac");
			req.setAttribute("whichPage", req.getParameter("whichPage"));
			
			MemService memSvc=new MemService();
			MemVO mem_vo= memSvc.getOneProd(mem_ac);
			String mem_stat="正常";
			mem_vo.setMem_stat(mem_stat);
			memSvc.updateMem(mem_vo);
			String url=req.getParameter("mem.jsp");
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
			
			
			}catch(Exception e){
				
				String url=req.getParameter("mem.jsp");
				RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				
				
			}
			
			
		}
		  
		  
		  
		  
		if("stop_mem_stat".equals(action)){
			
			try{
				String mem_ac= req.getParameter("mem_ac");
				req.setAttribute("whichPage", req.getParameter("whichPage"));
				
				MemService memSvc=new MemService();
				MemVO mem_vo= memSvc.getOneProd(mem_ac);
				String mem_stat="停權";
				mem_vo.setMem_stat(mem_stat);
				memSvc.updateMem(mem_vo);
				String url=req.getParameter("mem.jsp");
				RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				
				
				
				}catch(Exception e){
					
					String url=req.getParameter("mem.jsp");
					RequestDispatcher dispatcher=req.getRequestDispatcher(url);
					dispatcher.forward(req, res);
					
					
				}
			
			
			
			
			
			
			
			
			
			
			
			
		}
		  
		  
		  
		  
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
