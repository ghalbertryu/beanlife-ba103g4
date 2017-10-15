package com.mem.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.sms.service.Send;
import com.store.model.StoreService;
import com.store.model.StoreVO;


@WebServlet("/mem/mem.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
		
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		System.out.println(req.getParameter("action"));

		String action = req.getParameter("action");

		if ("Application".equals(action)) { // 來自reg_member.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String mem_ac = req.getParameter("mem_ac").trim().toLowerCase();
				String mem_pwd = req.getParameter("mem_pwd").trim();
				String mem_phone = req.getParameter("mem_phone").trim();
				String code =req.getParameter("code").trim();
				HttpSession session=req.getSession();
				String code_auth=(String)session.getAttribute("code");
				if(code.equals(code_auth)==false){
					errorMsgs.add("手機驗證碼不正確");
				}
				
				MemService memSvc = new MemService();
				List<MemVO> memVOs=memSvc.getAll();
				if (mem_ac == null || (mem_ac).length() == 0) {
					errorMsgs.add("請輸入會員帳號");
				}else {
					for(MemVO memVO:memVOs){
						if(mem_ac.equals(memVO.getMem_ac())){
							errorMsgs.add("該帳號已註冊");
						}
					}
				}
				if((mem_ac.length()<6)||mem_ac.length()>11){
					errorMsgs.add("會員帳號請介於6~11間");
				}
				
				if (mem_ac == null || (mem_ac).length() == 0) {
					errorMsgs.add("請輸入會員帳號");
				}
				String mem_ac_reg= ("^[a-z]\\w*$");
				if(mem_ac.matches(mem_ac_reg)==false){
					errorMsgs.add("帳號不符合格式");
				}
				
				if(mem_pwd==null||(mem_pwd).length()==0){
					errorMsgs.add("請輸入會員密碼");
				}
				if(mem_pwd.length()>20||mem_pwd.length()<6){
					errorMsgs.add("密碼介於6到20碼");
				}
				if(mem_phone==null||(mem_phone).length()==0){
					errorMsgs.add("請輸入電話");
				}
				String mem_phone_reg=("^09\\d{2}-?\\d{3}-?\\d{3}$");
				if(mem_phone.matches(mem_phone_reg)==false){
					errorMsgs.add("手機不符合格式");
				}
//				
				
				
				
				MemVO memVO = new MemVO();
				memVO.setMem_ac(mem_ac);
				memVO.setMem_pwd(mem_pwd);
				memVO.setMem_phone(mem_phone);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_mem/reg_member.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始新增資料 ***************************************/
				
				memVO = memSvc.reg_mem(mem_ac, mem_pwd, mem_phone);
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				
				
				
				req.setAttribute("memVO", memVO); // 資料庫取出的會員 放入req
				String url = "/FrontEnd/reg_mem/Finreg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_mem/reg_member.jsp");
				failureView.forward(req, res);
			}
		}
		if ("authphone".equals(action)) { // 來自reg_member.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String authphone=req.getParameter("authphone").trim();
				String mem_ac = req.getParameter("mem_ac").trim();
				String mem_pwd = req.getParameter("mem_pwd").trim();
				
				
				System.out.println(authphone);
				
				
				
					int authcode;
				
					authcode=(int)(Math.random()*9999)+1000;
					
					
					Send se = new Send();
					String[] tel ={authphone};
				 	String code = String.valueOf(authcode).trim();
				 	se.sendMessage(tel , "您的認證碼為："+code);
				MemVO memVO = new MemVO();
				memVO.setMem_ac(mem_ac);
				memVO.setMem_pwd(mem_pwd);
				memVO.setMem_phone(authphone);
				
				
				/*************************** 2.開始新增資料 ***************************************/
				
				
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				
				HttpSession session = req.getSession();
				req.setAttribute("memVO", memVO);
				session.setAttribute("code", code); // 資料庫取出的會員 放入req
				String url = "/FrontEnd/reg_mem/reg_member.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_mem/reg_member.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update_data".equals(action)) { // 來自store_index.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String mem_ac = req.getParameter("mem_ac").trim();
				
				
				
				String mem_lname = req.getParameter("mem_lname").trim();
				
				
				String mem_fname = req.getParameter("mem_fname").trim();
				
				
				String mem_email_req=("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4})*$");
				String mem_email = req.getParameter("mem_email").trim();
				if (mem_email.matches(mem_email_req)==false){
					errorMsgs.add("電子郵件不符合格式");
				}
				
				String mem_add = req.getParameter("mem_add").trim();
				

				InputStream is = req.getPart("mem_pic").getInputStream();
				byte[] mem_pic = null;
				if (!req.getPart("mem_pic").getContentType().contains("image")) {
					MemService MemSvc = new MemService();
					MemVO memvo = MemSvc.getOneMem(mem_ac);
					mem_pic = memvo.getMem_pic();
				} else {
					ByteArrayOutputStream memphoto = new ByteArrayOutputStream();
					int p1;
					byte[] pho1 = new byte[16384];
					while ((p1 = is.read(pho1)) != -1) {
						memphoto.write(pho1, 0, p1);
					}
					mem_pic = memphoto.toByteArray();
					req.getSession().setAttribute("mem_pic", mem_pic);
				}
				
				
				
				String mem_set1= req.getParameter("mem_set1").trim();
				String mem_set2= req.getParameter("mem_set2").trim();
				String mem_set3= req.getParameter("mem_set3").trim();
				String mem_set = mem_set1+","+mem_set2+","+mem_set3;
				
				
				MemVO memVO = new MemVO();
				memVO.setMem_lname(mem_lname);
				memVO.setMem_fname(mem_fname);
				memVO.setMem_email(mem_email);
				memVO.setMem_add(mem_add);
				memVO.setMem_pic(mem_pic);
				memVO.setMem_set(mem_set);
				
				
			
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/reg_mem/mem_data.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 2.開始修改資料 *****************************************/
				MemService memSvc = new MemService();
				memVO = memSvc.update_databyres(mem_ac, mem_lname, mem_fname, mem_email, mem_add, mem_pic, mem_set);
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("memVO", memVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/FrontEnd/mem/mem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_mem/mem_data.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("login".equals(action)) { // 來自reg_member.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String mem_ac = req.getParameter("mem_ac").trim();
				String mem_pwd = req.getParameter("mem_pwd").trim();
				
				if (mem_ac == null || (mem_ac).length() == 0) {
					errorMsgs.add("請輸入會員帳號");
				}
				
				if(mem_pwd==null||(mem_pwd).length()==0){
					errorMsgs.add("請輸入會員密碼");
				}
				MemService memSvc = new MemService();
				List<MemVO> memVOs=memSvc.getAll();
				HttpSession session = req.getSession();
				
				for(int i =0 ; i<memVOs.size();i++){
					
					if(memVOs.get(i).getMem_ac().equals(mem_ac)){
						System.out.println("帳號正確");
						if(memVOs.get(i).getMem_pwd().equals(mem_pwd)){
							  session.setAttribute("memVO", memVOs.get(i));
						}
						else{
							errorMsgs.add("帳號密碼錯誤");
						}
					}
				}
				
				
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/login/memlogin.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				/*************************** 2.開始新增資料 ***************************************/
				
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				String url = "/FrontEnd/index/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_mem/reg_member.jsp");
				failureView.forward(req, res);
			}
		}
		if ("loginnow".equals(action)) { // 來自reg_member.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				
				
				
				
				/*************************** 2.開始新增資料 ***************************************/
				
				
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				
				HttpSession session = req.getSession();
				
				session.setAttribute("showLogin", true); // 資料庫取出的會員 放入req
				String url = "/FrontEnd/index/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_mem/reg_member.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
