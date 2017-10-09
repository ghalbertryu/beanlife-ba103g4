package com.fo_prod.model;

import java.sql.Date;
import java.util.List;

public class Fo_prodService {
	private Fo_prodDAO_interface dao;
	
	public Fo_prodService() {		
		dao = new Fo_prodDAO();
	}

	public Fo_prodVO addFo_prod(String prod_no, String mem_ac) {

		Fo_prodVO fo_prodVO = new Fo_prodVO();
		fo_prodVO.setProd_no(prod_no);
		fo_prodVO.setMem_ac(mem_ac);
		fo_prodVO.setFo_date(new Date(System.currentTimeMillis()));
		dao.insert(fo_prodVO);
		return fo_prodVO;
	}


	public void deleteFo_prod(String prod_no, String mem_ac) {
		dao.delete(prod_no, mem_ac);
	}

	public List<Fo_prodVO> getAll() {
		return dao.getAll();
	}

	public int getCountByProd(String prod_no) {
		return  dao.countByProd(prod_no);
	}
	
	public  List<Fo_prodVO> getAllByMem(String mem_ac) {
		return  dao.getByMem(mem_ac);
	}
	
	public  Fo_prodVO getOne (String prod_no, String mem_ac) {
		return  dao.findByPrimaryKey(prod_no, mem_ac);
	}
}
