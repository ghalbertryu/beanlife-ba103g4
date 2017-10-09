package com.ad.model;

import java.io.Serializable;
import java.sql.Date;

public class AdVO implements Serializable{

	private String ad_no;
	private String prod_no;
	private String ad_title;
	 private	byte[] ad_img;
	 private Date ad_op_date;
	 private Date ad_ed_date;
	public AdVO() {
		
		super();
	}
	public String getAd_no() {
		return ad_no;
	}
	public void setAd_no(String aD_NO) {
		ad_no = aD_NO;
	}
	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String pROD_NO) {
		prod_no = pROD_NO;
	}
	public String getAd_title() {
		return ad_title;
	}
	public void setAd_title(String aD_TITLE) {
		ad_title = aD_TITLE;
	}
	public byte[] getAd_img() {
		return ad_img;
	}
	public void setAd_img(byte[] aD_IMG) {
		ad_img = aD_IMG;
	}
	public Date getAd_op_date() {
		return ad_op_date;
	}
	public void setAd_op_date(Date aD_OP_DATE) {
		ad_op_date = aD_OP_DATE;
	}
	public Date getAd_ed_date() {
		return ad_ed_date;
	}
	public void setAd_ed_date(Date aD_ED_DATE) {
		ad_ed_date = aD_ED_DATE;
	}
	
	
	
	
	
}
