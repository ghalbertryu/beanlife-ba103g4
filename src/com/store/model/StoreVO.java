package com.store.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class StoreVO implements Serializable {
	private String store_no;
	private String mem_ac;
	private String tax_id_no;
	private byte[] win_id_pic;
	private String store_phone;
	private String store_add;
	private String store_add_lat;
	private String store_add_lon;
	private String store_name;
	private String store_cont;
	private byte[] store_pic1;
	private byte[] store_pic2;
	private byte[] store_pic3;
	private Integer store_free_ship;
	private String store_atm_info;
	
	private String store_stat;
	private String store_stat_cont;
	private Date store_stat_cdate;
	
	
	public String getStore_no() {
		return store_no;
	}
	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mem_ac) {
		this.mem_ac = mem_ac;
	}
	public String getTax_id_no() {
		return tax_id_no;
	}
	public void setTax_id_no(String tax_id_no) {
		this.tax_id_no = tax_id_no;
	}
	public byte[] getWin_id_pic() {
		return win_id_pic;
	}
	public void setWin_id_pic(byte[] Win_id_pic) {
		this.win_id_pic = Win_id_pic;
	}
	public String getStore_phone() {
		return store_phone;
	}
	public void setStore_phone(String store_phone) {
		this.store_phone = store_phone;
	}
	public String getStore_add() {
		return store_add;
	}
	public void setStore_add(String store_add) {
		this.store_add = store_add;
	}
	public String getStore_add_lon() {
		return store_add_lon;
	}
	public void setStore_add_lon(String store_add_lon) {
		this.store_add_lon = store_add_lon;
	}
	public String getStore_add_lat() {
		return store_add_lat;
	}
	public void setStore_add_lat(String store_add_lat) {
		this.store_add_lat = store_add_lat;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getStore_cont() {
		return store_cont;
	}
	public void setStore_cont(String store_cont) {
		this.store_cont = store_cont;
	}
	public byte[] getStore_pic1() {
		return store_pic1;
	}
	public void setStore_pic1(byte[] store_pic1) {
		this.store_pic1 = store_pic1;
	}
	public byte[] getStore_pic2() {
		return store_pic2;
	}
	public void setStore_pic2(byte[] store_pic2) {
		this.store_pic2 = store_pic2;
	}
	public byte[] getStore_pic3() {
		return store_pic3;
	}
	public void setStore_pic3(byte[] store_pic3) {
		this.store_pic3 = store_pic3;
	}
	public Integer getStore_free_ship() {
		return store_free_ship;
	}
	public void setStore_free_ship(Integer store_free_ship) {
		this.store_free_ship = store_free_ship;
	}
	public String getStore_stat() {
		return store_stat;
	}
	public void setStore_stat(String store_stat) {
		this.store_stat = store_stat;
	}
	public String getStore_stat_cont() {
		return store_stat_cont;
	}
	public void setStore_stat_cont(String store_stat_cont) {
		this.store_stat_cont = store_stat_cont;
	}
	public Date getStore_stat_cdate() {
		return store_stat_cdate;
	}
	public void setStore_stat_cdate(Date store_stat_cdate) {
		this.store_stat_cdate = store_stat_cdate;
	}
	public String getStore_atm_info() {
		return store_atm_info;
	}
	public void setStore_atm_info(String store_atm_info) {
		this.store_atm_info = store_atm_info;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mem_ac == null) ? 0 : mem_ac.hashCode());
		result = prime * result + ((store_add == null) ? 0 : store_add.hashCode());
		result = prime * result + ((store_add_lat == null) ? 0 : store_add_lat.hashCode());
		result = prime * result + ((store_add_lon == null) ? 0 : store_add_lon.hashCode());
		result = prime * result + ((store_atm_info == null) ? 0 : store_atm_info.hashCode());
		result = prime * result + ((store_cont == null) ? 0 : store_cont.hashCode());
		result = prime * result + ((store_free_ship == null) ? 0 : store_free_ship.hashCode());
		result = prime * result + ((store_name == null) ? 0 : store_name.hashCode());
		result = prime * result + ((store_no == null) ? 0 : store_no.hashCode());
		result = prime * result + ((store_phone == null) ? 0 : store_phone.hashCode());
		result = prime * result + Arrays.hashCode(store_pic1);
		result = prime * result + Arrays.hashCode(store_pic2);
		result = prime * result + Arrays.hashCode(store_pic3);
		result = prime * result + ((store_stat == null) ? 0 : store_stat.hashCode());
		result = prime * result + ((store_stat_cdate == null) ? 0 : store_stat_cdate.hashCode());
		result = prime * result + ((store_stat_cont == null) ? 0 : store_stat_cont.hashCode());
		result = prime * result + ((tax_id_no == null) ? 0 : tax_id_no.hashCode());
		result = prime * result + Arrays.hashCode(win_id_pic);
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
		StoreVO other = (StoreVO) obj;
		if (mem_ac == null) {
			if (other.mem_ac != null)
				return false;
		} else if (!mem_ac.equals(other.mem_ac))
			return false;
		if (store_add == null) {
			if (other.store_add != null)
				return false;
		} else if (!store_add.equals(other.store_add))
			return false;
		if (store_add_lat == null) {
			if (other.store_add_lat != null)
				return false;
		} else if (!store_add_lat.equals(other.store_add_lat))
			return false;
		if (store_add_lon == null) {
			if (other.store_add_lon != null)
				return false;
		} else if (!store_add_lon.equals(other.store_add_lon))
			return false;
		if (store_atm_info == null) {
			if (other.store_atm_info != null)
				return false;
		} else if (!store_atm_info.equals(other.store_atm_info))
			return false;
		if (store_cont == null) {
			if (other.store_cont != null)
				return false;
		} else if (!store_cont.equals(other.store_cont))
			return false;
		if (store_free_ship == null) {
			if (other.store_free_ship != null)
				return false;
		} else if (!store_free_ship.equals(other.store_free_ship))
			return false;
		if (store_name == null) {
			if (other.store_name != null)
				return false;
		} else if (!store_name.equals(other.store_name))
			return false;
		if (store_no == null) {
			if (other.store_no != null)
				return false;
		} else if (!store_no.equals(other.store_no))
			return false;
		if (store_phone == null) {
			if (other.store_phone != null)
				return false;
		} else if (!store_phone.equals(other.store_phone))
			return false;
		if (!Arrays.equals(store_pic1, other.store_pic1))
			return false;
		if (!Arrays.equals(store_pic2, other.store_pic2))
			return false;
		if (!Arrays.equals(store_pic3, other.store_pic3))
			return false;
		if (store_stat == null) {
			if (other.store_stat != null)
				return false;
		} else if (!store_stat.equals(other.store_stat))
			return false;
		if (store_stat_cdate == null) {
			if (other.store_stat_cdate != null)
				return false;
		} else if (!store_stat_cdate.equals(other.store_stat_cdate))
			return false;
		if (store_stat_cont == null) {
			if (other.store_stat_cont != null)
				return false;
		} else if (!store_stat_cont.equals(other.store_stat_cont))
			return false;
		if (tax_id_no == null) {
			if (other.tax_id_no != null)
				return false;
		} else if (!tax_id_no.equals(other.tax_id_no))
			return false;
		if (!Arrays.equals(win_id_pic, other.win_id_pic))
			return false;
		return true;
	}
	

}
