package com.fo_act.model;

import java.sql.Date;
import java.util.List;

public class Fo_actService {
	private Fo_actDAO_interface dao;
	
	public Fo_actService(){
		dao=new Fo_actJNDIDAO();
	}
	public Fo_actVO addFo_act(String mem_ac,String act_no,Date fo_act_date){
		Fo_actVO fo_act_VO =new Fo_actVO();
		fo_act_VO.setMem_ac(mem_ac);
		fo_act_VO.setAct_no(act_no);
		fo_act_VO.setFo_act_date(fo_act_date);
		
		
		dao.insert(fo_act_VO);
		return fo_act_VO;
		
	}
	public Fo_actVO updateFo_act(String mem_ac,String act_no,Date fo_act_date){
		Fo_actVO fo_act_VO =new Fo_actVO();
		fo_act_VO.setMem_ac(mem_ac);
		fo_act_VO.setAct_no(act_no);
		fo_act_VO.setFo_act_date(fo_act_date);
		
		dao.update(fo_act_VO);
		return fo_act_VO;
		
	}
	
	public void deleteFo_act(String mem_ac,String act_no){
		dao.delete(mem_ac, act_no);
	}
	public Fo_actVO getFo_act(String mem_ac,String act_no){
		return dao.findByPrimaryKey(mem_ac,act_no);
	}
	
	public List<Fo_actVO>getAll(){
		return dao.getAll();
	}
	
	
}
