package com.gift_business.controller;

import com.convert_gift.model.Convert_giftService;
import com.convert_gift.model.Convert_giftVO;
import com.gift_data.model.Gift_dataService;
import com.gift_data.model.Gift_dataVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 500* 5 * 1024 * 1024)
public class Gift_businessServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		System.out.println(action);
if ("changeState".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try{
			
			String [] primaryKey=null;
			String [] statusValue=null;
			String [] send_no=null;
		 primaryKey=	 req.getParameterValues("primaryKey");
		 statusValue=	req.getParameterValues("statusValue");
		 send_no=req.getParameterValues("send_no");
		
if(primaryKey==null || statusValue==null){
	errorMsgs.add("無任何變更");
	System.out.println("got nothing");
	 String url =req.getParameter("convert_gift.jsp");
	  RequestDispatcher nullView=req.getRequestDispatcher(url);
	  nullView.forward(req,res);
	  return;
}
for(int i=0;i<statusValue.length;i++){
	System.out.println("statusValue["+i+"]="+statusValue[i]);
	System.out.println("send_no["+i+"].trim()="+send_no[i].trim());
if(statusValue[i].equals("已出貨") && send_no[i].trim().length()==0){
	
	errorMsgs.add("請輸入物流編號");
	String url =req.getParameter("convert_gift.jsp");
	  RequestDispatcher nullView=req.getRequestDispatcher(url);
	  nullView.forward(req,res);
	  return;
}

}
		 
System.out.println("track1");
		req.setAttribute("whichPage",req.getParameter("whichPage"));   //取得目前所在的頁數，不管失敗獲成功都會停留在同一頁，不會跑到第一頁
		System.out.println("track2");
//		Map<String ,String> getData=new Hashtable<String ,String>();
//			for(int i=0;i<primaryKey.length;i++){
//				getData.put(primaryKey[i],statusValue[i]);
//			}
			System.out.println("track3");
			Convert_giftService convert_giftSvc=new Convert_giftService();
			
			for(int i=0;i<primaryKey.length;i++){
			String mykey=primaryKey[i];
			String myStatus=statusValue[i];	
			String mySend_no=send_no[i];
				convert_giftSvc.updateStatus(mykey,myStatus,mySend_no);
			}
			
//			Set<String> mykeys=getData.keySet();
//			for(String mykey:mykeys){
//				convert_giftSvc.updateStatus(mykey,getData.get(mykey));
//				
//				
//			}
	
			
			
			String url =req.getParameter("convert_gift.jsp");
			  RequestDispatcher successView=req.getRequestDispatcher(url);
			  successView.forward(req,res);
			  return;
			}catch(Exception e){
				errorMsgs.add("更新失敗");
				String url =req.getParameter("convert_gift.jsp");
				  RequestDispatcher nullView=req.getRequestDispatcher(url);
				  nullView.forward(req,res);
			}
			  
		}
		
	
		
		
		
		
		if ("show_select_list".equals(action)) {
			String apply_stat=req.getParameter("requestURL");
			Map<String,String[]>map= req.getParameterMap();
			
			Convert_giftService convert_giftSvc=new Convert_giftService();
			List<Convert_giftVO> list=convert_giftSvc.getAll(map);
			
			HttpSession session=req.getSession();
			session.setAttribute("showConvert_gift", list);
			String my_apply_stat=req.getParameter("APPLY_STAT");
			session.setAttribute("apply_stat", my_apply_stat);
			String url=apply_stat;
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listEmps_ByCompositeQuery.jsp
			successView.forward(req, res);
			
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
			if (!errorMsgsForUpdate.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/BackEnd/gift_data.jsp");
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
						.getRequestDispatcher("/BackEnd/gift_data.jsp");
				failureView.forward(req, res);
				return;//程式中斷
			}
			req.setAttribute("gift_data_vo", gift_data_vo);
			String url =null;
			if(req.getAttribute("url")!=null){
				url=(String) req.getAttribute("url");
			}else{
			
		 url = "/BackEnd/gift_data.jsp";
			}
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			
		}catch (Exception e) {
			errorMsgsForUpdate.add("無法取得資料:" + e.getMessage());
			String errorUrl =null;
			if(req.getAttribute("url")!=null){
				errorUrl=(String) req.getAttribute("url");
			}else{
			errorUrl="/BackEnd/gift_data.jsp";
			}
			RequestDispatcher failureView = req
					.getRequestDispatcher(errorUrl);
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
								.getRequestDispatcher("/BackEnd/gift_data.jsp");
						failureView.forward(req, res);
						return;
					}
				  
				  Gift_dataService gift_data_service=new Gift_dataService();
				   gift_data_VO=gift_data_service.addGift_data(gift_name,gift_remain,gift_cont,gift_img,gift_pt,gift_launch_date);
				  
				   req.setAttribute("gift_data_VO", null);
//				  req.setAttribute("gift_data_VO",gift_data_VO);
			
				  
				  
				  String url ="/BackEnd/gift_data.jsp";
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req,res);
				  
			  }catch (Exception e) {
				  
				  errorMsgs.add(e.getMessage());
					errorMsgs.add("系統錯誤");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/BackEnd/gift_data.jsp");
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
						req.setAttribute("gift_data_vo", gift_data_vo);
						RequestDispatcher failureView = req
								.getRequestDispatcher("/BackEnd/gift_data.jsp");
						failureView.forward(req, res);
						return; //程式中斷
					}
					
					Gift_dataService gift_dataSvc=new Gift_dataService();
					 gift_data_vo=gift_dataSvc.updateGift_data(gift_no, gift_name, gift_remain, gift_cont, gift_img, gift_pt, gift_launch_date);
				
					req.setAttribute("gift_data_vo", gift_data_vo);
					
					String url="/BackEnd/gift_data.jsp";
					RequestDispatcher successView=req.getRequestDispatcher(url);
					successView.forward(req, res);
				}catch (Exception e) {
					errorMsgsForUpdate.add("修改資料失敗:"+e.getMessage());
				
					RequestDispatcher failureView = req
							.getRequestDispatcher("/BackEnd/gift_data.jsp");
				
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
				  
				  String url="/BackEnd/gift_data.jsp";
				  RequestDispatcher successView=req.getRequestDispatcher(url);
				  successView.forward(req,res);
			  }catch (Exception e) {
					errorMsgs.add("刪除資料失敗:"+e.getMessage());
					RequestDispatcher failureView = req
							.getRequestDispatcher("/BackEnd/gift_data.jsp");
					failureView.forward(req, res);
				}
			  
		  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		
	}
	
	
	
	
	
	
	
	
}
