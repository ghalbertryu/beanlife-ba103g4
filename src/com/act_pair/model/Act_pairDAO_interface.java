package com.act_pair.model;

import java.util.List;



public interface Act_pairDAO_interface {
	 public void insert(Act_pairVO act_pair_VO);
     public void update(Act_pairVO act_pair_VO);
     public void delete(String ACT_NO,String MEM_AC);
     public Act_pairVO findByPrimaryKey(String ACT_NO,String MEM_AC);
     public List<Act_pairVO> getAll();
     
}
