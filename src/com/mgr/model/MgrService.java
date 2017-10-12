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

		mgrVO.setMgr_no(MGR_NO);
		mgrVO.setMgr_pwd(MGR_PWD);
		mgrVO.setMgr_name(MGR_NAME);
		mgrVO.setMgr_gender(MGR_GENDER);
		mgrVO.setMgr_birth(MGR_BIRTH);
		mgrVO.setMgr_email(MGR_EMAIL);
		mgrVO.setMgr_phone(MGR_PHONE);
		mgrVO.setMgr_reg_date(MGR_REG_DATE);
		dao.insert(mgrVO);

		return mgrVO;
	}

	public MgrVO updateMgr(String MGR_NO, String MGR_PWD, String MGR_NAME,
			String MGR_GENDER, Date MGR_BIRTH, String MGR_EMAIL, String MGR_PHONE, Date MGR_REG_DATE) {

		MgrVO mgrVO = new MgrVO();
		mgrVO.setMgr_no(MGR_NO);
		mgrVO.setMgr_pwd(MGR_PWD);
		mgrVO.setMgr_name(MGR_NAME);
		mgrVO.setMgr_gender(MGR_GENDER);
		mgrVO.setMgr_birth(MGR_BIRTH);
		mgrVO.setMgr_email(MGR_EMAIL);
		mgrVO.setMgr_phone(MGR_PHONE);
		mgrVO.setMgr_reg_date(MGR_REG_DATE);
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
