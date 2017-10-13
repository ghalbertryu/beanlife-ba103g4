package com.prod.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.prod.model.ProdService;
import com.prod.model.ProdVO;



@WebServlet(name="ProdServlet",urlPatterns={"/prod/prod.do"})
public class ProdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");


		if ("searchProds".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String bean_contry = req.getParameter("bean_contry");
				String proc = req.getParameter("proc");
				String roast = req.getParameter("roast");
				String others = req.getParameter("others");

				//bring the search param back
				HttpSession session = req.getSession();
				Map<String, String> mapBack = new HashMap<>();
				mapBack.put("bean_contry", bean_contry);
				mapBack.put("proc", proc);
				mapBack.put("roast", roast);
				mapBack.put("others", others);
				session.setAttribute("mapBack", mapBack);
				
				System.out.println(roast.contains(","));
				String[] roastRange = roast.split(",");
				System.out.println(roastRange[0]);
				if(Integer.parseInt(roastRange[0])>Integer.parseInt(roastRange[1])){
					String str = roastRange[0];
					roastRange[0]=roastRange[1];
					roastRange[1]=str;
				}
				
				
				
				/***************************2.開始查詢資料*****************************************/

				// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
				Map<String, String[]> map = new TreeMap<String, String[]>();
				map.put("bean_contry", new String[] {bean_contry});
				map.put("proc", new String[] { proc });
				map.put("roast0", new String[] {roastRange[0] });
				map.put("roast1", new String[] {roastRange[1] });

//				map.put("action", new String[] { "searchProds" }); // 注意Map裡面會含有action的key

				Map<String, String[]> map2 = new TreeMap<String, String[]>();
				map2.put("prod_no", new String[] {others });
				map2.put("store_no", new String[] {others });
				map2.put("prod_name", new String[] {others });
				map2.put("bean_type", new String[] {others });
				map2.put("bean_grade", new String[] {others });
				map2.put("bean_region", new String[] {others });
				map2.put("bean_farm", new String[] {others });
				map2.put("bean_farmer", new String[] {others });
				map2.put("bean_aroma", new String[] {others });
				map2.put("prod_stat", new String[] {others });
				map2.put("prod_cont", new String[] {others });
				
				
				
				ProdService prodSvc = new ProdService();
				List<ProdVO> list  = prodSvc.getAllR(map,map2);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("searchRs", list); // 資料庫取出的list物件,存入request
				RequestDispatcher successView = req.getRequestDispatcher("/FrontEnd/shop/shop.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/FrontEnd/shop/shop.jsp");
				failureView.forward(req, res);
			}
		}
		
		
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}

}
