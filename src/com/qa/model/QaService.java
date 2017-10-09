package com.qa.model;

import java.sql.Date;
import java.util.List;



public class QaService {
	
	private QaDAO_interface dao;
	public QaService() {
		dao = new QaDAO();
	}
	
	public List<QaVO> getVOByProd(String prod_no) {
		return dao.getByProd(prod_no);
	}
	

	public QaVO addQa(String prod_no, String mem_ac,String qa_cont) {
		QaVO qaVO = new QaVO();
		qaVO.setProd_no(prod_no);
		qaVO.setMem_ac(mem_ac);
		qaVO.setQa_cont(qa_cont);
		qaVO.setQa_date(new Date(System.currentTimeMillis()));
		dao.insert(qaVO);
		return qaVO;
	}
	
	public QaVO replyQa(String qa_no, String qa_reply_cont) {
		QaVO qaVO = dao.findByPrimaryKey(qa_no);
		qaVO.setQa_reply_cont(qa_reply_cont);
		qaVO.setQa_reply_date(new Date(System.currentTimeMillis()));
		dao.update(qaVO);
		return qaVO;
	}

}
