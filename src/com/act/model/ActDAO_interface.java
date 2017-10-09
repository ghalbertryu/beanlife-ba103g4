package com.act.model;

import java.util.List;
import java.util.Map;
import java.util.Set;


import com.act_comm.model.Act_commVO;
import com.act_pair.model.Act_pairVO;
import com.convert_gift.model.Convert_giftVO;
import com.fo_act.model.Fo_actVO;


public interface ActDAO_interface {
	 public void insert(ActVO act_VO);
     public void update(ActVO act_VO);
     public void delete(String ACT_NO);
     public ActVO findByPrimaryKey(String ACT_NO);
     public List<ActVO> getAll();
     
     public Set<Act_commVO> getAct_commByAct_no(String ACT_NO);
     public Set<Act_pairVO> getAct_pairByAct_no(String ACT_NO);
     public Set<Fo_actVO> getFo_actByAct_no(String ACT_NO);
     //複合查詢使用
     public List<ActVO> getAll(Map<String, String[]> map); 
     
   //排序使用
     public List<ActVO> getSort(String sort); 
     
     //參與人數增加時加1使用
     //增減人數使用(20170930修改)
     public void update_mem_count(String ACT_NO,Integer number);
     
     
}
