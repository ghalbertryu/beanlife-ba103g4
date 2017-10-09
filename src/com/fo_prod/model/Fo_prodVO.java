package com.fo_prod.model;

import java.io.Serializable;
import java.sql.Date;

public class Fo_prodVO implements Serializable {
	private String prod_no ;
	private String mem_ac ;
	private Date fo_date ;
	
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
	public Date getFo_date() {
		return fo_date;
	}
	public void setFo_date(Date fo_date) {
		this.fo_date = fo_date;
	}

}
