package com.ord.model;

import java.io.Serializable;
import java.sql.Date;

public class OrdVO implements Serializable{
	private String ord_no;
	private String mem_ac;
	private Integer send_fee;
	private Integer total_pay;
	private String ord_name;
	private String ord_phone;
	private String ord_add;
	private String pay_info;
	private String ord_stat;
	private Date ord_date;
	private Date pay_date;
	private Date pay_chk_date;
	private Date send_date;
	private String send_id;
	
	public String getOrd_no() {
		return ord_no;
	}
	public void setOrd_no(String ord_no) {
		this.ord_no = ord_no;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mem_ac) {
		this.mem_ac = mem_ac;
	}
	public Integer getSend_fee() {
		return send_fee;
	}
	public void setSend_fee(Integer send_fee) {
		this.send_fee = send_fee;
	}
	public Integer getTotal_pay() {
		return total_pay;
	}
	public void setTotal_pay(Integer total_pay) {
		this.total_pay = total_pay;
	}
	public String getOrd_name() {
		return ord_name;
	}
	public void setOrd_name(String ord_name) {
		this.ord_name = ord_name;
	}
	public String getOrd_phone() {
		return ord_phone;
	}
	public void setOrd_phone(String ord_phone) {
		this.ord_phone = ord_phone;
	}
	public String getOrd_add() {
		return ord_add;
	}
	public void setOrd_add(String ord_add) {
		this.ord_add = ord_add;
	}
	public String getPay_info() {
		return pay_info;
	}
	public void setPay_info(String pay_info) {
		this.pay_info = pay_info;
	}
	public String getOrd_stat() {
		return ord_stat;
	}
	public void setOrd_stat(String ord_stat) {
		this.ord_stat = ord_stat;
	}
	public Date getOrd_date() {
		return ord_date;
	}
	public void setOrd_date(Date ord_date) {
		this.ord_date = ord_date;
	}
	public Date getPay_date() {
		return pay_date;
	}
	public void setPay_date(Date pay_date) {
		this.pay_date = pay_date;
	}
	public Date getPay_chk_date() {
		return pay_chk_date;
	}
	public void setPay_chk_date(Date pay_chk_date) {
		this.pay_chk_date = pay_chk_date;
	}
	public Date getSend_date() {
		return send_date;
	}
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}
	public String getSend_id() {
		return send_id;
	}
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mem_ac == null) ? 0 : mem_ac.hashCode());
		result = prime * result + ((ord_add == null) ? 0 : ord_add.hashCode());
		result = prime * result + ((ord_date == null) ? 0 : ord_date.hashCode());
		result = prime * result + ((ord_name == null) ? 0 : ord_name.hashCode());
		result = prime * result + ((ord_no == null) ? 0 : ord_no.hashCode());
		result = prime * result + ((ord_phone == null) ? 0 : ord_phone.hashCode());
		result = prime * result + ((ord_stat == null) ? 0 : ord_stat.hashCode());
		result = prime * result + ((pay_chk_date == null) ? 0 : pay_chk_date.hashCode());
		result = prime * result + ((pay_date == null) ? 0 : pay_date.hashCode());
		result = prime * result + ((pay_info == null) ? 0 : pay_info.hashCode());
		result = prime * result + ((send_date == null) ? 0 : send_date.hashCode());
		result = prime * result + ((send_fee == null) ? 0 : send_fee.hashCode());
		result = prime * result + ((send_id == null) ? 0 : send_id.hashCode());
		result = prime * result + ((total_pay == null) ? 0 : total_pay.hashCode());
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
		OrdVO other = (OrdVO) obj;
		if (mem_ac == null) {
			if (other.mem_ac != null)
				return false;
		} else if (!mem_ac.equals(other.mem_ac))
			return false;
		if (ord_add == null) {
			if (other.ord_add != null)
				return false;
		} else if (!ord_add.equals(other.ord_add))
			return false;
		if (ord_date == null) {
			if (other.ord_date != null)
				return false;
		} else if (!ord_date.equals(other.ord_date))
			return false;
		if (ord_name == null) {
			if (other.ord_name != null)
				return false;
		} else if (!ord_name.equals(other.ord_name))
			return false;
		if (ord_no == null) {
			if (other.ord_no != null)
				return false;
		} else if (!ord_no.equals(other.ord_no))
			return false;
		if (ord_phone == null) {
			if (other.ord_phone != null)
				return false;
		} else if (!ord_phone.equals(other.ord_phone))
			return false;
		if (ord_stat == null) {
			if (other.ord_stat != null)
				return false;
		} else if (!ord_stat.equals(other.ord_stat))
			return false;
		if (pay_chk_date == null) {
			if (other.pay_chk_date != null)
				return false;
		} else if (!pay_chk_date.equals(other.pay_chk_date))
			return false;
		if (pay_date == null) {
			if (other.pay_date != null)
				return false;
		} else if (!pay_date.equals(other.pay_date))
			return false;
		if (pay_info == null) {
			if (other.pay_info != null)
				return false;
		} else if (!pay_info.equals(other.pay_info))
			return false;
		if (send_date == null) {
			if (other.send_date != null)
				return false;
		} else if (!send_date.equals(other.send_date))
			return false;
		if (send_fee == null) {
			if (other.send_fee != null)
				return false;
		} else if (!send_fee.equals(other.send_fee))
			return false;
		if (send_id == null) {
			if (other.send_id != null)
				return false;
		} else if (!send_id.equals(other.send_id))
			return false;
		if (total_pay == null) {
			if (other.total_pay != null)
				return false;
		} else if (!total_pay.equals(other.total_pay))
			return false;
		return true;
	}
	
	
	

}
