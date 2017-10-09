package com.fo_store.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fo_store.model.Fo_storeService;
import com.fo_store.model.Fo_storeVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


@WebServlet(name="Fo_storeServletAjax",urlPatterns={"/fo_store/fo_storeAjax.do"} )
public class Fo_storeServletAjax extends HttpServlet {
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
		String store_no = jsonObject.get("store_no").getAsString();
		
		String outStr = "";
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		
		if(action.equals("foStore")){
			
			Map<String,String> errorMsgs = new HashMap<String,String>();
			
			try{
				HttpSession session = req.getSession();
				String mem_ac = (String) session.getAttribute("mem_ac");
				if(mem_ac==null || mem_ac.trim().length()==0){
					errorMsgs.put("err", "沒有登入");
				}
				
				if (!errorMsgs.isEmpty()) {
					outStr = gson.toJson(errorMsgs);
					out.print(outStr);
					return;
				}
				
				Fo_storeService fo_storeSvc = new Fo_storeService();
				Fo_storeVO fo_storeVO = fo_storeSvc.getOne(store_no,mem_ac);
				Map<String,Integer> jdata = new HashMap<String,Integer>();
				//add
				if(fo_storeVO == null){
					fo_storeVO = fo_storeSvc.addFo_store(store_no, mem_ac);
					Integer count = fo_storeSvc.getCountByStore(store_no);
					jdata.put("isAdd", 1);
					jdata.put("count", count);
				//delete
				} else {
					fo_storeSvc.deleteFo_store(store_no, mem_ac);
					Integer count = fo_storeSvc.getCountByStore(store_no);
					jdata.put("isAdd", 0);
					jdata.put("count", count);
				}
				
				outStr = gson.toJson(jdata);
				out.print(outStr);
				System.out.println("***************");
				System.out.println("Jout"+outStr);
				
				
			} catch (Exception e) {//其他錯誤
				errorMsgs.put("err", e.getMessage());
//				errorMsgs.put("err","查詢訂單資訊失敗");
				outStr = gson.toJson(errorMsgs);
				out.print(outStr);
				return;
			}

		}
	}

}
