package com.like_rev.model;

import java.util.List;

import com.fo_store.model.Fo_storeVO;


public interface Like_revDAO_interface {
	   public void insert(Like_revVO like_revVO);
	   public void update(Like_revVO like_revVO);
	   public void delete(String rev_no, String mem_ac);
	   public Like_revVO findByPrimaryKey(String rev_no, String mem_ac);
	   public List<Like_revVO> getAll();
	   public int countByReview(String rev_no);
	   public List<Like_revVO> getByMem(String mem_ac);
}
