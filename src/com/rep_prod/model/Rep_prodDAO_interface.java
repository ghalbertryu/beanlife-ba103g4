package com.rep_prod.model;

import java.util.List;


public interface Rep_prodDAO_interface {
	
    public void insert(Rep_prodVO rep_prodVO);
    public void update(Rep_prodVO rep_prodVO);
    public void delete(String prod_no, String mem_ac);
    public Rep_prodVO findByPrimaryKey(String prod_no, String mem_ac);
    public List<Rep_prodVO> getAll();

}
