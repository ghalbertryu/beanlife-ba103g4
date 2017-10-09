package com.mem.model;

import java.io.Serializable;
import java.sql.Date;

public class MemVO implements Serializable{
	private String mem_no;
	private String mem_ac;
	private String mem_pwd;
	private String mem_lname;
	private String mem_fname;
	private String mem_email;
	private String mem_phone;
	private String mem_add;
	private byte[] mem_pic;
	private String mem_set;
	private Integer mem_total_pt;
	private Integer mem_pt;
	private Integer grade_no;
	private String mem_stat;
	private Date mem_stat_cdate;
	private Date mem_reg_date;
	
	public String getMem_no() {
		return mem_no;
	}
	public void setMem_no(String mem_no) {
		this.mem_no = mem_no;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mem_ac) {
		this.mem_ac = mem_ac;
	}
	public String getMem_pwd() {
		return mem_pwd;
	}
	public void setMem_pwd(String mem_pwd) {
		this.mem_pwd = mem_pwd;
	}
	public String getMem_lname() {
		return mem_lname;
	}
	public void setMem_lname(String mem_lname) {
		this.mem_lname = mem_lname;
	}
	public String getMem_fname() {
		return mem_fname;
	}
	public void setMem_fname(String mem_fname) {
		this.mem_fname = mem_fname;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_add() {
		return mem_add;
	}
	public void setMem_add(String mem_add) {
		this.mem_add = mem_add;
	}
	public byte[] getMem_pic() {
		return mem_pic;
	}
	public void setMem_pic(byte[] mem_pic) {
		this.mem_pic = mem_pic;
	}
	public String getMem_set() {
		return mem_set;
	}
	public void setMem_set(String mem_set) {
		this.mem_set = mem_set;
	}
	public Integer getMem_total_pt() {
		return mem_total_pt;
	}
	public void setMem_total_pt(Integer mem_total_pt) {
		this.mem_total_pt = mem_total_pt;
	}
	public Integer getMem_pt() {
		return mem_pt;
	}
	public void setMem_pt(Integer mem_pt) {
		this.mem_pt = mem_pt;
	}
	public Integer getGrade_no() {
		return grade_no;
	}
	public void setGrade_no(Integer grade_no) {
		this.grade_no = grade_no;
	}
	public String getMem_stat() {
		return mem_stat;
	}
	public void setMem_stat(String mem_stat) {
		this.mem_stat = mem_stat;
	}
	public Date getMem_stat_cdate() {
		return mem_stat_cdate;
	}
	public void setMem_stat_cdate(Date mem_stat_cdate) {
		this.mem_stat_cdate = mem_stat_cdate;
	}
	public Date getMem_reg_date() {
		return mem_reg_date;
	}
	public void setMem_reg_date(Date mem_reg_date) {
		this.mem_reg_date = mem_reg_date;
	}
		
}
