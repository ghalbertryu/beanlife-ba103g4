package com.ad.model;

import java.sql.Date;
import java.util.List;

public class AdService {
	private AdDAO_interface dao;
	public AdService(){
		dao=new AdJNDIDAO();
		
	}
	
	
	public AdVO addAd(String prod_no,String ad_title,byte[] ad_img,Date ad_op_date,Date ad_ed_date){
		AdVO ad_VO=new AdVO();
	
		ad_VO.setProd_no(prod_no);
		ad_VO.setAd_title(ad_title);
		ad_VO.setAd_img(ad_img);
		ad_VO.setAd_op_date(ad_op_date);
		ad_VO.setAd_ed_date(ad_ed_date);
		dao.insert(ad_VO);
		return ad_VO;
	}
	public AdVO updateAd(String ad_no,String prod_no,String ad_title,byte[] ad_img,Date ad_op_date,Date ad_ed_date){
		AdVO ad_VO=new AdVO();
		ad_VO.setAd_no(ad_no);
		ad_VO.setProd_no(prod_no);
		ad_VO.setAd_title(ad_title);
		ad_VO.setAd_img(ad_img);
		ad_VO.setAd_op_date(ad_op_date);
		ad_VO.setAd_ed_date(ad_ed_date);
		dao.update(ad_VO);
		return ad_VO;
		
	}
	public void deleteAd(String ad_no){
		dao.delete(ad_no);
	}
	
	public AdVO getOneAd(String ad_no){
		return dao.findByPrimaryKey(ad_no);
	}
	
	public List<AdVO>getAll(){
		return dao.getAll();
	}
	
}
