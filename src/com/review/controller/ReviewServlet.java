package com.review.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.review.model.*;
/**
 * Servlet implementation class ReviewServlet
 */
@WebServlet(name="ReviewServlet",urlPatterns={"/review/review.do"})
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String rev_no = req.getParameter("rev_no");
				if (rev_no == null || (rev_no.trim()).length() == 0) {
					errorMsgs.add("請輸入Review編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/review/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
//				try {
					if(!rev_no.matches("R[0-9]{10}")){
						errorMsgs.add("Review編號格式不正確");
//						throw new Exception("str not match R[0-9]{10}");
					}
//				} catch (Exception e) {
//					errorMsgs.add("Review編號格式不正確");
//				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/review/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ReviewService reviewSvc = new ReviewService();
				ReviewVO reviewVO = reviewSvc.getOneReview(rev_no);
				if (reviewVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/review/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reviewVO", reviewVO); // 資料庫取出的empVO物件,存入req
				String url = "/review/listOneReview.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/review/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		if ("addRev".equals(action)) { // 來自addEmp.jsp的請求  
			
			Map<String,String> errorMsgs = new HashMap<String,String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String ord_no = req.getParameter("ord_no").trim();
				if(!ord_no.matches("O[0-9]{10}")){
					errorMsgs.put("errOrd_no","Order編號格式不正確");
				}
				
				String prod_no = req.getParameter("prod_no").trim();
				if(!prod_no.matches("P[0-9]{10}")){
					errorMsgs.put("errProd_no","Prod編號格式不正確");
				}
				Integer prod_score = null;
				try {
					prod_score = new Integer(req.getParameter("prod_score").trim());
				} catch (NumberFormatException e) {
					errorMsgs.put("errScore","評分請填數字1~5.");
				}
				
				String useG = req.getParameter("useG").trim();
				String useMl = req.getParameter("useMl").trim();
				String useC = req.getParameter("useC").trim();
				String useS = req.getParameter("useS").trim();
				if (useG == null || (useG).length() == 0) {
					errorMsgs.put("errUse_way","使用方式輸入錯誤");
				}
				if (useMl == null || (useMl).length() == 0) {
					errorMsgs.put("errUse_way","使用方式輸入錯誤");
				}
				if (useC == null || (useC).length() == 0) {
					errorMsgs.put("errUse_way","使用方式輸入錯誤");
				}
				if (useS == null || (useS).length() == 0) {
					errorMsgs.put("errUse_way","使用方式輸入錯誤");
				}
				
				StringBuffer sb = new StringBuffer();
				try{
					sb.append(Integer.parseInt(useG)+",");
					sb.append(Integer.parseInt(useMl)+",");
					sb.append(Integer.parseInt(useC)+",");
					sb.append(Integer.parseInt(useS));
				} catch (NumberFormatException e){
					errorMsgs.put("errUse_way","使用方式輸入錯誤");
				}
				
				String rev_cont = req.getParameter("rev_cont").trim();
				if (rev_cont == null || (rev_cont).length() == 0) {
					errorMsgs.put("errCont","請輸入心得評論");
				}
				
				
				// 打包回去
				ReviewVO reviewVO = new ReviewVO();
				reviewVO.setOrd_no(ord_no);
				reviewVO.setProd_no(prod_no);
				reviewVO.setProd_score(prod_score);
				reviewVO.setUse_way(sb.toString());
				reviewVO.setRev_cont(rev_cont);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reviewVO", reviewVO); // 含有輸入格式錯誤的empVO物件,也存入req
					String stat = "?status=3";
					String url = "/FrontEnd/buyerorder/buyerorder.jsp"+stat;
					RequestDispatcher failureView = req.getRequestDispatcher(url); 
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ReviewService reviewSvc = new ReviewService();
				
				reviewVO = reviewSvc.addReview( ord_no, prod_no, prod_score, sb.toString(), rev_cont);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/	
				String stat = "?status=3";
				String url = "/FrontEnd/buyerorder/buyerorder.jsp"+stat;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
//				System.out.println("Exception"+e.getMessage());
				errorMsgs.put("err",e.getMessage());
				errorMsgs.put("err","新增失敗");
				String stat = "?status=3";
				String url = "/FrontEnd/buyerorder/buyerorder.jsp"+stat;
				RequestDispatcher failureView = req.getRequestDispatcher(url); 
				failureView.forward(req, res);
				return;
			}
		}
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String rev_no = req.getParameter("rev_no").trim();
				
				/***************************2.開始刪除資料***************************************/
				ReviewService reviewSvc = new ReviewService();
				reviewSvc.deleteReview(rev_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/review/listAllReview.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/review/listAllReview.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String rev_no = req.getParameter("rev_no").trim();
				
				/***************************2.開始查詢資料****************************************/
				ReviewService reviewSvc = new ReviewService();
				ReviewVO reviewVO = reviewSvc.getOneReview(rev_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("reviewVO", reviewVO);         // 資料庫取出的empVO物件,存入req
				String url = "/review/update_review_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/review/listAllReview.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				
				String ord_no = req.getParameter("ord_no").trim();
				if(!ord_no.matches("O[0-9]{10}")){
					errorMsgs.add("Order編號格式不正確");
				}
				
				String prod_no = req.getParameter("prod_no").trim();
				if(!prod_no.matches("P[0-9]{10}")){
					errorMsgs.add("Prod編號格式不正確");
				}
				
				Integer prod_score = null;
				try {
					prod_score = new Integer(req.getParameter("prod_score").trim());
				} catch (NumberFormatException e) {
					prod_score = 3;
					errorMsgs.add("評分請填數字1~5.");
				}
				
				String use_way = req.getParameter("use_way").trim();
				if (use_way == null || (use_way).length() == 0) {
					errorMsgs.add("請輸入使用方式");
				}
				
				String rev_cont = req.getParameter("rev_cont").trim();
				if (rev_cont == null || (rev_cont).length() == 0) {
					errorMsgs.add("請輸入心得評分");
				}
				
				Date rev_date  = null;
				try {
					rev_date = Date.valueOf(req.getParameter("rev_date").trim());
				} catch (IllegalArgumentException e) {
					rev_date=new Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				// 打包回去
				ReviewVO reviewVO = new ReviewVO();
				reviewVO.setOrd_no(ord_no);
				reviewVO.setProd_no(prod_no);
				reviewVO.setProd_score(prod_score);
				reviewVO.setUse_way(use_way);
				reviewVO.setRev_cont(rev_cont);
				reviewVO.setRev_date(rev_date);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("reviewVO", reviewVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/review/update_review_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ReviewService reviewSvc = new ReviewService();
				
				reviewVO = reviewSvc.updateReview( ord_no, prod_no, prod_score, use_way, rev_cont, rev_date);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("reviewVO", reviewVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/review/listOneReview.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/review/update_review_input.jsp");
				failureView.forward(req, res);
			}
				
		}
	}

}
