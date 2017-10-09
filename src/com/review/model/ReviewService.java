package com.review.model;

import java.sql.Date;
import java.util.List;


public class ReviewService {
	private ReviewDAO_interface dao;

	public ReviewService() {
		dao = new ReviewDAO();
	}

	public ReviewVO addReview(String ord_no, String prod_no, Integer prod_score,
			String use_way, String rev_cont) {

		ReviewVO reviewVO = new ReviewVO();

		reviewVO.setOrd_no(ord_no);
		reviewVO.setProd_no(prod_no);
		reviewVO.setProd_score(prod_score);
		reviewVO.setUse_way(use_way);
		reviewVO.setRev_cont(rev_cont);
		reviewVO.setRev_date(new Date(System.currentTimeMillis()));
		dao.insert(reviewVO);

		return reviewVO;
	}

	public ReviewVO updateReview(String ord_no, String prod_no, Integer prod_score,
			String use_way, String rev_cont, Date rev_date) {

		ReviewVO reviewVO = new ReviewVO();

		reviewVO.setOrd_no(ord_no);
		reviewVO.setProd_no(prod_no);
		reviewVO.setProd_score(prod_score);
		reviewVO.setUse_way(use_way);
		reviewVO.setRev_cont(rev_cont);
		reviewVO.setRev_date(rev_date);
		dao.update(reviewVO);

		return reviewVO;
	}

	public void deleteReview(String rev_no) {
		dao.delete(rev_no);
	}

	public ReviewVO getOneReview(String rev_no) {
		return dao.findByPrimaryKey(rev_no);
	}
	
	public ReviewVO getByOrdProd(String ord_no, String prod_no) {
		return dao.findByOrdProd(ord_no, prod_no);
	}

	public List<ReviewVO> getAll() {
		return dao.getAll();
	}
	
	public List<ReviewVO> getVOByProd(String prod_no) {
		return dao.getByProd(prod_no);
	}
	
	public int getCountByProd(String prod_no) {
		return  dao.countByProd(prod_no);
	}
	
	public Double getScoreByProd(String prod_no) {
		return  dao.scoreByProd(prod_no);
	}
}
