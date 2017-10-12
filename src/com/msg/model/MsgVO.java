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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mem_rec == null) ? 0 : mem_rec.hashCode());
		result = prime * result + ((mem_sen == null) ? 0 : mem_sen.hashCode());
		result = prime * result + ((msg_cont == null) ? 0 : msg_cont.hashCode());
		result = prime * result + ((msg_no == null) ? 0 : msg_no.hashCode());
		result = prime * result + ((msg_send_date == null) ? 0 : msg_send_date.hashCode());
		result = prime * result + ((msg_stat == null) ? 0 : msg_stat.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MsgVO other = (MsgVO) obj;
		if (mem_rec == null) {
			if (other.mem_rec != null)
				return false;
		} else if (!mem_rec.equals(other.mem_rec))
			return false;
		if (mem_sen == null) {
			if (other.mem_sen != null)
				return false;
		} else if (!mem_sen.equals(other.mem_sen))
			return false;
		if (msg_cont == null) {
			if (other.msg_cont != null)
				return false;
		} else if (!msg_cont.equals(other.msg_cont))
			return false;
		if (msg_no == null) {
			if (other.msg_no != null)
				return false;
		} else if (!msg_no.equals(other.msg_no))
			return false;
		if (msg_send_date == null) {
			if (other.msg_send_date != null)
				return false;
		} else if (!msg_send_date.equals(other.msg_send_date))
			return false;
		if (msg_stat == null) {
			if (other.msg_stat != null)
				return false;
		} else if (!msg_stat.equals(other.msg_stat))
			return false;
		return true;
	}
	
	

}
