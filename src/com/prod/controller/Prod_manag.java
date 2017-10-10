package com.prod.controller;

import java.io.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.prod.model.*;
import com.store.model.*;

@WebServlet("/prod/Prod_manag.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class Prod_manag extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		System.out.println(req.getParameter("action"));
		String action = req.getParameter("action");

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String prod_no = new String(req.getParameter("prod_no"));
				String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁,

				// 存入req(只用於:istAllEmp.jsp)

				/*************************** 2.開始查詢資料 ****************************************/
				ProdService prodSvc = new ProdService();
				ProdVO prodvo = prodSvc.getOneProd(prod_no);
				
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("prodvo", prodvo); // 資料庫取出的empVO物件,存入req
				String url = "/FrontEnd/prod_mag/getprod_forupdate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
				// update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {

				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/prod_mag/listAllpro_bystore.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update_prod".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				String prod_no = req.getParameter("prod_no");
				String store_no = req.getParameter("store_no").trim();
				String prod_name = req.getParameter("prod_name").trim();
				String bean_type = req.getParameter("bean_type").trim();
				String bean_grade = req.getParameter("bean_grade").trim();
				String bean_contry = req.getParameter("bean_contry").trim();
				String bean_region = req.getParameter("bean_region").trim();
				String bean_farm = req.getParameter("bean_farm").trim();
				String bean_farmer = req.getParameter("bean_farmer").trim();
				Integer bean_el = new Integer(req.getParameter("bean_el").trim());
				String proc = req.getParameter("proc").trim();
				String roast = req.getParameter("roast").trim();
				Integer bean_attr_acid = new Integer(req.getParameter("bean_attr_acid").trim());
				Integer bean_attr_aroma = new Integer(req.getParameter("bean_attr_aroma").trim());
				Integer bean_attr_body = new Integer(req.getParameter("bean_attr_body").trim());
				Integer bean_attr_after = new Integer(req.getParameter("bean_attr_after").trim());
				Integer bean_attr_bal = new Integer(req.getParameter("bean_attr_bal").trim());
				String bean_aroma = req.getParameter("bean_aroma").trim();
				Integer prod_price = new Integer(req.getParameter("prod_price").trim());
				Double prod_wt = new Double(req.getParameter("prod_wt").trim());
				Integer send_fee = new Integer(req.getParameter("send_fee").trim());
				Integer prod_sup = new Integer(req.getParameter("prod_sup").trim());
				String prod_cont = req.getParameter("prod_cont").trim();
				String prod_stat = req.getParameter("prod_stat").trim();

				String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁,
															// 存入req(只用於:istAllEmp.jsp)

				InputStream is = req.getPart("prod_pic1").getInputStream();
				byte[] prod_pic1 = null;
				if (!req.getPart("prod_pic1").getContentType().contains("image")) {
					ProdService prodSvc = new ProdService();
					ProdVO prodvo = prodSvc.getOneProd(prod_no);
					prod_pic1 = prodvo.getProd_pic1();
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] pho1 = new byte[16384];
					while ((p1 = is.read(pho1)) != -1) {
						pro_1.write(pho1, 0, p1);
					}
					prod_pic1 = pro_1.toByteArray();
					req.getSession().setAttribute("prod_pic1", prod_pic1);
				}

				InputStream is2 = req.getPart("prod_pic2").getInputStream();
				byte[] prod_pic2 = null;
				if (!req.getPart("prod_pic2").getContentType().contains("image")) {
					ProdService prodSvc = new ProdService();
					ProdVO prodvo = prodSvc.getOneProd(prod_no);
					prod_pic2 = prodvo.getProd_pic2();
				} else {
					ByteArrayOutputStream pro_2 = new ByteArrayOutputStream();
					int p2;
					byte[] pho2 = new byte[16384];
					while ((p2 = is2.read(pho2)) != -1) {
						pro_2.write(pho2, 0, p2);
					}
					prod_pic2 = pro_2.toByteArray();
					req.getSession().setAttribute("prod_pic2", prod_pic2);
				}

				InputStream is3 = req.getPart("prod_pic3").getInputStream();
				byte[] prod_pic3 = null;
				if (!req.getPart("prod_pic3").getContentType().contains("image")) {
					ProdService prodSvc = new ProdService();
					ProdVO prodvo = prodSvc.getOneProd(prod_no);
					prod_pic3 = prodvo.getProd_pic3();
				} else {
					ByteArrayOutputStream pro_3 = new ByteArrayOutputStream();
					int p3;
					byte[] pho3 = new byte[16384];
					while ((p3 = is3.read(pho3)) != -1) {
						pro_3.write(pho3, 0, p3);
					}
					prod_pic3 = pro_3.toByteArray();
					req.getSession().setAttribute("prod_pic3", prod_pic3);
				}
				
				
//				String prod_wt_reg= "(^[0-9]+(.[0-9]{1})?$)";
//				if(String.valueOf( prod_wt).matches(prod_wt_reg)==false){
//					errorMsgs.add("重量只能小數點後一位");
//				}
				if(prod_name.length()==0||prod_name==null){
					errorMsgs.add("商品名不能為空");
				}
				if(bean_contry.length()==0||bean_contry==null){
					errorMsgs.add("生產國不能為空");
				}
				if(prod_price.toString().length()==0){
					errorMsgs.add("價錢不能為空");
				}
				if(prod_wt.toString().length()==0){
					errorMsgs.add("重量不能為空");
				}
				if(send_fee.toString().length()==0){
					errorMsgs.add("重量不能為空");
				}
				if(prod_sup.toString().length()==0){
					errorMsgs.add("供應數量不能為空");
				}
				if(prod_cont.length()==0||bean_contry==null){
					errorMsgs.add("商品介紹不能為空");
				}
				
				ProdVO prodVO = new ProdVO();
				prodVO.setProd_no(prod_no);
				prodVO.setStore_no(store_no);

				prodVO.setProd_name(prod_name);
				prodVO.setBean_type(bean_type);
				prodVO.setBean_grade(bean_grade);
				prodVO.setBean_contry(bean_contry);

				prodVO.setBean_region(bean_region);
				prodVO.setBean_farm(bean_farm);

				prodVO.setBean_el(bean_el);
				prodVO.setProc(proc);
				prodVO.setRoast(roast);

				prodVO.setBean_attr_acid(bean_attr_acid);
				prodVO.setBean_attr_aroma(bean_attr_aroma);
				prodVO.setBean_attr_body(bean_attr_body);
				prodVO.setBean_attr_after(bean_attr_after);
				prodVO.setBean_attr_bal(bean_attr_bal);

				prodVO.setBean_aroma(bean_aroma);
				prodVO.setProd_price(prod_price);
				prodVO.setProd_wt(prod_wt);

				prodVO.setSend_fee(send_fee);
				prodVO.setProd_sup(prod_sup);
				prodVO.setProd_cont(prod_cont);
				prodVO.setProd_stat(prod_stat);
				prodVO.setProd_pic1(prod_pic1);
				prodVO.setProd_pic2(prod_pic2);
				prodVO.setProd_pic3(prod_pic3);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("prodVO", prodVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/prod_mag/getprod_forupdate.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/
				System.out.println(1111111);
				System.out.println(222233);
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.updateProdbysto(prod_no, store_no, prod_name, bean_type, bean_grade, bean_contry,
						bean_region, bean_farm, bean_farmer, bean_el, proc, roast, bean_attr_acid, bean_attr_aroma,
						bean_attr_body, bean_attr_after, bean_attr_bal, bean_aroma, prod_price, prod_wt, send_fee,
						prod_sup, prod_cont, prod_pic1, prod_pic2, prod_pic3, prod_stat);
				
				/***************************
				 * 
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("prodVO", prodVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/FrontEnd/prod_mag/listAllpro_bystore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/prod_mag/getprod_forupdate.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) { // 來自addprod.jsp

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				System.out.println(3333);
				

				String store_no = req.getParameter("store_no").trim();
				String prod_name = req.getParameter("prod_name").trim();
				
				System.out.println(2222);
				
				
				
				
				
				String bean_type = req.getParameter("bean_type").trim();
				System.out.println("aa");
				
				String bean_grade = req.getParameter("bean_grade");
				System.out.println("aa11");
				
				String bean_contry = req.getParameter("bean_contry").trim();
				String bean_region = req.getParameter("bean_region").trim();
				String bean_farm = req.getParameter("bean_farm").trim();

				System.out.println(1111);
				
				String bean_farmer = req.getParameter("bean_farmer").trim();
				
				
				Integer bean_el =  0;
				if(!req.getParameter("bean_el").trim().equals("") ){
					 bean_el = new Integer(req.getParameter("bean_el").trim());
				}
				
				
				System.out.println(55555);
				
				String proc = (req.getParameter("proc").trim());

				String roast = (req.getParameter("roast").trim());
				
				
				System.out.println("99aa");
				Integer bean_attr_acid = null ;
				if(req.getParameter("bean_attr_acid")!=null){
					 bean_attr_acid = new Integer(req.getParameter("bean_attr_acid"));
				} else{
					errorMsgs.add("請選擇酸度");
				}
				
				
				
				
				System.out.println("88aa");
				
				Integer bean_attr_aroma = null;
				if(req.getParameter("bean_attr_aroma")!=null){
					bean_attr_aroma = new Integer(req.getParameter("bean_attr_aroma"));
				} else{
					errorMsgs.add("請選擇香氣");
				}
				
				System.out.println("11aa");
				
				
				Integer bean_attr_body = null;
				if(req.getParameter("bean_attr_body")!=null){
					bean_attr_body = new Integer(req.getParameter("bean_attr_body"));
				} else{
					errorMsgs.add("請選擇醇度");
				}
				
				System.out.println("bb123");
				
				Integer bean_attr_after = null;
				if(req.getParameter("bean_attr_after")!=null){
					bean_attr_after = new Integer(req.getParameter("bean_attr_after"));
				}else{
					errorMsgs.add("請選擇餘味");
				}
				
				
				Integer bean_attr_bal=null;
				if(req.getParameter("bean_attr_bal")!=null){
					bean_attr_bal = new Integer(req.getParameter("bean_attr_bal"));
				} else{
					errorMsgs.add("請選擇平衡度");
				}
				
				
				
				
				System.out.println("66aa:");
				
				String bean_aroma = (req.getParameter("bean_aroma").trim());
				
				Integer prod_price =  null;
				if(!req.getParameter("prod_price").trim().equals("") ){
					prod_price = new Integer(req.getParameter("prod_price").trim());
				}
				
				
				
				
				
				Integer send_fee = null;
				if(!req.getParameter("send_fee").trim().equals("") ){
					send_fee = new Integer(req.getParameter("send_fee").trim());
				}
				
				
				
				
				
				
				Integer prod_sup = null;
				if(!req.getParameter("prod_sup").trim().equals("")){
					prod_sup = new Integer(req.getParameter("prod_sup").trim());
				}
				
				
				
				
				String prod_cont = (req.getParameter("prod_cont").trim());
				
				

				InputStream is1 = req.getPart("prod_pic1").getInputStream();
				byte[] prod_pic1 = null;
				if (!req.getPart("prod_pic1").getContentType().contains("image")) {
					prod_pic1 =  (byte[])req.getSession().getAttribute("prod_pic1");
					if(prod_pic1==null){
						errorMsgs.add("請上傳商品照1");
					}
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] idpic1 = new byte[16384];
					while ((p1 = is1.read(idpic1)) != -1) {
						pro_1.write(idpic1, 0, p1);
					}
					prod_pic1 = pro_1.toByteArray();
					req.getSession().setAttribute("prod_pic1", prod_pic1);
				}

				InputStream is2 = req.getPart("prod_pic2").getInputStream();
				byte[] prod_pic2 = null;
				if (!req.getPart("prod_pic2").getContentType().contains("image")) {
					prod_pic2 =  (byte[])req.getSession().getAttribute("prod_pic2");
					
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] idpic1 = new byte[16384];
					while ((p1 = is2.read(idpic1)) != -1) {
						pro_1.write(idpic1, 0, p1);
					}
					prod_pic2 = pro_1.toByteArray();
					req.getSession().setAttribute("prod_pic2", prod_pic2);
				}
				
				System.out.println(123);
				

				InputStream is3 = req.getPart("prod_pic3").getInputStream();
				byte[] prod_pic3 = null;
				if (!req.getPart("prod_pic3").getContentType().contains("image")) {
					prod_pic3 =  (byte[])req.getSession().getAttribute("prod_pic3");
					
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] idpic1 = new byte[16384];
					while ((p1 = is3.read(idpic1)) != -1) {
						pro_1.write(idpic1, 0, p1);
					}
					prod_pic3 = pro_1.toByteArray();
					req.getSession().setAttribute("prod_pic3", prod_pic3);
				}
				
				System.out.println("789aaaa");
				
				
				
				String prod_stat=null;
				if(req.getParameter("prod_stat")!=null){
					 prod_stat = req.getParameter("prod_stat").trim();
				} else{
					errorMsgs.add("請選擇上下架狀態");
				}

				System.out.println(456);
				

				if (bean_contry == null || (bean_contry.trim()).length() == 0) {
					errorMsgs.add("請輸入生產國");
				}
				
				if(prod_price == null || (prod_price.equals(""))){
					errorMsgs.add("請輸入價錢");
				}
				
				if(send_fee == null || (send_fee.equals(""))){
					errorMsgs.add("請輸入運費");
				}
				
				if(prod_sup== null || (send_fee.equals(""))){
					errorMsgs.add("請輸入供應數量");
				}
				if(bean_grade==null){
					errorMsgs.add("請選擇豆等級");
				}
				
				if (prod_name == null || (prod_name.trim()).length() == 0) {
					errorMsgs.add("請輸入商品名稱");
				}
				
				if(prod_stat==null){
					errorMsgs.add("請選擇上下架狀態");
				}
				
				Double prod_wt = null;

				try {
					prod_wt = new Double(req.getParameter("prod_wt").trim());
				} catch (NumberFormatException e) {
					prod_wt = 0.0;
					errorMsgs.add("重量請填數字");
				}

				
				
				if (prod_cont == null || (prod_cont.trim()).length() == 0) {
					errorMsgs.add("請輸入商品描述");
				}

				

				ProdVO prodVO = new ProdVO();
				prodVO.setProd_name(prod_name);
				prodVO.setBean_type(bean_type);
				prodVO.setBean_grade(bean_grade);
				prodVO.setBean_contry(bean_contry);
				prodVO.setBean_region(bean_region);
			
				prodVO.setBean_farm(bean_farm);
				prodVO.setBean_farmer(bean_farmer);
				prodVO.setBean_el(bean_el);
				prodVO.setProc(proc);
				prodVO.setRoast(roast);
				if(bean_attr_acid!=null){
					prodVO.setBean_attr_acid(bean_attr_acid);
				}
				
				
				
				prodVO.setBean_attr_aroma(bean_attr_aroma);
				prodVO.setBean_attr_body(bean_attr_body);
				

				prodVO.setBean_attr_after(bean_attr_after);
				prodVO.setBean_attr_bal(bean_attr_bal);
				prodVO.setBean_aroma(bean_aroma);
				prodVO.setProd_price(prod_price);
				
			

				prodVO.setProd_wt(prod_wt);
				prodVO.setSend_fee(send_fee);
				prodVO.setProd_sup(prod_sup);

				prodVO.setProd_cont(prod_cont);
				prodVO.setProd_sup(prod_sup);
				prodVO.setProd_stat(prod_stat);

				
				
				
				prodVO.setProd_pic1(prod_pic1);
				prodVO.setProd_pic2(prod_pic2);
				prodVO.setProd_pic3(prod_pic3);
				
				// Send the use back to the form, if there were errors
				
				if (!errorMsgs.isEmpty()) {
					
					req.setAttribute("prodVO", prodVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/prod_mag/addprod.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 2.開始新增資料 ***************************************/
				ProdService prodSvc = new ProdService();
				prodVO = prodSvc.addProd(store_no, prod_name, bean_type, bean_grade, bean_contry, bean_region,
						bean_farm, bean_farmer, bean_el, proc, roast, bean_attr_acid, bean_attr_aroma, bean_attr_body,
						bean_attr_after, bean_attr_bal, bean_aroma, prod_price, prod_wt, send_fee, prod_sup, prod_cont,
						prod_pic1, prod_pic2, prod_pic3, prod_stat);
				
				
				HttpSession session = req.getSession();
				session.removeAttribute("prod_pic1");
				session.removeAttribute("prod_pic2");
				session.removeAttribute("prod_pic3");
				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/FrontEnd/prod_mag/listAllpro_bystore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交Finreg.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/prod_mag/addprod.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getprod_display".equals(action)) { // 來自listAllStore.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			List<String> openModal = new LinkedList<String>();
			openModal.add("aaaa");
			req.setAttribute("openModal", openModal);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String prod_no = req.getParameter("prod_no");
				System.out.println(prod_no);
				// String url =req.getParameter("url");
				String whichPage = req.getParameter("whichPage");
				System.out.println(34848);
				req.setAttribute("whichPage", whichPage);

				// req.setAttribute(arg0, arg1);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/rod_mag/listAllpro_bystore.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				ProdService proSvc = new ProdService();
				ProdVO prodVO = proSvc.getOneProd(prod_no);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/prod_mag/listAllpro_bystore.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("prodVO", prodVO); // 資料庫取出的empVO物件,存入req
				String url = "/FrontEnd/prod_mag/listAllpro_bystore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/prod_mag/listAllpro_bystore.jsp");
				failureView.forward(req, res);
			}
		}
		if ("Update_prodstat".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				String prod_no = req.getParameter("prod_no").trim();
			
				String prod_stat = req.getParameter("prod_stat").trim();

				String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁,
															// 存入req(只用於:istAllEmp.jsp)

				
				

				ProdVO prodVO = new ProdVO();
				
				
				
				
				System.out.println(111122);
				System.out.println(prod_no+prod_stat+whichPage);
				/*************************** 2.開始修改資料 *****************************************/
				ProdService prodSvc = new ProdService();
				if(prod_stat.equals("上架")){
					
					prodVO = prodSvc.updateProdstat(prod_no, "下架");
				} else	if(prod_stat.equals("下架")){
					prodVO = prodSvc.updateProdstat(prod_no, "上架");
				} 
				
				
				/***************************
				 * 
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("prodVO", prodVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/FrontEnd/prod_mag/listAllpro_bystore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getStackTrace());
				
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/prod_mag/listAllpro_bystore.jsp");
				failureView.forward(req, res);
			}
		}
		

	}

}
