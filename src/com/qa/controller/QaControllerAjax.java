package com.qa.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cart_list.model.Cart_listService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.qa.model.QaService;
import com.qa.model.QaVO;
import com.review.model.ReviewService;
import com.review.model.ReviewVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;


@WebServlet(name="QaControllerAjax",urlPatterns={"/qa/qaAjax.do"})
public class QaControllerAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "application/json; charset=utf-8";
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while((line = br.readLine()) != null){
			jsonIn.append(line);
		}
		br.close();
//		System.out.println("Jin"+jsonIn);
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();

		
		String outStr = "";
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		
		
		if ("addQa".equals(action)) { // 來自addEmp.jsp的請求  
			
			Map<String,String> errorMsgs = new HashMap<String,String>();
			
			String prod_no = jsonObject.get("prod_no").getAsString();
			if(!prod_no.matches("P[0-9]{10}")){
				errorMsgs.put("errProd_no","Prod編號格式不正確");
			}
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				HttpSession session = req.getSession();
				String mem_ac = (String) session.getAttribute("mem_ac");
				if(mem_ac==null || mem_ac.trim().length()==0){
					errorMsgs.put("errLogin", "沒有登入");
				}
				
				String qa_cont = jsonObject.get("qa_cont").getAsString().trim();
				if (qa_cont == null || (qa_cont).length() == 0) {
					errorMsgs.put("errQa_cont","請輸入提問");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					String stat = "?prodNo="+prod_no+"&status=2&errQa_cont="+errorMsgs.get("errQa_cont");
					String url = "/FrontEnd/prod/prodPage.jsp"+stat;
					RequestDispatcher successView = req.getRequestDispatcher(url); 
					successView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始新增資料***************************************/
				QaService qaSvc = new QaService();
				
				QaVO qaVO = qaSvc.addQa( prod_no,  mem_ac, qa_cont);
				String stat = "?prodNo="+prod_no+"&status=2";
				String url = "/FrontEnd/prod/prodPage.jsp"+stat;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
	
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("err", e.getMessage());
//				errorMsgs.put("err","加入購物車失敗");
				String stat = "?prodNo="+prod_no+"&status=2&errQa_cont="+errorMsgs.get("errQa_cont");
				String url = "/FrontEnd/prod/prodPage.jsp"+stat;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				return;//程式中斷
			}
		}
		
		if ("replyQa".equals(action)) { // 來自addEmp.jsp的請求  
			
			Map<String,String> errorMsgs = new HashMap<String,String>();
				
			String prod_no = jsonObject.get("prod_no").getAsString();
			if(!prod_no.matches("P[0-9]{10}")){
				errorMsgs.put("errProd_no","Prod編號格式不正確");
			}
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				HttpSession session = req.getSession();
				String mem_ac = (String) session.getAttribute("mem_ac");
				if(mem_ac==null || mem_ac.trim().length()==0){
					errorMsgs.put("errLogin", "沒有登入");
				}
				
				ProdService prodSvc =new ProdService();
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore((prodSvc.getOneProd(prod_no).getStore_no()));
				
				if(!storeVO.getMem_ac().equals(mem_ac)){
					System.out.println(storeVO.getMem_ac());
					errorMsgs.put("errMem_ac","回覆帳號不正確");
				}				
				String qa_no = jsonObject.get("qa_no").getAsString();
				if(!qa_no.matches("Q[0-9]{10}")){
					errorMsgs.put("errQa_no","QA編號格式不正確");
				}
				String qa_reply_cont = jsonObject.get("qa_reply_cont").getAsString().trim();
				if (qa_reply_cont == null || (qa_reply_cont).length() == 0) {
					errorMsgs.put("errQa_reply_cont","請輸入回覆");
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
//					System.out.println(errorMsgs);
					String stat = "?prodNo="+prod_no+"&status=2&errQa_reply_cont="+errorMsgs.get("errQa_reply_cont");
					String url = "/FrontEnd/prod/prodPage.jsp"+stat;
					RequestDispatcher successView = req.getRequestDispatcher(url); 
					successView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始新增資料***************************************/
				QaService qaSvc = new QaService();
				
				QaVO qaVO = qaSvc.replyQa( qa_no,  qa_reply_cont);
				String stat = "?prodNo="+prod_no+"&status=2";
				String url = "/FrontEnd/prod/prodPage.jsp"+stat;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
	
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
//				System.out.println(e.getMessage());
				errorMsgs.put("err", e.getMessage());
//				errorMsgs.put("err","加入購物車失敗");
				String stat = "?prodNo="+prod_no+"&status=2&errQa_reply_cont="+errorMsgs.get("errQa_reply_cont");
				String url = "/FrontEnd/prod/prodPage.jsp"+stat;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				return;//程式中斷
			}
		}
	}

}
