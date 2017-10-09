package com.act.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ActVO implements Serializable{
	private String act_no;
	private String mem_ac;
	private String org_cont;
	private String act_name;
	private Integer min_mem;
	private Integer max_mem;
	private Integer mem_count;
	private Date act_op_date;
	private Date act_ed_date;
	private Date dl_date;
	private Date fd_date;
//	private Timestamp act_op_date;
//	private  Timestamp act_ed_date;
//	private  Timestamp dl_date;
//	private  Timestamp fd_date;
	private String act_add;
	private String act_add_lat;
	private String act_add_lon;
	private String act_cont;
	private String act_tag;
	private Integer act_fee;
	private String pay_way;
	private byte[] act_pic1;
	private byte[] act_pic2;
	private byte[] act_pic3;
	private String act_stat;
	private String re_cont;
	private Date review_ed_date;
//	private Timestamp review_ed_date;
	
	private String act_atm_info;
	public String getAct_atm_info() {
		return act_atm_info;
	}
	public void setAct_atm_info(String act_atm_info) {
		this.act_atm_info = act_atm_info;
	}
	public ActVO() {
		
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
	public String getOrg_cont() {
		return org_cont;
	}
	public void setOrg_cont(String oRG_CONT) {
		org_cont = oRG_CONT;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String aCT_NAME) {
		act_name = aCT_NAME;
	}
	public Integer getMin_mem() {
		return min_mem;
	}
	public void setMin_mem(Integer mIN_MEM) {
		min_mem = mIN_MEM;
	}
	public Integer getMax_mem() {
		return max_mem;
	}
	public void setMax_mem(Integer mAX_MEM) {
		max_mem = mAX_MEM;
	}
	public Integer getMem_count() {
		return mem_count;
	}
	public void setMem_count(Integer mEM_COUNT) {
		mem_count = mEM_COUNT;
	}
	public Date getAct_op_date() {
	
		return act_op_date;
	}
	public void setAct_op_date(Date aCT_OP_DATE) {
		act_op_date = aCT_OP_DATE;
	}
	public Date getAct_ed_date() {
		return act_ed_date;
	}
	public void setAct_ed_date(Date aCT_ED_DATE) {
		act_ed_date = aCT_ED_DATE;
	}
	public Date getDl_date() {
		return dl_date;
	}
	public void setDl_date(Date dL_DATE) {
		dl_date = dL_DATE;
	}
	public Date getFd_date() {
		return fd_date;
	}
	public void setFd_date(Date fD_DATE) {
		fd_date = fD_DATE;
	}
//	public Timestamp getAct_op_date() {
//		
//		return act_op_date;
//	}
//	public void setAct_op_date(Timestamp aCT_OP_DATE) {
//		act_op_date = aCT_OP_DATE;
//	}
//	public Timestamp getAct_ed_date() {
//		return act_ed_date;
//	}
//	public void setAct_ed_date(Timestamp aCT_ED_DATE) {
//		act_ed_date = aCT_ED_DATE;
//	}
//	public Timestamp getDl_date() {
//		return dl_date;
//	}
//	public void setDl_date(Timestamp dL_DATE) {
//		dl_date = dL_DATE;
//	}
//	public Timestamp getFd_date() {
//		return fd_date;
//	}
//	public void setFd_date(Timestamp fD_DATE) {
//		fd_date = fD_DATE;
//	}
	
	
	
	public String getAct_add() {
		return act_add;
	}
	public void setAct_add(String aCT_ADD) {
		act_add = aCT_ADD;
	}
	public String getAct_add_lat() {
		return act_add_lat;
	}
	public void setAct_add_lat(String aCT_ADD_LAT) {
		act_add_lat = aCT_ADD_LAT;
	}
	public String getAct_add_lon() {
		return act_add_lon;
	}
	public void setAct_add_lon(String aCT_ADD_LON) {
		act_add_lon = aCT_ADD_LON;
	}
	public String getAct_cont() {
		return act_cont;
	}
	public void setAct_cont(String aCT_CONT) {
		act_cont = aCT_CONT;
	}
	public String getAct_tag() {
		return act_tag;
	}
	public void setAct_tag(String aCT_TAG) {
		act_tag = aCT_TAG;
	}
	public Integer getAct_fee() {
		return act_fee;
	}
	public void setAct_fee(Integer aCT_FEE) {
		act_fee = aCT_FEE;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pAY_WAY) {
		pay_way = pAY_WAY;
	}
	public byte[] getAct_pic1() {
		return act_pic1;
	}
	public void setAct_pic1(byte[] aCT_PIC1) {
		act_pic1 = aCT_PIC1;
	}
	public byte[] getAct_pic2() {
		return act_pic2;
	}
	public void setAct_pic2(byte[] aCT_PIC2) {
		act_pic2 = aCT_PIC2;
	}
	public byte[] getAct_pic3() {
		return act_pic3;
	}
	public void setAct_pic3(byte[] aCT_PIC3) {
		act_pic3 = aCT_PIC3;
	}
	public String getAct_stat() {
		return act_stat;
	}
	public void setAct_stat(String aCT_STAT) {
		act_stat = aCT_STAT;
	}
	public String getRe_cont() {
		return re_cont;
	}
	public void setRe_cont(String rE_CONT) {
		re_cont = rE_CONT;
	}
	public Date getReview_ed_date() {
		return review_ed_date;
	}
//	public Timestamp getReview_ed_date() {
//		return review_ed_date;
//	}
	public void setReview_ed_date(Date rEVIEW_ED_DATE) {
		review_ed_date = rEVIEW_ED_DATE;
	}
//	public void setReview_ed_date(Timestamp rEVIEW_ED_DATE) {
//		review_ed_date = rEVIEW_ED_DATE;
//	}
	
	
	
	
}
