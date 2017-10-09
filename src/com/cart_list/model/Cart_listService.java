package com.cart_list.model;
import java.util.Set;


public class Cart_listService {
	
	private Cart_listDAO_interface dao;
	
	public Cart_listService() {
		dao = new Cart_listDAO();
	}
	
	public Set<Cart_listVO> getVOsByMem(String mem_ac){
		return dao.getByMem(mem_ac);
	}
	
	public Cart_listVO addCart_list(String prod_no,String mem_ac,Integer prod_amount){
		Cart_listVO cart_listVO =new Cart_listVO();
		cart_listVO.setProd_no(prod_no);
		cart_listVO.setMem_ac(mem_ac);
		cart_listVO.setProd_amount(prod_amount);

		dao.insert(cart_listVO);
		return cart_listVO;
	}

	public Cart_listVO updateCart_list(String prod_no,String mem_ac,Integer prod_amount){
		Cart_listVO cart_listVO =new Cart_listVO();
		cart_listVO.setProd_no(prod_no);
		cart_listVO.setMem_ac(mem_ac);
		cart_listVO.setProd_amount(prod_amount);
		dao.update(cart_listVO);
		return cart_listVO;
	}
	
	public void deleteCart_list(String prod_no, String mem_ac){
		dao.delete(prod_no, mem_ac);
	}
	public Cart_listVO getCart_list(String prod_no, String mem_ac){
		return dao.findByPrimaryKey(prod_no,mem_ac);
	}
	
}
