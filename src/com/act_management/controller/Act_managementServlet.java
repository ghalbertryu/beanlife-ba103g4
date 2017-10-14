package com.act_management.controller;




import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.act.model.ActService;
import com.act.model.ActVO;
import com.act_comm.model.Act_commService;
import com.act_comm.model.Act_commVO;
import com.act_pair.model.Act_pairService;
import com.act_pair.model.Act_pairVO;
import com.convert_gift.model.Convert_giftService;
import com.convert_gift.model.Convert_giftVO;
import com.fo_act.model.Fo_actService;
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
import com.sys_msg.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 500 * 1024 * 1024, maxRequestSize = 500* 5 * 1024 * 1024)
public class Act_managementServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("track get");
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		req.setCharacterEncoding("UTF-8");
		System.out.println("track post");
		
		System.out.println("track1");
		String action = req.getParameter("action");
		
		
	
	System.out.println("action= "+action);
		
	
	
	
		if("goto_start_act.jsp".equals(action)){
			
			try{
			
			String url="/FrontEnd/act/start_act.jsp";
			
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req,res);
			
			}catch(Exception e){
				
				System.out.println("error");
			}
			
		}
		
		
		
		
if("search_for_actTag".equals(action)){
	try{
	String act_tag=req.getParameter("act_tag");
	System.out.println("act_tag= "+act_tag);
	ActService actSvc=new ActService();
	List<ActVO> act_vo_list= actSvc.getAll();
	List<ActVO> add_date_query=new ArrayList<ActVO>();
	for(int i=0;i<act_vo_list.size();i++){
		System.out.println("act_vo_list.get(i).getAct_tag().equals(act_tag) = "+act_vo_list.get(i).getAct_tag().equals(act_tag));
		System.out.println("act_vo_list.get(i).getAct_tag() = "+act_vo_list.get(i).getAct_tag());
		if(act_vo_list.get(i).getAct_tag().equals(act_tag)){
			add_date_query.add(act_vo_list.get(i));
		}
		
		
	}
	
	for(int i=0;i<add_date_query.size();i++){
		System.out.println("add_date_query.get("+i+").getAct_tag() = "+add_date_query.get(i).getAct_tag());
	}
	req.setAttribute("act_tag", act_tag);
	HttpSession session=req.getSession();
	session.setAttribute("add_date_query",add_date_query);
	
	
	String url=req.getParameter("act.jsp");
	
	RequestDispatcher dispatcher=req.getRequestDispatcher(url);
	dispatcher.forward(req,res);
	
	}catch(Exception e){
		String url=req.getParameter("act.jsp");
		RequestDispatcher dispatcher=req.getRequestDispatcher(url);
		dispatcher.forward(req,res);
	}
	
	
	
}
		
		
if("confirm_mem_pay".equals(action)){
	
	 List<String> openTab2=new LinkedList<String>();
	 openTab2.add("baba");
		req.setAttribute("openTab2", openTab2);
	
	
	
	String act_no=req.getParameter("act_no");
	String mem_ac=req.getParameter("mem_ac");
	Act_pairService act_pairSvc=new Act_pairService();
	Act_pairVO act_pair_vo= act_pairSvc.getOneAct_pair(act_no, mem_ac);
	String pay_state="已繳費";
	act_pair_vo.setPay_state(pay_state);
	act_pairSvc.updateAct_pair(act_pair_vo.getAct_no(), act_pair_vo.getMem_ac(), act_pair_vo.getApply_date(),act_pair_vo.getPay_state(),act_pair_vo.getChk_state());
	
	String url=req.getParameter("my_act.jsp");
	RequestDispatcher dispatcher=req.getRequestDispatcher(url);
	dispatcher.forward(req, res);
		}
		
		
		
		
		if("notice_pay".equals(action)){
			String act_no=req.getParameter("act_no");
			String mem_ac=req.getParameter("mem_ac");
			Act_pairService act_pairSvc=new Act_pairService();
			Act_pairVO act_pair_vo= act_pairSvc.getOneAct_pair(act_no, mem_ac);
			String pay_state=req.getParameter("pay_state");
			if(pay_state.trim().equals("未繳費")){
		    pay_state="未繳費(會員通知已繳費)";
			act_pair_vo.setPay_state(pay_state);
			act_pairSvc.updateAct_pair(act_pair_vo.getAct_no(), act_pair_vo.getMem_ac(), act_pair_vo.getApply_date(),act_pair_vo.getPay_state(),act_pair_vo.getChk_state());
			}
			String url=req.getParameter("my_act.jsp");
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
			
		}
		
		
		
		
		
		if("modify_act".equals(action)){
			HttpSession session=req.getSession();
			
		String	 act_no=req.getParameter("act_no");
		ActService actSvc=new ActService();
		ActVO act_vo= actSvc.getOneAct(act_no);
			session.setAttribute("act_vo",act_vo);
			java.sql.Timestamp dl_date=dateToTimestamp(act_vo.getDl_date());
			session.setAttribute("dl_date", dl_date);
			java.sql.Timestamp act_op_date=dateToTimestamp(act_vo.getAct_op_date());
			session.setAttribute("act_op_date", act_op_date);
			java.sql.Timestamp act_ed_date=dateToTimestamp(act_vo.getAct_ed_date());
			session.setAttribute("act_ed_date", act_ed_date);
			
			String url="/FrontEnd/act/start_act.jsp";
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
			
			
		}
		
		
		
		
		
		if("action_fail".equals(action)){
		
			List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			
			  List<String> openModal=new LinkedList<String>();
				openModal.add("baba");
				HttpSession session=req.getSession();
			  
			  
			try{
				
				String act_no=(String)session.getAttribute("act_no");
				if(act_no==null){
				 act_no=req.getParameter("act_no");
				}
				
				
				
				
				String re_cont=req.getParameter("re_cont");
				if(re_cont.trim().length()==0){
					errorMsgs.add("請輸入不通過原因!!");
				}
				if (!errorMsgs.isEmpty()) {
				
					session.setAttribute("act_no",act_no);
					
			
					req.setAttribute("openModal", openModal);
					String url=req.getParameter("action_check.jsp");
					
				
					RequestDispatcher dispatcher=req.getRequestDispatcher(url);
					dispatcher.forward(req, res);
					return;
				}
				
				
			
			
		
			ActService actSvc=new ActService();
			ActVO act_vo= actSvc.getOneAct(act_no);
	
			String act_stat="不通過審核";
			act_vo.setAct_stat(act_stat);
		
			java.sql.Date review_ed_date=new java.sql.Date(new Date().getTime());
		
			
			
			
			
			
			act_vo.setReview_ed_date(review_ed_date);
			act_vo.setRe_cont(re_cont);
			actSvc.updateAct(act_vo);
			
		
			String mem_ac=act_vo.getMem_ac();
			
			
			
			String msg_cont="活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"    審核失敗，不通過原因為: "+re_cont+"，請至個人活動頁面修正，修正後將重新審核，謝謝!!";
			java.sql.Date msg_send_date=new java.sql.Date(new Date().getTime());
			Sys_msgService sys_msgSvc=new Sys_msgService();
			Sys_msgVO sys_msg_vo=new Sys_msgVO();
			 sys_msg_vo.setMem_ac(mem_ac);	
			 sys_msg_vo.setMsg_cont(msg_cont);	
			 sys_msg_vo.setMsg_send_date(msg_send_date);
			 sys_msgSvc.addSys_msg(sys_msg_vo);
			
			
			
//			String myName = "sys"; 
//			String urName = mem_ac;//發給通知會員的帳號 
//			String message = "活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"    審核失敗，不通過原因為: "+re_cont+"，請至個人活動頁面修正，修正後將重新審核，謝謝!!";
//			req.setAttribute("myName", myName); 
//			req.setAttribute("urName", urName); 
//			req.setAttribute("message", message);	
			
			
			
			
			session.removeAttribute("act_no");
			
		
			
			String url=req.getParameter("action_check.jsp");
		
			
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
			
			
			
			}catch(Exception e){
				String act_no=(String)session.getAttribute("act_no");
				if(act_no==null){
				 act_no=req.getParameter("act_no");
				}
				
				
				session.setAttribute("act_no",act_no);
			
				req.setAttribute("openModal", openModal);
				String url=req.getParameter("action_check.jsp");
				RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				return;
			}
		}
		
		
		
		
		
		if("action_check_pass".equals(action)){
			String act_no=req.getParameter("act_no");
			ActService actSvc=new ActService();
			ActVO act_vo= actSvc.getOneAct(act_no);
			String act_stat=act_vo.getAct_stat();
			act_stat="可報名";
			act_vo.setAct_stat(act_stat);
			actSvc.updateAct(act_vo);
			String mem_ac=act_vo.getMem_ac();
			
			String msg_cont="活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"    已審核通過，將開始受理報名，謝謝!!";
			java.sql.Date msg_send_date=new java.sql.Date(new Date().getTime());
			Sys_msgService sys_msgSvc=new Sys_msgService();
			Sys_msgVO sys_msg_vo=new Sys_msgVO();
		
			 sys_msg_vo.setMem_ac(mem_ac);
			
			 sys_msg_vo.setMsg_cont(msg_cont);
			
			 sys_msg_vo.setMsg_send_date(msg_send_date);
			
			 sys_msgSvc.addSys_msg(sys_msg_vo);
			
//			String myName = "sys"; 
//			String urName = mem_ac;//發給通知會員的帳號 
//			 
//			String message = "活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"    已審核通過，將開始受理報名，謝謝!!"; 
//			req.setAttribute("myName", myName); 
//			req.setAttribute("urName", urName); 
//			req.setAttribute("message", message);
			
			
			
			String url=req.getParameter("action_check.jsp");
			RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			dispatcher.forward(req, res);
			
		}
		
		
		
		if("delete_fo_act".equals(action)){
			 List<String> openTab3=new LinkedList<String>();
			 openTab3.add("baba");
				req.setAttribute("openTab3", openTab3);
			try{
				String act_no=req.getParameter("act_no");
				String mem_ac=req.getParameter("mem_ac");
				Fo_actService fo_actSvc=new Fo_actService();
				fo_actSvc.deleteFo_act(mem_ac, act_no);
				 String url=req.getParameter("my_act.jsp");
				 RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				 dispatcher.forward(req, res);
			}catch(Exception e){
		
				 String url=req.getParameter("my_act.jsp");
				 RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				 dispatcher.forward(req, res);
			}
				
		}
		
		
		if("delete_act".equals(action)){
			System.out.println("track1");
			 List<String> openTab2=new LinkedList<String>();
			 openTab2.add("baba");
				req.setAttribute("openTab2", openTab2);
			
		try{
			String act_no=req.getParameter("act_no");
			System.out.println("track2");
			ActService actSvc=new ActService();
			String act_name=actSvc.getOneAct(act_no).getAct_name();
			System.out.println("track3");
			
			System.out.println("track4");
			String[] mem_ac_array=req.getParameterValues("mem_ac");
			System.out.println("track5");
			
	Act_pairService act_pairSvc=new Act_pairService();
	
	System.out.println("track6");
			if(mem_ac_array!=null){
			for(int i=0;i<mem_ac_array.length;i++){
				System.out.println("track7");
				String mem_ac=mem_ac_array[i];
				
			Act_pairVO act_pair_vo=act_pairSvc.getOneAct_pair(act_no, mem_ac);
			
			String pay_state=act_pair_vo.getPay_state();
			
//			String msg_cont;
//			
//			if(pay_state.equals("已繳費")){
//					
//				 msg_cont="活動編號"+act_no+"，活動名稱"+act_name+"     已被取消，系統已自動退款，歡迎再次參加活動!!";
//			}else{
//				 msg_cont="活動編號"+act_no+"，活動名稱"+act_name+"     已被取消，歡迎再次參加活動!!";
//			}
//				java.sql.Date msg_send_date=new java.sql.Date(new Date().getTime());
//				Sys_msgService sys_msgSvc=new Sys_msgService();
//				Sys_msgVO sys_msg_vo=new Sys_msgVO();
//				System.out.println("track8");
//				 sys_msg_vo.setMem_ac(mem_ac);
//				
//				 sys_msg_vo.setMsg_cont(msg_cont);
//				
//				 sys_msg_vo.setMsg_send_date(msg_send_date);
//				
//				 sys_msgSvc.addSys_msg(sys_msg_vo);
			
			String myName = "sys"; 
			String urName = mem_ac;//發給通知會員的帳號 
			 
			String message ;
			if(pay_state.equals("已繳費")){
				message="活動編號"+act_no+"，活動名稱"+act_name+"     已被取消，系統已自動退款，歡迎再次參加活動!!";
			}else{
				message="活動編號"+act_no+"，活動名稱"+act_name+"     已被取消，歡迎再次參加活動!!";
			}
			
			req.setAttribute("myName", myName); 
			req.setAttribute("urName", urName); 
			req.setAttribute("message", message);
			
			
			
				
				 MemService memSvc=new MemService();
					MemVO mem_vo=memSvc.getOneProd(mem_ac);
				Integer mem_pt=	mem_vo.getMem_pt();
					mem_pt=mem_pt-5;
					Integer mem_total_pt=mem_vo.getMem_total_pt();
					mem_total_pt=mem_total_pt-5;
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
					
					
					
					
					mem_vo.setMem_pt(mem_pt);
					mem_vo.setMem_total_pt(mem_total_pt);
					mem_vo.setGrade_no(grade_no);
					
					memSvc.updateMem(mem_vo);
					
					System.out.println("track9");
				 
				 
				 
			}
			}
		
			String mem_ac= actSvc.getOneAct(act_no).getMem_ac();
			 MemService memSvc=new MemService();
				MemVO mem_vo=memSvc.getOneProd(mem_ac);
			Integer mem_pt=	mem_vo.getMem_pt();
				mem_pt=mem_pt-5;
				Integer mem_total_pt=mem_vo.getMem_total_pt();
				mem_total_pt=mem_total_pt-5;
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
				
				
				
				
				mem_vo.setMem_pt(mem_pt);
				mem_vo.setMem_total_pt(mem_total_pt);
				mem_vo.setGrade_no(grade_no);
				
				memSvc.updateMem(mem_vo);
			
			
			
			
			
			
			
			
			
			
			
			
			actSvc.deleteAct(act_no);
			
			System.out.println("track10");
			
			 String url=req.getParameter("my_act.jsp");
			 RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			 dispatcher.forward(req, res);
		}catch(Exception e){
	System.out.println("track error");
		 String url=req.getParameter("my_act.jsp");
		 RequestDispatcher dispatcher=req.getRequestDispatcher(url);
		 dispatcher.forward(req, res);
				
				
			}
		}
		
		
		
		
		
		
		
		
		if("cancel_join".equals(action)){
			try{
			String mem_ac=req.getParameter("mem_ac");
			String act_no=req.getParameter("act_no");
			Act_pairService act_pairSvc=new Act_pairService();
			act_pairSvc.deleteAct_pair(act_no, mem_ac);
			ActService actSvc=new ActService();
			ActVO act_vo= actSvc.getOneAct(act_no);
			actSvc.reduce_mem_count(act_no);
			
//			String msg_cont="活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"  已完成退款!!";
//			java.sql.Date msg_send_date=new java.sql.Date(new Date().getTime());
//			Sys_msgService sys_msgSvc=new Sys_msgService();
//			Sys_msgVO sys_msg_vo=new Sys_msgVO();
//			 sys_msg_vo.setMem_ac(mem_ac);
//			 sys_msg_vo.setMsg_cont(msg_cont);
//			 sys_msg_vo.setMsg_send_date(msg_send_date);
//			 sys_msgSvc.addSys_msg(sys_msg_vo);
			
			String myName = "sys"; 
			String urName = mem_ac;//發給通知會員的帳號 
			 
			String message = "活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"  已完成退款!!";
			req.setAttribute("myName", myName); 
			req.setAttribute("urName", urName); 
			req.setAttribute("message", message);
			
			
			
			 MemService memSvc=new MemService();
				MemVO mem_vo=memSvc.getOneProd(mem_ac);
			Integer mem_pt=	mem_vo.getMem_pt();
				mem_pt=mem_pt-5;
				Integer mem_total_pt=mem_vo.getMem_total_pt();
				mem_total_pt=mem_total_pt-5;
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
				
				
				  mem_vo.setMem_total_pt(mem_total_pt);
					mem_vo.setGrade_no(grade_no);
				
				mem_vo.setMem_pt(mem_pt);
				memSvc.updateMem(mem_vo);
			 
			 
			 
			 
			 String url=req.getParameter("my_act.jsp");
			 RequestDispatcher dispatcher=req.getRequestDispatcher(url);
			 dispatcher.forward(req, res);
			}catch(Exception e){
		
				 String url=req.getParameter("my_act.jsp");
				 RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				 dispatcher.forward(req, res);
			}
			 
			
		}
		
		
		
		if("display_act_pair".equals(action)){
			 List<String> openModal=new LinkedList<String>();
				openModal.add("baba");
				req.setAttribute("openModal", openModal);
				
				 List<String> openTab2=new LinkedList<String>();
				 openTab2.add("baba");
					req.setAttribute("openTab2", openTab2);
			
	try{				
				
			String[] act_no_array=req.getParameterValues("act_no");
		
		
			String[] mem_ac_array=req.getParameterValues("mem_ac");
	
			List<Act_pairVO> list=new ArrayList<Act_pairVO>();
			Act_pairService act_pairSvc=new Act_pairService();
			if(act_no_array.length==mem_ac_array.length && act_no_array.length!=0){
				for(int i=0;i<act_no_array.length;i++){
				
					Act_pairVO act_pair_vo=act_pairSvc.getOneAct_pair(act_no_array[i], mem_ac_array[i]);
				
					list.add(act_pair_vo);
				}
			}
		
			req.setAttribute("act_pair_vo_list", list);
		
			String url= req.getParameter("my_act.jsp");
	
			RequestDispatcher successView=req.getRequestDispatcher(url);
			successView.forward(req, res);
			
	}catch(Exception e){
	
		String url= req.getParameter("my_act.jsp");
		RequestDispatcher errorView=req.getRequestDispatcher(url);
		errorView.forward(req, res);
	}
			
			
			
		}
		
		
		
		
		
		
		
		
		
		if("confirm_buy".equals(action)){
			  List<String> openModal=new LinkedList<String>();
				openModal.add("baba");
				req.setAttribute("openModal", openModal);
			
			
			HttpSession session=req.getSession();
			ActVO act_vo=(ActVO)session.getAttribute("act_vo");
			String mem_ac=req.getParameter("mem_ac");
			String act_no=act_vo.getAct_no();
			java.sql.Date apply_date=new java.sql.Date(new Date().getTime());
			String pay_state="未繳費";
			String chk_state="未報到";
			
			
			
			Act_pairService act_pairSvc=new Act_pairService();
			act_pairSvc.addAct_pair(act_no,mem_ac,apply_date,pay_state,chk_state);
			ActService actSvc=new ActService();
			Integer mem_count=new Integer(act_vo.getMem_count());
			Integer min_mem=new Integer(act_vo.getMin_mem());
			Integer max_mem=new Integer(act_vo.getMax_mem());
			String act_stat=act_vo.getAct_stat();
			System.out.println("act_stat= "+act_stat);
			System.out.println("mem_count= "+(mem_count+1));
			System.out.println("min_mem= "+min_mem);
			System.out.println("mem_count>=min_mem? = "+((mem_count+1)>=min_mem));
			if(act_stat.equals("可報名") && (mem_count+1)>=min_mem){
				act_stat="已成團";
				act_vo.setAct_stat(act_stat);
				actSvc.updateAct(act_vo);

	List<Act_pairVO> act_pair_list=  act_pairSvc.getAll();

	for(int i=0;i<act_pair_list.size();i++){
	
		if(act_pair_list.get(i).getAct_no().equals(act_no)){		
//				String msg_cont="活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"     已成團，請準時參加活動!!";
//				java.sql.Date msg_send_date=new java.sql.Date(new Date().getTime());
//				Sys_msgService sys_msgSvc=new Sys_msgService();
//				Sys_msgVO sys_msg_vo=new Sys_msgVO();
//		
//				 sys_msg_vo.setMem_ac(act_pair_list.get(i).getMem_ac());
//				 sys_msg_vo.setMsg_cont(msg_cont);
//				 sys_msg_vo.setMsg_send_date(msg_send_date);
//				 sys_msgSvc.addSys_msg(sys_msg_vo);
			
			String myName = "sys"; 
			String urName = act_pair_list.get(i).getMem_ac();//發給通知會員的帳號 
			 
			String message ="活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"     已成團，請準時參加活動!!";
			req.setAttribute("myName", myName); 
			req.setAttribute("urName", urName); 
			req.setAttribute("message", message);
			
			
			
		}
	}
			}
			System.out.println("mem_count= "+(mem_count+1));
			System.out.println("max_mem= "+max_mem);
			System.out.println("mem_count>=max_mem?="+((mem_count+1)>=max_mem));
		
			if((mem_count+1)>=max_mem){
				act_stat="已滿團";
				act_vo.setAct_stat(act_stat);
				actSvc.updateAct(act_vo);
		String host_mem_ac=act_vo.getMem_ac();
//		String msg_cont="恭喜，活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"     已滿團囉!!";
//		java.sql.Date msg_send_date=new java.sql.Date(new Date().getTime());
//		Sys_msgService sys_msgSvc=new Sys_msgService();
//		Sys_msgVO sys_msg_vo=new Sys_msgVO();
//		sys_msg_vo.setMem_ac(host_mem_ac);
//		 sys_msg_vo.setMsg_cont(msg_cont);
//		 sys_msg_vo.setMsg_send_date(msg_send_date);
//		 sys_msgSvc.addSys_msg(sys_msg_vo);
		
		String myName = "sys"; 
		String urName = host_mem_ac;//發給通知會員的帳號 
		 
		String message = "恭喜，活動編號"+act_no+"，活動名稱"+act_vo.getAct_name()+"     已滿團囉!!";
		req.setAttribute("myName", myName); 
		req.setAttribute("urName", urName); 
		req.setAttribute("message", message);
		
				
			}		
			actSvc.update_mem_count(act_no);
			
			MemService memSvc=new MemService();
			MemVO mem_vo=memSvc.getOneProd(mem_ac);
		Integer mem_pt=	mem_vo.getMem_pt();
			mem_pt=mem_pt+5;
			Integer mem_total_pt=mem_vo.getMem_total_pt();
			mem_total_pt=mem_total_pt+5;
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
			
			mem_vo.setMem_pt(mem_pt);
			mem_vo.setMem_total_pt(mem_total_pt);
			mem_vo.setGrade_no(grade_no);
			memSvc.updateMem(mem_vo);
			
			
			
			
			String url=req.getParameter("buy_act.jsp");
			RequestDispatcher successView=req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}
		
		
		if("buy_add".equals(action)){
			
		try{	
			String act_no=req.getParameter("act_no");
			ActService actSvc=new ActService();
			ActVO act_vo= actSvc.getOneAct(act_no);
			HttpSession session=req.getSession();
			session.setAttribute("act_vo",act_vo);
			String url="/FrontEnd/act/buy_act.jsp";
			RequestDispatcher successView=req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}catch(Exception e){
			String url=req.getParameter("act_detail.jsp");
			RequestDispatcher failView=req.getRequestDispatcher(url);
			failView.forward(req, res);
		}
			
		}
		
		
		if("add_favorite".equals(action)){
			
	try{		
			String mem_ac=req.getParameter("mem_ac");
			
		    String act_no=req.getParameter("act_no");
		 
		    
		    java.sql.Date fo_act_date=new java.sql.Date(new Date().getTime());
		
		    Fo_actService fo_actSvc=new Fo_actService();
		    fo_actSvc.addFo_act(mem_ac, act_no, fo_act_date);
		    
		    List<String> openModal=new LinkedList<String>();
			openModal.add("baba");
			req.setAttribute("openModal", openModal);
		    
		    
		    String url=req.getParameter("act_detail.jsp");
		    RequestDispatcher successView=req.getRequestDispatcher(url);
		    successView.forward(req, res);
		    
		    
		    
		    
		    
		    
	}catch(Exception e){
		 String url=req.getParameter("act_detail.jsp");
		    RequestDispatcher successView=req.getRequestDispatcher(url);
		    successView.forward(req, res);
		
		
	}
			
			
			
		}
		
		
		
		
		
		
		
		
		if("response_comm".equals(action)){
		
			try{
				String comm_reply_cont=req.getParameter("comm_reply_cont");
			
			
				String comm_no=req.getParameter("comm_no");
			
			
				Act_commService act_commSvc=new Act_commService();
				java.sql.Date comm_reply_date=new java.sql.Date(new Date().getTime());
			
				act_commSvc.update_response(comm_reply_cont,comm_reply_date,comm_no);
				
				Act_commVO act_comm_vo=act_commSvc.getOneAct_comm(comm_no);
				
				String mem_ac=act_comm_vo.getMem_ac();
				String act_no=act_comm_vo.getAct_no();
				ActService actSvc=new ActService();
				ActVO act_vo= actSvc.getOneAct(act_no);
				
				String myName = "sys"; 
				String urName = mem_ac;//發給通知會員的帳號 
				 
				String message = "活動編號: "+act_no+" 活動名稱: "+act_vo.getAct_name()+" 主辦單位已經回覆囉，請至該活動頁面查看。"; 
				req.setAttribute("myName", myName); 
				req.setAttribute("urName", urName); 
				req.setAttribute("message", message);

				System.out.println("comm successful");
				
				
				
				String url=req.getParameter("act_detail.jsp");
				RequestDispatcher successView=req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e){
			
				String url=req.getParameter("act_detail.jsp");
				RequestDispatcher failureView=req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
			
			
			
		}
		
		
		
		
		
		
		
		if("insert_comm".equals(action)){
		
			HttpSession session=req.getSession();
			List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			try{
		System.out.println("track1");
				String comm_cont=req.getParameter("comm_cont");
			
				if(comm_cont.length()==0){
					  errorMsgs.add("請輸入留言");
				}
				System.out.println("track2");
				if (!errorMsgs.isEmpty()) {
					System.out.println("track not empty");
					String url=req.getParameter("act_detail.jsp");
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				System.out.println("track3");
			
				String act_no=req.getParameter("act_no");
			
				String mem_ac=req.getParameter("mem_ac");
				System.out.println("track4");
			
//				java.sql.Date comm_date=new java.sql.Date(new Date().getTime());
				java.sql.Timestamp timestamp_comm_date=new java.sql.Timestamp(new Date().getTime());
				java.sql.Date comm_date=timestampToDate(timestamp_comm_date);
				
				System.out.println("track5");
			
				Act_commService act_commSvc=new Act_commService();
				act_commSvc.addAct_comm(act_no,mem_ac,comm_cont,comm_date,null,null);
				System.out.println("track6");
				
				ActService actSvc=new ActService();
				ActVO act_vo= actSvc.getOneAct(act_no);
				String host_mem_ac=act_vo.getMem_ac();
				
				
				
				String myName = "sys"; 
				String urName = host_mem_ac;//發給通知會員的帳號 
				 
				String message = "活動編號: "+act_no+" 活動名稱: "+act_vo.getAct_name()+"有訪客留言囉，請到該活動頁面解答訪客的問題。"; 
				req.setAttribute("myName", myName); 
				req.setAttribute("urName", urName); 
				req.setAttribute("message", message);

				
				
				
				String url=req.getParameter("act_detail.jsp");
				RequestDispatcher successView = req
						.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
				
			}catch(Exception e){
				System.out.println("track error");
				String url=req.getParameter("act_detail.jsp");
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			
			
			
			
		}
		
		
		
		
		
		
		
		
		if("start_act_complete".equals(action)){
			HttpSession session=req.getSession();
			List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			try{
				ActVO act_vo=(ActVO)session.getAttribute("act_vo");
				
				String act_no=act_vo.getAct_no();
			
				
				if(act_no!=null){
					
					System.out.println("開始修改活動");
					ActService actSvc=new ActService();
					
					String re_cont="無";
					act_vo.setRe_cont(re_cont);
					actSvc.updateAct(act_vo);	
					
				}else{
				System.out.println("開始新增活動");
				ActService actSvc=new ActService();
				actSvc.addAct(act_vo);
				
				String mem_ac=act_vo.getMem_ac();
				MemService memSvc=new MemService();
				MemVO mem_vo=memSvc.getOneProd(mem_ac);
			Integer mem_pt=	mem_vo.getMem_pt();
				mem_pt=mem_pt+5;
				Integer mem_total_pt=mem_vo.getMem_total_pt();
				mem_total_pt=mem_total_pt+5;
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
				
				
				
				
				
				mem_vo.setMem_pt(mem_pt);
				mem_vo.setMem_total_pt(mem_total_pt);
				mem_vo.setGrade_no(grade_no);
				memSvc.updateMem(mem_vo);
				}
				
				session.removeAttribute("act_vo");
				session.removeAttribute("act_op_date");
				session.removeAttribute("act_ed_date");
				session.removeAttribute("dl_date");
				String url="/FrontEnd/act/act.jsp";
				RequestDispatcher successView = req
						.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
				
			}catch(Exception e){
				String url=req.getParameter("start_act3.jsp");
				errorMsgs.add(e.getMessage());
				errorMsgs.add("系統錯誤");
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
			
		}
		
		
		
	
		if("start_act_to_pg3".equals(action)){
			HttpSession session=req.getSession();
			List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
		try{	  
	
			
			
		
//		InputStream is1=req.getPart("act_pic1").getInputStream(); 
		  InputStream is1= req.getPart("act_pic1").getInputStream(); 
		
		
		byte[] act_pic1=null;
		if(!req.getPart("act_pic1").getContentType().contains("image")){
		
			ActVO act_vo=(ActVO) session.getAttribute("act_vo");
			
			try{
			act_pic1=act_vo.getAct_pic1();
	
			
			}catch(Exception e){
				errorMsgs.add("請上傳活動照片1");
			
				
			}
		
			}else{
				
				ByteArrayOutputStream buffer=new ByteArrayOutputStream();
				
				int nRead;
				byte[] pic1=new byte[16384];
				while((nRead=is1.read(pic1))!=-1){
					buffer.write(pic1,0,nRead);
				}
				act_pic1=buffer.toByteArray();
			
			}
	
			InputStream is2=req.getPart("act_pic2").getInputStream();
			byte[] act_pic2=null;
			if(!req.getPart("act_pic2").getContentType().contains("image")){
				
				
				try{
				act_pic2=((ActVO) session.getAttribute("act_vo")).getAct_pic2();
				}catch(Exception e){
					act_pic2=null;
				}
			}else{
					
					ByteArrayOutputStream buffer=new ByteArrayOutputStream();
					
					int nRead;
					byte[] pic2=new byte[16384];
					while((nRead=is2.read(pic2))!=-1){
						buffer.write(pic2,0,nRead);
					}
					act_pic2=buffer.toByteArray();
					
				}
			
			InputStream is3=req.getPart("act_pic3").getInputStream();
			byte[] act_pic3=null;
			if(!req.getPart("act_pic3").getContentType().contains("image")){
				try{
				act_pic3=((ActVO) session.getAttribute("act_vo")).getAct_pic3();
				}catch(Exception e){
					act_pic3=null;
				}
			}else{
					
					ByteArrayOutputStream buffer=new ByteArrayOutputStream();
					
					int nRead;
					byte[] pic3=new byte[16384];
					while((nRead=is3.read(pic3))!=-1){
						buffer.write(pic3,0,nRead);
					}
					act_pic3=buffer.toByteArray();
					
				}
			
			
		
			java.sql.Timestamp timestamp_op_date;
			java.sql.Date act_op_date;
			try{		
				timestamp_op_date=java.sql.Timestamp.valueOf(req.getParameter("act_op_date"));
				session.setAttribute("act_op_date", timestamp_op_date);
				act_op_date=timestampToDate(timestamp_op_date);
			}catch(IllegalArgumentException e){
				timestamp_op_date=new java.sql.Timestamp(System.currentTimeMillis());
				act_op_date=timestampToDate(timestamp_op_date);
				errorMsgs.add("請輸入活動開始時間!");
			}	
		
			java.sql.Timestamp timestamp_ed_date;
			java.sql.Date act_ed_date;
			try{		
				timestamp_ed_date=java.sql.Timestamp.valueOf(req.getParameter("act_ed_date"));
				session.setAttribute("act_ed_date", timestamp_ed_date);
				act_ed_date=timestampToDate(timestamp_ed_date);
			}catch(IllegalArgumentException e){
				timestamp_ed_date=new java.sql.Timestamp(System.currentTimeMillis());
				act_ed_date=timestampToDate(timestamp_ed_date);
				errorMsgs.add("請輸入活動結束時間!");
			}	
			
			if((act_ed_date.getTime()-act_op_date.getTime())<0){
				errorMsgs.add("結束時間需晚於開始時間");
			}
			
			
			String org_cont=req.getParameter("org_cont");
			if(org_cont.length()==0){
				errorMsgs.add("請輸入主辦單位簡介");
			}
		
			String act_cont=req.getParameter("act_cont");
			if(act_cont.length()==0){
				errorMsgs.add("請輸入活動介紹");
			}
		
		ActVO act_vo=(ActVO) session.getAttribute("act_vo");
	

	
			act_vo.setAct_pic1(act_pic1);
		
			
		
	
			act_vo.setAct_pic2(act_pic2);
			act_vo.setAct_pic3(act_pic3);
		
			
		
		
			act_vo.setAct_op_date(act_op_date);
			act_vo.setAct_ed_date(act_ed_date);
		
		
			act_vo.setOrg_cont(org_cont);
			act_vo.setAct_cont(act_cont);
		
			act_vo.setMem_count(0);
		java.util.Date util_fd_date=new java.util.Date();
		java.sql.Date fd_date=new java.sql.Date(util_fd_date.getTime());
		   act_vo.setFd_date(fd_date);
		
			String act_stat="待審核";
			act_vo.setAct_stat(act_stat);
			java.util.Date util_review_ed_date=new java.util.Date();
			java.sql.Date review_ed_date=new java.sql.Date(util_review_ed_date.getTime());
		
			act_vo.setReview_ed_date(review_ed_date);
		
			session.setAttribute("act_vo", act_vo);
		
			if (!errorMsgs.isEmpty()) {
			
				String url=req.getParameter("start_act2.jsp");
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			
		
			
			
			
			String url="/FrontEnd/act/start_act3.jsp";
			RequestDispatcher successView=req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			
		}catch(Exception e){
	
			String url=req.getParameter("start_act2.jsp");
			errorMsgs.add(e.getMessage());
			errorMsgs.add("系統錯誤");
			RequestDispatcher failureView = req
					.getRequestDispatcher(url);
			failureView.forward(req, res);
		
		}
		}	
		
		
		if("start_act_to_pg2".equals(action)){
			List<String> errorMsgs = new LinkedList<String>();
			  req.setAttribute("errorMsgs", errorMsgs);
			try{
				String act_name= req.getParameter("act_name");
				System.out.println("track1");
				if(act_name.length()==0){
					  errorMsgs.add("請輸入活動名稱");
				}
				String act_add=req.getParameter("act_add");
				System.out.println("track2");
				if(act_add.length()==0){
					errorMsgs.add("請輸入活動地點");
				}
				
				Integer act_fee=null;
				try{
				 act_fee=new Integer(req.getParameter("act_fee"));
					System.out.println("track3");
				}catch(NumberFormatException e){
					
					errorMsgs.add("價格請填數字");
				}
				
//			String act_tag=req.getParameter("act_tag");
//			if(act_tag.length()==0){
//				errorMsgs.add("請輸入活動標籤");
//			}
				String act_tag=req.getParameter("act_tag");
				if(act_tag==null){
					errorMsgs.add("請選擇活動標籤");
				
				}
				
		Integer min_mem=null;
		try{
			min_mem=new Integer(req.getParameter("min_mem"));
			System.out.println("track5");
		}catch(NumberFormatException e){
			min_mem=0;
			errorMsgs.add("最低參加人數請填數字");
		}
		System.out.println("track6");
			if(min_mem==0){
				errorMsgs.add("請輸入最低參加人數");
			}
		Integer max_mem=null;
		try{
			max_mem=new Integer(req.getParameter("max_mem"));
			System.out.println("track7");
		}catch(NumberFormatException e){
			max_mem=0;
			errorMsgs.add("最高參加人數請填數字");
		}	
		System.out.println("track8");
		if(max_mem==0){
			errorMsgs.add("請輸入最高參加人數");
		}
		System.out.println("track9");
		if(min_mem>max_mem || min_mem==max_mem){
			errorMsgs.add("最高人數須大於最低人數");
		}
		
		
		
		
		System.out.println("track10");
		
		java.sql.Timestamp timestamp_dl_date;
		java.sql.Date dl_date=null;
		try{		
			timestamp_dl_date=java.sql.Timestamp.valueOf(req.getParameter("dl_date"));
			System.out.println("track11");
			dl_date=timestampToDate(timestamp_dl_date);
		}catch(IllegalArgumentException e){
			
			errorMsgs.add("請輸入截止日期!");
		}
		
		String pay_way=req.getParameter("pay_way");
		String act_atm_info="";
		if(pay_way==null){
			errorMsgs.add("請選擇繳費方式");
		
		}else{
		
		
		if(pay_way.equals("ATM")){
			act_atm_info=req.getParameter("act_atm_info");
			if(act_atm_info.trim().equals("")){
				errorMsgs.add("請輸入繳費資訊");
			}
			
		}
		}
		System.out.println("track14");
		String act_add_lat=req.getParameter("act_add_lat");
		System.out.println("track15");
		if(act_add_lat.equals("")){
			errorMsgs.add("請輸入有效的地址");
		}
		
		System.out.println("act_add_lat長度= "+act_add_lat.length());
		String act_add_lon=req.getParameter("act_add_lon");
		System.out.println("act_add_lon長度= "+act_add_lon.length());
		String mem_ac= req.getParameter("mem_ac");
		System.out.println("track16");
		HttpSession session=req.getSession();
		ActVO act_vo=(ActVO) session.getAttribute("act_vo");
		System.out.println("track17");
		if(act_vo==null){
			act_vo=new ActVO();
		}
		System.out.println("track18");
		act_vo.setAct_name(act_name);
		act_vo.setAct_add(act_add);
		act_vo.setAct_fee(act_fee);
		act_vo.setAct_tag(act_tag);
		act_vo.setMin_mem(min_mem);
		act_vo.setMax_mem(max_mem);
		act_vo.setDl_date(dl_date);
		act_vo.setPay_way(pay_way);
		act_vo.setAct_add_lat(act_add_lat);
		act_vo.setAct_add_lon(act_add_lon);
		act_vo.setMem_ac(mem_ac);
		act_vo.setAct_atm_info(act_atm_info);
		System.out.println("track19");
		if(dl_date!=null){
		session.setAttribute("dl_date",dateToTimestamp(dl_date));
		}
		session.setAttribute("act_vo",act_vo);
		System.out.println("track20");
		if (!errorMsgs.isEmpty()) {
			System.out.println("track not empty");
			String url=req.getParameter("start_act.jsp");
			RequestDispatcher failureView = req
					.getRequestDispatcher(url);
			failureView.forward(req, res);
			return;
		}
		System.out.println("track21");
		String url="/FrontEnd/act/start_act2.jsp";
		RequestDispatcher successView=req.getRequestDispatcher(url);
		successView.forward(req, res);
		
			}catch(Exception e){
				System.out.println("track error");
				String url=req.getParameter("start_act.jsp");
				errorMsgs.add(e.getMessage());
				errorMsgs.add("系統錯誤");
				RequestDispatcher failureView = req
						.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
			
			
			
		}
		
		
		
		if("sort".equals(action)){
			String url=req.getParameter("act.jsp");
			try{
				String sort=req.getParameter("sort");
				ActService actSvc=new ActService();
				List<ActVO> list=   actSvc.getSort(sort);
				HttpSession session=req.getSession();
				session.setAttribute("add_date_query", list);
				RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				
				
				
			}catch(Exception e){
				RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
			}
			
			
		}
		
		
		
		
		
		
		if ("add_date_query".equals(action)) {
		
			
		String url=req.getParameter("act.jsp");
		try{
			Map<String,String[]>map= req.getParameterMap();
			String act_add=req.getParameter("act_add");
			String act_op_date=req.getParameter("act_op_date");
			String act_ed_date=req.getParameter("act_ed_date");
			req.setAttribute("act_add",act_add);
			req.setAttribute("act_op_date",act_op_date);
			req.setAttribute("act_ed_date", act_ed_date);
		
		
		
			
			
			ActService actSvc=new ActService();
			List<ActVO> list=actSvc.getAll(map);
			HttpSession session=req.getSession();
		
			session.setAttribute("add_date_query", list);
			
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交listEmps_ByCompositeQuery.jsp
			
			successView.forward(req, res);
		}catch(Exception e){
			
			RequestDispatcher errorView = req.getRequestDispatcher(url); // 成功轉交listEmps_ByCompositeQuery.jsp
			errorView.forward(req, res);
			
		}
		}
		
		if ("goto_act_detail".equals(action)) {
			try{
		String act_no=req.getParameter("act_no");
	
		ActService actSvc=new ActService();
		ActVO act_vo= actSvc.getOneAct(act_no);
		String mem_ac=act_vo.getMem_ac();
	
		MemService memSvc=new MemService();
	
		MemVO mem_vo= memSvc.getOneProd(mem_ac);
	
		Set<Act_commVO> act_comm_set= actSvc.getAct_commByAct_no(act_no);
		
		
		
	
		
		HttpSession session=req.getSession();
		session.setAttribute("mem_vo",mem_vo);
		session.setAttribute("act_vo", act_vo);
		session.setAttribute("act_comm_set", act_comm_set);
		String url="/FrontEnd/act/act_detail.jsp";
		
		RequestDispatcher dispatcher=req.getRequestDispatcher(url);
		dispatcher.forward(req, res);
		
		
			}catch(Exception e){
			
				String url=req.getParameter("act.jsp");
				RequestDispatcher dispatcher=req.getRequestDispatcher(url);
				dispatcher.forward(req, res);
				
				
				
			}
			
			
			
		}
		
//		if ("getOne_For_Display".equals(action)) {
//		
//			List<String> errorMsgsForUpdate = new LinkedList<String>();
//			List<String> openModal=new LinkedList<String>();
//			openModal.add("baba");
//			req.setAttribute("openModal", openModal);
//			
//			req.setAttribute("errorMsgsForUpdate", errorMsgsForUpdate);
//		
//		try{	
//			String str = req.getParameter("GIFT_NO");
//		
//			if (str == null || (str.trim()).length() == 0) {
//				errorMsgsForUpdate.add("請輸入贈品編號");
//			}
//			req.setAttribute("whichPage",req.getParameter("whichPage"));   //取得目前所在的頁數，不管失敗獲成功都會停留在同一頁，不會跑到第一頁
//		
//			String url=req.getParameter("gift_data.jsp");
//	
//			if (!errorMsgsForUpdate.isEmpty()) {
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(url);
//				failureView.forward(req, res);
//				return;//程式中斷
//			}
//		
//			String gift_no=str;
//			Gift_dataService gift_dataSvc=new Gift_dataService();
//		
//			Gift_dataVO gift_data_vo=gift_dataSvc.getOneGift_data(gift_no);
//			
//			if ( gift_data_vo == null) {
//				
//				errorMsgsForUpdate.add("查無資料");
//			}
//		
//			if (!errorMsgsForUpdate.isEmpty()) {
//				RequestDispatcher failureView = req
//						.getRequestDispatcher(url);
//				failureView.forward(req, res);
//				return;//程式中斷
//			}
//	
//			
//	
//			if(req.getParameter("url")!=null){
//				url=(String) req.getParameter("url");
//			
//			}else{
//				url=(String) req.getParameter("url");
//		
//			}
//			
//			req.setAttribute("gift_data_vo", gift_data_vo);
//		
//			RequestDispatcher successView = req.getRequestDispatcher(url);
//			successView.forward(req, res);
//			
//			
//		}catch (Exception e) {
//		
//			String url=req.getParameter("gift_data.jsp");
//			errorMsgsForUpdate.add("無法取得資料:" + e.getMessage());
//			RequestDispatcher failureView = req
//					.getRequestDispatcher(url);
//			failureView.forward(req, res);
//		}
//			
//		}
//		
//		  if ("insert".equals(action)) { 
//			  
//			  List<String> errorMsgs = new LinkedList<String>();
//			  req.setAttribute("errorMsgs", errorMsgs);
//			
//			  try{
//				  String gift_name=req.getParameter("GIFT_NAME").trim();
//				  if(gift_name.length()==0){
//					  errorMsgs.add("請輸入贈品名稱");
//				  }
//				  
//				  
//				  String gift_cont=req.getParameter("GIFT_CONT").trim();
//				  if(gift_cont.length()==0){
//					  errorMsgs.add("請輸入贈品描述");
//				  }
//				 
//				  java.sql.Date gift_launch_date=null;
//				 
//				  try{
//					  gift_launch_date=java.sql.Date.valueOf(req.getParameter("GIFT_LAUNCH_DATE").trim());
//					 
//				  }catch (IllegalArgumentException e) {
//					 
//					  gift_launch_date=new java.sql.Date(System.currentTimeMillis());
//						errorMsgs.add("請輸入日期!");
//					}
//				 
//				  Integer gift_remain=null;
//				 
//				  try{
//				  gift_remain=new Integer(req.getParameter("GIFT_REMAIN").trim());
//				
//				  }catch (NumberFormatException e) {
//						gift_remain = 0;
//						errorMsgs.add("數量請填數字.");
//					}
//				  Integer gift_pt=null;
//				  try{
//					  gift_pt=new Integer(req.getParameter("GIFT_PT").trim());
//				  }catch (NumberFormatException e) {
//					  gift_pt = 0;
//						errorMsgs.add("積分請填數字.");
//					}
//				 
//
//				 
//				  InputStream  is= req.getPart("GIFT_IMG").getInputStream(); 
//				
//				  byte[] 	gift_img=null;
//				  if(!req.getPart("GIFT_IMG").getContentType().contains("image")){   //判斷是否有上傳照片
//					 
//					
//					 
//					   gift_img= ( byte[]) req.getSession().getAttribute("gift_img");         //抓使用者之前上傳的圖片
//					   if(gift_img==null){                                                                                         //判斷是否曾經上傳過圖片
//						   errorMsgs.add("請上傳贈品圖片");
//					   }
//				  }else{
//				  
//				
//					 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//					 int nRead;
//					 byte[] gift = new byte[16384];
//
//					 while ((nRead = is.read(gift)) != -1) {
//					   buffer.write(gift, 0, nRead);
//					 }
//
//					 	gift_img=buffer.toByteArray();
//				  req.getSession().setAttribute("gift_img",gift_img);  //將gift_img上傳到session上，如果使用者上架失敗可以從session拿到舊圖，使用者不用重新上傳
//				  }
//				  String url=req.getParameter("gift_data.jsp");
//				  
//				  Gift_dataVO gift_data_VO=new Gift_dataVO();
//				  gift_data_VO.setGift_name(gift_name);
//				  gift_data_VO.setGift_cont(gift_cont);
//				  gift_data_VO.setGift_launch_date(gift_launch_date);
//				  gift_data_VO.setGift_remain(gift_remain);
//				  gift_data_VO.setGift_pt(gift_pt);
//				  gift_data_VO.setGift_img(gift_img);
//					if (!errorMsgs.isEmpty()) {
//						req.setAttribute("gift_data_VO", gift_data_VO); // 含有輸入格式錯誤的empVO物件,也存入req
//						RequestDispatcher failureView = req
//								.getRequestDispatcher(url);
//						failureView.forward(req, res);
//						return;
//					}
//				  
//				  Gift_dataService gift_data_service=new Gift_dataService();
//				   gift_data_VO=gift_data_service.addGift_data(gift_name,gift_remain,gift_cont,gift_img,gift_pt,gift_launch_date);
//				  
//				   req.setAttribute("gift_data_VO", null);
////				  req.setAttribute("gift_data_VO",gift_data_VO);
//			
//				  
//				  
//				  
//				  RequestDispatcher successView=req.getRequestDispatcher(url);
//				  successView.forward(req,res);
//				  
//			  }catch (Exception e) {
//				  String url=req.getParameter("gift_data.jsp");
//				  errorMsgs.add(e.getMessage());
//					errorMsgs.add("系統錯誤");
//					RequestDispatcher failureView = req
//							.getRequestDispatcher(url);
//					failureView.forward(req, res);
//				}
//		
//		  }
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
//		  if ("update".equals(action)) { 
//			  
//				List<String> errorMsgsForUpdate = new LinkedList<String>();
//				req.setAttribute("errorMsgsForUpdate", errorMsgsForUpdate);
//			
//				try{
//					String gift_no=req.getParameter("gift_no").trim();
//				
//					String gift_name=req.getParameter("gift_name").trim();
//					if(gift_name.length()==0){
//						errorMsgsForUpdate.add("請輸入贈品名稱");
//					}
//					
//					Integer gift_remain=null;
//					try{
//					 gift_remain=new Integer(req.getParameter("gift_remain"));
//					
//					}catch(NumberFormatException e){
//						errorMsgsForUpdate.add("剩餘數量請填入數字");
//					}
//					
//					String gift_cont=req.getParameter("gift_cont").trim();
//					if(gift_name.length()==0){
//						errorMsgsForUpdate.add("請輸入贈品描述");
//					}
//				
//
//					
//					  InputStream  is= req.getPart("gift_img").getInputStream(); 
//					
//				
//					  byte[] 	gift_img=null;
//					  if(!req.getPart("gift_img").getContentType().contains("image")){   //判斷是否有上傳照片
//						  Gift_dataService gift_dataSvc=new Gift_dataService();
//						 
//							Gift_dataVO gift_data_vo=gift_dataSvc.getOneGift_data(gift_no);
//						  gift_img=gift_data_vo.getGift_img();
//						  
//						  
//
//					  }else{
//					  
//					  
//				
//					  
//						 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//						 int nRead;
//						 byte[] gift = new byte[16384];
//
//						 while ((nRead = is.read(gift)) != -1) {
//						   buffer.write(gift, 0, nRead);
//						 }
//					 
//				 	gift_img=buffer.toByteArray();
//					  }
//					
//			
//					 Integer gift_pt=null;
//					 try{
//					gift_pt=new Integer(req.getParameter("gift_pt"));
//					 }catch(NumberFormatException e){
//						 errorMsgsForUpdate.add("積分請填入數字");
//						 
//					 }
//				
//					java.sql.Date gift_launch_date=null;
//					try{
//						gift_launch_date=java.sql.Date.valueOf(req.getParameter("gift_launch_date"));
//					}catch(IllegalArgumentException e){
//						errorMsgsForUpdate.add("請輸入日期");
//					}
//					req.setAttribute("whichPage",req.getParameter("whichPage")); //取得目前所在的頁數，不管失敗獲成功都會停留在同一頁，不會跑到第一頁
//					Gift_dataVO gift_data_vo=new Gift_dataVO();
//					gift_data_vo.setGift_no(gift_no);
//					gift_data_vo.setGift_name(gift_name);
//					gift_data_vo.setGift_remain(gift_remain);
//					gift_data_vo.setGift_cont(gift_cont);
//					gift_data_vo.setGift_img(gift_img);
//					gift_data_vo.setGift_pt(gift_pt);
//					gift_data_vo.setGift_launch_date(gift_launch_date);
//				
//					if (!errorMsgsForUpdate.isEmpty()) {
//					 // 含有輸入格式錯誤的empVO物件,也存入req
//						String url=req.getParameter("gift_data.jsp");
//						
//						req.setAttribute("gift_data_vo", gift_data_vo);
//						RequestDispatcher failureView = req
//								.getRequestDispatcher(url);
//						failureView.forward(req, res);
//						return; //程式中斷
//					}
//					
//					Gift_dataService gift_dataSvc=new Gift_dataService();
//					 gift_data_vo=gift_dataSvc.updateGift_data(gift_no, gift_name, gift_remain, gift_cont, gift_img, gift_pt, gift_launch_date);
//				
//					req.setAttribute("gift_data_vo", gift_data_vo);
//					
//					String url=req.getParameter("gift_data.jsp");
//					RequestDispatcher successView=req.getRequestDispatcher(url);
//					successView.forward(req, res);
//				}catch (Exception e) {
//					errorMsgsForUpdate.add("修改資料失敗:"+e.getMessage());
//					String url=req.getParameter("gift_data.jsp");
//					RequestDispatcher failureView = req
//							.getRequestDispatcher(url);
//				
//					failureView.forward(req, res);
//				}
//			  
//		  }
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
//				  String url=req.getParameter("gift_data.jsp");
//				  RequestDispatcher successView=req.getRequestDispatcher(url);
//				  successView.forward(req,res);
//			  }catch (Exception e) {
//					errorMsgs.add("刪除資料失敗:"+e.getMessage());
//					String url=req.getParameter("gift_data.jsp");
//					RequestDispatcher failureView = req
//							.getRequestDispatcher(url);
//					failureView.forward(req, res);
//				}
//			  
//		  }
		  
		  
		  
		  
		  
		  
		  
		  
		  
		
	}
	
	public static java.sql.Date timestampToDate(java.sql.Timestamp timestamp){
		Date test_timestamp=timestamp;
		java.sql.Date test_date=new java.sql.Date(test_timestamp.getTime());
		return test_date;
	}

	public static java.sql.Timestamp dateToTimestamp(java.sql.Date date){
		
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		return timestamp;
		
	}
	
	
	
		
		
		
		
	}
	
	
	

