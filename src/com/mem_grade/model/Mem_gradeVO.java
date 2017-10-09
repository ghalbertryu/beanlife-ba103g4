package com.mem_grade.model;

import java.io.Serializable;

public class Mem_gradeVO implements Serializable{
	private Integer grade_no;
	private String grade_title;
	
	public Integer getGrade_no() {
		return grade_no;
	}
	public void setGrade_no(Integer grade_no) {
		this.grade_no = grade_no;
	}
	public String getGrade_title() {
		return grade_title;
	}
	public void setGrade_title(String grade_title) {
		this.grade_title = grade_title;
	}

}
