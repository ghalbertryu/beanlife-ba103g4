package com.act_pair.model;

import java.io.Serializable;
import java.sql.Date;

public class Act_pairVO implements Serializable{
	private String act_no;
	private String mem_ac;
	private Date apply_date;
	private String pay_state;
	private String chk_state;
	public Act_pairVO() {
		super();
	}
	public String getAct_no() {
		return act_no;
		
	}
	public void setAct_no(String aCT_NO) {
		act_no = aCT_NO;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mEM_AC) {
		mem_ac = mEM_AC;
	}
	public Date getApply_date() {
		return apply_date;
	}
	public void setApply_date(Date aPPLY_DATE) {
		apply_date = aPPLY_DATE;
	}
	public String getPay_state() {
		return pay_state;
	}
	public void setPay_state(String pAY_STATE) {
		pay_state = pAY_STATE;
	}
	public String getChk_state() {
		return chk_state;
	}
	public void setChk_state(String cHK_STATE) {
		chk_state = cHK_STATE;
	}
	
	
	
	
}
