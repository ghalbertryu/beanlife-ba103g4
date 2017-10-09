package com.review.model;

import java.io.Serializable;
import java.sql.Date;

public class ReviewVO implements Serializable {
	private String rev_no;
	private String	ord_no;
	private String	prod_no;
	private Integer	prod_score;
	private String	use_way;
	private String	rev_cont;
	private Date	rev_date;
	
	public String getRev_no() {
		return rev_no;
	}
	public void setRev_no(String rev_no) {
		this.rev_no = rev_no;
	}
	public String getOrd_no() {
		return ord_no;
	}
	public void setOrd_no(String ord_no) {
		this.ord_no = ord_no;
	}
	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	public Integer getProd_score() {
		return prod_score;
	}
	public void setProd_score(Integer prod_score) {
		this.prod_score = prod_score;
	}
	public String getUse_way() {
		return use_way;
	}
	public void setUse_way(String use_way) {
		this.use_way = use_way;
	}
	public String getRev_cont() {
		return rev_cont;
	}
	public void setRev_cont(String rev_cont) {
		this.rev_cont = rev_cont;
	}
	public Date getRev_date() {
		return rev_date;
	}
	public void setRev_date(Date rev_date) {
		this.rev_date = rev_date;
	}
	
	
}
