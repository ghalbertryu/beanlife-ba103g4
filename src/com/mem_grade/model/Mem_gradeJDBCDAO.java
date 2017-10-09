package com.mem_grade.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.fo_store.model.Fo_storeJDBCDAO;
import com.fo_store.model.Fo_storeVO;

public class Mem_gradeJDBCDAO implements Mem_gradeDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	
	private static final String GET_ONE_STMT = 
			"SELECT * from mem_grade where grade_no = ? ";
	

	@Override
	public Mem_gradeVO findByPrimaryKey(Integer grade_no) {
		Mem_gradeVO mem_gradeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, grade_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				mem_gradeVO = new Mem_gradeVO();
				mem_gradeVO.setGrade_no(rs.getInt("grade_no"));
				mem_gradeVO.setGrade_title(rs.getString("grade_title"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return mem_gradeVO;
	}

	
	public static void main(String[] args) {

		Mem_gradeJDBCDAO dao = new Mem_gradeJDBCDAO();


		// 查詢
		Mem_gradeVO mem_gradeVO = dao.findByPrimaryKey(2);
		System.out.print(mem_gradeVO.getGrade_no()+ ",");
		System.out.print(mem_gradeVO.getGrade_title() + ",");

		System.out.println("---------------------");
		

	}
}
