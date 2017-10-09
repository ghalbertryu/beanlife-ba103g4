package com.ord_list.model;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface Ord_listDAO_interface {
	public void insert(Ord_listVO ord_listvo);
	
	public void insertByCon(Ord_listVO ord_listvo, Connection con);

	public void update(Ord_listVO ord_listvo);
	public void  delete(String ord_no, String prod_no);
	
	public Ord_listVO findByPrimaryKey(String ord_no, String prod_no);
	public List<Ord_listVO> getAll();
	
}
