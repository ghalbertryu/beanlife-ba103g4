package com.fo_store.model;

import java.sql.Date;
import java.util.List;

import com.fo_prod.model.Fo_prodVO;

public class Fo_storeService {
	private Fo_storeDAO_interface dao;
	
	public Fo_storeService() {		
		dao = new Fo_storeDAO();
	}

	public Fo_storeVO addFo_store(String store_no, String mem_ac) {

		Fo_storeVO fo_storeVO = new Fo_storeVO();
		fo_storeVO.setStore_no(store_no);
		fo_storeVO.setMem_ac(mem_ac);
		fo_storeVO.setFo_date(Date.valueOf(java.time.LocalDate.now()));
		dao.insert(fo_storeVO);
		return fo_storeVO;
	}


	public void deleteFo_store(String store_no, String mem_ac) {
		dao.delete(store_no, mem_ac);
	}

	public List<Fo_storeVO> getAll() {
		return dao.getAll();
	}

	public int getCountByStore(String store_no) {
		return  dao.countByStore(store_no);
	}
	
	public  List<Fo_storeVO> getAllByMem(String mem_ac) {
		return  dao.getByMem(mem_ac);
	}
	
	public  Fo_storeVO getOne (String store_no, String mem_ac) {
		return  dao.findByPrimaryKey(store_no, mem_ac);
	}
}
