package com.sys_msg.model;

import java.io.Serializable;
import java.sql.Date;

public class Sys_msgVO implements Serializable{
	private String sys_msg_no ;
	private String mem_ac ;
	private String msg_cont ;
	private Date msg_send_date ;
	
	public String getSys_msg_no() {
		return sys_msg_no;
	}
	public void setSys_msg_no(String sys_msg_no) {
		this.sys_msg_no = sys_msg_no;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mem_ac) {
		this.mem_ac = mem_ac;
	}
	public String getMsg_cont() {
		return msg_cont;
	}
	public void setMsg_cont(String msg_cont) {
		this.msg_cont = msg_cont;
	}
	public Date getMsg_send_date() {
		return msg_send_date;
	}
	public void setMsg_send_date(Date msg_send_date) {
		this.msg_send_date = msg_send_date;
	}

}
