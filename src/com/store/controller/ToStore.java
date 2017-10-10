package com.store.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prod.model.ProdService;
import com.prod.model.ProdVO;
import com.store.model.StoreService;
import com.store.model.StoreVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet(name="ToStore",urlPatterns={"/store/ToStore.do"})
public class ToStore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		System.out.println(req.getParameter("action"));

		String action = req.getParameter("action");

		if ("Application".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				String mem_ac = req.getParameter("mem_ac").trim();
				if (mem_ac == null || (mem_ac).length() == 0) {
					errorMsgs.add("請登入會員");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_store/ToStore.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/***************************
				 * 3.查詢完成,準備轉交(Send the Success view)
				 *************/
				req.setAttribute("mem_ac", mem_ac); // 資料庫取出的會員 放入req
				String url = "/FrontEnd/reg_store/UpToStore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交
																				// listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_store/ToStore.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自UpToStore.jsp

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 *************************/
				String mem_ac = req.getParameter("mem_ac").trim();

				String store_name = req.getParameter("store_name").trim();
				String tax_id_no = req.getParameter("tax_id_no").trim();

				String store_phone = req.getParameter("store_phone").trim();

				String store_add = req.getParameter("store_add").trim();

				String store_cont = req.getParameter("store_cont");

				String store_add_lat = req.getParameter("store_add_lat").trim();

				String store_add_lon = req.getParameter("store_add_lon").trim();

				Integer store_free_ship = new Integer(req.getParameter("store_free_ship").trim());

				String store_atm_info = req.getParameter("store_atm_info").trim();
				

				
				
				
				InputStream is = req.getPart("win_id_pic").getInputStream();
				byte[] win_id_pic = null;
				if (!req.getPart("win_id_pic").getContentType().contains("image")) {
					win_id_pic =  (byte[])req.getSession().getAttribute("win_id_pic");
					if(win_id_pic==null){
						errorMsgs.add("請上傳證件照");
					}
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] idpic1 = new byte[16384];
					while ((p1 = is.read(idpic1)) != -1) {
						pro_1.write(idpic1, 0, p1);
					}
					win_id_pic = pro_1.toByteArray();
					req.getSession().setAttribute("win_id_pic", win_id_pic);
				}
				
				
				
				InputStream is1 = req.getPart("store_pic1").getInputStream();
				byte[] store_pic1 = null;
				if (!req.getPart("store_pic1").getContentType().contains("image")) {
					store_pic1 =  (byte[])req.getSession().getAttribute("store_pic1");
					if(store_pic1==null){
						errorMsgs.add("請上傳店家照1");
					}
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] idpic1 = new byte[16384];
					while ((p1 = is1.read(idpic1)) != -1) {
						pro_1.write(idpic1, 0, p1);
					}
					store_pic1 = pro_1.toByteArray();
					req.getSession().setAttribute("store_pic1", store_pic1);
				}
				
				
				
				
				InputStream is2 = req.getPart("store_pic2").getInputStream();
				byte[] store_pic2 = null;
				if (!req.getPart("store_pic2").getContentType().contains("image")) {
					store_pic2 =  (byte[])req.getSession().getAttribute("store_pic2");
					
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] idpic1 = new byte[16384];
					while ((p1 = is2.read(idpic1)) != -1) {
						pro_1.write(idpic1, 0, p1);
					}
					store_pic2 = pro_1.toByteArray();
					req.getSession().setAttribute("store_pic2", store_pic2);
				}
				
				
				
				InputStream is3 = req.getPart("store_pic3").getInputStream();
				byte[] store_pic3 = null;
				if (!req.getPart("store_pic3").getContentType().contains("image")) {
					store_pic3 =  (byte[])req.getSession().getAttribute("store_pic3");
					
				} else {
					ByteArrayOutputStream pro_1 = new ByteArrayOutputStream();
					int p1;
					byte[] idpic1 = new byte[16384];
					while ((p1 = is3.read(idpic1)) != -1) {
						pro_1.write(idpic1, 0, p1);
					}
					store_pic3 = pro_1.toByteArray();
					req.getSession().setAttribute("store_pic3", store_pic3);
				}
				
				

				// 驗證
				if (mem_ac == null || (mem_ac).length() == 0) {
					errorMsgs.add("請登入會員");
				}

				if (store_name == null || (store_name).length() == 0) {
					errorMsgs.add("請輸入店家名稱");
				}

				if (store_phone == null || (store_phone).length() == 0) {
					errorMsgs.add("請輸入電話");
				}
				
				System.out.println("11111111");
				StoreService storeSvc = new StoreService();
				List<StoreVO> storeVOs=storeSvc.getAll();
				
				
				for(StoreVO storevo :storeVOs){
					 if(tax_id_no.equals(storevo.getTax_id_no())){
						 errorMsgs.add("統一編號已註冊");
					 }
					
				}
				
			
				
				if (chknum(tax_id_no) == false) {
					errorMsgs.add("統一編號格式驗證錯誤");
				}
				System.out.println("2223333332");
				if (store_add == null || (store_add).length() == 0) {
					errorMsgs.add("請輸入地址");
				}

				

				
				if (store_atm_info == null || (store_atm_info.length() == 0)) {
					errorMsgs.add("匯款資訊不可為空");
				}

				
				
				StoreVO storeVO = new StoreVO();
				storeVO.setStore_name(store_name);
				storeVO.setTax_id_no(tax_id_no);
				storeVO.setStore_add(store_add);
				storeVO.setStore_phone(store_phone);
				storeVO.setStore_cont(store_cont);
				storeVO.setMem_ac(mem_ac);
				storeVO.setWin_id_pic(win_id_pic);
				storeVO.setStore_add_lat(store_add_lat);
				storeVO.setStore_add_lon(store_add_lon);
				storeVO.setStore_pic1(store_pic1);
				storeVO.setStore_pic2(store_pic2);
				storeVO.setStore_pic3(store_pic3);
				storeVO.setStore_atm_info(store_atm_info);
				storeVO.setStore_free_ship(store_free_ship);
				
				
				System.out.println("this");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("storeVO", storeVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_store/UpToStore.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				
				storeVO = storeSvc.addStore(mem_ac, tax_id_no, win_id_pic, store_phone, store_add, store_add_lat,
						store_add_lon, store_name, store_cont, store_pic1, store_pic2, store_pic3, store_free_ship,
						store_atm_info);

				/***************************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ***********/
				String url = "/FrontEnd/reg_store/Finreg.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交Finreg.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {

				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/reg_store/UpToStore.jsp");
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
				req.setAttribute("whichPage", whichPage); // 送出修改的來源網頁的第幾頁,
															// 存入req(只用於:istAllEmp.jsp)

				// String store_stat1 = req.getParameter("store_stat1");
				// req.setAttribute("store_stat1", store_stat1);

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

		if ("update_data".equals(action)) { // 來自store_index.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				
				String store_no = req.getParameter("store_no").trim();
				
				String store_name = req.getParameter("store_name").trim();
				
				String store_phone = req.getParameter("store_phone").trim();
				
				String store_add = req.getParameter("store_add").trim();
				
				String store_cont = req.getParameter("store_cont").trim();
				
//				Integer store_free_ship = new Integer(req.getParameter("store_free_ship").trim());
				String store_add_lat = req.getParameter("store_add_lat").trim();
				String store_add_lon = req.getParameter("store_add_lon").trim();
				
				if (store_name == null || (store_name).length() == 0) {
					errorMsgs.add("請輸入店家名稱");
				}

				if (store_phone == null || (store_phone).length() == 0) {
					errorMsgs.add("請輸入電話");
				}
				if (store_cont == null || (store_cont).length() == 0) {
					errorMsgs.add("請輸入店家介紹");
				}
				Integer store_free_ship = 0 ;
				if(!req.getParameter("store_free_ship").equals("")){
					store_free_ship = new Integer(req.getParameter("store_free_ship"));
				} else{
					errorMsgs.add("請輸入免運費金額");
				}



				InputStream is = req.getPart("store_pic1").getInputStream();
				byte[] store_pic1 = null;
				if (!req.getPart("store_pic1").getContentType().contains("image")) {
					StoreService storeSvc = new StoreService();
					StoreVO storevo = storeSvc.getOneStore(store_no);
					store_pic1 = storevo.getStore_pic1();
				} else {
					ByteArrayOutputStream sto_1 = new ByteArrayOutputStream();
					int p1;
					byte[] pho1 = new byte[16384];
					while ((p1 = is.read(pho1)) != -1) {
						sto_1.write(pho1, 0, p1);
					}
					store_pic1 = sto_1.toByteArray();
					req.getSession().setAttribute("store_pic1", store_pic1);
				}

				InputStream is2 = req.getPart("store_pic2").getInputStream();
				byte[] store_pic2 = null;
				if (!req.getPart("store_pic2").getContentType().contains("image")) {
					StoreService storeSvc = new StoreService();
					StoreVO storevo = storeSvc.getOneStore(store_no);
					store_pic2 = storevo.getStore_pic2();
				} else {
					ByteArrayOutputStream pro_2 = new ByteArrayOutputStream();
					int p2;
					byte[] pho2 = new byte[16384];
					while ((p2 = is2.read(pho2)) != -1) {
						pro_2.write(pho2, 0, p2);
					}
					store_pic2 = pro_2.toByteArray();
					req.getSession().setAttribute("store_pic2", store_pic2);
				}

				InputStream is3 = req.getPart("store_pic3").getInputStream();
				byte[] store_pic3 = null;
				if (!req.getPart("store_pic3").getContentType().contains("image")) {
					StoreService storeSvc = new StoreService();
					StoreVO storevo = storeSvc.getOneStore(store_no);
					store_pic3 = storevo.getStore_pic3();
				} else {
					ByteArrayOutputStream pro_3 = new ByteArrayOutputStream();
					int p3;
					byte[] pho3 = new byte[16384];
					while ((p3 = is3.read(pho3)) != -1) {
						pro_3.write(pho3, 0, p3);
					}
					store_pic3 = pro_3.toByteArray();
					req.getSession().setAttribute("store_pic3", store_pic3);
				}

				
				
				StoreVO storeVO = new StoreVO();
				storeVO.setStore_name(store_name);
				storeVO.setStore_no(store_no);
				

				storeVO.setStore_phone(store_phone);
				storeVO.setStore_add(store_add);
				storeVO.setStore_cont(store_cont);
				storeVO.setStore_free_ship(store_free_ship);
				storeVO.setStore_add_lat(store_add_lat);
				storeVO.setStore_add_lon(store_add_lon);
				storeVO.setStore_pic1(store_pic1);
				storeVO.setStore_pic2(store_pic2);
				storeVO.setStore_pic3(store_pic3);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("storeVO", storeVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/store_mag/store_databypass.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 2.開始修改資料 *****************************************/
				StoreService storeSvc = new StoreService();
				storeVO = storeSvc.update_bypass(store_no, store_name, store_free_ship, store_phone, store_add, store_add_lat, store_add_lon, store_cont, store_pic1, store_pic2, store_pic3);
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("storeVO", storeVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/FrontEnd/prod_mag/listAllpro_bystore.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/store_mag/store_databypass.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update_forAud".equals(action)) { // 來自store_index.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/***************************
				 * 1.接收請求參數 - 輸入格式的錯誤處理
				 **********************/
				
				String store_no = req.getParameter("store_no").trim();
				
				String store_name = req.getParameter("store_name").trim();
				
				String store_phone = req.getParameter("store_phone").trim();
				
				String store_add = req.getParameter("store_add").trim();
				
				String store_cont = req.getParameter("store_cont").trim();
				
				Integer store_free_ship = new Integer(req.getParameter("store_free_ship").trim());
				String store_add_lat = req.getParameter("store_add_lat").trim();
				String store_add_lon = req.getParameter("store_add_lon").trim();
				
				

				InputStream is = req.getPart("store_pic1").getInputStream();
				byte[] store_pic1 = null;
				if (!req.getPart("store_pic1").getContentType().contains("image")) {
					StoreService storeSvc = new StoreService();
					StoreVO storevo = storeSvc.getOneStore(store_no);
					store_pic1 = storevo.getStore_pic1();
				} else {
					ByteArrayOutputStream sto_1 = new ByteArrayOutputStream();
					int p1;
					byte[] pho1 = new byte[16384];
					while ((p1 = is.read(pho1)) != -1) {
						sto_1.write(pho1, 0, p1);
					}
					store_pic1 = sto_1.toByteArray();
					req.getSession().setAttribute("store_pic1", store_pic1);
				}

				InputStream is2 = req.getPart("store_pic2").getInputStream();
				byte[] store_pic2 = null;
				if (!req.getPart("store_pic2").getContentType().contains("image")) {
					StoreService storeSvc = new StoreService();
					StoreVO storevo = storeSvc.getOneStore(store_no);
					store_pic2 = storevo.getStore_pic2();
				} else {
					ByteArrayOutputStream pro_2 = new ByteArrayOutputStream();
					int p2;
					byte[] pho2 = new byte[16384];
					while ((p2 = is2.read(pho2)) != -1) {
						pro_2.write(pho2, 0, p2);
					}
					store_pic2 = pro_2.toByteArray();
					req.getSession().setAttribute("store_pic2", store_pic2);
				}

				InputStream is3 = req.getPart("store_pic3").getInputStream();
				byte[] store_pic3 = null;
				if (!req.getPart("store_pic3").getContentType().contains("image")) {
					StoreService storeSvc = new StoreService();
					StoreVO storevo = storeSvc.getOneStore(store_no);
					store_pic3 = storevo.getStore_pic3();
				} else {
					ByteArrayOutputStream pro_3 = new ByteArrayOutputStream();
					int p3;
					byte[] pho3 = new byte[16384];
					while ((p3 = is3.read(pho3)) != -1) {
						pro_3.write(pho3, 0, p3);
					}
					store_pic3 = pro_3.toByteArray();
					req.getSession().setAttribute("store_pic3", store_pic3);
				}

				
				
				StoreVO storeVO = new StoreVO();
				storeVO.setStore_name(store_name);
				storeVO.setStore_no(store_no);
				

				storeVO.setStore_phone(store_phone);
				storeVO.setStore_add(store_add);
				storeVO.setStore_cont(store_cont);
				storeVO.setStore_free_ship(store_free_ship);
				storeVO.setStore_add_lat(store_add_lat);
				storeVO.setStore_add_lon(store_add_lon);
				storeVO.setStore_pic1(store_pic1);
				storeVO.setStore_pic2(store_pic2);
				storeVO.setStore_pic3(store_pic3);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("storeVO", storeVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/FrontEnd/store_mag/store_databypass.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				
				/*************************** 2.開始修改資料 *****************************************/
				StoreService storeSvc = new StoreService();
				storeVO = storeSvc.update_bynotpass(store_no, store_name, store_free_ship, store_phone, store_add, store_add_lat, store_add_lon, store_cont, store_pic1, store_pic2, store_pic3);
				/***************************
				 * 3.修改完成,準備轉交(Send the Success view)
				 *************/

				req.setAttribute("storeVO", storeVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/FrontEnd/store_mag/store_index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/FrontEnd/store_mag/store_databypass.jsp");
				failureView.forward(req, res);
			}
		}
	}

	// 驗證統編
	int car[] = { 1, 2, 1, 2, 1, 2, 4, 1 };

	boolean chknum(String tax_id_no) {
		int SUM = 0;

		String[] cnum = tax_id_no.split("");
		if(cnum.length!=8){
			return false;
		}

		for (int i = 0; i < 8; i++) {
			SUM += toOneDigi(Integer.parseInt(cnum[i]) * car[i]);
		}
		if (SUM % 10 == 0) {
			return true;
		} else if (Integer.parseInt(cnum[6]) == 7 && (SUM + 1) % 10 == 0) {
			return true;
		} else {
			return false;
		}
	}

	int toOneDigi(Integer x) {
		if (x > 9) {
			String num = x.toString();
			x = Integer.valueOf(num.substring(0, 1)) + Integer.valueOf(num.substring(1, 2));
		}
		return x;
	}

}
