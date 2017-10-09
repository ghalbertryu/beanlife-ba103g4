package com.sys_msg.model;

public class Sys_msgService {
	private Sys_msgDAO_Interface dao;
	public Sys_msgService(){
		dao=new Sys_msgDAO();
	}
	public Sys_msgVO addSys_msg(Sys_msgVO sys_msg_vo){
		dao.insert(sys_msg_vo);
		return sys_msg_vo;
	}
	
	
	
}
