package com.qa.model;

import java.io.Serializable;
import java.sql.Date;

public class QaVO  implements Serializable  {
	private String qa_no ;
	private String prod_no ;
	private String mem_ac ;
	private String qa_cont ;
	private Date qa_date ;
	private String qa_reply_cont ;
	private Date qa_reply_date ;
	
	public String getQa_no() {
		return qa_no;
	}
	public void setQa_no(String qa_no) {
		this.qa_no = qa_no;
	}
	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mem_ac) {
		this.mem_ac = mem_ac;
	}
	public String getQa_cont() {
		return qa_cont;
	}
	public void setQa_cont(String qa_cont) {
		this.qa_cont = qa_cont;
	}
	public Date getQa_date() {
		return qa_date;
	}
	public void setQa_date(Date qa_date) {
		this.qa_date = qa_date;
	}
	public String getQa_reply_cont() {
		return qa_reply_cont;
	}
	public void setQa_reply_cont(String qa_reply_cont) {
		this.qa_reply_cont = qa_reply_cont;
	}
	public Date getQa_reply_date() {
		return qa_reply_date;
	}
	public void setQa_reply_date(Date qa_reply_date) {
		this.qa_reply_date = qa_reply_date;
	}

}
