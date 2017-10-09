package com.ord.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ord.model.OrdService;
import com.ord.model.OrdVO;
import com.ord_list.model.Ord_listVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.sys_msg.model.Sys_msgVO;

@WebServlet("/ord/Ord_manag.do")
public class Ord_manag extends HttpServlet {
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
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String ord_no = new String(req.getParameter("ord_no"));
				String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁,

				/*************************** 2.開始查詢資料 ****************************************/
				OrdService ordSvc = new OrdService();

				Set<Ord_listVO> ord_listVOs = ordSvc.getOrd_listByOrd(ord_no);
				OrdVO ordVO = ordSvc.getOrdByOrdno(ord_no);

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("ord_listVOs", ord_listVOs); // 資料庫取出的empVO物件,存入req
				req.setAttribute("ordVO", ordVO);
				String url = "/FrontEnd/ord_mag/Ord_listforUpdate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
				// update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {

				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/ord_mag/listAllorder_bystore.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update_stat".equals(action)) { // 來自ord_listforupdate.jsp 確認付款

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				String ord_no = req.getParameter("ord_no");
				String ord_stat = req.getParameter("ord_stat");

				String send_id = req.getParameter("send_id");
				String ordmem_ac = req.getParameter("ordmem_ac");

				OrdVO ordVO = new OrdVO();
				OrdService ordSvc = new OrdService();
				
//				Sys_msgVO sys_msgVO = new Sys_msgVO();
//				Sys_msgService sys_msgSvc = new Sys_msgService();
//				sys_msgVO.setMem_ac(ordmem_ac);
//				sys_msgVO.setMsg_cont("您訂購的商品已出貨");
//				java.sql.Date msg_send_date = new java.sql.Date(new java.util.Date().getTime());
//				sys_msgVO.setMsg_send_date(msg_send_date);
				
				
				if (ord_stat.equals("已付款")) {
					ordVO = ordSvc.update_payconiform(ord_no);

				}
				if (ord_stat.equals("已確認付款")) {
					ordVO = ordSvc.update_sendstat(ord_no, send_id);
					
//					sys_msgVO = sys_msgSvc.addSys_msg(sys_msgVO);
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ordVO", ordVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/prod_mag/getprod_forupdate.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/

				/***************************
				 * 
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("ordVO", ordVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/FrontEnd/ord_mag/Ord_listforUpdate.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println(e.toString());
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/ord_mag/Ord_listforUpdate.jsp");
				failureView.forward(req, res);
			}
		}

		if ("selectstat".equals(action)) { // 來自ord_listforupdate.jsp 確認付款

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/

				String ord_stat = req.getParameter("ord_stat");
				
				System.out.println(ord_stat);
				
				
				
				
				Set<OrdVO> ordVOs  = new LinkedHashSet<OrdVO>();
				HttpSession session =req.getSession();
				Set<OrdVO> ordvos_stat=(Set<OrdVO>) session.getAttribute("ordVOs");
				
				if(ord_stat.equals("getAll")){
					ordVOs.addAll(ordvos_stat);
				}else {
					for (OrdVO ordvo : ordvos_stat){
						 if(ordvo.getOrd_stat().equals(ord_stat)){
							ordVOs.add(ordvo);
						}
						
					}
				}
				
				
				System.out.println(ordVOs);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ordVOs", ordVOs); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/prod_mag/listAllorder_bystore.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/

				/***************************
				 * 
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("ordVOs", ordVOs); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/FrontEnd/ord_mag/listAllorder_bystore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				System.out.println(e.toString());
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/ord_mag/listAllorder_bystore.jsp");
				failureView.forward(req, res);
			}
		}

	}

	public static java.sql.Date timestampToDate(java.sql.Timestamp timestamp) {
		java.util.Date test_timestamp = timestamp;
		java.sql.Date test_date = new java.sql.Date(test_timestamp.getTime());
		return null;

	}
}
