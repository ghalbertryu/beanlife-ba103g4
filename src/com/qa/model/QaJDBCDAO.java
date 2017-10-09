package com.qa.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QaJDBCDAO implements QaDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	
	static String key;

	private static final String INSERT_STMT = 
		"INSERT INTO QA (qa_no, prod_no, mem_ac, qa_cont, qa_date, qa_reply_cont, qa_reply_date) VALUES ('Q'||QA_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT qa_no, prod_no, mem_ac, qa_cont, to_char(qa_date,'yyyy-mm-dd') qa_date, qa_reply_cont, to_char(qa_reply_date,'yyyy-mm-dd') qa_reply_date FROM QA order by qa_no";
	private static final String GET_ONE_STMT = 
		"SELECT qa_no, prod_no, mem_ac, qa_cont, to_char(qa_date,'yyyy-mm-dd') qa_date, qa_reply_cont, to_char(qa_reply_date,'yyyy-mm-dd') qa_reply_date FROM QA where qa_no = ?";
	private static final String DELETE = 
		"DELETE FROM QA where qa_no = ?";
	private static final String UPDATE = 
		"UPDATE QA set prod_no=?, mem_ac=?, qa_cont=?, qa_date=?, qa_reply_cont=?, qa_reply_date=? where qa_no =?";
	private static final String GET_VO_BY_PROD = 
			"SELECT * FROM QA where prod_no = ? order by qa_no desc";
		
	
	@Override
	public void insert(QaVO qaVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String pk[] = {"qa_no"};

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT, pk);

			pstmt.setString(1, qaVO.getProd_no());
			pstmt.setString(2, qaVO.getMem_ac());
			pstmt.setString(3, qaVO.getQa_cont());
			pstmt.setDate(4, qaVO.getQa_date());
			pstmt.setString(5, qaVO.getQa_reply_cont());
			pstmt.setDate(6, qaVO.getQa_reply_date());

			pstmt.executeUpdate();
			
			
			//掘取對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (rs.next()) {
				do {
					for (int i = 1; i <= columnCount; i++) {
						key = rs.getString(i);
						System.out.println("自增主鍵值(" + i + ") = " + key);
					}
				} while (rs.next());
			} else {
				System.out.println("NO KEYS WERE GENERATED.");
			}
			rs.close();

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
	public void update(QaVO qaVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(7, qaVO.getQa_no());
			pstmt.setString(1, qaVO.getProd_no());
			pstmt.setString(2, qaVO.getMem_ac());
			pstmt.setString(3, qaVO.getQa_cont());
			pstmt.setDate(4, qaVO.getQa_date());
			pstmt.setString(5, qaVO.getQa_reply_cont());
			pstmt.setDate(6, qaVO.getQa_reply_date());

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
	public void delete(String qa_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, qa_no);

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
	public QaVO findByPrimaryKey(String qa_no) {
		QaVO qaVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, qa_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				qaVO = new QaVO();
				qaVO.setQa_no(rs.getString("qa_no"));
				qaVO.setProd_no(rs.getString("prod_no"));
				qaVO.setMem_ac(rs.getString("mem_ac"));
				qaVO.setQa_cont(rs.getString("qa_cont"));
				qaVO.setQa_date(rs.getDate("qa_date"));
				qaVO.setQa_reply_cont(rs.getString("qa_reply_cont"));
				qaVO.setQa_reply_date(rs.getDate("qa_reply_date"));
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
		return qaVO;
	}

	@Override
	public List<QaVO> getAll() {
		List<QaVO> list = new ArrayList<QaVO>();
		QaVO qaVO = null;

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
				qaVO = new QaVO();
				qaVO.setQa_no(rs.getString("qa_no"));
				qaVO.setProd_no(rs.getString("prod_no"));
				qaVO.setMem_ac(rs.getString("mem_ac"));
				qaVO.setQa_cont(rs.getString("qa_cont"));
				qaVO.setQa_date(rs.getDate("qa_date"));
				qaVO.setQa_reply_cont(rs.getString("qa_reply_cont"));
				qaVO.setQa_reply_date(rs.getDate("qa_reply_date"));
				list.add(qaVO); // Store the row in the list
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
	
	
	@Override
	public List<QaVO> getByProd(String prod_no) {
		List<QaVO> list = new ArrayList<QaVO>();
		QaVO qaVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_VO_BY_PROD);
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				qaVO = new QaVO();
				qaVO.setQa_no(rs.getString("qa_no"));
				qaVO.setProd_no(rs.getString("prod_no"));
				qaVO.setMem_ac(rs.getString("mem_ac"));
				qaVO.setQa_cont(rs.getString("qa_cont"));
				qaVO.setQa_date(rs.getDate("qa_date"));
				qaVO.setQa_reply_cont(rs.getString("qa_reply_cont"));
				qaVO.setQa_reply_date(rs.getDate("qa_reply_date"));
				list.add(qaVO); // Store the row in the list
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

		QaJDBCDAO dao = new QaJDBCDAO();

		// 新增
//		QaVO qaVO = new QaVO();
//		qaVO.setProd_no("P1000000001");
//		qaVO.setMem_ac("mamabeak");
//		qaVO.setQa_cont("請問有打折嗎");
//		qaVO.setQa_date(Date.valueOf("2005-01-01"));
//		qaVO.setQa_reply_cont("請看商品說明");
//		qaVO.setQa_reply_date(Date.valueOf("2017-01-01"));
//		dao.insert(qaVO);
//		
//
//		// 修改
//		QaVO qaVO2 = new QaVO();
//		qaVO2.setQa_no(key);
//		qaVO2.setProd_no("P1000000001");
//		qaVO2.setMem_ac("mamabeak");
//		qaVO2.setQa_cont("sdaf");
//		qaVO2.setQa_date(Date.valueOf("2005-01-21"));
//		qaVO2.setQa_reply_cont("fffffffffffff");
//		qaVO2.setQa_reply_date(Date.valueOf(java.time.LocalDate.now()));
//		dao.update(qaVO2);



		// 查詢
//		QaVO qaVO3 = dao.findByPrimaryKey(key);
//		System.out.print(qaVO3.getQa_no() + ",");
//		System.out.print(qaVO3.getProd_no() + ",");
//		System.out.print(qaVO3.getMem_ac() + ",");
//		System.out.print(qaVO3.getQa_cont() + ",");
//		System.out.print(qaVO3.getQa_date() + ",");
//		System.out.print(qaVO3.getQa_reply_cont() + ",");
//		System.out.println(qaVO3.getQa_reply_date());
//		System.out.println("---------------------");
//		
//		// 刪除
//		dao.delete(key);

		// 查詢
		List<QaVO> list = dao.getAll();
		for (QaVO aqaVO : list) {
			System.out.print(aqaVO.getQa_no() + ",");
			System.out.print(aqaVO.getProd_no() + ",");
			System.out.print(aqaVO.getMem_ac() + ",");
			System.out.print(aqaVO.getQa_cont() + ",");
			System.out.print(aqaVO.getQa_date() + ",");
			System.out.print(aqaVO.getQa_reply_cont() + ",");
			System.out.println(aqaVO.getQa_reply_date());
			System.out.println("---------------");
		}
		
		// 查詢BybProd
		List<QaVO> list2 = dao.getByProd("P1000000001");
		for (QaVO aqaVO : list2) {
			System.out.print(aqaVO.getQa_no() + ",");
			System.out.print(aqaVO.getProd_no() + ",");
			System.out.print(aqaVO.getMem_ac() + ",");
			System.out.print(aqaVO.getQa_cont() + ",");
			System.out.print(aqaVO.getQa_date() + ",");
			System.out.print(aqaVO.getQa_reply_cont() + ",");
			System.out.println(aqaVO.getQa_reply_date());
			System.out.println("*********");
		}
	}

}
