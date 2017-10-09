package com.cart_list.model;

import java.util.List;
import java.util.Set;

public interface Cart_listDAO_interface {
	public void insert(Cart_listVO cart_listVO);
    public void update(Cart_listVO cart_listVO);
    public void delete(String prod_no, String mem_ac);
    public Cart_listVO findByPrimaryKey(String prod_no, String mem_ac);
    public List<Cart_listVO> getAll();
    public Set<Cart_listVO> getByMem(String mem_ac);
}
