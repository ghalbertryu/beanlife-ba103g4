package com.prod.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ord.model.OrdService;
import com.ord.model.OrdVO;
import com.ord_list.model.Ord_listVO;
import com.prod.model.ProdService;
import com.prod.model.ProdVO;

@WebServlet("/prod/prodBack.do")
public class ProdServletBack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public static Timer timer = new Timer();
	
    @Override
    public void init(){
    	
    	//find countrys every hr
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				Date exeTime = new Date(scheduledExecutionTime());
				
				Set<String> countrys = setCountrys();
				ServletContext context = getServletContext();
		    	context.setAttribute("countrys", countrys);

				System.out.println(exeTime+" "+countrys);
			}		
		}, new Date(System.currentTimeMillis()), 60*60*1000);		
		
		//find hotProds every week
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				Map <String, Integer> rank = new HashMap<>();
				
				OrdService ordSvc = new OrdService();
				Set<OrdVO> ordVOs = ordSvc.getOrdThisWeek();
				for(OrdVO ordVO : ordVOs){
					Set<Ord_listVO> ord_listVOs = ordSvc.getOrd_listByOrd(ordVO.getOrd_no());
					for(Ord_listVO ord_listVO : ord_listVOs){
						if(rank.get(ord_listVO.getProd_no())==null){
							rank.put(ord_listVO.getProd_no(),ord_listVO.getAmont());
						} else {
							int amount = rank.get(ord_listVO.getProd_no());
							amount += ord_listVO.getAmont();
							rank.put(ord_listVO.getProd_no(),amount);
						}
					}
				}
				List<Map.Entry<String, Integer>> list = new ArrayList<>(rank.entrySet());
				Comparator<Map.Entry> sortByValue = (e1,e2)->{ return ((Integer)e1.getValue()).compareTo( (Integer)e2.getValue()); };
				Collections.sort(list, sortByValue);
				Collections.reverse(list);
				
				ProdService prodSvc = new ProdService();
				Set<ProdVO> prodVOs = new LinkedHashSet<>();
				for(int i=0 ;i<Math.min(list.size(),10);i++){
					prodVOs.add(prodSvc.getOneProd(list.get(i).getKey()));		
				}
				
				ServletContext context = getServletContext();
		    	context.setAttribute("hotProdVOs", prodVOs);
			

			}
			
		}, new Date(), 7*24*60*60*1000);
    }
    
    public static Set<String> setCountrys(){
    	Set<String> countrys = new TreeSet<>();
    	ProdService prodSvc = new ProdService();
    	List<ProdVO> prodVOs = prodSvc.getAllNoImg();
    	for(ProdVO prodVO : prodVOs){
    		countrys.add(prodVO.getBean_contry());
    	}
    	System.out.println(countrys);
    	return countrys;
    }
    
    @Override
    public void destroy(){
    	timer.cancel();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}

}
