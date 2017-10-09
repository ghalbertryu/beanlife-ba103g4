package com.ord.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ord.model.OrdService;
import com.ord.model.OrdVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;


@WebServlet(name="OrdServletAjax",urlPatterns={"/ord/ordAjax.do"})
public class OrdServletAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "application/json; charset=utf-8";
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println("doGet");
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
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
		String ord_no = jsonObject.get("ord_no").getAsString();
		String store_no = jsonObject.get("store_no").getAsString();
		
		String outStr = "";
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		
		if(action.equals("getPayInfo")){
			
			Map<String,String> errorMsgs = new HashMap<String,String>();
	
			try{
				HttpSession session = req.getSession();
				String mem_ac = (String) session.getAttribute("mem_ac");
				if(mem_ac==null || mem_ac.trim().length()==0){
					errorMsgs.put("errLogin", "沒有登入");
				}
				
				OrdService ordSvc = new OrdService();
				OrdVO ordVO = ordSvc.getOrdByOrdno(ord_no);
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getOneStore(store_no);
				
				if(!mem_ac.equals(ordVO.getMem_ac())){
					errorMsgs.put("errMem_ac", "非下訂單帳號查詢");
				}
				
				if (!errorMsgs.isEmpty()) {
					outStr = gson.toJson(errorMsgs);
					out.print(outStr);
					return;
				}
				
				PayInfo payInfo = new PayInfo(ordVO.getOrd_no(),  ordVO.getOrd_date(),  ordVO.getTotal_pay(),  storeVO.getStore_atm_info());
				outStr = gson.toJson(payInfo);
				out.print(outStr);
//				System.out.println("Jout"+outStr);
				
				
			} catch (Exception e) {//其他錯誤
				errorMsgs.put("err", e.getMessage());
//				errorMsgs.put("err","查詢訂單資訊失敗");
				outStr = gson.toJson(errorMsgs);
				out.print(outStr);
				return;//程式中斷		
			}

		}

	}
	
	class PayInfo{
		String ord_no;
		Date ord_date;
		Integer total_pay;
		String store_atm_info;
		
		public PayInfo(String ord_no, Date ord_date, Integer total_pay, String store_atm_info) {
			super();
			this.ord_no = ord_no;
			this.ord_date = ord_date;
			this.total_pay = total_pay;
			this.store_atm_info = store_atm_info;
		}
		
	}
}
