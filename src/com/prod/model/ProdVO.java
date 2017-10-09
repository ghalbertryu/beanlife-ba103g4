package com.prod.model;

import java.io.Serializable;
import java.sql.Date;

public class ProdVO implements Serializable {
	private String prod_no;
	private String store_no;
	private String prod_name;
	private String bean_type;
	private String bean_grade;
	private String bean_contry;
	private String bean_region;
	private String bean_farm;
	private String bean_farmer;
	private Integer bean_el;
	private String proc;
	private String roast;
	private Integer bean_attr_acid;
	private Integer bean_attr_aroma;
	private Integer bean_attr_body;
	private Integer bean_attr_after;
	private Integer bean_attr_bal;
	private String bean_aroma;
	private Integer prod_price;
	private Double prod_wt;
	private Integer send_fee;
	private Integer prod_sup;
	private String prod_cont;
	private byte[] prod_pic1;
	private byte[] prod_pic2;
	private byte[] prod_pic3;
	private String prod_stat;
	private Date ed_time;
		
	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	public String getStore_no() {
		return store_no;
	}
	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getBean_type() {
		return bean_type;
	}
	public void setBean_type(String bean_type) {
		this.bean_type = bean_type;
	}
	public String getBean_grade() {
		return bean_grade;
	}
	public void setBean_grade(String bean_grade) {
		this.bean_grade = bean_grade;
	}
	public String getBean_contry() {
		return bean_contry;
	}
	public void setBean_contry(String bean_contry) {
		this.bean_contry = bean_contry;
	}
	public String getBean_region() {
		return bean_region;
	}
	public void setBean_region(String bean_region) {
		this.bean_region = bean_region;
	}
	public String getBean_farm() {
		return bean_farm;
	}
	public void setBean_farm(String bean_farm) {
		this.bean_farm = bean_farm;
	}
	public String getBean_farmer() {
		return bean_farmer;
	}
	public void setBean_farmer(String bean_farmer) {
		this.bean_farmer = bean_farmer;
	}
	public Integer getBean_el() {
		return bean_el;
	}
	public void setBean_el(Integer bean_el) {
		this.bean_el = bean_el;
	}
	public String getProc() {
		return proc;
	}
	public void setProc(String proc) {
		this.proc = proc;
	}
	public String getRoast() {
		return roast;
	}
	public void setRoast(String roast) {
		this.roast = roast;
	}
	public Integer getBean_attr_acid() {
		return bean_attr_acid;
	}
	public void setBean_attr_acid(Integer bean_attr_acid) {
		this.bean_attr_acid = bean_attr_acid;
	}
	public Integer getBean_attr_aroma() {
		return bean_attr_aroma;
	}
	public void setBean_attr_aroma(Integer bean_attr_aroma) {
		this.bean_attr_aroma = bean_attr_aroma;
	}
	public Integer getBean_attr_body() {
		return bean_attr_body;
	}
	public void setBean_attr_body(Integer bean_attr_body) {
		this.bean_attr_body = bean_attr_body;
	}
	public Integer getBean_attr_after() {
		return bean_attr_after;
	}
	public void setBean_attr_after(Integer bean_attr_after) {
		this.bean_attr_after = bean_attr_after;
	}
	public Integer getBean_attr_bal() {
		return bean_attr_bal;
	}
	public void setBean_attr_bal(Integer bean_attr_bal) {
		this.bean_attr_bal = bean_attr_bal;
	}
	public String getBean_aroma() {
		return bean_aroma;
	}
	public void setBean_aroma(String bean_aroma) {
		this.bean_aroma = bean_aroma;
	}
	public Integer getProd_price() {
		return prod_price;
	}
	public void setProd_price(Integer prod_price) {
		this.prod_price = prod_price;
	}
	public Double getProd_wt() {
		return prod_wt;
	}
	public void setProd_wt(Double prod_wt) {
		this.prod_wt = prod_wt;
	}
	public Integer getSend_fee() {
		return send_fee;
	}
	public void setSend_fee(Integer send_fee) {
		this.send_fee = send_fee;
	}
	public Integer getProd_sup() {
		return prod_sup;
	}
	public void setProd_sup(Integer prod_sup) {
		this.prod_sup = prod_sup;
	}
	public String getProd_cont() {
		return prod_cont;
	}
	public void setProd_cont(String prod_cont) {
		this.prod_cont = prod_cont;
	}
	public byte[] getProd_pic1() {
		return prod_pic1;
	}
	public void setProd_pic1(byte[] prod_pic1) {
		this.prod_pic1 = prod_pic1;
	}
	public byte[] getProd_pic2() {
		return prod_pic2;
	}
	public void setProd_pic2(byte[] prod_pic2) {
		this.prod_pic2 = prod_pic2;
	}
	public byte[] getProd_pic3() {
		return prod_pic3;
	}
	public void setProd_pic3(byte[] prod_pic3) {
		this.prod_pic3 = prod_pic3;
	}
	public String getProd_stat() {
		return prod_stat;
	}
	public void setProd_stat(String prod_stat) {
		this.prod_stat = prod_stat;
	}
	public Date getEd_time() {
		return ed_time;
	}
	public void setEd_time(Date ed_time) {
		this.ed_time = ed_time;
	}

}
