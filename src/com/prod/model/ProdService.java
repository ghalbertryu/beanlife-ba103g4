package com.prod.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ord_list.model.Ord_listVO;

public class ProdService {

	private ProdDAO_interface dao;

	public ProdService() {
		dao = new ProdDAO();
	}

	public ProdVO addProd(String store_no, String prod_name, String bean_type, String bean_grade, String bean_contry,
			String bean_region, String bean_farm, String bean_farmer, Integer bean_el, String proc, String roast,
			Integer bean_attr_acid, Integer bean_attr_aroma, Integer bean_attr_body, Integer bean_attr_after,
			Integer bean_attr_bal, String bean_aroma, Integer prod_price, Double prod_wt, Integer send_fee,
			Integer prod_sup, String prod_cont, byte[] prod_pic1, byte[] prod_pic2, byte[] prod_pic3,
			String prod_stat) {

		ProdVO prodVO = new ProdVO();

		prodVO.setStore_no(store_no);
		prodVO.setProd_name(prod_name);
		prodVO.setBean_type(bean_type);
		prodVO.setBean_grade(bean_grade);
		prodVO.setBean_contry(bean_contry);
		prodVO.setBean_region(bean_region);
		prodVO.setBean_farm(bean_farm);
		prodVO.setBean_farmer(bean_farmer);
		prodVO.setBean_el(bean_el);
		prodVO.setProc(proc);
		prodVO.setRoast(roast);
		prodVO.setBean_attr_acid(bean_attr_acid);
		prodVO.setBean_attr_aroma(bean_attr_aroma);
		prodVO.setBean_attr_body(bean_attr_body);
		prodVO.setBean_attr_after(bean_attr_after);
		prodVO.setBean_attr_bal(bean_attr_bal);
		prodVO.setBean_aroma(bean_aroma);
		prodVO.setProd_price(prod_price);
		prodVO.setProd_wt(prod_wt);
		prodVO.setSend_fee(send_fee);
		prodVO.setProd_sup(prod_sup);
		prodVO.setProd_cont(prod_cont);
		prodVO.setProd_pic1(prod_pic1);
		prodVO.setProd_pic2(prod_pic2);
		prodVO.setProd_pic3(prod_pic3);
		prodVO.setProd_stat(prod_stat);
		prodVO.setEd_time(Date.valueOf(java.time.LocalDate.now()));
		dao.insert(prodVO);

		return prodVO;

	}

	public void addProd(ProdVO prodVO) {
		dao.insert(prodVO);
	}

	public ProdVO updateProd(String prod_no, String store_no, String prod_name, String bean_type, String bean_grade,
			String bean_contry, String bean_region, String bean_farm, String bean_farmer, Integer bean_el, String proc,
			String roast, Integer bean_attr_acid, Integer bean_attr_aroma, Integer bean_attr_body,
			Integer bean_attr_after, Integer bean_attr_bal, String bean_aroma, Integer prod_price, Double prod_wt,
			Integer send_fee, Integer prod_sup, String prod_cont, byte[] prod_pic1, byte[] prod_pic2, byte[] prod_pic3,
			String prod_stat, java.sql.Date ed_time) {

		ProdVO prodVO = new ProdVO();

		prodVO.setProd_no(prod_no);
		prodVO.setStore_no(store_no);
		prodVO.setProd_name(prod_name);
		prodVO.setBean_type(bean_type);
		prodVO.setBean_grade(bean_grade);
		prodVO.setBean_contry(bean_contry);
		prodVO.setBean_region(bean_region);
		prodVO.setBean_farm(bean_farm);
		prodVO.setBean_farmer(bean_farmer);
		prodVO.setBean_el(bean_el);
		prodVO.setProc(proc);
		prodVO.setRoast(roast);
		prodVO.setBean_attr_acid(bean_attr_acid);
		prodVO.setBean_attr_aroma(bean_attr_aroma);
		prodVO.setBean_attr_body(bean_attr_body);
		prodVO.setBean_attr_after(bean_attr_after);
		prodVO.setBean_attr_bal(bean_attr_bal);
		prodVO.setBean_aroma(bean_aroma);
		prodVO.setProd_price(prod_price);
		prodVO.setProd_wt(prod_wt);
		prodVO.setSend_fee(send_fee);
		prodVO.setProd_sup(prod_sup);
		prodVO.setProd_cont(prod_cont);
		prodVO.setProd_pic1(prod_pic1);
		prodVO.setProd_pic2(prod_pic2);
		prodVO.setProd_pic3(prod_pic3);
		prodVO.setProd_stat(prod_stat);
		prodVO.setEd_time(ed_time);

		dao.update(prodVO);

		return prodVO;
	}

	public void updateProd(ProdVO prodVO) {
		dao.update(prodVO);
	}

	// 改上下架
	public ProdVO updateProdstat(String prod_no, String prod_stat) {
		ProdVO prodVO = dao.findByPrimaryKey(prod_no);
		prodVO.setProd_stat(prod_stat);
		prodVO.setEd_time(new Date(System.currentTimeMillis()));
		dao.update(prodVO);
		return prodVO;

	}

	public ProdVO updateProdbysto(String prod_no, String store_no, String prod_name, String bean_type,
			String bean_grade, String bean_contry, String bean_region, String bean_farm, String bean_farmer,
			Integer bean_el, String proc, String roast, Integer bean_attr_acid, Integer bean_attr_aroma,
			Integer bean_attr_body, Integer bean_attr_after, Integer bean_attr_bal, String bean_aroma,
			Integer prod_price, Double prod_wt, Integer send_fee, Integer prod_sup, String prod_cont, byte[] prod_pic1,
			byte[] prod_pic2, byte[] prod_pic3, String prod_stat) {

		ProdVO prodVO = new ProdVO();
		prodVO.setStore_no(store_no);
		prodVO.setProd_no(prod_no);
		prodVO.setProd_name(prod_name);
		prodVO.setBean_type(bean_type);
		prodVO.setBean_grade(bean_grade);
		prodVO.setBean_contry(bean_contry);
		prodVO.setBean_region(bean_region);
		prodVO.setBean_farm(bean_farm);
		prodVO.setBean_farmer(bean_farmer);
		prodVO.setBean_el(bean_el);
		prodVO.setProc(proc);
		prodVO.setRoast(roast);
		prodVO.setBean_attr_acid(bean_attr_acid);
		prodVO.setBean_attr_aroma(bean_attr_aroma);
		prodVO.setBean_attr_body(bean_attr_body);
		prodVO.setBean_attr_after(bean_attr_after);
		prodVO.setBean_attr_bal(bean_attr_bal);
		prodVO.setBean_aroma(bean_aroma);
		prodVO.setProd_price(prod_price);
		prodVO.setProd_wt(prod_wt);
		prodVO.setSend_fee(send_fee);
		prodVO.setProd_sup(prod_sup);
		prodVO.setProd_cont(prod_cont);
		prodVO.setProd_pic1(prod_pic1);
		prodVO.setProd_pic2(prod_pic2);
		prodVO.setProd_pic3(prod_pic3);
		prodVO.setProd_stat(prod_stat);
		prodVO.setEd_time(Date.valueOf(java.time.LocalDate.now()));

		dao.update(prodVO);

		return prodVO;
	}

	public void deleteProd(String prod_no) {
		dao.delete(prod_no);
	}

	public ProdVO getOneProd(String prod_no) {
		return dao.findByPrimaryKey(prod_no);
	}

	public ProdVO getOneProdR(String prod_no) {
		ProdVO prodVO = dao.findByPrimaryKey(prod_no);
		prodVO = transToCht(prodVO);
		return prodVO;
	}

	public List<ProdVO> getAll() {
		return dao.getAll();
	}

	public List<ProdVO> getAllR() {
		List<ProdVO> prodVOs = dao.getAll();
		List<ProdVO> prodVOs2 = new ArrayList<ProdVO>();
		for (ProdVO prodVO : prodVOs) {
			prodVO = transToCht(prodVO);
			prodVOs2.add(prodVO);
		}
		return prodVOs2;
	}

	public List<ProdVO> getAll(Map<String, String[]> map, Map<String, String[]> map2) {
		return dao.getAll(map, map2);
	}

	public List<ProdVO> getAllR(Map<String, String[]> map, Map<String, String[]> map2) {
		List<ProdVO> prodVOs = dao.getAll(map, map2);
		List<ProdVO> prodVOs2 = new ArrayList<ProdVO>();
		for (ProdVO prodVO : prodVOs) {
			prodVO = transToCht(prodVO);
			prodVOs2.add(prodVO);
		}
		return prodVOs2;
	}

	public List<ProdVO> getAllNoImg() {
		return dao.getAllNoImg();

	}

	public List<byte[]> getImageByPK(String prod_no) {
		return dao.getImageByPK(prod_no);
	}

	public List<ProdVO> getQueryResult(String bean_contry, String proc, String roast, String prod_name) {
		return dao.getQueryResult(bean_contry, proc, roast, prod_name);
	}

	public ProdVO getOneProdNoImg(String prod_no) {
		return dao.findByPrimaryKeyNoImg(prod_no);
	}

	public Set<Ord_listVO> getOrd_listByProd(String prod_no) {
		return dao.getOrd_listByProd(prod_no);
	}

	private ProdVO transToCht(ProdVO prodVO) {
		if (prodVO.getRoast().equals("0")) {
			prodVO.setRoast("極淺焙");
		} else if (prodVO.getRoast().equals("1")) {
			prodVO.setRoast("淺焙");
		} else if (prodVO.getRoast().equals("2")) {
			prodVO.setRoast("中焙");
		} else if (prodVO.getRoast().equals("3")) {
			prodVO.setRoast("中深焙");
		} else if (prodVO.getRoast().equals("4")) {
			prodVO.setRoast("城市烘焙");
		} else if (prodVO.getRoast().equals("5")) {
			prodVO.setRoast("深焙");
		} else if (prodVO.getRoast().equals("6")) {
			prodVO.setRoast("法式烘焙");
		} else if (prodVO.getRoast().equals("7")) {
			prodVO.setRoast("重焙");
		}
		return prodVO;
	}

}
