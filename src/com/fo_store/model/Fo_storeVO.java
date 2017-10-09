package com.fo_store.model;

import java.io.Serializable;
import java.sql.Date;

public class Fo_storeVO implements Serializable{
	private String store_no; 
	private String mem_ac;
	private Date fo_date;
	
	public String getStore_no() {
		return store_no;
	}
	public void setStore_no(String store_no) {
		this.store_no = store_no;
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
