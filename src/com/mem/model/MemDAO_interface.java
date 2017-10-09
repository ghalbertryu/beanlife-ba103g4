package com.mem.model;

import java.util.List;


public interface MemDAO_interface {
	public void insert(MemVO memVO);
	public void update(MemVO memVO);
	public void delete(String mem_ac);
	public MemVO findByPrimaryKey(String mem_ac);
	public List<MemVO> getAll();
	
	public List<MemVO> getAllNoImg();
	public MemVO findByPrimaryKeyNoImg(String mem_ac);
	public byte[] getImageByPK(String mem_ac);
	public MemVO findPwdByPK(String mem_ac);
}
