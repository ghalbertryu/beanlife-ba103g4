package com.ad_management.controller;

import com.ad.model.AdService;
import com.ad.model.AdVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 500* 5 * 1024 * 1024)
public class Ad_managementServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		
		
		
		
  if ("insert".equals(action)) { 
			  
			  List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			  
			
			System.out.println("track1");
			String url=req.getParameter("ad.jsp");
			System.out.println("track2");
			  try{
				  String ad_title=req.getParameter("ad_title").trim();
				  System.out.println("track3");
				  if(ad_title.length()==0){
					  errorMsgs.add("請輸入廣告名稱");
				  }
				  System.out.println("track4");
				  String prod_no=req.getParameter("prod_no");
				  System.out.println("track5");
			if(prod_no.equals("請選擇")){
				
				errorMsgs.add("請選擇廣告商品");
				
			}
			System.out.println("track6");
				
				 
				 

					System.out.println("track10");
				  InputStream  is= req.getPart("ad_img").getInputStream(); 
				  System.out.println("track11");
				  byte[] 	ad_img=null;
				  if(!req.getPart("ad_img").getContentType().contains("image")){   //判斷是否有上傳照片
					 
					
					  System.out.println("track12");
					   ad_img= ( byte[]) req.getSession().getAttribute("ad_img");         //抓使用者之前上傳的圖片
					   if(ad_img==null){                                                                                         //判斷是否曾經上傳過圖片
						   errorMsgs.add("請上傳贈品圖片");
					   }
				  }else{
				  
				
					 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					 System.out.println("track13");
					 int nRead;
					 byte[] gift = new byte[16384];

					 while ((nRead = is.read(gift)) != -1) {
					   buffer.write(gift, 0, nRead);
					 }

					 	ad_img=buffer.toByteArray();
					 	System.out.println("track14");
				  req.getSession().setAttribute("ad_img",ad_img);  //將ad_img上傳到session上，如果使用者上架失敗可以從session拿到舊圖，使用者不用重新上傳
				  }
				  
				  
				  
				  java.sql.Date ad_op_date=null;
					 
				  try{
					  String get_ad_op_date=req.getParameter("ad_op_date");
					  System.out.println("get_ad_op_date= "+(get_ad_op_date));
					  System.out.println("get_ad_op_date.equals('')= "+(get_ad_op_date.equals("")));
					  if(!get_ad_op_date.equals("")){
						  ad_op_date=java.sql.Date.valueOf(req.getParameter("ad_op_date").trim());
					  }else{
						  
						  errorMsgs.add("請輸入開始日期!");
					  }
						  
					  
					
					  System.out.println("track7");
				  }catch (IllegalArgumentException e) {		
//					  ad_op_date=new java.sql.Date(System.currentTimeMillis());
						errorMsgs.add("開始日期格式錯誤!");
					}
				 
				  java.sql.Date ad_ed_date=null;
					 
				  try{
					  String get_ad_ed_date=req.getParameter("ad_ed_date");
					  if(!get_ad_ed_date.equals("")){
					  ad_ed_date=java.sql.Date.valueOf(req.getParameter("ad_ed_date").trim());
					  }else{
						  
						  errorMsgs.add("請輸入結束日期!");
					  }
						  
					  System.out.println("track8");
				  }catch (IllegalArgumentException e) {
			
//					  ad_ed_date=new java.sql.Date(System.currentTimeMillis());
						errorMsgs.add("結束日期格式錯誤!");
					}
				  long outcome=0;
				  if((!req.getParameter("ad_ed_date").equals("")) && (!req.getParameter("ad_op_date").equals("")) ){
					 outcome=ad_ed_date.getTime()-ad_op_date.getTime();
				  }
					
					System.out.println("track9");
					if(outcome<0){
						errorMsgs.add("結束日期須比開始日期還晚");
					}
				  
				  
				  
				  
				  
				  System.out.println("track15");
				  AdVO ad_vo=new AdVO();
				  ad_vo.setProd_no(prod_no);
				  ad_vo.setAd_title(ad_title);
				  ad_vo.setAd_op_date(ad_op_date);
				  ad_vo.setAd_ed_date(ad_ed_date);
				  ad_vo.setAd_img(ad_img);
				  System.out.println("track16");
					if (!errorMsgs.isEmpty()) {
						System.out.println("track not empty");
						req.setAttribute("ad_vo", ad_vo); // 含有輸入格式錯誤的empVO物件,也存入req
					
		
						RequestDispatcher failureView = req
								.getRequestDispatcher(url);
						failureView.forward(req, res);
						return;
					}
					System.out.println("track17");
					 req.getSession().removeAttribute("ad_img");
				  AdService adService=new AdService();
				   ad_vo=adService.addAd(prod_no,ad_title,ad_img,ad_op_date,ad_ed_date);
				  
				   req.removeAttribute("ad_vo");
				   
				   
//				  req.setAttribute("gift_data_VO",gift_data_VO);
			
				  
				  
//				  String url ="/BackEnd/gift_data.jsp";
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req,res);
				  
			  }catch (Exception e) {
				  System.out.println("track error");
				  errorMsgs.add(e.getMessage());
					errorMsgs.add("系統錯誤");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
				}
		
		  }
		
  if ("delete".equals(action)) {
  
  List<String> errorMsgs = new LinkedList<String>();
  req.setAttribute("errorMsgs", errorMsgs);
  
  try{
	  
	  String ad_no=req.getParameter("ad_no");
	  AdService ad_Svc=new AdService();
	  ad_Svc.deleteAd(ad_no);
	 req.setAttribute("whichPage", req.getParameter("whichPage")); 
	  String url= req.getParameter("ad.jsp");
//	  String url="/BackEnd/gift_data.jsp";
	  RequestDispatcher successView=req.getRequestDispatcher(url);
	  successView.forward(req,res);
  }catch (Exception e) {
		errorMsgs.add("刪除資料失敗:"+e.getMessage());
		 String url= req.getParameter("ad.jsp");
		RequestDispatcher failureView = req
				.getRequestDispatcher("url");
		failureView.forward(req, res);
	}
  
}
		
		
		if ("getOne_For_Display".equals(action)) {
			
			List<String> errorMsgsForUpdate = new LinkedList<String>();
			List<String> openModal=new LinkedList<String>();
			openModal.add("baba");
			req.setAttribute("openModal", openModal);
			
			req.setAttribute("errorMsgsForUpdate", errorMsgsForUpdate);
		
		try{	
			String str = req.getParameter("ad_no");
			
			String url=req.getParameter("ad.jsp");
		
			req.setAttribute("whichPage",req.getParameter("whichPage"));   //取得目前所在的頁數，不管失敗獲成功都會停留在同一頁，不會跑到第一頁
		
			if (!errorMsgsForUpdate.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;//程式中斷
			}
		
			String ad_no=str;
			AdService adSvc=new AdService();
		
			AdVO ad_vo=adSvc.getOneAd(ad_no);
		
			if ( ad_vo == null) {
			
				errorMsgsForUpdate.add("查無資料");
			}
	
			if (!errorMsgsForUpdate.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;//程式中斷
			}
		
//			String url =null;
			
//			if(req.getParameter("url")!=null){
//				url=(String) req.getParameter("url");
//				System.out.println("url="+url);
//			}else{
//			 url = "/BackEnd/gift_data.jsp";
//			 System.out.println("track8 not got url");
//			}
			
			req.setAttribute("ad_VO", ad_vo);
		
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			
		}catch (Exception e) {
	
			errorMsgsForUpdate.add("無法取得資料:" + e.getMessage());
			String url=req.getParameter("ad.jsp");
			RequestDispatcher failureView = req
					.getRequestDispatcher(url);
			failureView.forward(req, res);
		}
			
		}
		
		
		  if ("update".equals(action)) { 
		  
			List<String> errorMsgsForUpdate = new LinkedList<String>();
			req.setAttribute("errorMsgsForUpdate", errorMsgsForUpdate);
		
			try{
				String ad_no=req.getParameter("ad_no").trim();
				String prod_no=req.getParameter("prod_no").trim();
				String ad_title=req.getParameter("ad_title").trim();
			
				if(ad_title.length()==0){
					errorMsgsForUpdate.add("請輸入贈品名稱");
				}
				
			
		
			
//				 InputStream  is= req.getPart("gift_img").getInputStream(); 
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
				
				  InputStream  is= req.getPart("ad_img").getInputStream(); 
				
			
				  byte[] 	ad_img=null;
				  if(!req.getPart("ad_img").getContentType().contains("image")){   //判斷是否有上傳照片
					  AdService ad_Svc=new AdService();
					 
						AdVO ad_vo=ad_Svc.getOneAd(ad_no);
					  ad_img=ad_vo.getAd_img();
					  
					  
//					  errorMsgsForUpdate.add("請上傳贈品圖片");
				  }else{
				  
				  
			
				  
					 ByteArrayOutputStream buffer = new ByteArrayOutputStream();

					 int nRead;
					 byte[] gift = new byte[16384];

					 while ((nRead = is.read(gift)) != -1) {
					   buffer.write(gift, 0, nRead);
					 }
				 
			 	ad_img=buffer.toByteArray();
				  }
				
		
			
			
				java.sql.Date ad_op_date=null;
				try{
					ad_op_date=java.sql.Date.valueOf(req.getParameter("ad_op_date"));
				}catch(IllegalArgumentException e){
					errorMsgsForUpdate.add("請輸入開始日期");
				}
				
				java.sql.Date ad_ed_date=null;
				try{
					ad_ed_date=java.sql.Date.valueOf(req.getParameter("ad_ed_date"));
				}catch(IllegalArgumentException e){
					errorMsgsForUpdate.add("請輸入結束日期");
				}
				
				long outcome=ad_ed_date.getTime()-ad_op_date.getTime();
				if(outcome<0){
					errorMsgsForUpdate.add("結束日期須比開始日期還晚");
				}
				
				
				req.setAttribute("whichPage",req.getParameter("whichPage")); //取得目前所在的頁數，不管失敗獲成功都會停留在同一頁，不會跑到第一頁
				AdVO ad_vo=new AdVO();
				ad_vo.setAd_no(ad_no);
				ad_vo.setProd_no(prod_no);
				ad_vo.setAd_title(ad_title);
				ad_vo.setAd_img(ad_img);
				ad_vo.setAd_op_date(ad_op_date);
				ad_vo.setAd_ed_date(ad_ed_date);
			
			
				if (!errorMsgsForUpdate.isEmpty()) {
				 // 含有輸入格式錯誤的empVO物件,也存入req
					req.setAttribute("ad_VO", ad_vo);
					String url=req.getParameter("ad.jsp");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				AdService ad_Svc=new AdService();
				 ad_vo=ad_Svc.updateAd(ad_no, prod_no, ad_title, ad_img, ad_op_date, ad_ed_date);
			
//				req.setAttribute("gift_data_vo", gift_data_vo);
				
				String url=req.getParameter("ad.jsp");
				RequestDispatcher successView=req.getRequestDispatcher(url);
				successView.forward(req, res);
			}catch (Exception e) {
				errorMsgsForUpdate.add("修改資料失敗:"+e.getMessage());
				String url=req.getParameter("ad.jsp");
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
			
				failureView.forward(req, res);
			}
		  
	  }
		
		
		
//		
//		
//		
//		  if ("getOne_For_Update".equals(action)) { 
//			  List<String> errorMsgs = new LinkedList<String>();
//			  req.setAttribute("errorMsgs", errorMsgs);
//			 
//			  try{
//				  String gift_no=req.getParameter("GIFT_NO");
//				 
//				 
//				  Gift_dataService gift_dataSvc=new Gift_dataService();
//				
//				  Gift_dataVO gift_dataVO=gift_dataSvc.getOneGift_data(gift_no);
//				 
//				  
//				 
//				  req.setAttribute("gift_dataVO",gift_dataVO);
//				
//				  String url="/gift_data/update_gift_data_input.jsp";
//				  RequestDispatcher successView=req.getRequestDispatcher(url);
//				  successView.forward(req, res);
//				  
//			  }catch (Exception e) {
//					errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/gift_data/listAllGift_data.jsp");
//					failureView.forward(req, res);
//				}
//			  
//			  
//		  }
//		  
//		  

//		  
//		  
//		  
//		  if ("delete".equals(action)) {
//			  
//			  List<String> errorMsgs = new LinkedList<String>();
//			  req.setAttribute("errorMsgs", errorMsgs);
//			  
//			  try{
//				  
//				  String gift_no=req.getParameter("gift_no");
//				  Gift_dataService gift_dataSvc=new Gift_dataService();
//				  gift_dataSvc.deleteGift_data(gift_no);
//				  
//				  String url="/BackEnd/gift_data.jsp";
//				  RequestDispatcher successView=req.getRequestDispatcher(url);
//				  successView.forward(req,res);
//			  }catch (Exception e) {
//					errorMsgs.add("刪除資料失敗:"+e.getMessage());
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/BackEnd/gift_data.jsp");
//					failureView.forward(req, res);
//				}
//			  
//		  }
//		  
//		  
//		  
//		  
//		  
//		  
//		  
//		  
//		  
//		
	}
	
	
	
	
	
	
	
	
}
