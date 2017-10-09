package com.act_comm.model;

import java.util.List;



public interface Act_commDAO_interface {
	 public void insert( Act_commVO act_comm_VO);
     public void update( Act_commVO act_comm_VO);
     public void delete(String comm_no);
     public  Act_commVO findByPrimaryKey(String comm_no);
     public List< Act_commVO> getAll();
     
	public void update_response(String comm_reply_cont,java.sql.Date comm_reply_date,String comm_no);
	
	
}
