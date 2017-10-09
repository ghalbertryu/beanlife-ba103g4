package com.mgr.model;

import java.sql.Date;


public class MgrVO implements java.io.Serializable{
	private String mgr_no;
	private String mgr_pwd;
	private String mgr_name;
	private String mgr_gender;
	private Date mgr_birth;
	private String mgr_email;
	private String mgr_phone;
	private Date mgr_reg_date;
	
	public String getMgr_no() {
		return mgr_no;
	}
	public void setMgr_no(String mGR_NO) {
		mgr_no = mGR_NO;
	}
	public String getMgr_pwd() {
		return mgr_pwd;
	}
	public void setMgr_pwd(String mGR_PWD) {
		mgr_pwd = mGR_PWD;
	}
	public String getMgr_name() {
		return mgr_name;
	}
	public void setMgr_name(String mGR_NAME) {
		mgr_name = mGR_NAME;
	}
	public String getMgr_gender() {
		return mgr_gender;
	}
	public void setMgr_gender(String mGR_GENDER) {
		mgr_gender = mGR_GENDER;
	}
	public Date getMgr_birth() {
		return mgr_birth;
	}
	public void setMgr_birth(Date mGR_BIRTH) {
		mgr_birth = mGR_BIRTH;
	}
	public String getMgr_email() {
		return mgr_email;
	}
	public void setMgr_email(String mGR_EMAIL) {
		mgr_email = mGR_EMAIL;
	}
	public String getMgr_phone() {
		return mgr_phone;
	}
	public void setMgr_phone(String mGR_PHONE) {
		mgr_phone = mGR_PHONE;
	}
	public Date getMgr_reg_date() {
		return mgr_reg_date;
	}
	public void setMgr_reg_date(Date mGR_REG_DATE) {
		mgr_reg_date = mGR_REG_DATE;
	}

	
}
