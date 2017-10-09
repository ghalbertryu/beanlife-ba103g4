package com.act_comm.model;

import java.util.List;
import java.util.Set;





public class Act_commService {

	
	private Act_commDAO_interface dao;
	
	public Act_commService(){
		dao=new Act_commJNDIDAO();
	}
	
	public Act_commVO addAct_comm( String act_no, String mem_ac,
		String	comm_cont,java.sql.Date comm_date, String comm_reply_cont,java.sql.Date comm_reply_date) {

		Act_commVO act_comm_VO = new Act_commVO();

		
		act_comm_VO.setAct_no(act_no);
		act_comm_VO.setMem_ac(mem_ac);
		act_comm_VO.setComm_cont(comm_cont);
		act_comm_VO.setComm_date(comm_date);
		act_comm_VO.setComm_reply_cont(comm_reply_cont);
		act_comm_VO.setComm_reply_date(comm_reply_date);
		dao.insert(act_comm_VO);

		return act_comm_VO;
	}
	public Act_commVO updateAct_comm(String comm_no, String act_no, String mem_ac,
			String	comm_cont,java.sql.Date comm_date, String comm_reply_cont,java.sql.Date comm_reply_date) {
		Act_commVO act_comm_VO = new Act_commVO();
		act_comm_VO.setComm_no(comm_no);
		act_comm_VO.setAct_no(act_no);
		act_comm_VO.setMem_ac(mem_ac);
		act_comm_VO.setComm_cont(comm_cont);
		act_comm_VO.setComm_date(comm_date);
		act_comm_VO.setComm_reply_cont(comm_reply_cont);
		act_comm_VO.setComm_reply_date(comm_reply_date);
		dao.update(act_comm_VO);

		return act_comm_VO;
	}

	public void deleteAct_comm(String comm_no) {
		dao.delete(comm_no);
	}

	public Act_commVO getOneAct_comm(String comm_no) {
		return dao.findByPrimaryKey(comm_no);
	}

	public List<Act_commVO> getAll() {
		return dao.getAll();
	}
	
	public void update_response(String comm_reply_cont, java.sql.Date comm_reply_date, String comm_no){
		dao.update_response(comm_reply_cont, comm_reply_date, comm_no);
		
		
	}
	
	
}
