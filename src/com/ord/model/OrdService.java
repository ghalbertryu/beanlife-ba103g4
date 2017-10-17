package com.ord.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import com.ord.model.OrdVO;
import com.ord_list.model.Ord_listVO;
import com.prod.controller.ProdServletBack;

public class OrdService {
	private OrdDAO_interface dao;

	public OrdService() {
		dao = new OrdDAO();
	}

	// 查詢某訂單
	public OrdVO getOrdByOrdno(String ord_no) {
		return dao.findByPrimaryKey(ord_no);
	}

	// 查詢某訂單細目
	public Set<Ord_listVO> getOrd_listByOrd(String ord_no) {
		return dao.getOrd_listByOrd(ord_no);
	}

	//新增訂單 要取消的方法 不符合MVC架構
	public String newAnOrder(OrdVO ordVO, Set<Ord_listVO> ord_listVOs) {
		return dao.insertWithOrd_list(ordVO, ord_listVOs);
	}
	public String newAnOrder(String mem_ac,int send_fee,int total_pay,String ord_name, String ord_add, String ord_phone, String[] prod_noAry,String[] amountAry){	
		OrdVO ordVO = new OrdVO();
		Set<Ord_listVO> ord_listSet = new LinkedHashSet<Ord_listVO>();
		//OrdVO
		ordVO.setMem_ac(mem_ac);
		ordVO.setSend_fee(send_fee);
		ordVO.setTotal_pay(total_pay);
		ordVO.setOrd_name(ord_name);
		ordVO.setOrd_add(ord_add);
		ordVO.setOrd_phone(ord_phone);
		ordVO.setOrd_stat("未付款");
		ordVO.setOrd_date(new Date(System.currentTimeMillis()));

		//Set<Ord_listVO>
		for(int i=0; i<prod_noAry.length; i++){
			Ord_listVO ord_listVO = new Ord_listVO();
			ord_listVO.setProd_no(prod_noAry[i]);
			ord_listVO.setAmont(Integer.parseInt(amountAry[i]));
			ord_listSet.add(ord_listVO);
		}
		
		String ord_no = dao.insertWithOrd_list(ordVO, ord_listSet);
		
		//Auto cancel in a day
		ProdServletBack.timer.schedule(new TimerTask(){
			@Override
			public void run() {
								
				Date exeTime = new Date(scheduledExecutionTime());
				if(dao.findByPrimaryKey(ord_no).getOrd_stat().equals("未付款")){
					updateCancel(ord_no);
				}
				System.out.println(exeTime);
			}		
		}, 24*60*60*1000);
		
		return ord_no;
	}

	public List<OrdVO> getOrdByMem_ac(String mem_ac) {
		return dao.getOrdByMem_ac(mem_ac);
	}

	// 修改訂單狀態已付款-改已確認付款狀態
	public OrdVO update_payconiform(String ord_no) {
		OrdVO ordVO = dao.findByPrimaryKey(ord_no);
		ordVO.setOrd_stat("已確認付款");
		ordVO.setPay_chk_date(new Date(System.currentTimeMillis()));
		dao.update(ordVO);
		return ordVO;
	}

	// 修改訂單狀態 已確認付款-改已出貨
	public OrdVO update_sendstat(String ord_no, String send_id) {
		OrdVO ordVO = dao.findByPrimaryKey(ord_no);
		ordVO.setOrd_stat("已出貨");
		ordVO.setSend_date(new Date(System.currentTimeMillis()));
		ordVO.setSend_id(send_id);
		dao.update(ordVO);
		return ordVO;
	}
	
	// 修改訂單狀態 未付款-改已付款
	public OrdVO updatePayInfo(String ord_no, String pay_info) {
		OrdVO ordVO = dao.findByPrimaryKey(ord_no);
		ordVO.setOrd_stat("已付款");
		ordVO.setPay_date(new Date(System.currentTimeMillis()));
		ordVO.setPay_info(pay_info);
		System.out.println(pay_info+"ordSvc");
		dao.update(ordVO);
		return ordVO;
	}
	
	// 修改訂單狀態 未付款-改已取消
	public OrdVO updateCancel(String ord_no) {
		OrdVO ordVO = dao.findByPrimaryKey(ord_no);
		ordVO.setOrd_stat("已取消");
		
		dao.updateCancel(ordVO);
		return ordVO; 
	}
	
	public Set<OrdVO> getOrdThisWeek(){
		return dao.getAll(new Date(System.currentTimeMillis()-7*24*60*60*1000));
	}

}
