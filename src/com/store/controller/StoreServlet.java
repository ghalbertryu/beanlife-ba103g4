package com.store.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.store.model.StoreService;
import com.store.model.StoreVO;

@MultipartConfig(fileSizeThreshold=1024*1024,maxFileSize=5*1024*1024,maxRequestSize=5*5*1024*1024)
@WebServlet(name="StoreServlet",urlPatterns={"/store/store.do"})
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		
		System.out.println(req.getParameter("action"));
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) { // 來自listAllStore.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String store_no = req.getParameter("store_no");
				if (store_no == null || (store_no.trim()).length() == 0) {
					errorMsgs.add("請輸入店家編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/listAllStore.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}


				/*************************** 2.開始查詢資料 *****************************************/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getonestore(store_no);
				if (storeVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/listAllStore.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("storeVO", storeVO); // 資料庫取出的empVO物件,存入req
				String url = "/BackEnd/reg_store/listOneStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/listAllStore.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String store_no = new String(req.getParameter("store_no"));
				String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				req.setAttribute("whichPage", whichPage);   // 送出修改的來源網頁的第幾頁, 存入req(只用於:istAllEmp.jsp)
				
				
//				String store_stat1 = req.getParameter("store_stat1"); 
//				req.setAttribute("store_stat1", store_stat1);
				
				/*************************** 2.開始查詢資料 ****************************************/
				StoreService storeSvc = new StoreService();
				StoreVO storeVO = storeSvc.getonestore(store_no);
				

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 ************/
				req.setAttribute("storeVO", storeVO); // 資料庫取出的empVO物件,存入req
				String url = "/BackEnd/reg_store/update_store_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
																				// update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/update_store_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update_stat".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				
				String store_stat1 =req.getParameter("store_stat1");
				System.out.println(store_stat1);
//				req.setAttribute("store_stat1", store_stat1);
				
				String store_no = req.getParameter("store_no").trim();
				String store_name = req.getParameter("store_name").trim();
				String tax_id_no =req.getParameter("tax_id_no").trim();
				String store_stat = req.getParameter("store_stat").trim();
				String store_phone = req.getParameter("store_phone").trim();
				String store_add = req.getParameter("store_add").trim();
//				String store_cont = req.getParameter("store_cont").trim();
//				Integer store_free_ship = new Integer(req.getParameter("store_free_ship").trim());
//				String store_add_lat =req.getParameter("store_add_lat").trim();
//				String store_add_lon =req.getParameter("store_add_lon").trim();
				String store_stat_cont =req.getParameter("store_stat_cont").trim();
				
				
				
//				
//				String store_stat_cont =req.getParameter("store_stat_cont");
				java.sql.Date store_stat_cdate = null;
				try {
					store_stat_cdate = java.sql.Date.valueOf(req.getParameter("store_stat_cdate").trim());
				} catch (IllegalArgumentException e) {
					store_stat_cdate=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				String whichPage = req.getParameter("whichPage"); // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				req.setAttribute("whichPage", whichPage);   // 送出修改的來源網頁的第幾頁, 存入req(只用於:istAllEmp.jsp)
								
				
				StoreVO storeVO = new StoreVO();
				storeVO.setStore_name(store_name);
				storeVO.setStore_no(store_no);
//				
				storeVO.setStore_stat(store_stat);
				storeVO.setStore_phone(store_phone);
				storeVO.setStore_add(store_add);
//				storeVO.setStore_cont(store_cont);
//				storeVO.setStore_no(store_no);
//				storeVO.setStore_free_ship(store_free_ship);
				storeVO.setTax_id_no(tax_id_no);
//				storeVO.setStore_add_lat(store_add_lat);
//				storeVO.setStore_add_lon(store_add_lon);
				storeVO.setStore_stat_cont(store_stat_cont);
//				InputStream win_id_pic = req.getPart("win_id_pic").getInputStream();
//				ByteArrayOutputStream id_pic = new ByteArrayOutputStream();
//				int idpic;
//				byte[] idimg = new byte[16384];
//				while ((idpic = win_id_pic.read(idimg, 0, idimg.length)) != -1) {
//					id_pic.write(idimg, 0, idpic);
//				}
//				byte [] idimg1 = id_pic.toByteArray();
//				
//				if (idimg1.length == 0){
//					errorMsgs.add("證照照片不可為空!");
//				}
				
				
				
//				InputStream storepic1 = req.getPart("store_pic1").getInputStream();
//				ByteArrayOutputStream spic_1 = new ByteArrayOutputStream();
//				int spic1;
//				byte[] sphoto1 = new byte[16384];
//				while ((spic1 = storepic1.read(sphoto1, 0, sphoto1.length)) != -1) {
//					spic_1.write(sphoto1, 0, spic1);
//				}
//				byte [] sphoto1_1 = spic_1.toByteArray();
//				if (sphoto1_1.length == 0){
//					errorMsgs.add("店家照片1不可為空!");
//				}
		
//				InputStream storepic2 = req.getPart("store_pic2").getInputStream();
//				ByteArrayOutputStream spic_2 = new ByteArrayOutputStream();
//				int spic2;
//				byte[] sphoto2 = new byte[16384];
//				while ((spic2 = storepic2.read(sphoto2, 0, sphoto2.length)) != -1) {
//					spic_2.write(sphoto2, 0, spic2);
//				}
//				byte [] sphoto2_1 = spic_2.toByteArray();
				
				
//				InputStream storepic3 = req.getPart("store_pic3").getInputStream();
//				ByteArrayOutputStream spic_3 = new ByteArrayOutputStream();
//				int spic3;
//				byte[] sphoto3 = new byte[16384];
//				while ((spic3 = storepic3.read(sphoto3, 0, sphoto3.length)) != -1) {
//					spic_3.write(sphoto3, 0, spic3);
//				}
//				byte [] sphoto3_1 = spic_3.toByteArray();


				
				storeVO.setStore_stat_cont(store_stat_cont);
				storeVO.setStore_stat_cdate(store_stat_cdate);
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("storeVO", storeVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/update_store_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/
				StoreService storeSvc = new StoreService();
				storeVO = storeSvc.update_stat(store_stat, store_stat_cdate, store_no, store_stat_cont);
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/
				
				req.setAttribute("storeVO", storeVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/BackEnd/reg_store/listAllStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/update_store_input.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String store_no = req.getParameter("store_no");
				
				/***************************2.開始刪除資料***************************************/
				StoreService StoreSvc = new StoreService();
				StoreSvc.deletestore(store_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/BackEnd/reg_store/listAllStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/BackEnd/reg_store/listAllStore.jsp");
				failureView.forward(req, res);
			}
		}
		if ("selstat".equals(action)) { // 來自listAllStore.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String store_stat1 = req.getParameter("store_stat1");
				System.out.println(store_stat1);
				
//				HttpSession abc=req.getSession();
//				req.setAttribute("store_stat1", store_stat1);//
				
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/listAllStore.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}


				/*************************** 2.開始查詢資料 *****************************************/
				StoreService storeSvc = new StoreService();
				List<StoreVO> storeVOlist = storeSvc.getstatstr(store_stat1);
				if (storeVOlist == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/listAllStore.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				
				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
//				HttpSession test=req.getSession();
				req.setAttribute("storeVOlist", storeVOlist); // 資料庫取出的empVO物件,存入req
				String url = "/BackEnd/reg_store/listAllStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/BackEnd/reg_store/listAllStore.jsp");
				failureView.forward(req, res);
			}
		}

	}

	// 取出上傳的檔案名稱 (因為API未提供method,所以必須自行撰寫)
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
