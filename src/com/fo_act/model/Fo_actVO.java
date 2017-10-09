package com.fo_act.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Fo_actVO implements Serializable{
	private String mem_ac;
	private String act_no;
	private Date fo_act_date;
//	private Timestamp FO_ACT_DATE;
	public Fo_actVO() {
		super();
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mEM_AC) {
		mem_ac = mEM_AC;
		
	}
	public String getAct_no() {
		return act_no;
	}
	public void setAct_no(String aCT_NO) {
		act_no = aCT_NO;
	}
	public Date getFo_act_date() {
		return fo_act_date;
	}
//	public Timestamp getFO_ACT_DATE() {
//		return FO_ACT_DATE;
//	}
	public void setFo_act_date(Date fO_ACT_DATE) {
		fo_act_date = fO_ACT_DATE;
	}
//	public void setFO_ACT_DATE(Timestamp fO_ACT_DATE) {
//		FO_ACT_DATE = fO_ACT_DATE;
//	}
	
}
