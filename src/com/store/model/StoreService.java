package com.store.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.prod.model.*;

public class StoreService {
	
	private StoreDAO_interface dao;
	
	public StoreService(){
		dao =new StoreDAO();
	}
	  
	public StoreVO addStore( String mem_ac, String tax_id_no,
	  byte[] win_id_pic, String store_phone, String store_add, String store_add_lat,
	  String store_add_lon, String store_name, String store_cont,  byte[] store_pic1,
	  byte[] store_pic2, byte[] store_pic3, Integer store_free_ship,String store_atm_info){
		StoreVO storeVO = new StoreVO();
		storeVO.setMem_ac(mem_ac);
		storeVO.setTax_id_no(tax_id_no);
		storeVO.setWin_id_pic(win_id_pic);
		storeVO.setStore_phone(store_phone);
		storeVO.setStore_add(store_add);
		storeVO.setStore_add_lat(store_add_lat);
		storeVO.setStore_add_lon(store_add_lon);
		storeVO.setStore_name(store_name);
		storeVO.setStore_cont(store_cont);
		storeVO.setStore_pic1(store_pic1);
		storeVO.setStore_pic2(store_pic2);
		storeVO.setStore_pic3(store_pic3);
		storeVO.setStore_free_ship(store_free_ship);
		storeVO.setStore_atm_info(store_atm_info);
		dao.insert(storeVO);
		return storeVO;
	}
	
	public void addStore(StoreVO storeVO){
		dao.insert(storeVO);
	}
	//小寫方法
	public void addstore(StoreVO storeVO){
		addStore(storeVO);
	}
	
	public StoreVO updateStore(String tax_id_no, byte[] win_id_pic,String store_phone,String store_add,String store_add_lat 
			,String store_add_lon,String store_name,String store_cont,byte[] store_pic1,byte[] store_pic2 ,byte[] store_pic3,Integer store_free_ship,String store_atm_info,String store_stat,Date store_stat_cdate,String store_no){
		StoreVO storevo = new StoreVO();
		storevo.setStore_no(store_no);
		storevo.setStore_phone(store_phone);
		storevo.setStore_add(store_add);
		storevo.setStore_name(store_name);
		storevo.setStore_cont(store_cont);
		storevo.setStore_free_ship(store_free_ship);
		storevo.setStore_stat(store_stat);
		storevo.setTax_id_no(tax_id_no);
		storevo.setWin_id_pic(win_id_pic);
		storevo.setStore_add_lat(store_add_lat);
		storevo.setStore_add_lon(store_add_lon);
		storevo.setStore_pic1(store_pic1);
		storevo.setStore_pic2(store_pic2);
		storevo.setStore_pic3(store_pic3);
		storevo.setStore_stat_cdate(store_stat_cdate);
		dao.update(storevo);
		
		return dao.findByPrimaryKey(store_no);
	}
	//小寫錯字方法
	public StoreVO updatesotre(String tax_id_no, byte[] win_id_pic,String store_phone,String store_add,String store_add_lat 
			,String store_add_lon,String store_name,String store_cont,byte[] store_pic1,byte[] store_pic2 ,byte[] store_pic3,Integer store_free_ship,String store_stat,String store_stat_cont,Date store_stat_cdate,String store_no){
		return updatesotre( tax_id_no,  win_id_pic, store_phone, store_add, store_add_lat 
				, store_add_lon, store_name, store_cont, store_pic1, store_pic2 , store_pic3, store_free_ship, store_stat, store_stat_cont, store_stat_cdate, store_no);

	}
	
	public void updateStore(StoreVO storeVO){
		dao.update(storeVO);
	}
	//小寫方法
	public void updatestore(StoreVO storeVO){
		updateStore(storeVO);
	}
	public StoreVO updateStat(String store_stat,Date store_stat_cdate,String store_no,String store_stat_cont){
		StoreVO storevo =new StoreVO();
		storevo.setStore_stat(store_stat);
		storevo.setStore_stat_cdate(store_stat_cdate);
		storevo.setStore_no(store_no);
		storevo.setStore_stat_cont(store_stat_cont);
		dao.update_stat(storevo);
		return dao.findByPrimaryKey(store_no);
	}
	//小寫方法
	public StoreVO update_stat(String store_stat,Date store_stat_cdate,String store_no,String store_stat_cont){
		return updateStat( store_stat, store_stat_cdate, store_no, store_stat_cont);
	}
	
	//通過審核店家修改資料
		public StoreVO update_bypass(String store_no ,String store_name,Integer store_free_ship,String store_phone,String store_add,String store_add_lat 
				,String store_add_lon,String store_cont ,byte[] store_pic1,byte[] store_pic2,byte[] store_pic3){
			StoreVO storevo = dao.findByPrimaryKey(store_no);
			storevo.setStore_name(store_name);
			storevo.setStore_free_ship(store_free_ship);
			storevo.setStore_phone(store_phone);
			storevo.setStore_add(store_add);
			storevo.setStore_add_lat(store_add_lat);
			storevo.setStore_add_lon(store_add_lon);
			storevo.setStore_cont(store_cont);
			storevo.setStore_pic1(store_pic1);
			storevo.setStore_pic2(store_pic2);
			storevo.setStore_pic3(store_pic3);
			dao.update(storevo);
			return storevo;
		}
		//未通過審核店家修改資料
		public StoreVO update_bynotpass(String store_no ,String store_name,Integer store_free_ship,String store_phone,String store_add,String store_add_lat 
				,String store_add_lon,String store_cont ,byte[] store_pic1,byte[] store_pic2,byte[] store_pic3){
			StoreVO storevo = dao.findByPrimaryKey(store_no);
			storevo.setStore_name(store_name);
			storevo.setStore_free_ship(store_free_ship);
			storevo.setStore_phone(store_phone);
			storevo.setStore_add(store_add);
			storevo.setStore_add_lat(store_add_lat);
			storevo.setStore_add_lon(store_add_lon);
			storevo.setStore_cont(store_cont);
			storevo.setStore_pic1(store_pic1);
			storevo.setStore_pic2(store_pic2);
			storevo.setStore_pic3(store_pic3);
			storevo.setStore_stat("待審中");
			dao.update(storevo);
				return storevo;	
		}
	
	public void deleteStore(String store_no){
		dao.delete(store_no);
	}
	//小寫方法
	public void deletestore(String store_no){
		deleteStore(store_no);
	}
	
	public List<StoreVO> getAll(){
		return dao.getAll();
	}
	
	public StoreVO getOneStore(String store_no){
		return dao.findByPrimaryKey(store_no);
	}
	//小寫方法
	public StoreVO getonestore(String store_no){
		return getOneStore(store_no);
	}
	
	public StoreVO getOneByMem(String mem_ac){
		return dao.findByMem(mem_ac);
	}
	
	public List<StoreVO> getVOsByStat(String store_stat){
		return dao.getAll_stat(store_stat);
	}
	//小寫方法
	public List<StoreVO> getstatstr(String store_stat){
		return getVOsByStat(store_stat);
	}
	
	public Set<ProdVO> getProdsByStore(String store_no){
		return dao.getProdsByStore_no(store_no);
	}
	//小寫方法
	public Set<ProdVO> getProdsByStore_no(String store_no){
		return getProdsByStore(store_no);
	}

	
}
