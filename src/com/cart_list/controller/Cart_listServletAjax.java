package com.cart_list.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cart_list.model.Cart_listService;
import com.cart_list.model.Cart_listVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

@WebServlet(name="Cart_listServletAjax",urlPatterns={"/cart_list/cart_listAjax.do"} )
public class Cart_listServletAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "application/json; charset=utf-8";
	
	private Cart_listService cart_listSvc;
	private ProdService prodSvc;


	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		out.println("doGet");
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
		String prod_no = jsonObject.get("prod_no").getAsString();
		String mem_ac = jsonObject.get("mem_ac").getAsString();
		
		cart_listSvc = new Cart_listService();
		prodSvc = new ProdService();
		ProdVO prodVO = prodSvc.getOneProd(prod_no);
		
		String outStr = "";
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		
		if(action.equals("insert")){
			String amount = jsonObject.get("amount").getAsString();
			Map<String,String> errorMsgs = new HashMap<String,String>();
			try{
				int amountInt = 0;
				try {
					amountInt = Integer.parseInt(amount);
				} catch (NumberFormatException e){
					errorMsgs.put("err","請輸入數字");
					outStr = gson.toJson(errorMsgs);
//					System.out.println(outStr);
					out.print(outStr);
					return;//程式中斷
				}
				
				if(amountInt<=0||amountInt>prodVO.getProd_sup()){
					errorMsgs.put("err","存入購物車數量為0或超過庫存");
					outStr = gson.toJson(errorMsgs);
					out.print(outStr);
					return;//程式中斷
				}
				
				//開始新增或update
				Cart_listVO oldCart_listVO = null;
				if((oldCart_listVO=cart_listSvc.getCart_list(prod_no, mem_ac))==null){
					cart_listSvc.addCart_list(prod_no, mem_ac, amountInt);
				} else {
					amountInt += oldCart_listVO.getProd_amount();
					if(amountInt>prodVO.getProd_sup()){
						errorMsgs.put("err","存入購物車數量超過庫存");
						outStr = gson.toJson(errorMsgs);
						out.print(outStr);
						return;//程式中斷
					}
					cart_listSvc.updateCart_list(prod_no, mem_ac, amountInt);
				}
				
			
				
				List<NameCart> cartList = new ArrayList<NameCart>();
				//找回更新後的購物車
				Set<Cart_listVO> cartSet = cart_listSvc.getVOsByMem(mem_ac);
//				System.out.println(cartSet);
				for(Cart_listVO cart_listVO : cartSet){
					NameCart nc = new NameCart((prodSvc.getOneProd(cart_listVO.getProd_no()).getProd_name()),
							prodSvc.getOneProd(cart_listVO.getProd_no()).getProd_price(), cart_listVO.getProd_amount());
					cartList.add(nc);
				}
//				System.out.println(cartList);
				
				outStr = gson.toJson(cartList);
//				System.out.println(outStr);
				out.println(outStr);
	
			} catch (Exception e) {//其他錯誤
				errorMsgs.put("err", e.getMessage());
//				errorMsgs.put("err","加入購物車失敗");
				outStr = gson.toJson(errorMsgs);
				out.print(outStr);
				return;//程式中斷
				
			}
		}
		
		if(action.equals("update")){
			String amount = jsonObject.get("amount").getAsString();
			Map<String,String> errorMsgs = new HashMap<String,String>();
			try{
				int amountInt = 0;
				try {
					amountInt = Integer.parseInt(amount);
				} catch (NumberFormatException e){
					errorMsgs.put("err","請輸入數字");
					outStr = gson.toJson(errorMsgs);
//					System.out.println(outStr);
					out.print(outStr);
					return;//程式中斷
				}
				
				if(amountInt<=0||amountInt>prodVO.getProd_sup()){
					errorMsgs.put("err","存入購物車數量為0或超過庫存");
					outStr = gson.toJson(errorMsgs);
					out.print(outStr);
					return;//程式中斷
				}
				
				//開始新增或update
				cart_listSvc.updateCart_list(prod_no, mem_ac, amountInt);

				List<NameCart> cartList = new ArrayList<NameCart>();
				outStr = gson.toJson("true");
				out.println(outStr);
//				System.out.println(outStr+"52+");
	
			} catch (Exception e) {//其他錯誤
				errorMsgs.put("err", e.getMessage());
//				errorMsgs.put("err","加入購物車失敗");
				outStr = gson.toJson(errorMsgs);
				out.print(outStr);
				return;//程式中斷
				
			}
		}
		
		if(action.equals("delete")){
			Map<String,String> errorMsgs = new HashMap<String,String>();
			try{
				cart_listSvc.deleteCart_list(prod_no, mem_ac);
				outStr = gson.toJson("true");
				out.println(outStr);
//				System.out.println("del"+outStr);
			} catch (Exception e) {//其他錯誤
				errorMsgs.put("err", e.getMessage());
//				errorMsgs.put("err","刪除購物車商品失敗");
				outStr = gson.toJson(errorMsgs);
				out.print(outStr);
				return;//程式中斷
				
			}
				
		}
	}
}

class NameCart{
	String prod_name;
	Integer prod_price;
	Integer amount;
	NameCart(String prod_name, Integer prod_price, Integer amount){
		this.prod_name = prod_name;
		this. prod_price =  prod_price;
		this.amount = amount;
	}
}
