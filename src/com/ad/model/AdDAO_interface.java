package com.ad.model;

import java.util.List;



public interface AdDAO_interface {
	 public void insert(AdVO ad_VO);
     public void update(AdVO ad_VO);
     public void delete(String AD_NO);
     public AdVO findByPrimaryKey(String AD_NO);
     public List<AdVO> getAll();
     
}
