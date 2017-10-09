package com.like_rev.model;

import java.io.Serializable;

public class Like_revVO  implements Serializable  {
	private String rev_no;
	private String mem_ac;
	
	public String getRev_no() {
		return rev_no;
	}
	public void setRev_no(String rev_no) {
		this.rev_no = rev_no;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mem_ac) {
		this.mem_ac = mem_ac;
	}
	
}
