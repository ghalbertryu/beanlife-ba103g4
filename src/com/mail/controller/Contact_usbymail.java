package com.mail.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mail.service.MailService;

@WebServlet("/Contact/mail.do")
public class Contact_usbymail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		System.out.println(req.getParameter("action"));
		String action = req.getParameter("action");

		if ("mail_contact".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String to = new String(req.getParameter("to"));
				String subject = req.getParameter("subject");
				String messageText = req.getParameter("messageText");
				
				
				MailService mailService = new MailService();
			    mailService.sendMail(to, subject, messageText);

				/*************************** 2.開始查詢資料 ****************************************/

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("subject", subject);
				String url = "/FrontEnd/contact_us/contact_us.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
				// update_emp_input.jsp
				System.out.println("`111111111");
				successView.forward(req, res);
				System.out.println("22222222");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {

				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/contact_us/contact_us.jsp");
				System.out.println("3333333");
				failureView.forward(req, res);
			}
		}
	}

}
