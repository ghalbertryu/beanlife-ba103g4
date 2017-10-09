package com.cart_list.model;

import java.io.Serializable;

public class Cart_listVO  implements Serializable  {
	private String prod_no ;
	private String mem_ac ;
	private Integer prod_amount ;
	
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
	public Integer getProd_amount() {
		return prod_amount;
	}
	public void setProd_amount(Integer prod_amount) {
		this.prod_amount = prod_amount;
	}

}
