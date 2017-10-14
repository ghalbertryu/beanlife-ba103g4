package com.sys_msg.model;

import java.util.Set;

public class Sys_msgService {
	private Sys_msgDAO_Interface dao;
	public Sys_msgService(){
		dao=new Sys_msgDAO();
	}
	public Sys_msgVO addSys_msg(Sys_msgVO sys_msg_vo){
		dao.insert(sys_msg_vo);
		return sys_msg_vo;
	}
	public void addSys_msg(String mem_ac, String msg_cont){
		Sys_msgVO sys_msgVO = new Sys_msgVO();
		sys_msgVO.setMem_ac(mem_ac);
		sys_msgVO.setMsg_cont(msg_cont);
		dao.insert(sys_msgVO);
	}
	public Set<Sys_msgVO> getAllByMem(String mem_ac){
		return dao.getAllByMem(mem_ac);
	}
	
	
	
}
