package com.review.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;



public class ReviewJDBCDAO implements ReviewDAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "ba103g4";
	String passwd = "123456";
	
	static String key;

	private static final String INSERT_STMT = 
		"INSERT INTO REVIEW (rev_no, ord_no, prod_no, prod_score, use_way, rev_cont, rev_date) VALUES ('R'||REV_NO_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT rev_no, ord_no, prod_no, prod_score, use_way, rev_cont, to_char(rev_date,'yyyy-mm-dd') rev_date FROM REVIEW order by rev_no";
	private static final String GET_ONE_STMT = 
		"SELECT rev_no, ord_no, prod_no, prod_score, use_way, rev_cont, to_char(rev_date,'yyyy-mm-dd') rev_date FROM REVIEW where rev_no = ?";
	private static final String DELETE = 
		"DELETE FROM REVIEW where rev_no = ?";
	private static final String UPDATE = 
		"UPDATE REVIEW set ord_no=?, prod_no=?, prod_score=?, use_way=?, rev_cont=?, rev_date=? where rev_no = ?";
	private static final String GET_COUNT_BY_PROD =
		"SELECT count(*) FROM review WHERE PROD_NO = ?";
	private static final String GET_SCORE_BY_PROD =
		"select avg(prod_score) from review where prod_no = ?";
	private static final String GET_VO_BY_PROD = 
		"SELECT rev_no, ord_no, prod_no, prod_score, use_way, rev_cont, to_char(rev_date,'yyyy-mm-dd') rev_date FROM REVIEW where prod_no = ? order by rev_no";
	private static final String GET_VO_BY_ORD_PROD = 
			"SELECT * FROM REVIEW where ord_no =? and prod_no = ?";
	
	
	@Override
	public void insert(ReviewVO reviewVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String pk[] = {"rev_no"};

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT, pk);

			pstmt.setString(1, reviewVO.getOrd_no());
			pstmt.setString(2, reviewVO.getProd_no());
			pstmt.setInt(3, reviewVO.getProd_score());
			pstmt.setString(4, reviewVO.getUse_way());
			pstmt.setString(5, reviewVO.getRev_cont());
			pstmt.setDate(6, reviewVO.getRev_date());

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
	public void update(ReviewVO reviewVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(7, reviewVO.getRev_no());
			pstmt.setString(1, reviewVO.getOrd_no());
			pstmt.setString(2, reviewVO.getProd_no());
			pstmt.setInt(3, reviewVO.getProd_score());
			pstmt.setString(4, reviewVO.getUse_way());
			pstmt.setString(5, reviewVO.getRev_cont());
			pstmt.setDate(6, reviewVO.getRev_date());

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
	public void delete(String rev_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, rev_no);

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
	public ReviewVO findByPrimaryKey(String rev_no) {
		ReviewVO reviewVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rev_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				reviewVO = new ReviewVO();
				reviewVO.setRev_no(rs.getString("rev_no"));
				reviewVO.setOrd_no(rs.getString("ord_no"));
				reviewVO.setProd_no(rs.getString("prod_no"));
				reviewVO.setProd_score(rs.getInt("prod_score"));
				reviewVO.setUse_way(rs.getString("use_way"));
				reviewVO.setRev_cont(rs.getString("rev_cont"));
				reviewVO.setRev_date(rs.getDate("rev_date"));
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
		return reviewVO;
	}
	
	@Override
	public ReviewVO findByOrdProd(String ord_no, String prod_no) {
		ReviewVO reviewVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_VO_BY_ORD_PROD);

			pstmt.setString(1, ord_no);
			pstmt.setString(2, prod_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				reviewVO = new ReviewVO();
				reviewVO.setRev_no(rs.getString("rev_no"));
				reviewVO.setOrd_no(rs.getString("ord_no"));
				reviewVO.setProd_no(rs.getString("prod_no"));
				reviewVO.setProd_score(rs.getInt("prod_score"));
				reviewVO.setUse_way(rs.getString("use_way"));
				reviewVO.setRev_cont(rs.getString("rev_cont"));
				reviewVO.setRev_date(rs.getDate("rev_date"));
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
		return reviewVO;
	}

	@Override
	public List<ReviewVO> getAll() {
		
		List<ReviewVO> list = new ArrayList<ReviewVO>();
		ReviewVO reviewVO = null;

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
				reviewVO = new ReviewVO();
				reviewVO.setRev_no(rs.getString("rev_no"));
				reviewVO.setOrd_no(rs.getString("ord_no"));
				reviewVO.setProd_no(rs.getString("prod_no"));
				reviewVO.setProd_score(rs.getInt("prod_score"));
				reviewVO.setUse_way(rs.getString("use_way"));
				reviewVO.setRev_cont(rs.getString("rev_cont"));
				reviewVO.setRev_date(rs.getDate("rev_date"));
				list.add(reviewVO); // Store the row in the list
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
	public List<ReviewVO> getByProd(String prod_no) {
		
		List<ReviewVO> list = new ArrayList<ReviewVO>();
		ReviewVO reviewVO = null;
	
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
				reviewVO = new ReviewVO();
				reviewVO.setRev_no(rs.getString("rev_no"));
				reviewVO.setOrd_no(rs.getString("ord_no"));
				reviewVO.setProd_no(rs.getString("prod_no"));
				reviewVO.setProd_score(rs.getInt("prod_score"));
				reviewVO.setUse_way(rs.getString("use_way"));
				reviewVO.setRev_cont(rs.getString("rev_cont"));
				reviewVO.setRev_date(rs.getDate("rev_date"));
				list.add(reviewVO); // Store the row in the list
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
	public int countByProd(String prod_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Integer count = 0;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_COUNT_BY_PROD);		
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				count = rs.getInt(1);
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
		return count;
	}
	
	
	
	@Override
	public Double scoreByProd(String prod_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Double score = 0.0;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_SCORE_BY_PROD);		
			pstmt.setString(1, prod_no);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				score = rs.getDouble(1);
				DecimalFormat formatter = new DecimalFormat("#.#");
				score = Double.parseDouble(formatter.format(score));
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
		return score;
	}
	
	
	

	public static void main(String[] args) {

		ReviewJDBCDAO dao = new ReviewJDBCDAO();

		// 新增
//		ReviewVO reviewVO1 = new ReviewVO();
//		reviewVO1.setOrd_no("O1000000001");
//		reviewVO1.setProd_no("P1000000001");
//		reviewVO1.setProd_score(5);
//		reviewVO1.setUse_way("25,250,90,60");
//		reviewVO1.setRev_cont("不錯喝");
//		reviewVO1.setRev_date(Date.valueOf("2005-01-01"));
//		dao.insert(reviewVO1);
		

		// 修改
//		ReviewVO reviewVO2 = new ReviewVO();
//		reviewVO2.setRev_no(key);
//		reviewVO2.setOrd_no("O1000000001");
//		reviewVO2.setProd_no("P1000000001");
//		reviewVO2.setProd_score(2);
//		reviewVO2.setUse_way("25,250,87,60");
//		reviewVO2.setRev_cont("不錯喝!!");
//		reviewVO2.setRev_date(Date.valueOf(java.time.LocalDate.now()));
//		
//		dao.update(reviewVO2);



		// 查詢
//		ReviewVO reviewVO3 = dao.findByPrimaryKey(key);
//		System.out.print(reviewVO3.getRev_no() + ",");
//		System.out.print(reviewVO3.getOrd_no() + ",");
//		System.out.print(reviewVO3.getProd_no() + ",");
//		System.out.print(reviewVO3.getProd_score() + ",");
//		System.out.print(reviewVO3.getUse_way() + ",");
//		System.out.print(reviewVO3.getRev_cont() + ",");
//		System.out.println(reviewVO3.getRev_date());
//		System.out.println("---------------------");
		
		// 刪除
//		dao.delete(key);

		// 查詢
//		List<ReviewVO> list = dao.getAll();
//		for (ReviewVO aReviewVO : list) {
//			System.out.print(aReviewVO.getRev_no() + ",");
//			System.out.print(aReviewVO.getOrd_no() + ",");
//			System.out.print(aReviewVO.getProd_no() + ",");
//			System.out.print(aReviewVO.getProd_score() + ",");
//			System.out.print(aReviewVO.getUse_way() + ",");
//			System.out.print(aReviewVO.getRev_cont() + ",");
//			System.out.println(aReviewVO.getRev_date());
//			System.out.println();
//		}
		
		// 查詢by Prod_no
//		List<ReviewVO> list = dao.getByProd("P1000000001");
//		for (ReviewVO aReviewVO : list) {
//			System.out.print(aReviewVO.getRev_no() + ",");
//			System.out.print(aReviewVO.getOrd_no() + ",");
//			System.out.print(aReviewVO.getProd_no() + ",");
//			System.out.print(aReviewVO.getProd_score() + ",");
//			System.out.print(aReviewVO.getUse_way() + ",");
//			System.out.print(aReviewVO.getRev_cont() + ",");
//			System.out.println(aReviewVO.getRev_date());
//			System.out.println();
//		}
		
		//查心得分享人數
//		System.out.println(dao.countByProd("P1000000002"));
		
		//查分數
//		System.out.println(dao.scoreByProd("P1000000002"));
		
		// 查詢
		ReviewVO reviewVO3 = dao.findByOrdProd("O1000000010", "P1000000004");
		System.out.print(reviewVO3.getRev_no() + ",");
		System.out.print(reviewVO3.getOrd_no() + ",");
		System.out.print(reviewVO3.getProd_no() + ",");
		System.out.print(reviewVO3.getProd_score() + ",");
		System.out.print(reviewVO3.getUse_way() + ",");
		System.out.print(reviewVO3.getRev_cont() + ",");
		System.out.println(reviewVO3.getRev_date());
		System.out.println("---------------------");
	}



}
