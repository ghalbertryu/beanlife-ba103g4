package com.ord_list.model;

public class Ord_listVO {
	String ord_no;
	String prod_no;
	Integer amont;
	
	
	public String getOrd_no() {
		return ord_no;
	}
	public void setOrd_no(String ord_no) {
		this.ord_no = ord_no;
	}
	public String getProd_no() {
		return prod_no;
	}
	public void setProd_no(String prod_no) {
		this.prod_no = prod_no;
	}
	public Integer getAmont() {
		return amont;
	}
	public void setAmont(Integer amont) {
		this.amont = amont;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amont == null) ? 0 : amont.hashCode());
		result = prime * result + ((ord_no == null) ? 0 : ord_no.hashCode());
		result = prime * result + ((prod_no == null) ? 0 : prod_no.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ord_listVO other = (Ord_listVO) obj;
		if (amont == null) {
			if (other.amont != null)
				return false;
		} else if (!amont.equals(other.amont))
			return false;
		if (ord_no == null) {
			if (other.ord_no != null)
				return false;
		} else if (!ord_no.equals(other.ord_no))
			return false;
		if (prod_no == null) {
			if (other.prod_no != null)
				return false;
		} else if (!prod_no.equals(other.prod_no))
			return false;
		return true;
	}
	
	
}
