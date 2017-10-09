package com.gift_data.model;
import java.io.Serializable;
import java.sql.Date;

public class Gift_dataVO implements Serializable{
	private String gift_no;
	private String gift_name;
	private Integer gift_remain;
	private String gift_cont;
    private	byte[] gift_img;
    private	Integer gift_pt;
	private Date gift_launch_date;
	public Gift_dataVO() {
		super();
	}
	
	
	public String getGift_no() {
		return gift_no;
	}
	public void setGift_no(String gIFT_NO) {
		gift_no = gIFT_NO;
	}
	public String getGift_name() {
		return gift_name;
	}
	public void setGift_name(String gIFT_NAME) {
		gift_name = gIFT_NAME;
	}
	public Integer getGift_remain() {
		return gift_remain;
	}
	public void setGift_remain(Integer gIFT_REMAIN) {
		gift_remain = gIFT_REMAIN;
	}
	public String getGift_cont() {
		return gift_cont;
	}
	public void setGift_cont(String gIFT_CONT) {
		gift_cont = gIFT_CONT;
	}
	public byte[] getGift_img() {
		return gift_img;
	}
	public void setGift_img(byte[] gIFT_IMG) {
		gift_img = gIFT_IMG;
	}
	
	
	
	
	public Integer getGift_pt() {
		return gift_pt;
	}
	public void setGift_pt(Integer gIFT_PT) {
		gift_pt = gIFT_PT;
	}
	public Date getGift_launch_date() {
		return gift_launch_date;
	}
	public void setGift_launch_date(Date gIFT_LAUNCH_DATE) {
		gift_launch_date = gIFT_LAUNCH_DATE;
	}
	
	
	
	
	
}
