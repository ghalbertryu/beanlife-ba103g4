package com.convert_gift.model;

import java.io.Serializable;
import java.sql.Date;

public class Convert_giftVO implements Serializable{
	private String apply_no;
	private String mem_ac;
	private String apply_name;
	private String apply_phone;
	private String gift_no;
	
	private Integer gift_amount;
	
	private Date apply_date;
	private String apply_stat;
	private String apply_add;
	private Date send_date;
	
	private String send_no;
	public Convert_giftVO() {
		
		super();
	}
	public String getApply_no() {
		return apply_no;
	}
	public void setApply_no(String aPPLY_NO) {
		apply_no = aPPLY_NO;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mEM_AC) {
		mem_ac = mEM_AC;
	}
	public String getApply_name() {
		return apply_name;
	}
	public void setApply_name(String aPPLY_NAME) {
		apply_name = aPPLY_NAME;
	}
	public String getApply_phone() {
		return apply_phone;
	}
	public void setApply_phone(String aPPLY_PHONE) {
		apply_phone = aPPLY_PHONE;
	}
	public String getGift_no() {
		return gift_no;
	}
	public void setGift_no(String gIFT_NO) {
		gift_no = gIFT_NO;
	}
	public Integer getGift_amount() {
		return gift_amount;
	}
	public void setGift_amount(Integer gift_amount) {
		this.gift_amount = gift_amount;
	}
	
	
	
	public Date getApply_date() {
		return apply_date;
	}
	public void setApply_date(Date aPPLY_DATE) {
		apply_date = aPPLY_DATE;
	}
	public String getApply_stat() {
		return apply_stat;
	}
	public void setApply_stat(String aPPLY_STAT) {
		apply_stat = aPPLY_STAT;
	}
	public String getApply_add() {
		return apply_add;
	}
	public void setApply_add(String aPPLY_ADD) {
		apply_add = aPPLY_ADD;
	}
	public Date getSend_date() {
		return send_date;
	}
	public void setSend_date(Date sEND_DATE) {
		send_date = sEND_DATE;
	}
	public String getSend_no() {
		return send_no;
	}
	public void setSend_no(String sEND_NO) {
		send_no = sEND_NO;
	}
	
	
	
}
