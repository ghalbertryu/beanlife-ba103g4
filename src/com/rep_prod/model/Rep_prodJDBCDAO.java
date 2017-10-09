package com.rep_prod.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Rep_prodJDBCDAO implements Rep_prodDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";

	private static final String INSERT_STMT = 
		"INSERT INTO REP_PROD (prod_no, mem_ac, rep_type, rep_cont, rep_date, rep_stat, rep_stat_cont, stat_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT prod_no, mem_ac, rep_type, rep_cont, to_char(rep_date,'yyyy-mm-dd') rep_date, rep_stat, rep_stat_cont, to_char(stat_date,'yyyy-mm-dd') stat_date FROM REP_PROD order by rep_date";
	private static final String GET_ONE_STMT = 
		"SELECT prod_no, mem_ac, rep_type, rep_cont, to_char(rep_date,'yyyy-mm-dd') rep_date, rep_stat, rep_stat_cont, to_char(stat_date,'yyyy-mm-dd') stat_date FROM REP_PROD where prod_no = ? AND mem_ac = ?";
	private static final String DELETE = 
		"DELETE FROM REP_PROD where prod_no = ? AND mem_ac = ?";
	private static final String UPDATE = 
		"UPDATE REP_PROD set rep_type= ?, rep_cont=?, rep_date=?, rep_stat=?, rep_stat_cont=?, stat_date=? where prod_no = ? AND mem_ac = ?";
	
	
	@Override
	public void insert(Rep_prodVO rep_prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, rep_prodVO.getProd_no());
			pstmt.setString(2, rep_prodVO.getMem_ac());
			pstmt.setString(3, rep_prodVO.getRep_type());
			pstmt.setString(4, rep_prodVO.getRep_cont());
			pstmt.setDate(5, rep_prodVO.getRep_date());
			pstmt.setString(6, rep_prodVO.getRep_stat());
			pstmt.setString(7, rep_prodVO.getRep_stat_cont());
			pstmt.setDate(8, rep_prodVO.getStat_date());

			pstmt.executeUpdate();
				

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
		
	}

	@Override
	public void update(Rep_prodVO rep_prodVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(7, rep_prodVO.getProd_no());
			pstmt.setString(8, rep_prodVO.getMem_ac());
			pstmt.setString(1, rep_prodVO.getRep_type());
			pstmt.setString(2, rep_prodVO.getRep_cont());
			pstmt.setDate(3, rep_prodVO.getRep_date());
			pstmt.setString(4, rep_prodVO.getRep_stat());
			pstmt.setString(5, rep_prodVO.getRep_stat_cont());
			pstmt.setDate(6, rep_prodVO.getStat_date());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
		
	}

	@Override
	public void delete(String prod_no, String mem_ac) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, prod_no);
			pstmt.setString(2, mem_ac);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
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
		
	}

	@Override
	public Rep_prodVO findByPrimaryKey(String prod_no, String mem_ac) {
		Rep_prodVO rep_prodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, prod_no);
			pstmt.setString(2, mem_ac);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				rep_prodVO = new Rep_prodVO();
				rep_prodVO.setProd_no(rs.getString("prod_no"));
				rep_prodVO.setMem_ac(rs.getString("mem_ac"));
				rep_prodVO.setRep_type(rs.getString("rep_type"));
				rep_prodVO.setRep_cont(rs.getString("rep_cont"));
				rep_prodVO.setRep_date(rs.getDate("rep_date"));
				rep_prodVO.setRep_stat(rs.getString("rep_stat"));
				rep_prodVO.setRep_stat_cont(rs.getString("rep_stat_cont"));
				rep_prodVO.setStat_date(rs.getDate("stat_date"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return rep_prodVO;
	}

	@Override
	public List<Rep_prodVO> getAll() {
		List<Rep_prodVO> list = new ArrayList<Rep_prodVO>();
		Rep_prodVO rep_prodVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				rep_prodVO = new Rep_prodVO();
				rep_prodVO.setProd_no(rs.getString("prod_no"));
				rep_prodVO.setMem_ac(rs.getString("mem_ac"));
				rep_prodVO.setRep_type(rs.getString("rep_type"));
				rep_prodVO.setRep_cont(rs.getString("rep_cont"));
				rep_prodVO.setRep_date(rs.getDate("rep_date"));
				rep_prodVO.setRep_stat(rs.getString("rep_stat"));
				rep_prodVO.setRep_stat_cont(rs.getString("rep_stat_cont"));
				rep_prodVO.setStat_date(rs.getDate("stat_date"));
				list.add(rep_prodVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return list;
	}
	
	public static void main(String[] args) {

		Rep_prodJDBCDAO dao = new Rep_prodJDBCDAO();

		// 新增
		Rep_prodVO rep_prodVO = new Rep_prodVO();
		rep_prodVO.setProd_no("P1000000001");
		rep_prodVO.setMem_ac("amy39");
		rep_prodVO.setRep_type("大量無意義內容");
		rep_prodVO.setRep_cont("我來亂的");
		rep_prodVO.setRep_date(Date.valueOf("2005-01-01"));
		rep_prodVO.setRep_stat("已處理");
		rep_prodVO.setRep_stat_cont("亂檢舉");
		rep_prodVO.setStat_date(Date.valueOf("2017-01-01"));
		dao.insert(rep_prodVO);
		

		// 修改
		Rep_prodVO rep_prodVO2 = new Rep_prodVO();
		rep_prodVO2.setProd_no("P1000000001");
		rep_prodVO2.setMem_ac("amy39");
		rep_prodVO2.setRep_type("大量無意義內容");
		rep_prodVO2.setRep_cont("我來亂的fsdf");
		rep_prodVO2.setRep_date(Date.valueOf("2005-01-01"));
		rep_prodVO2.setRep_stat("已處理");
		rep_prodVO2.setRep_stat_cont("亂檢舉fdsfaf");
		rep_prodVO2.setStat_date(Date.valueOf(java.time.LocalDate.now()));
	
		dao.update(rep_prodVO2);



		// 查詢
		Rep_prodVO rep_prodVO3 = dao.findByPrimaryKey("P1000000001","amy39");
		System.out.print(rep_prodVO3.getProd_no() + ",");
		System.out.print(rep_prodVO3.getMem_ac() + ",");
		System.out.print(rep_prodVO3.getRep_type() + ",");
		System.out.print(rep_prodVO3.getRep_cont() + ",");
		System.out.print(rep_prodVO3.getRep_date() + ",");
		System.out.print(rep_prodVO3.getRep_stat() + ",");
		System.out.print(rep_prodVO3.getRep_stat_cont() + ",");
		System.out.println(rep_prodVO3.getStat_date());
		System.out.println("---------------------");
		
		// 刪除
		dao.delete("P1000000001","amy39");

		// 查詢
		List<Rep_prodVO> list = dao.getAll();
		for (Rep_prodVO arep_prodVO : list) {
			System.out.print(arep_prodVO.getProd_no() + ",");
			System.out.print(arep_prodVO.getMem_ac() + ",");
			System.out.print(arep_prodVO.getRep_type() + ",");
			System.out.print(arep_prodVO.getRep_cont() + ",");
			System.out.print(arep_prodVO.getRep_date() + ",");
			System.out.print(arep_prodVO.getRep_stat() + ",");
			System.out.print(arep_prodVO.getRep_stat_cont() + ",");
			System.out.println(arep_prodVO.getStat_date());
			System.out.println();
		}
	}

}
