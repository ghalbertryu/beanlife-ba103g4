package com.like_rev.model;

import java.util.List;

import com.fo_prod.model.Fo_prodVO;
import com.fo_store.model.Fo_storeVO;

public class Like_revService {
	private Like_revDAO_interface dao;
	
	public Like_revService() {		
		dao = new Like_revDAO();
	}

	public Like_revVO addLike_rev(String rev_no, String mem_ac) {

		Like_revVO like_revVO = new Like_revVO();
		like_revVO.setRev_no(rev_no);
		like_revVO.setMem_ac(mem_ac);
		dao.insert(like_revVO);
		return like_revVO;
	}


	public void deleteLike_rev(String rev_no, String mem_ac) {
		dao.delete(rev_no, mem_ac);
	}

	public List<Like_revVO> getAll() {
		return dao.getAll();
	}

	public int getCountByRev(String rev_no) {
		return  dao.countByReview(rev_no);
	}
	
	public  List<Like_revVO> getAllByMem(String mem_ac) {
		return  dao.getByMem(mem_ac);
	}
	
	public  Like_revVO getOne (String rev_no, String mem_ac) {
		return  dao.findByPrimaryKey(rev_no, mem_ac);
	}
	
}
