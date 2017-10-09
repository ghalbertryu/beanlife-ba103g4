package com.rep_prod.model;

import java.io.Serializable;
import java.sql.Date;

public class Rep_prodVO  implements Serializable  {
	
	private String prod_no ;
	private String mem_ac ;
	private String rep_type ;
	private String rep_cont ;
	private Date rep_date ;
	private String rep_stat ;
	private String rep_stat_cont ;
	private Date stat_date ;
	
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
	public String getRep_type() {
		return rep_type;
	}
	public void setRep_type(String rep_type) {
		this.rep_type = rep_type;
	}
	public String getRep_cont() {
		return rep_cont;
	}
	public void setRep_cont(String rep_cont) {
		this.rep_cont = rep_cont;
	}
	public Date getRep_date() {
		return rep_date;
	}
	public void setRep_date(Date rep_date) {
		this.rep_date = rep_date;
	}
	public String getRep_stat() {
		return rep_stat;
	}
	public void setRep_stat(String rep_stat) {
		this.rep_stat = rep_stat;
	}
	public String getRep_stat_cont() {
		return rep_stat_cont;
	}
	public void setRep_stat_cont(String rep_stat_cont) {
		this.rep_stat_cont = rep_stat_cont;
	}
	public Date getStat_date() {
		return stat_date;
	}
	public void setStat_date(Date stat_date) {
		this.stat_date = stat_date;
	}


}
