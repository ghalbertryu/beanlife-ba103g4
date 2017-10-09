package com.msg.model;

import java.io.Serializable;
import java.sql.Date;

public class MsgVO implements Serializable{
	private String msg_no ;
	private String mem_sen ;
	private String mem_rec ;
	private String msg_cont ;
	private Date msg_send_date ;
	private String msg_stat ;
	
	
	public String getMsg_no() {
		return msg_no;
	}
	public void setMsg_no(String msg_no) {
		this.msg_no = msg_no;
	}
	public String getMem_sen() {
		return mem_sen;
	}
	public void setMem_sen(String mem_sen) {
		this.mem_sen = mem_sen;
	}
	public String getMem_rec() {
		return mem_rec;
	}
	public void setMem_rec(String mem_rec) {
		this.mem_rec = mem_rec;
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
	public String getMsg_stat() {
		return msg_stat;
	}
	public void setMsg_stat(String msg_stat) {
		this.msg_stat = msg_stat;
	}
	
	

}
