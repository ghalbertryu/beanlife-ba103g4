package com.fo_act.model;

import java.util.List;

import com.gift_data.model.Gift_dataVO;

public interface Fo_actDAO_interface {
	 public void insert(Fo_actVO fo_act_VO);
     public void update(Fo_actVO fo_act_VO);
     public void delete(String MEM_AC,String ACT_NO);
     public Fo_actVO findByPrimaryKey(String MEM_AC,String ACT_NO);
     public List<Fo_actVO> getAll();
     
}
