package com.act_pair.model;

import java.util.List;

public class Act_pairService {
	private Act_pairDAO_interface dao;
	public Act_pairService(){
		dao=new Act_pairJNDIDAO();
	}
	
	public Act_pairVO addAct_pair(String act_no,String mem_ac,java.sql.Date apply_date,String pay_state,String chk_state){
		
		Act_pairVO act_pair_VO=new Act_pairVO();
		
		act_pair_VO.setAct_no(act_no);
		act_pair_VO.setMem_ac(mem_ac);
		act_pair_VO.setApply_date(apply_date);
		act_pair_VO.setPay_state(pay_state);
		act_pair_VO.setChk_state(chk_state);
		dao.insert(act_pair_VO);
		return act_pair_VO;
	}
	
	public Act_pairVO updateAct_pair(String act_no,String mem_ac,java.sql.Date apply_date,String pay_state,String chk_state){
		Act_pairVO act_pair_VO=new Act_pairVO();
		act_pair_VO.setAct_no(act_no);
		act_pair_VO.setMem_ac(mem_ac);
		act_pair_VO.setApply_date(apply_date);
		act_pair_VO.setPay_state(pay_state);
		act_pair_VO.setChk_state(chk_state);
		dao.update(act_pair_VO);
		return act_pair_VO;
	}
	public void deleteAct_pair(String act_no,String mem_ac){
		dao.delete(act_no,mem_ac);
	}
	public Act_pairVO getOneAct_pair(String act_no,String mem_ac){
	
	return dao.findByPrimaryKey(act_no,mem_ac);
}
	public List<Act_pairVO>getAll(){
		return dao.getAll();
	}


}
