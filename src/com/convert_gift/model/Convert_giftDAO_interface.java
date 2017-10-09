package com.convert_gift.model;

import java.util.List;
import java.util.Map;





public interface Convert_giftDAO_interface {
	 public void insert(Convert_giftVO convert_gift_VO);
     public void update(Convert_giftVO convert_gift_VO);
     public void delete(String APPLY_NO);
     public Convert_giftVO findByPrimaryKey(String APPLY_NO);
     public void updateStatus(String apply_no,String apply_stat,String send_no);
     public List<Convert_giftVO> getAll();
     //複合查詢使用
     public List<Convert_giftVO> getAll(Map<String, String[]> map); 
}
