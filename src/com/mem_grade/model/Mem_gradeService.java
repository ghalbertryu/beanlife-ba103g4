package com.mem_grade.model;

public class Mem_gradeService {
	private Mem_gradeDAO_interface dao;
	
	public Mem_gradeService(){
		dao = new Mem_gradeDAO();
	}
	
	public Mem_gradeVO getOneMem_grade(Integer grade_no){
		return dao.findByPrimaryKey(grade_no);
	}
}
