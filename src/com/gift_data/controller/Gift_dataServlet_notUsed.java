package com.gift_data.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gift_data.model.Gift_dataJDBCDAO;
import com.gift_data.model.Gift_dataJNDIDAO;
import com.gift_data.model.Gift_dataService;
import com.gift_data.model.Gift_dataVO;
import com.google.gson.Gson;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 500* 5 * 1024 * 1024)
public class Gift_dataServlet_notUsed extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
		try{	
			String str = req.getParameter("GIFT_NO");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入贈品編號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/gift_data/select_page.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			String gift_no=str;
			Gift_dataService gift_dataSvc=new Gift_dataService();
			Gift_dataVO gift_data_vo=gift_dataSvc.getOneGift_data(gift_no);
			if ( gift_data_vo == null) {
				errorMsgs.add("查無資料");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/gift_data/select_page.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			req.setAttribute("gift_data_vo", gift_data_vo);
			String url = "/gift_data/listOneGift_data.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			
		}catch (Exception e) {
			errorMsgs.add("無法取得資料:" + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/gift_data/select_page.jsp");
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
					 ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					 int nRead;
					 byte[] gift = new byte[16384];

					 while ((nRead = is.read(gift)) != -1) {
					   buffer.write(gift, 0, nRead);
					 }

			byte[] 	gift_img=buffer.toByteArray();
				  
				  
				  
				  
				  Gift_dataVO gift_data_VO=new Gift_dataVO();
				  gift_data_VO.setGift_name(gift_name);
				  gift_data_VO.setGift_cont(gift_cont);
				  gift_data_VO.setGift_launch_date(gift_launch_date);
				  gift_data_VO.setGift_remain(gift_remain);
				  gift_data_VO.setGift_pt(gift_pt);
				  gift_data_VO.setGift_img(gift_img);
					if (!errorMsgs.isEmpty()) {
						req.setAttribute("GIFT_DATA_VO", gift_data_VO); // 含有輸入格式錯誤的empVO物件,也存入req
						RequestDispatcher failureView = req
								.getRequestDispatcher("/gift_data/addGift_data.jsp");
						failureView.forward(req, res);
						return;
					}
				  
				  Gift_dataService gift_data_service=new Gift_dataService();
				   gift_data_VO=gift_data_service.addGift_data(gift_name,gift_remain,gift_cont,gift_img,gift_pt,gift_launch_date);
				  
				  
				  req.setAttribute("gift_data_VO",gift_data_VO);
			
				  
				  
				  String url ="/gift_data/listAllGift_data.jsp";
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req,res);
				  
			  }catch (Exception e) {
				  
				  errorMsgs.add(e.getMessage());
					errorMsgs.add("反正就是有錯誤");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/gift_data/addGift_data.jsp");
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
			  
				List<String> errorMsgs = new LinkedList<String>();
				req.setAttribute("errorMsgs", errorMsgs);
				
				try{
					String gift_no=req.getParameter("gift_no").trim();
					String gift_name=req.getParameter("gift_name").trim();
					if(gift_name.length()==0){
						errorMsgs.add("請輸入贈品名稱");
					}
					Integer gift_remain=null;
					try{
					 gift_remain=new Integer(req.getParameter("gift_remain"));
					}catch(NumberFormatException e){
						errorMsgs.add("剩餘數量請填入數字");
					}
					String gift_cont=req.getParameter("gift_cont").trim();
					if(gift_name.length()==0){
						errorMsgs.add("請輸入贈品描述");
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
					
					  if(req.getPart("gift_img")==null){
						  errorMsgs.add("請上傳更新圖片");
						  RequestDispatcher failureView = req
									.getRequestDispatcher("/gift_data/update_gift_data_input.jsp");
							failureView.forward(req, res);
					  }
					  
					  
					  
					  
						 ByteArrayOutputStream buffer = new ByteArrayOutputStream();

						 int nRead;
						 byte[] gift = new byte[16384];

						 while ((nRead = is.read(gift)) != -1) {
						   buffer.write(gift, 0, nRead);
						 }

				byte[] 	gift_img=buffer.toByteArray();
					
					
						
					 Integer gift_pt=null;
					 try{
					gift_pt=new Integer(req.getParameter("gift_pt"));
					 }catch(NumberFormatException e){
						 errorMsgs.add("積分請填入數字");
						 
					 }
					java.sql.Date gift_launch_date=null;
					try{
						gift_launch_date=java.sql.Date.valueOf(req.getParameter("gift_launch_date"));
					}catch(IllegalArgumentException e){
						errorMsgs.add("請輸入日期");
					}
					Gift_dataVO gift_dataVO=new Gift_dataVO();
					gift_dataVO.setGift_no(gift_no);
					gift_dataVO.setGift_name(gift_name);
					gift_dataVO.setGift_remain(gift_remain);
					gift_dataVO.setGift_cont(gift_cont);
					gift_dataVO.setGift_img(gift_img);
					gift_dataVO.setGift_pt(gift_pt);
					gift_dataVO.setGift_launch_date(gift_launch_date);
					
					if (!errorMsgs.isEmpty()) {
					 // 含有輸入格式錯誤的empVO物件,也存入req
						req.setAttribute("gift_dataVO", gift_dataVO);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/gift_data/update_gift_data_input.jsp");
						failureView.forward(req, res);
						return; //程式中斷
					}
					Gift_dataService gift_dataSvc=new Gift_dataService();
					Gift_dataVO gift_data_vo=gift_dataSvc.updateGift_data(gift_no, gift_name, gift_remain, gift_cont, gift_img, gift_pt, gift_launch_date);
					
					req.setAttribute("gift_data_vo", gift_data_vo);
					String url="/gift_data/listOneGift_data.jsp";
					RequestDispatcher successView=req.getRequestDispatcher(url);
					successView.forward(req, res);
				}catch (Exception e) {
					errorMsgs.add("修改資料失敗:"+e.getMessage());
				
					RequestDispatcher failureView = req
							.getRequestDispatcher("/gift_data/update_gift_data_input.jsp");
				
					failureView.forward(req, res);
				}
			  
		  }
		  
		  
		  
		  if ("delete".equals(action)) {
			  
			  List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			  
			  try{
				  
				  String gift_no=req.getParameter("GIFT_NO");
				  Gift_dataService gift_dataSvc=new Gift_dataService();
				  gift_dataSvc.deleteGift_data(gift_no);
				  
				  String url="/gift_data/listAllGift_data.jsp";
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req,res);
			  }catch (Exception e) {
					errorMsgs.add("刪除資料失敗:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/gift_data/listAllGift_data.jsp");
					failureView.forward(req, res);
				}
			  
		  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		
	}
	
	
	
	
	
	
	
	
}
