package com.gift_data.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.convert_gift.model.Convert_giftVO;

public class Gift_dataService {
 private Gift_dataDAO_interface dao;
 
 public Gift_dataService(){
	 dao=new Gift_dataJNDIDAO();
 }
 public Gift_dataVO addGift_data(String gift_name,int gift_remain,String gift_cont,byte[] gift_img,int gift_pt,Date gift_launch_date){
	 Gift_dataVO gift_data_vo=new Gift_dataVO();
//	 gift_data_vo.setGIFT_NO(gift_no);
	 
	 gift_data_vo.setGift_name(gift_name);
	 gift_data_vo.setGift_remain(gift_remain);
	 gift_data_vo.setGift_cont(gift_cont);
	 gift_data_vo.setGift_img(gift_img);
	 gift_data_vo.setGift_pt(gift_pt);
	 gift_data_vo.setGift_launch_date(gift_launch_date);
	 dao.insert(gift_data_vo);
	 return gift_data_vo;
	 
 }
 public Gift_dataVO updateGift_data(String gift_no,String gift_name,int gift_remain,String gift_cont,byte[] gift_img,int gift_pt,Date gift_launch_date){
	 Gift_dataVO gift_data_vo=new Gift_dataVO();
	 gift_data_vo.setGift_no(gift_no);
	 gift_data_vo.setGift_name(gift_name);
	 gift_data_vo.setGift_remain(gift_remain);
	 gift_data_vo.setGift_cont(gift_cont);
	 gift_data_vo.setGift_img(gift_img);
	 gift_data_vo.setGift_pt(gift_pt);
	 gift_data_vo.setGift_launch_date(gift_launch_date);
	 dao.update(gift_data_vo);
	 return gift_data_vo;
	 
 }
 public void deleteGift_data(String gift_no){
	 dao.delete(gift_no);
 }
 public Gift_dataVO getOneGift_data(String gift_no){
	 return dao.findByPrimaryKey(gift_no);
 }
 public List<Gift_dataVO>getAll(){
	 return dao.getAll();
 }
 
 public Set<Convert_giftVO> getConvert_giftByGift_no(String GIFT_NO) {
	 return dao.getConvert_giftByGift_no(GIFT_NO);
 }
 
 
 
}
