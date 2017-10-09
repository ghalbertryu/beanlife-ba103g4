package com.gift_management.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.convert_gift.model.Convert_giftService;
import com.convert_gift.model.Convert_giftVO;
import com.gift_data.model.Gift_dataJDBCDAO;
import com.gift_data.model.Gift_dataJNDIDAO;
import com.gift_data.model.Gift_dataService;
import com.gift_data.model.Gift_dataVO;
import com.google.gson.Gson;
import com.mem.model.MemService;
import com.mem.model.MemVO;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 500* 5 * 1024 * 1024)
public class Gift_managementServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if("modify_total_pt".equals(action)){
			 List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			  try{		
				  
				  String mem_ac=req.getParameter("mem_ac");
				  Integer mem_total_pt=null;
					try{
						mem_total_pt=new Integer(req.getParameter("mem_total_pt").trim());
					}catch(NumberFormatException e){
						mem_total_pt=0;
						errorMsgs.add("積分請填數字");
					}
					
					if(mem_total_pt>99999){
						errorMsgs.add("積分須小於99999");
					}
					
					if(mem_total_pt<0){
						errorMsgs.add("積分不能小於0");
					}
					
				  Integer grade_no;
				  if(mem_total_pt<=100){
					  grade_no=1;
				  }else if(mem_total_pt<=200){
					  grade_no=2;
				  }else if(mem_total_pt<=300){
					  grade_no=3;
				  }else if(mem_total_pt<=400){
					  grade_no=4;
				  }else if(mem_total_pt<=500){
					  grade_no=5;
				  }else{
					  grade_no=6;
				  }
					
				  MemService memSvc=new MemService();
					MemVO mem_vo=memSvc.getOneProd(mem_ac);
				mem_vo.setMem_total_pt(mem_total_pt);
				mem_vo.setGrade_no(grade_no);
				memSvc.updateMem(mem_vo);
				String url=req.getParameter("mem_pt.jsp");
				RequestDispatcher successView = req
						.getRequestDispatcher(url);
				successView.forward(req, res);
				return;
					
					
			
				}catch(Exception e){
					String url=req.getParameter("mem_pt.jsp");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
		}
		
		
		
		if("modify_pt".equals(action)){
			  List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			  
	try{		  
			String mem_ac=req.getParameter("mem_ac");
			
			Integer mem_pt=null;
			try{
			 mem_pt=new Integer(req.getParameter("mem_pt").trim());
			}catch(NumberFormatException e){
				mem_pt=0;
				errorMsgs.add("積分請填數字");
			}
			
			if(mem_pt>99999){
				errorMsgs.add("積分須小於99999");
			}
			
			if(mem_pt<0){
				errorMsgs.add("積分不能小於0");
			}
			
			if (!errorMsgs.isEmpty()) {
				
				String url=req.getParameter("mem.jsp");
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			
			
			MemService memSvc=new MemService();
			MemVO mem_vo=memSvc.getOneProd(mem_ac);
			mem_vo.setMem_pt(mem_pt);
			memSvc.updateMem(mem_vo);
			String url=req.getParameter("mem.jsp");
			RequestDispatcher successView = req
					.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
			
			
			
			
			
	}catch(Exception e){
		String url=req.getParameter("mem.jsp");
		RequestDispatcher failureView = req
				.getRequestDispatcher(url);
		failureView.forward(req, res);
		return;
	}
			
			
		}
		
		
		
		
		if("buy_gift_confirm".equals(action)){
			  List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
	try{		  
		Integer gift_amount=new Integer(req.getParameter("gift_amount"));
			String mem_ac=req.getParameter("mem_ac").trim();
			java.sql.Date apply_date=new java.sql.Date(new Date().getTime());
			String gift_no=req.getParameter("gift_no");
			String apply_name =req.getParameter("apply_name");
			if(apply_name.length()==0){
				errorMsgs.add("請輸入收件人姓名");
			}
			
	
				  String apply_phone=req.getParameter("apply_phone").trim();
				  try{
				if(apply_phone.trim().length()!=10){
					errorMsgs.add("電話格式錯誤");
				}else if(!apply_phone.trim().substring(0,2).equals("09")){
					errorMsgs.add("電話格式錯誤");
				}
				  }catch(Exception e){
					  errorMsgs.add("電話格式錯誤");
				  }
				
				
				String apply_add =req.getParameter("apply_add").trim();
				if(apply_add.trim().length()==0){
					errorMsgs.add("請輸入收件人地址");
				}
				
				String apply_stat="待出貨";
				
			Convert_giftVO convert_gift_vo=new Convert_giftVO();
			convert_gift_vo.setApply_name(apply_name);
			convert_gift_vo.setApply_phone(apply_phone);
			convert_gift_vo.setApply_add(apply_add);
			req.setAttribute("convert_gift_vo", convert_gift_vo);
			req.setAttribute("apply_date", apply_date);
			req.setAttribute("gift_amount", gift_amount);
	if (!errorMsgs.isEmpty()) {
	
					String url=req.getParameter("buy_gift.jsp");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
	Convert_giftService convert_giftSvc=new Convert_giftService();
	convert_giftSvc.addConvert_gift(mem_ac, apply_name, apply_phone, gift_no,gift_amount, apply_date, apply_stat, apply_add, null, null);
	HttpSession session=req.getSession();
	Gift_dataVO gift_data_vo=(Gift_dataVO) session.getAttribute("gift_data_vo");
	Integer gift_pt=gift_data_vo.getGift_pt();
	MemService memSvc=new MemService();
	MemVO mem_vo= memSvc.getOneProd(mem_ac);
	Integer mem_pt=  mem_vo.getMem_pt();
	mem_pt=mem_pt-(gift_amount*gift_pt);
	mem_vo.setMem_pt(mem_pt);
	memSvc.updateMem(mem_vo);
	Integer gift_remain=gift_data_vo.getGift_remain();
	gift_remain =gift_remain-gift_amount;
	gift_data_vo.setGift_remain(gift_remain);
	Gift_dataService gift_dataSvc=new Gift_dataService();
	gift_dataSvc.updateGift_data(gift_data_vo.getGift_no(), gift_data_vo.getGift_name(),gift_data_vo.getGift_remain(),gift_data_vo.getGift_cont(),gift_data_vo.getGift_img(),gift_data_vo.getGift_pt(),gift_data_vo.getGift_launch_date());
	
	
	
	
	
	session.removeAttribute("gift_data_vo");
	
	
	
	
	String url="/FrontEnd/gift/gift_data_frontEnd.jsp";
	RequestDispatcher successView = req
			.getRequestDispatcher(url);
	successView.forward(req, res);
	return;
	
	
	
	}catch(Exception e){
		
					String url=req.getParameter("buy_gift.jsp");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
			
			
		}
		
		
		
		if("buy_gift".equals(action)){
			
		try{	
			String gift_no=req.getParameter("gift_no");
			Gift_dataService gift_dataSvc=new Gift_dataService();
			Gift_dataVO gift_data_vo= gift_dataSvc.getOneGift_data(gift_no);
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String apply_date=df.format(new Date());
			
		Integer gift_amount=new Integer(req.getParameter("gift_amount"));
		if(gift_amount==null || gift_amount==0){
			String url=req.getParameter("gift_data_frontEnd.jsp");
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
		}
		
		req.setAttribute("gift_amount", gift_amount);
			
			HttpSession session=req.getSession();
			session.setAttribute("gift_data_vo", gift_data_vo);
			req.setAttribute("apply_date", apply_date);
			String url="/FrontEnd/gift/buy_gift.jsp";
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
		}catch(Exception e){
			String url=req.getParameter("gift_data_frontEnd.jsp");
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
		}
			
			
		}
		
		
		
		
		
		
		if ("getOne_For_Display".equals(action)) {
		
			List<String> errorMsgsForUpdate = new LinkedList<String>();
			List<String> openModal=new LinkedList<String>();
			openModal.add("baba");
			req.setAttribute("openModal", openModal);
			
			req.setAttribute("errorMsgsForUpdate", errorMsgsForUpdate);
		
		try{	
			String str = req.getParameter("GIFT_NO");
		
			if (str == null || (str.trim()).length() == 0) {
				errorMsgsForUpdate.add("請輸入贈品編號");
			}
			req.setAttribute("whichPage",req.getParameter("whichPage"));   //取得目前所在的頁數，不管失敗獲成功都會停留在同一頁，不會跑到第一頁
		
			String url=req.getParameter("gift_data.jsp");
	
			if (!errorMsgsForUpdate.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;//程式中斷
			}
		
			String gift_no=str;
			Gift_dataService gift_dataSvc=new Gift_dataService();
		
			Gift_dataVO gift_data_vo=gift_dataSvc.getOneGift_data(gift_no);
			
			if ( gift_data_vo == null) {
				
				errorMsgsForUpdate.add("查無資料");
			}
		
			if (!errorMsgsForUpdate.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;//程式中斷
			}
	
			
	
			if(req.getParameter("url")!=null){
				url=(String) req.getParameter("url");
			
			}else{
				url=(String) req.getParameter("url");
		
			}
			
			req.setAttribute("gift_data_vo", gift_data_vo);
		
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			
		}catch (Exception e) {
		
			String url=req.getParameter("gift_data.jsp");
			errorMsgsForUpdate.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher(url);
			failureView.forward(req, res);
		}
			
		}
		
		  if ("insert".equals(action)) { 
			  
			  List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			
			  try{
				  String gift_name=req.getParameter("GIFT_NAME").trim();
				  if(gift_name.length()==0){
					  errorMsgs.add("請輸入贈品名稱");
				  }
				  
				  
				  String gift_cont=req.getParameter("GIFT_CONT").trim();
				  if(gift_cont.length()==0){
					  errorMsgs.add("請輸入贈品描述");
				  }
				 
				  java.sql.Date gift_launch_date=null;
				 
				  try{
					  gift_launch_date=java.sql.Date.valueOf(req.getParameter("GIFT_LAUNCH_DATE").trim());
					 
				  }catch (IllegalArgumentException e) {
					 
					  gift_launch_date=new java.sql.Date(System.currentTimeMillis());
						errorMsgs.add("請輸入日期!");
					}
				 
				  Integer gift_remain=null;
				 
				  try{
				  gift_remain=new Integer(req.getParameter("GIFT_REMAIN").trim());
				
				  }catch (NumberFormatException e) {
						gift_remain = 0;
						errorMsgs.add("數量請填數字.");
					}
				  Integer gift_pt=null;
				  try{
					  gift_pt=new Integer(req.getParameter("GIFT_PT").trim());
				  }catch (NumberFormatException e) {
					  gift_pt = 0;
						errorMsgs.add("積分請填數字.");
					}
				 
//				 InputStream  is= req.getPart("GIFT_IMG").getInputStream(); 
//				 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//				 int nRead;
//				 byte[] gift_img = new byte[16384];
//
//				 while ((nRead = is.read(gift_img, 0, gift_img.length)) != -1) {
//				   buffer.write(gift_img, 0, nRead);
//				 }
//
//				 buffer.flush();
				 
				  InputStream  is= req.getPart("GIFT_IMG").getInputStream(); 
			
				  byte[] 	gift_img=null;
				  if(!req.getPart("GIFT_IMG").getContentType().contains("image")){   //判斷是否有上傳照片
					 
					
					 
					   gift_img= ( byte[]) req.getSession().getAttribute("gift_img");         //抓使用者之前上傳的圖片
					   if(gift_img==null){                                                                                         //判斷是否曾經上傳過圖片
						   errorMsgs.add("請上傳贈品圖片");
					   }
				  }else{
				  
				
					 ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					 int nRead;
					 byte[] gift = new byte[16384];

					 while ((nRead = is.read(gift)) != -1) {
					   buffer.write(gift, 0, nRead);
					 }

					 	gift_img=buffer.toByteArray();
				  req.getSession().setAttribute("gift_img",gift_img);  //將gift_img上傳到session上，如果使用者上架失敗可以從session拿到舊圖，使用者不用重新上傳
				  }
				  String url=req.getParameter("gift_data.jsp");
				  
				  Gift_dataVO gift_data_VO=new Gift_dataVO();
				  gift_data_VO.setGift_name(gift_name);
				  gift_data_VO.setGift_cont(gift_cont);
				  gift_data_VO.setGift_launch_date(gift_launch_date);
				  gift_data_VO.setGift_remain(gift_remain);
				  gift_data_VO.setGift_pt(gift_pt);
				  gift_data_VO.setGift_img(gift_img);
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("gift_data_VO", gift_data_VO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher(url);
						failureView.forward(req, res);
						return;
					}
				  
				  Gift_dataService gift_data_service=new Gift_dataService();
				   gift_data_VO=gift_data_service.addGift_data(gift_name,gift_remain,gift_cont,gift_img,gift_pt,gift_launch_date);
				  
				   req.setAttribute("gift_data_VO", null);
//				  req.setAttribute("gift_data_VO",gift_data_VO);
			
				  
				  
				  
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req,res);
				  
			  }catch (Exception e) {
				  String url=req.getParameter("gift_data.jsp");
				  errorMsgs.add(e.getMessage());
					errorMsgs.add("系統錯誤");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
				}
		
		  }
		
		  if ("getOne_For_Update".equals(action)) { 
			  List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			 
			  try{
				  String gift_no=req.getParameter("GIFT_NO");
				 
				 
				  Gift_dataService gift_dataSvc=new Gift_dataService();
				
				  Gift_dataVO gift_dataVO=gift_dataSvc.getOneGift_data(gift_no);
				 
				  
				 
				  req.setAttribute("gift_dataVO",gift_dataVO);
				
				  String url="/gift_data/update_gift_data_input.jsp";
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req, res);
				  
			  }catch (Exception e) {
					errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/gift_data/listAllGift_data.jsp");
					failureView.forward(req, res);
				}
			  
			  
		  }
		  
		  
		  if ("update".equals(action)) { 
			  
				List<String> errorMsgsForUpdate = new LinkedList<String>();
				req.setAttribute("errorMsgsForUpdate", errorMsgsForUpdate);
			
				try{
					String gift_no=req.getParameter("gift_no").trim();
				
					String gift_name=req.getParameter("gift_name").trim();
					if(gift_name.length()==0){
						errorMsgsForUpdate.add("請輸入贈品名稱");
					}
					
					Integer gift_remain=null;
					try{
					 gift_remain=new Integer(req.getParameter("gift_remain"));
					
					}catch(NumberFormatException e){
						errorMsgsForUpdate.add("剩餘數量請填入數字");
					}
					
					String gift_cont=req.getParameter("gift_cont").trim();
					if(gift_name.length()==0){
						errorMsgsForUpdate.add("請輸入贈品描述");
					}
				
//					 InputStream  is= req.getPart("gift_img").getInputStream(); 
//					 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//					
//					 int nRead;
//					 byte[] gift_img = new byte[16384];
//
//					 while ((nRead = is.read(gift_img, 0, gift_img.length)) != -1) {
//					   buffer.write(gift_img, 0, nRead);
//					 }
//				
//					 buffer.flush();
					
					  InputStream  is= req.getPart("gift_img").getInputStream(); 
					
				
					  byte[] 	gift_img=null;
					  if(!req.getPart("gift_img").getContentType().contains("image")){   //判斷是否有上傳照片
						  Gift_dataService gift_dataSvc=new Gift_dataService();
						 
							Gift_dataVO gift_data_vo=gift_dataSvc.getOneGift_data(gift_no);
						  gift_img=gift_data_vo.getGift_img();
						  
						  
//						  errorMsgsForUpdate.add("請上傳贈品圖片");
					  }else{
					  
					  
				
					  
						 ByteArrayOutputStream buffer = new ByteArrayOutputStream();

						 int nRead;
						 byte[] gift = new byte[16384];

						 while ((nRead = is.read(gift)) != -1) {
						   buffer.write(gift, 0, nRead);
						 }
					 
				 	gift_img=buffer.toByteArray();
					  }
					
			
					 Integer gift_pt=null;
					 try{
					gift_pt=new Integer(req.getParameter("gift_pt"));
					 }catch(NumberFormatException e){
						 errorMsgsForUpdate.add("積分請填入數字");
						 
					 }
				
					java.sql.Date gift_launch_date=null;
					try{
						gift_launch_date=java.sql.Date.valueOf(req.getParameter("gift_launch_date"));
					}catch(IllegalArgumentException e){
						errorMsgsForUpdate.add("請輸入日期");
					}
					req.setAttribute("whichPage",req.getParameter("whichPage")); //取得目前所在的頁數，不管失敗獲成功都會停留在同一頁，不會跑到第一頁
					Gift_dataVO gift_data_vo=new Gift_dataVO();
					gift_data_vo.setGift_no(gift_no);
					gift_data_vo.setGift_name(gift_name);
					gift_data_vo.setGift_remain(gift_remain);
					gift_data_vo.setGift_cont(gift_cont);
					gift_data_vo.setGift_img(gift_img);
					gift_data_vo.setGift_pt(gift_pt);
					gift_data_vo.setGift_launch_date(gift_launch_date);
				
					if (!errorMsgsForUpdate.isEmpty()) {
					 // 含有輸入格式錯誤的empVO物件,也存入req
						String url=req.getParameter("gift_data.jsp");
						
						req.setAttribute("gift_data_vo", gift_data_vo);
						RequestDispatcher failureView = req
								.getRequestDispatcher(url);
						failureView.forward(req, res);
						return; //程式中斷
					}
					
					Gift_dataService gift_dataSvc=new Gift_dataService();
					 gift_data_vo=gift_dataSvc.updateGift_data(gift_no, gift_name, gift_remain, gift_cont, gift_img, gift_pt, gift_launch_date);
				
					req.setAttribute("gift_data_vo", gift_data_vo);
					
					String url=req.getParameter("gift_data.jsp");
					RequestDispatcher successView=req.getRequestDispatcher(url);
					successView.forward(req, res);
				}catch (Exception e) {
					errorMsgsForUpdate.add("修改資料失敗:"+e.getMessage());
					String url=req.getParameter("gift_data.jsp");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
				
					failureView.forward(req, res);
				}
			  
		  }
		  
		  
		  
		  if ("delete".equals(action)) {
			  
			  List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			  
			  try{
				  
				  String gift_no=req.getParameter("gift_no");
				  Gift_dataService gift_dataSvc=new Gift_dataService();
				  gift_dataSvc.deleteGift_data(gift_no);
				  
				  String url=req.getParameter("gift_data.jsp");
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req,res);
			  }catch (Exception e) {
					errorMsgs.add("刪除資料失敗:"+e.getMessage());
					String url=req.getParameter("gift_data.jsp");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
				}
			  
		  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		
	}
	
	
	
	
	
	
	
	
}
