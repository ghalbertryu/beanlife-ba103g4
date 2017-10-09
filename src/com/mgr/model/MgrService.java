package com.mgr.model;

import java.sql.Date;
import java.util.List;

public class MgrService {
	
	private MgrDAO_interface dao;

	public MgrService() {
		dao = new MgrDAO();
	}

	public MgrVO addMgr(String MGR_NO, String MGR_PWD, String MGR_NAME,
			String MGR_GENDER, Date MGR_BIRTH, String MGR_EMAIL, String MGR_PHONE, Date MGR_REG_DATE) {

		MgrVO mgrVO = new MgrVO();

		mgrVO.setMGR_NO(MGR_NO);
		mgrVO.setMGR_PWD(MGR_PWD);
		mgrVO.setMGR_NAME(MGR_NAME);
		mgrVO.setMGR_GENDER(MGR_GENDER);
		mgrVO.setMGR_BIRTH(MGR_BIRTH);
		mgrVO.setMGR_EMAIL(MGR_EMAIL);
		mgrVO.setMGR_PHONE(MGR_PHONE);
		mgrVO.setMGR_REG_DATE(MGR_REG_DATE);
		dao.insert(mgrVO);

		return mgrVO;
	}

	public MgrVO updateMgr(String MGR_NO, String MGR_PWD, String MGR_NAME,
			String MGR_GENDER, Date MGR_BIRTH, String MGR_EMAIL, String MGR_PHONE, Date MGR_REG_DATE) {

		MgrVO mgrVO = new MgrVO();
		mgrVO.setMGR_NO(MGR_NO);
		mgrVO.setMGR_PWD(MGR_PWD);
		mgrVO.setMGR_NAME(MGR_NAME);
		mgrVO.setMGR_GENDER(MGR_GENDER);
		mgrVO.setMGR_BIRTH(MGR_BIRTH);
		mgrVO.setMGR_EMAIL(MGR_EMAIL);
		mgrVO.setMGR_PHONE(MGR_PHONE);
		mgrVO.setMGR_REG_DATE(MGR_REG_DATE);
		dao.update(mgrVO);

		return mgrVO;
	}

	public void deleteEmp(String MGR_NO) {
		dao.delete(MGR_NO);
	}

	public MgrVO getOneMgr(String MGR_NO) {
		return dao.findByPrimaryKey(MGR_NO);
	}

	public List<MgrVO> getAll() {
		return dao.getAll();
	}
}
